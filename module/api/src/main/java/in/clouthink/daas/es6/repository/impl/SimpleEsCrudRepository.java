package in.clouthink.daas.es6.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.clouthink.daas.es6.exception.Catching;
import in.clouthink.daas.es6.exception.DataNotFoundException;
import in.clouthink.daas.es6.exception.IncorrectResultSizeDataAccessException;
import in.clouthink.daas.es6.model.MutableIdentityProvider;
import in.clouthink.daas.es6.repository.EsCrudRepository;
import in.clouthink.daas.es6.repository.EsTemplate;
import in.clouthink.daas.es6.repository.Fields;
import in.clouthink.daas.es6.repository.SearchSourceBuilderBuilder;
import in.clouthink.daas.es6.repository.metadata.IndexMetadata;
import in.clouthink.daas.es6.repository.metadata.IndexMetadataParser;
import in.clouthink.daas.es6.repository.page.PageableSearchRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.ParameterizedType;
import java.util.*;

public abstract class SimpleEsCrudRepository<T extends MutableIdentityProvider<String>, R extends PageableSearchRequest>
        extends EsTemplate
        implements EsCrudRepository<T, R> {

    protected final Log logger = LogFactory.getLog(getClass());

    private final RestHighLevelClient restHighLevelClient;
    private final ObjectMapper objectMapper;

    private RequestOptions requestOptions = RequestOptions.DEFAULT;

    private Class<T> entityClass;

    private IndexMetadata indexMetadata;

    public SimpleEsCrudRepository(RestHighLevelClient restHighLevelClient, ObjectMapper objectMapper) {
        this.restHighLevelClient = restHighLevelClient;
        this.objectMapper = objectMapper;
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.indexMetadata = IndexMetadataParser.parseIndexMetadata(this.entityClass);
    }

    @Override
    public RestHighLevelClient getRestHighLevelClient() {
        return restHighLevelClient;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Override
    public RequestOptions getRequestOptions() {
        return requestOptions;
    }

    public void setRequestOptions(RequestOptions requestOptions) {
        this.requestOptions = requestOptions;
    }

    protected String getEsIndex() {
        return this.indexMetadata.getIndexName();
    }

    protected Class<T> getJavaType() {
        return this.entityClass;
    }

    /**
     * Helper method to empty index
     */
    public void createIndex() {
        createIndex(getEsIndex());
    }

    /**
     * Helper method to empty buildFrom
     */
    public void createType() {
        createType(getEsIndex(), ES_DEFAULT_TYPE, XContentBuilderBuilder.buildFrom(getJavaType()));
    }

    @Override
    public String save(final T data) {
        return Catching.around(() -> doSave(data));
    }

    protected String doSave(T data) throws Exception {
        if (indexMetadata.isGenerateUuid()) {
            data.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        }
        Map<String, Object> dataMap = getObjectMapper().convertValue(data, Map.class);
        IndexRequest indexRequest = new IndexRequest(getEsIndex(), ES_DEFAULT_TYPE, data.getId()).source(dataMap);
        //TODO change to async write
        IndexResponse indexResponse = getRestHighLevelClient().index(indexRequest, getRequestOptions());
        logger.debug(String.format("save %s result : %s",
                                   getObjectMapper().writeValueAsString(data),
                                   indexResponse.getResult()));
        data.setId(indexResponse.getId());
        return data.getId();
    }

    @Override
    public void saveBulk(final T... records) {
        Catching.around(() -> doSaveBulk(records));
    }

    protected void doSaveBulk(T... records) throws Exception {
        BulkRequest bulkRequest = new BulkRequest();

        for (T item : records) {
            if (indexMetadata.isGenerateUuid()) {
                item.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            }
            Map<String, Object> dataMap = getObjectMapper().convertValue(item, Map.class);
            //TODO change to async write
            IndexRequest indexRequest = new IndexRequest(getEsIndex(), item.getId()).source(dataMap);
            bulkRequest.add(indexRequest);
        }

        getRestHighLevelClient().bulk(bulkRequest, getRequestOptions());
    }


    @Override
    public void update(String id, T data) {
        Catching.around(() -> doUpdate(id, data));
    }

    protected void doUpdate(String id, T data) throws Exception {
        T exists = getOne(id);
        if (exists == null) {
            throw new DataNotFoundException(String.format("The data[id=%s] not found.", id));
        }
        UpdateRequest updateRequest = new UpdateRequest(getEsIndex(),
                                                        ES_DEFAULT_TYPE,
                                                        id).fetchSource(true);    // Fetch Object after its update
        String dataJson = getObjectMapper().writeValueAsString(data);
        updateRequest.doc(dataJson, XContentType.JSON);
        //TODO change to async write
        UpdateResponse updateResponse = getRestHighLevelClient().update(updateRequest, getRequestOptions());
        logger.debug(String.format("update %s result : %s", dataJson, updateResponse.getResult()));
    }

    @Override
    public void delete(String id) {
        Catching.around(() -> doDelete(id));
    }

    public void doDelete(String id) throws Exception {
        T exists = getOne(id);
        if (exists == null) {
            return;
        }

        DeleteRequest deleteRequest = new DeleteRequest(getEsIndex(), ES_DEFAULT_TYPE, id);
        DeleteResponse deleteResponse = getRestHighLevelClient().delete(deleteRequest, getRequestOptions());
        logger.debug(String.format("delete %s result : %s", id, deleteResponse.getResult()));
    }

    @Override
    public T getOne(String id) {
        return Catching.around(() -> doGetOne(id));
    }

    protected T doGetOne(String id) throws Exception {
        GetRequest getRequest = new GetRequest(getEsIndex(), ES_DEFAULT_TYPE, id);
        GetResponse getResponse = getRestHighLevelClient().get(getRequest, getRequestOptions());
        if (!getResponse.isExists()) {
            return null;
        }
        Map<String, Object> resultMap = getResponse.getSource();
        T result = getObjectMapper().convertValue(resultMap, getJavaType());
        result.setId(getResponse.getId());
        return result;
    }

    @Override
    public Page<T> search(R searchRequest) {
        return Catching.around(() -> doSearch(searchRequest, Fields.empty()));
    }

    @Override
    public Page<T> search(R searchRequest, Fields fields) {
        return Catching.around(() -> doSearch(searchRequest, fields));
    }

    protected Page<T> doSearch(R request, Fields fields) throws Exception {
        SearchRequest searchRequest = new SearchRequest(getEsIndex());
        SearchSourceBuilder searchSourceBuilder = buildSearchSource(request, fields);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = getRestHighLevelClient().search(searchRequest, getRequestOptions());

        SearchHits searchResponseHits = searchResponse.getHits();
        List<T> searchResultList = convertEsResult(searchResponseHits);

        return new PageImpl<>(searchResultList, request.resolvePageable(), searchResponseHits.totalHits);
    }

    @Override
    public Page<T> getAll(PageableSearchRequest pageableSearchRequest) {
        return Catching.around(() -> doGetAll(pageableSearchRequest, Fields.empty()));
    }

    @Override
    public Page<T> getAll(PageableSearchRequest pageableSearchRequest, Fields fields) {
        return Catching.around(() -> doGetAll(pageableSearchRequest, fields));
    }

    protected Page<T> doGetAll(PageableSearchRequest request, Fields fields) throws Exception {
        //handle search
        SearchRequest searchRequest = new SearchRequest(getEsIndex());

        Pageable pageable = request.resolvePageable();
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilderBuilder.build(request.resolvePageable(),
                                                                                   fields);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = getRestHighLevelClient().search(searchRequest, getRequestOptions());

        //handle result
        SearchHits searchResponseHits = searchResponse.getHits();
        List<T> searchResultList = convertEsResult(searchResponseHits);

        return new PageImpl<>(searchResultList, pageable, searchResponseHits.totalHits);
    }

    public T getFirst(SearchSourceBuilder searchSourceBuilder) {
        return Catching.around(() -> doGetFirst(searchSourceBuilder));
    }

    protected T doGetFirst(SearchSourceBuilder searchSourceBuilder) throws Exception {
        //handle search
        SearchRequest searchRequest = new SearchRequest(getEsIndex());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = getRestHighLevelClient().search(searchRequest, getRequestOptions());

        //handle result
        SearchHits searchResponseHits = searchResponse.getHits();
        if (searchResponseHits.getTotalHits() == 0) {
            return null;
        }

        SearchHit searchHit = searchResponseHits.getAt(0);
        T result = getObjectMapper().convertValue(searchHit.getSourceAsMap(), getJavaType());
        result.setId(searchHit.getId());
        return result;
    }

    public T getOne(SearchSourceBuilder searchSourceBuilder) {
        return Catching.around(() -> doGetOne(searchSourceBuilder));
    }

    protected T doGetOne(SearchSourceBuilder searchSourceBuilder) throws Exception {
        //handle search
        SearchRequest searchRequest = new SearchRequest(getEsIndex());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = getRestHighLevelClient().search(searchRequest, getRequestOptions());

        //handle result
        SearchHits searchResponseHits = searchResponse.getHits();
        if (searchResponseHits.getTotalHits() == 0) {
            return null;
        }

        //only 1 record expected
        if (searchResponseHits.getTotalHits() > 1) {
            throw new IncorrectResultSizeDataAccessException(1, searchResponseHits.getTotalHits());
        }

        SearchHit searchHit = searchResponseHits.getAt(0);
        T result = getObjectMapper().convertValue(searchHit.getSourceAsMap(), getJavaType());
        result.setId(searchHit.getId());
        return result;
    }

    protected List<T> convertEsResult(SearchHits searchHits) {
        List<T> searchResultList = new ArrayList<>();
        SearchHit[] hits = searchHits.getHits();
        if (hits.length > 0) {
            Arrays.stream(hits).forEach(
                    item ->
                    {
                        T resultItem = getObjectMapper().convertValue(item.getSourceAsMap(), getJavaType());
                        resultItem.setId(item.getId());
                        searchResultList.add(resultItem);
                    }
            );
        }
        return searchResultList;
    }

    /**
     * By default , only handle the pageable part
     *
     * @param request
     * @param fields
     * @return
     */
    protected SearchSourceBuilder buildSearchSource(R request, Fields fields) {
        return SearchSourceBuilderBuilder.build(request.resolvePageable(),
                                                fields);
    }

}

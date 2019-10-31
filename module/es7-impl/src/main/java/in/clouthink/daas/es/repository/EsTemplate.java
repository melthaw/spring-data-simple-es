package in.clouthink.daas.es.repository;

import in.clouthink.daas.es.exception.Catching;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.xcontent.XContentBuilder;

public abstract class EsTemplate {

    private static final Log log = LogFactory.getLog(EsTemplate.class);

    public static final String ES_DEFAULT_TYPE = "_doc";

    public abstract RestHighLevelClient getRestHighLevelClient();

    public abstract RequestOptions getRequestOptions();

    public GetIndexResponse getIndex(final String indexName) {
        return Catching.around(() -> doGetIndex(indexName));
    }

    protected GetIndexResponse doGetIndex(final String indexName) throws Exception {
        return getRestHighLevelClient().indices().get(new GetIndexRequest(indexName), getRequestOptions());
    }

    public boolean indexExists(final String indexName) {
        boolean result = Catching.around(() -> doIndexExists(indexName));
        log.debug(String.format("index:%s  existed:%s", indexName, result));
        return result;
    }

    protected boolean doIndexExists(final String indexName) throws Exception {
        return getRestHighLevelClient().indices().exists(new GetIndexRequest(indexName), getRequestOptions());
    }

    public void createIndex(final String indexName) {
        Catching.around(() -> doCreateIndex(indexName));
    }

    protected void doCreateIndex(final String indexName) throws Exception {
        if (!indexExists(indexName)) {
            getRestHighLevelClient().indices().create(new CreateIndexRequest(indexName), getRequestOptions());
            log.debug(String.format("index:%s is created", indexName));
        }
    }

    public MappingMetaData typeMappings(final String indexName, final String typeName) {
        return Catching.around(() -> doTypeMappings(indexName, typeName));
    }

    protected MappingMetaData doTypeMappings(final String indexName, final String typeName) throws Exception {
        GetMappingsRequest getMappingsRequest = new GetMappingsRequest();
        getMappingsRequest.indices(indexName);
        GetMappingsResponse getMappingResponse = getRestHighLevelClient().indices()
                                                                         .getMapping(getMappingsRequest,
                                                                                     getRequestOptions());
        return getMappingResponse.mappings().get(typeName);
    }

    public boolean typeExists(final String indexName, final String typeName) {
        boolean result = Catching.around(() -> doTypeExists(indexName, typeName));
        log.debug(String.format("index:%s  type:%s  existed:%s", indexName, typeName, result));
        return result;
    }

    protected boolean doTypeExists(final String indexName, final String typeName) throws Exception {
        GetMappingsRequest getMappingsRequest = new GetMappingsRequest();
        getMappingsRequest.indices(indexName);
        GetMappingsResponse getMappingResponse = getRestHighLevelClient().indices()
                                                                         .getMapping(getMappingsRequest,
                                                                                     getRequestOptions());
        MappingMetaData mappingMetaData = getMappingResponse.mappings().get(indexName);
        if (mappingMetaData == null) {
            return false;
        }
        return mappingMetaData.type().equals(typeName) && !mappingMetaData.sourceAsMap().isEmpty();
    }

    public void createType(final String indexName, final String typeName, final XContentBuilder contentBuilder) {
        Catching.around(() -> doCreateType(indexName, typeName, contentBuilder));
    }

    protected void doCreateType(final String indexName, final String typeName, final XContentBuilder contentBuilder)
            throws Exception {
        if (!typeExists(indexName, typeName)) {
            PutMappingRequest putMappingRequest = new PutMappingRequest(indexName);
            putMappingRequest.source(contentBuilder);
            AcknowledgedResponse acknowledgedResponse = getRestHighLevelClient().indices()
                                                                                .putMapping(putMappingRequest,
                                                                                            getRequestOptions());
            log.debug(String.format("index:%s  type:%s  created:%s",
                                    indexName,
                                    typeName,
                                    acknowledgedResponse.isAcknowledged()));
        }
    }

}

package in.clouthink.daas.es6.test.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.clouthink.daas.es6.repository.Fields;
import in.clouthink.daas.es6.repository.SearchSourceBuilderBuilder;
import in.clouthink.daas.es6.repository.impl.SimpleEsCrudRepository;
import in.clouthink.daas.es6.test.model.HttpRequestEvent;
import in.clouthink.daas.es6.test.repository.HttpRequestEventRepository;
import in.clouthink.daas.es6.test.request.HttpRequestEventSearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("httpRequestEventRepository")
public class HttpRequestEventRepositoryImpl
        extends SimpleEsCrudRepository<HttpRequestEvent, HttpRequestEventSearchRequest>
        implements HttpRequestEventRepository {

    public HttpRequestEventRepositoryImpl(RestHighLevelClient restHighLevelClient, ObjectMapper objectMapper) {
        super(restHighLevelClient, objectMapper);
    }

    @Override
    protected SearchSourceBuilder buildSearchSource(HttpRequestEventSearchRequest request, Fields fields) {
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilderBuilder.build(request.resolvePageable(),
                                                                                   fields);

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(request.getRealm())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("realm", request.getRealm()));
        }
        if (!StringUtils.isEmpty(request.getRequestUri())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("requestUri", request.getRequestUri()));
        }
        if (!StringUtils.isEmpty(request.getRealIp())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("realIp", request.getRealIp()));
        }
        if (!StringUtils.isEmpty(request.getRemoteAddr())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("remoteAddr", request.getRemoteAddr()));
        }

        return searchSourceBuilder.query(boolQueryBuilder);
    }

}

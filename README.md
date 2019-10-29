
> Simple Es6 Api with Spring Data Style

# Api Guide

## Define Domain Model (Mapping to ES Index)

> Available annotations is under package `in.clouthink.daas.es6.annotation` 

```java
@Index("daas_es6_http_request_events")
public class HttpRequestEvent implements MutableIdentityProvider<String> {

    @Id
    @JsonIgnore
    private String id;

    @Keyword
    private String realm;

    @Keyword
    private String requestUrl;

    @Keyword
    private String requestUri;

    @Keyword
    private String requestMethod;

    @Keyword
    private String remoteAddr;

    @Ip
    private String realIp;

    @Keyword
    private String userAgent;

    private String requestBody;

    private Date requestAt;

    @Keyword
    private String authorization;

    @Keyword
    private String username;

    @Keyword
    private String responseStatus;

    private String responseBody;

    private String exception;

    long timeConsuming;

}
```

## Define the Repository 

> Extends `EsCrudRepository` 

```java
public interface HttpRequestEventRepository extends EsCrudRepository<HttpRequestEvent, HttpRequestEventSearchRequest> {
}
```

> Extends `SimpleEsCrudRepository` and override the buildSearchSource method 

```java
@Service
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
        //place your implementation
        return searchSourceBuilder;
    }
}    
```

## Start up

* Setup ElasticSearch connection in application.yml

```yml
daas.es6:
  autoCreateIndex: ${ES_AUTO_CREATE_INDEX:true}
  host: ${ES_HOST:127.0.0.1}
  port: ${ES_PORT:9200}
  proto: ${ES_PROTO:http}
```

* Setup Spring Application Configuration

```

```


# Appendix - Build the source

Install all (skip test)

```shell
mvn clean install -Dmaven.test.skip=true
```

Build all

```shell
mvn clean package
```

Build single project

* api

```shell
mvn -pl module/api clean package
# or
mvn --projects module/api clean package
```

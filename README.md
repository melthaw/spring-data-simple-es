# Introduction

> Spring Data Style Simplified ElasticSearch Api with ElasticSearch6 and ElasticSearch7 compatibility.


# ElasticSearch version compatibility

Now ElasticSearch6 and ElasticSearch7 are supported , but you need choose the corresponding version by manual.

**ElasticSearch6**

```xml

	<dependency>
		<groupId>in.clouthink.daas</groupId>
		<artifactId>daas-es-api</artifactId>
		<version>1.0.2</version>
	</dependency>
	

	<dependency>
		<groupId>in.clouthink.daas</groupId>
		<artifactId>daas-es-6</artifactId>
		<version>1.0.2</version>
	</dependency>

```

And the elasticsearch java client v6 will be imported automatically.

> * org.elasticsearch:elasticsearch:6.7.2
> * org.elasticsearch.client:elasticsearch-rest-high-level-client:6.7.2
> * org.elasticsearch.client:elasticsearch-rest-client:6.7.2
> * org.elasticsearch.client:elasticsearch-rest-client-sniffer:6.7.2


**ElasticSearch7**

```xml

	<dependency>
		<groupId>in.clouthink.daas</groupId>
		<artifactId>daas-es-api</artifactId>
		<version>1.0.2</version>
	</dependency>

	<dependency>
		<groupId>in.clouthink.daas</groupId>
		<artifactId>daas-es-7</artifactId>
		<version>1.0.2</version>
	</dependency>

```

And the elasticsearch java client v7 will be imported automatically.

> * org.elasticsearch:elasticsearch:7.2.1
> * org.elasticsearch.client:elasticsearch-rest-high-level-client:7.2.1
> * org.elasticsearch.client:elasticsearch-rest-client:7.2.1
> * org.elasticsearch.client:elasticsearch-rest-client-sniffer:7.2.1


# Latest Release

So far the following version is available 

| group id |artifact id | latest version |
|---|---|---|
| in.clouthink.daas | daas-es-api | [1.0.2](https://mvnrepository.com/artifact/in.clouthink.daas/daas-es-api/1.0.2)
| in.clouthink.daas | daas-es-6 | [1.0.2](https://mvnrepository.com/artifact/in.clouthink.daas/daas-es-6/1.0.2)
| in.clouthink.daas | daas-es-7 | [1.0.2](https://mvnrepository.com/artifact/in.clouthink.daas/daas-es-7/1.0.2)

  
# Api Guide

## Quick Start

* Available annotations is under package `in.clouthink.daas.es.annotation` 
* Very useful helper class `in.clouthink.daas.es.repository.EsTemplate` 
* The core Interface is `in.clouthink.daas.es.repository.EsCrudRepository`


```java
public interface EsCrudRepository<T extends MutableIdentityProvider<String>, R extends PageableSearchRequest> {

    T getOne(String id);

    Page<T> getAll(PageableSearchRequest pageableSearchRequest);

    Page<T> getAll(PageableSearchRequest pageableSearchRequest, Fields fields);

    Page<T> search(R searchRequest);

    Page<T> search(R searchRequest, Fields fields);

    String save(T record);

    void saveBulk(T... records);

    void update(String id, T record);

    void delete(String id);

}

```


## Define Domain Model (Mapping to ElasticSearch Index)


For example:

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
        //Place your implementation here.
        //See module/test/src/main/java/in/clouthink/daas/es6/test/repository/impl/HttpRequestEventRepositoryImpl.java
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

```java
@SpringBootApplication
@EnableConfigurationProperties(Es6Properties.class)
public class Es6TestApplication {

    @Autowired
    private Es6Properties es6Properties;

    @Bean(destroyMethod = "close")
    @ConditionalOnMissingBean(RestHighLevelClient.class)
    public RestHighLevelClient client() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(es6Properties.getHost(),
                                     es6Properties.getPort(),
                                     es6Properties.getProto())));
    }

    public static void main(String[] args) {
        SpringApplication.run(new Object[]{Es6TestApplication.class}, args);
    }

}
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

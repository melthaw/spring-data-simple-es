package in.clouthink.daas.es.test.repository;


import in.clouthink.daas.es.repository.EsCrudRepository;
import in.clouthink.daas.es.test.model.HttpRequestEvent;
import in.clouthink.daas.es.test.request.HttpRequestEventSearchRequest;

public interface HttpRequestEventRepository extends EsCrudRepository<HttpRequestEvent, HttpRequestEventSearchRequest> {

}

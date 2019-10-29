package in.clouthink.daas.es6.test.repository;


import in.clouthink.daas.es6.repository.EsCrudRepository;
import in.clouthink.daas.es6.test.model.HttpRequestEvent;
import in.clouthink.daas.es6.test.request.HttpRequestEventSearchRequest;

public interface HttpRequestEventRepository extends EsCrudRepository<HttpRequestEvent, HttpRequestEventSearchRequest> {
}

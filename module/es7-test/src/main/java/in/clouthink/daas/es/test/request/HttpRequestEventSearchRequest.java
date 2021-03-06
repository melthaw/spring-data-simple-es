package in.clouthink.daas.es.test.request;

import in.clouthink.daas.es.repository.page.PageableSearchRequest;

public interface HttpRequestEventSearchRequest extends PageableSearchRequest {

    String getRealm();

    String getRequestUri();

    String getRealIp();

    String getRemoteAddr();

}

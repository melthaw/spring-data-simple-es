package in.clouthink.daas.es6.test.request;

import in.clouthink.daas.es6.repository.page.PageableSearchRequest;

public interface HttpRequestEventSearchRequest extends PageableSearchRequest {

    String getRealm();

    String getRequestUri();

    String getRealIp();

    String getRemoteAddr();

}

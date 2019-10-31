package in.clouthink.daas.es.test.request;

import in.clouthink.daas.es.repository.page.DefaultPageableSearchRequest;

public class DefaultHttpRequestEventSearchRequest extends DefaultPageableSearchRequest
        implements HttpRequestEventSearchRequest {

    private String realm;

    private String requestUri;

    private String realIp;

    private String remoteAddr;

    @Override
    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    @Override
    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    @Override
    public String getRealIp() {
        return realIp;
    }

    public void setRealIp(String realIp) {
        this.realIp = realIp;
    }

    @Override
    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }
}

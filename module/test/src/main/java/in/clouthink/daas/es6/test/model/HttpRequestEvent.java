package in.clouthink.daas.es6.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.clouthink.daas.es6.annotation.Id;
import in.clouthink.daas.es6.annotation.Index;
import in.clouthink.daas.es6.annotation.Ip;
import in.clouthink.daas.es6.annotation.Keyword;
import in.clouthink.daas.es6.model.MutableIdentityProvider;

import java.util.Date;

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


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }


    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }


    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }


    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }


    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }


    public String getRealIp() {
        return realIp;
    }

    public void setRealIp(String realIp) {
        this.realIp = realIp;
    }


    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }


    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }


    public Date getRequestAt() {
        return requestAt;
    }

    public void setRequestAt(Date requestAt) {
        this.requestAt = requestAt;
    }


    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }


    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }


    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }


    public long getTimeConsuming() {
        return timeConsuming;
    }

    public void setTimeConsuming(long timeConsuming) {
        this.timeConsuming = timeConsuming;
    }
}

package in.clouthink.daas.es6.test.suites;

import in.clouthink.daas.es6.repository.Fields;
import in.clouthink.daas.es6.repository.page.DefaultPageableSearchRequest;
import in.clouthink.daas.es6.test.common.AbstractTest;
import in.clouthink.daas.es6.test.model.HttpRequestEvent;
import in.clouthink.daas.es6.test.repository.HttpRequestEventRepository;
import in.clouthink.daas.es6.test.request.DefaultHttpRequestEventSearchRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Date;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HttpRequestEventRepositoryTest extends AbstractTest {

    @Autowired
    private HttpRequestEventRepository eventRepository;


    //    @Test
    public void testCreateHttpRequestEvent() {

        for (int i = 0; i < 100; i++) {
            HttpRequestEvent newData = new HttpRequestEvent();
            newData.setRealm("daas-es6");
            newData.setRemoteAddr("mock-remote-addr");
            newData.setRealIp("127.0.0.1");
            newData.setUserAgent("mock-user-agent");
            newData.setRequestMethod("GET");
            newData.setRequestBody("<mock-body/>" + RandomStringUtils.random(100));
            newData.setRequestAt(new Date());
            newData.setResponseStatus("404");
            newData.setResponseBody("Not found");
            newData.setAuthorization("bearer " + RandomStringUtils.random(20));
            newData.setUsername("admin" + RandomStringUtils.random(2));
            newData.setTimeConsuming(System.currentTimeMillis());

            String id = eventRepository.save(newData);
        }

    }

    @Test
    public void testPageableSearch() {
        //Now test search with fields specified
        Page<HttpRequestEvent> eventPage = eventRepository.getAll(new DefaultPageableSearchRequest(),
                                                                  Fields.empty()
                                                                        .excludes("responseBody"));

        System.out.println(eventPage.getTotalElements());
    }

    //    @Test
    public void testHttpRequestEventRepo() {
        Page<HttpRequestEvent> eventPage = eventRepository.search(new DefaultHttpRequestEventSearchRequest());
        long totalElements = eventPage.getTotalElements();

        HttpRequestEvent newData = new HttpRequestEvent();
        newData.setRealm("daas-es6");
        newData.setRemoteAddr("mock-remote-addr");
        newData.setRealIp("127.0.0.1");
        newData.setUserAgent("mock-user-agent");
        newData.setRequestMethod("GET");
        newData.setRequestBody("<mock-body/>");
        newData.setRequestAt(new Date());
        newData.setResponseStatus("404");
        newData.setResponseBody("Not found");
        newData.setAuthorization("bearer xxxxx");
        newData.setUsername("admin");
        newData.setTimeConsuming(System.currentTimeMillis());

        String id = eventRepository.save(newData);

        HttpRequestEvent foundData = eventRepository.getOne(id);

        Assert.assertEquals(newData.getRealm(), foundData.getRealm());
        Assert.assertEquals(newData.getRealIp(), foundData.getRealIp());
        Assert.assertEquals(newData.getRemoteAddr(), foundData.getRemoteAddr());
        Assert.assertEquals(newData.getRequestBody(), foundData.getRequestBody());
        Assert.assertEquals(newData.getRequestMethod(), foundData.getRequestMethod());
        Assert.assertEquals(newData.getRequestUri(), foundData.getRequestUri());
        Assert.assertEquals(newData.getRequestUrl(), foundData.getRequestUrl());
        Assert.assertEquals(newData.getUserAgent(), foundData.getUserAgent());
        Assert.assertEquals(newData.getRequestBody(), foundData.getRequestBody());
        Assert.assertEquals(newData.getRequestAt(), foundData.getRequestAt());
        Assert.assertEquals(newData.getResponseBody(), foundData.getResponseBody());
        Assert.assertEquals(newData.getResponseStatus(), foundData.getResponseStatus());
        Assert.assertEquals(newData.getAuthorization(), foundData.getAuthorization());
        Assert.assertEquals(newData.getUsername(), foundData.getUsername());
        Assert.assertEquals(newData.getTimeConsuming(), foundData.getTimeConsuming());

        //Wait for ES to flush the data (by default, 1s is required by ES)
        try {
            Thread.sleep(2000);
        } catch (Throwable e) {
        }

        eventPage = eventRepository.search(new DefaultHttpRequestEventSearchRequest());
        Assert.assertEquals(totalElements + 1, eventPage.getTotalElements());

        eventPage = eventRepository.getAll(new DefaultPageableSearchRequest());
        Assert.assertEquals(totalElements + 1, eventPage.getTotalElements());

        //Now test search with fields specified
        eventPage = eventRepository.getAll(new DefaultPageableSearchRequest(),
                                           Fields.empty()
                                                 .includes("username", "responseStatus", "responseBody")
                                                 .excludes("responseBody"));
        Assert.assertEquals(totalElements + 1, eventPage.getTotalElements());
    }

}

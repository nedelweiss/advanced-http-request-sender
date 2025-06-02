package com.github.nedelweiss.http;

import com.github.nedelweiss.prepared_testing_components.CarNameUriParameter;
import com.github.nedelweiss.uri.UriComponentsHolder;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Collections;

import static com.github.nedelweiss.uri.UriBuilderTest.API_CONTEXT;
import static com.github.nedelweiss.uri.UriBuilderTest.API_METHOD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class HttpRequestSenderTest {

    @Test
    void buildGetHttpRequestTest() throws IOException, InterruptedException {
        ArgumentCaptor<HttpRequest> myCaptor = ArgumentCaptor.forClass(HttpRequest.class);

        HttpClient httpClientMock = Mockito.mock(HttpClient.class);

        HttpRequestSender httpRequestSender = new HttpRequestSender(httpClientMock);
        httpRequestSender.send(
            HttpMethod.GET,
            Collections.emptyMap(),
            new UriComponentsHolder(API_CONTEXT, API_METHOD, new CarNameUriParameter[]{new CarNameUriParameter("Citroen")})
        );

        Mockito.verify(httpClientMock).send(myCaptor.capture(), any());

        assertEquals("http://testhost.com/test?car=Citroen", myCaptor.getValue().uri().getPath());
    }

    @Test
    void buildDeleteHttpRequestTest() throws IOException, InterruptedException {
        ArgumentCaptor<HttpRequest> myCaptor = ArgumentCaptor.forClass(HttpRequest.class);

        HttpClient httpClientMock = Mockito.mock(HttpClient.class);

        HttpRequestSender httpRequestSender = new HttpRequestSender(httpClientMock);
        httpRequestSender.send(
            HttpMethod.DELETE,
            Collections.emptyMap(),
            URI.create("http://testhost.com/test?car=Citroen"),
            HttpRequest.BodyPublishers.noBody()
        );

        Mockito.verify(httpClientMock).send(myCaptor.capture(), any());

        assertEquals("DELETE", myCaptor.getValue().method());
    }

    @Test
    void buildDeleteHttpRequestTest1() throws IOException, InterruptedException {
        ArgumentCaptor<HttpRequest> myCaptor = ArgumentCaptor.forClass(HttpRequest.class);

        HttpClient httpClientMock = Mockito.mock(HttpClient.class);

        HttpRequestSender httpRequestSender = new HttpRequestSender(httpClientMock);
        httpRequestSender.send(
            HttpMethod.DELETE,
            Collections.emptyMap(),
            URI.create("http://testhost.com/test?car=Citroen")
        );

        Mockito.verify(httpClientMock).send(myCaptor.capture(), any());

        assertEquals("DELETE", myCaptor.getValue().method());
    }

    @Test
    void buildPostHttpRequestTest() throws IOException, InterruptedException {
        ArgumentCaptor<HttpRequest> myCaptor = ArgumentCaptor.forClass(HttpRequest.class);

        HttpClient httpClientMock = Mockito.mock(HttpClient.class);

        HttpRequestSender httpRequestSender = new HttpRequestSender(httpClientMock);
        httpRequestSender.send(
            HttpMethod.POST,
            Collections.emptyMap(),
            URI.create("http://testhost.com/test?car=Citroen")
        );

        Mockito.verify(httpClientMock).send(myCaptor.capture(), any());

        assertEquals("DELETE", myCaptor.getValue().method());
    }
}
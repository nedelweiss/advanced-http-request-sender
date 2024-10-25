package com.github.nedelweiss.http;

import com.github.nedelweiss.uri.UriBuilder;
import com.github.nedelweiss.uri.UriComponentsHolder;
import com.github.nedelweiss.uri.UriParameter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class HttpRequestSender {

    private final HttpClient httpClient;

    public HttpRequestSender(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public HttpResponse<String> send(
        HttpMethod method,
        Map<String, String> headers,
        UriComponentsHolder uriComponentsHolder,
        HttpRequest.BodyPublisher... bodyPublishers
    ) throws IOException, InterruptedException {
        UriBuilder uriBuilder = new UriBuilder(uriComponentsHolder.getApiContext(), uriComponentsHolder.getApiMethod());
        for (UriParameter parameter : uriComponentsHolder.getParameters()) {
            uriBuilder.addParameter(parameter);
        }
        return send(method, headers, URI.create(uriBuilder.build()), bodyPublishers);
    }

    public HttpResponse<String> send(
        HttpMethod method,
        Map<String, String> headers,
        URI uri,
        HttpRequest.BodyPublisher... bodyPublishers
    ) throws IOException, InterruptedException {
        return httpClient.send(buildRequest(method, headers, uri, bodyPublishers), HttpResponse.BodyHandlers.ofString());
    }

    private HttpRequest buildRequest(
        HttpMethod method,
        Map<String, String> headers,
        URI uri,
        HttpRequest.BodyPublisher... bodyPublishers
    ) {
        HttpRequest.Builder request = HttpRequest.newBuilder()
            .uri(uri);
        applyHttpMethod(request, method, bodyPublishers);
        applyHeaders(request, headers);

        return request.build();
    }

    private void applyHttpMethod(
        HttpRequest.Builder request,
        HttpMethod method,
        HttpRequest.BodyPublisher[] bodyPublishers
    ) {
        boolean noBodyFlag = bodyPublishers.length == 0;
        if (HttpMethod.GET.equals(method) && noBodyFlag) {
            request.GET();
        } else if (HttpMethod.DELETE.equals(method) && noBodyFlag) {
            request.DELETE();
        } else {
            boolean bodyFlag = bodyPublishers.length == 1;
            HttpRequest.BodyPublisher bodyPublisher = bodyPublishers[0];
            if (HttpMethod.POST.equals(method) && bodyFlag) {
                request.POST(bodyPublisher);
            } else if (HttpMethod.PUT.equals(method) && bodyFlag) {
                request.PUT(bodyPublisher);
            }
        }
    }

    private void applyHeaders(HttpRequest.Builder request, Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.header(entry.getKey(), entry.getValue());
        }
    }
}

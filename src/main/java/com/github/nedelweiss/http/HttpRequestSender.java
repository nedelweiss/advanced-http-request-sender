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

// TODO: fix tests
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
        String apiContext = uriComponentsHolder.getApiContext();
        String apiMethod = uriComponentsHolder.getApiMethod();
        UriParameter[] parameters = uriComponentsHolder.getParameters();

        if (parameters == null) {
            return send(method, headers, URI.create(apiContext + apiMethod), bodyPublishers);
        }

        UriBuilder uriBuilder = new UriBuilder(apiContext, apiMethod);
        for (UriParameter parameter : parameters) {
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

        if (headers != null && !headers.isEmpty()) {
            applyHeaders(request, headers);
        }

        return request.build();
    }

    private void applyHttpMethod(
        HttpRequest.Builder request,
        HttpMethod method,
        HttpRequest.BodyPublisher[] bodyPublishers
    ) {
        boolean noBodyFlag = bodyPublishers.length == 0;
        boolean noContentLength = !noBodyFlag && bodyPublishers[0].contentLength() == 0;

        if (HttpMethod.GET.equals(method) && (noBodyFlag || noContentLength)) {
            request.GET();
        } else if (HttpMethod.DELETE.equals(method) && (noBodyFlag || noContentLength)) {
            request.DELETE();
        } else {
            HttpRequest.BodyPublisher bodyPublisher = bodyPublishers[0];
            if (HttpMethod.POST.equals(method)) {
                request.POST(bodyPublisher);
            } else if (HttpMethod.PUT.equals(method)) {
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

package com.github.nedelweiss.uri;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UriBuilder {

    private final String apiContext;
    private final String apiMethod;
    private final List<UriParameter> uriParameters = new ArrayList<>();

    public UriBuilder(String apiContext, String apiMethod) {
        this.apiContext = apiContext;
        this.apiMethod = apiMethod;
    }

    public void addParameter(UriParameter uriParameter) {
        uriParameters.add(uriParameter);
    }

    public String build() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(apiContext)
            .append(apiMethod)
            .append("?");

        final String joinedUrlParameters = uriParameters.stream()
            .map(UriParameter::stringify)
            .collect(Collectors.joining("&"));

        stringBuilder.append(joinedUrlParameters);
        return stringBuilder.toString();
    }
}

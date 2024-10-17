package com.github.nedelweiss.uri;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class UriBuilder {

    public static final String EMPTY_STRING = "";
    private static final String QUESTION_MARK = "?";
    private static final String AND_DELIMITER = "&";
    private static final String SLASH = "/";

    private final String apiContext;
    private final String apiMethod;
    private final List<UriParameter> uriParameters = new ArrayList<>();

    public UriBuilder(String apiContext, String apiMethod) {
        String url = apiContext + apiMethod;
        if (!isURIValid(url) || isNull(apiMethod)) {
            throw new URLValidityException("The URL " + url + " isn't valid");
        }

        if (!apiMethod.isEmpty() && !apiMethod.startsWith(SLASH)) {
            throw new URLValidityException("The api method " + apiMethod + "isn't valid");
        }

        this.apiContext = apiContext;
        this.apiMethod = apiMethod;
    }

    public void addParameter(UriParameter uriParameter) {
        uriParameters.add(uriParameter);
    }

    public String build() {
        String uriParameters = joinUriParameters();
        return new StringBuilder()
            .append(apiContext)
            .append(apiMethod)
            .append(uriParameters.isEmpty() ? EMPTY_STRING : QUESTION_MARK)
            .append(uriParameters)
            .toString();
    }

    private String joinUriParameters() {
        return uriParameters.stream()
            .map(UriParameter::stringify)
            .collect(Collectors.joining(AND_DELIMITER));
    }

    private Boolean isURIValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    static final class URLValidityException extends RuntimeException {

        public URLValidityException(String message) {
            super(message);
        }
    }
}

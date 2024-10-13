package com.github.nedelweiss.uri;

import java.util.Arrays;
import java.util.Objects;

public class UriComponentsHolder {

    private String apiContext;
    private String apiMethod;
    private UriParameter[] parameters;

    public UriComponentsHolder(String apiContext, String apiMethod, UriParameter[] parameters) {
        this.apiContext = apiContext;
        this.apiMethod = apiMethod;
        this.parameters = parameters;
    }

    public String getApiContext() {
        return apiContext;
    }

    public void setApiContext(String apiContext) {
        this.apiContext = apiContext;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public UriParameter[] getParameters() {
        return parameters;
    }

    public void setParameters(UriParameter[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UriComponentsHolder uriComponentsHolder = (UriComponentsHolder) o;

        if (!Objects.equals(apiContext, uriComponentsHolder.apiContext)) return false;
        if (!Objects.equals(apiMethod, uriComponentsHolder.apiMethod)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(parameters, uriComponentsHolder.parameters);
    }

    @Override
    public int hashCode() {
        int result = apiContext != null ? apiContext.hashCode() : 0;
        result = 31 * result + (apiMethod != null ? apiMethod.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(parameters);
        return result;
    }

    @Override
    public String toString() {
        return "UrlParts{" +
            "apiContext='" + apiContext + '\'' +
            ", apiMethod='" + apiMethod + '\'' +
            ", parameters=" + Arrays.toString(parameters) +
            '}';
    }
}

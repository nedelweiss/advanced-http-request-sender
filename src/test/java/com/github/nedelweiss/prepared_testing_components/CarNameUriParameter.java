package com.github.nedelweiss.prepared_testing_components;

import com.github.nedelweiss.uri.UriParameter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CarNameUriParameter implements UriParameter {

    private final String name;

    public CarNameUriParameter(String name) {
        this.name = name;
    }

    @Override
    public String stringify() {
        return String.format("car=%s", URLEncoder.encode(name, StandardCharsets.UTF_8));
    }
}

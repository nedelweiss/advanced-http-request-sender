package com.github.nedelweiss.prepared_testing_components;

import com.github.nedelweiss.uri.UriParameter;

public class VelocityUriParameter implements UriParameter {

    private final Double velocity;

    public VelocityUriParameter(Double velocity) {
        this.velocity = velocity;
    }

    @Override
    public String stringify() {
        return String.format("v=%.2f", velocity);
    }
}

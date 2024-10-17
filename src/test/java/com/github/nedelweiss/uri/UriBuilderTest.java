package com.github.nedelweiss.uri;

import com.github.nedelweiss.prepared_testing_components.CarNameUriParameter;
import com.github.nedelweiss.prepared_testing_components.VelocityUriParameter;
import org.junit.jupiter.api.Test;

import static com.github.nedelweiss.uri.UriBuilder.EMPTY_STRING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UriBuilderTest {

    public static final String API_CONTEXT = "http://testhost.com";
    public static final String API_METHOD = "/test";

    private UriBuilder uriBuilder = new UriBuilder(API_CONTEXT, API_METHOD);

    @Test
    public void whenParametersAddedToUri_buildValidUri() {
        uriBuilder.addParameter(new VelocityUriParameter(60.32));
        uriBuilder.addParameter(new CarNameUriParameter("Bentley"));
        String expected = "http://testhost.com/test?v=60.32&car=Bentley";

        String actual = uriBuilder.build();

        assertEquals(expected, actual);
    }

    @Test
    public void whenParametersAddedToUri_buildValidUri2() { // added for example of parameter class implementation
        uriBuilder.addParameter(new CarNameUriParameter("Ford Mustang"));
        String expected = "http://testhost.com/test?car=Ford+Mustang";

        String actual = uriBuilder.build();

        assertEquals(expected, actual);
    }

    @Test
    public void whenNoUriParameters_buildUrl() {
        assertEquals("http://testhost.com/test", uriBuilder.build());
    }

    @Test
    public void whenApiMethodIsEmpty_buildUrl() {
        uriBuilder = new UriBuilder(API_CONTEXT, EMPTY_STRING);

        assertEquals("http://testhost.com", uriBuilder.build());
    }

    @Test
    public void whenApiContextIsEmpty_throwURLValidityException() {
        assertThrows(
            UriBuilder.URLValidityException.class,
            () -> new UriBuilder(EMPTY_STRING, API_METHOD)
        );
    }

    @Test
    public void whenApiMethodIsNull_throwURLValidityException() {
        assertThrows(
            UriBuilder.URLValidityException.class,
            () -> new UriBuilder(API_CONTEXT, null)
        );
    }

    @Test
    public void whenApiContextIsNull_throwURLValidityException() {
        assertThrows(
            UriBuilder.URLValidityException.class,
            () -> new UriBuilder(null, API_METHOD)
        );
    }

    @Test
    public void whenApiMethodIsWithoutSlash_throwURLValidityException() {
        assertThrows(
            UriBuilder.URLValidityException.class,
            () -> new UriBuilder(API_CONTEXT, "test")
        );
    }
}
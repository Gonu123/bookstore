package com.project.bookstore.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class InterceptorTest {

    @Test
    void shouldReturnTrueWhenProtectedUrlMapIsNotConfigured() throws Exception {
        HttpServletRequest httpServletRequest = new MockHttpServletRequest();
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();

        TokenInterceptor tokenInterceptor = new TokenInterceptor(null);

        boolean result = tokenInterceptor.preHandle(httpServletRequest, httpServletResponse, null);

        Assertions.assertTrue(result);
    }

    // Rest of the tests added for apis at corresponding controller test files
}

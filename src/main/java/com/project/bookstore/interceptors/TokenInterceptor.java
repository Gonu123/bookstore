package com.project.bookstore.interceptors;


import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.resolvers.HttpsJwksVerificationKeyResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    Map<String, String> protectedUrls;

    TokenInterceptor(Map<String, String> protectedUrls) {
        this.protectedUrls = protectedUrls;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (protectedUrls == null) {
            return true;
        }

        String uri = request.getRequestURI();
        String method = request.getMethod();

        String allowedMethods = protectedUrls.get(uri);
        if (allowedMethods != null && allowedMethods.contains(method)) {
                return decodeAccessTokenAndValidateUsername(request, response);
        }

        return true;
    }

    private boolean decodeAccessTokenAndValidateUsername(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String token = request.getHeader("accessToken");
        if (token == null || token.equals("")) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "token missing in headers");
            return false;
        }

        try {
            HttpsJwks httpsJkws = new HttpsJwks("http://localhost:8090/default/jwks");
            HttpsJwksVerificationKeyResolver httpsJwksKeyResolver = new HttpsJwksVerificationKeyResolver(httpsJkws);

            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setRequireExpirationTime()
                    .setVerificationKeyResolver(httpsJwksKeyResolver)
                    .setSkipDefaultAudienceValidation()
                    .build();

            JwtClaims jwtClaims = jwtConsumer.processToClaims(token);

            request.setAttribute("username", jwtClaims.getClaimValueAsString("sub"));
        } catch (Exception e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "token validation failed" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {}
}
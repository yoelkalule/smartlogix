package com.smartlogix.apigateway;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ApiGatewayApplicationTest {

    @Test
    void mainNoDebeLanzarErrores() {
        ApiGatewayApplication app = new ApiGatewayApplication();
        assertNotNull(app);
    }

    @Test
    void corsBeanDebeCrearse() {
        ApiGatewayApplication app = new ApiGatewayApplication();
        assertNotNull(app.corsWebFilter());
    }
}
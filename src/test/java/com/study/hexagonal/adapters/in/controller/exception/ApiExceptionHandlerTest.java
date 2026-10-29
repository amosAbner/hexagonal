package com.study.hexagonal.adapters.in.controller.exception;

import com.study.hexagonal.application.core.exception.CustomerNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiExceptionHandlerTest {

    private final ApiExceptionHandler handler = new ApiExceptionHandler();

    @AfterEach
    void clearMdc() {
        MDC.clear();
    }

    @Test
    void shouldTranslateCoreNotFoundExceptionToStandard404Response() {
        var request = request();
        MDC.put("requestId", "request-123");

        var response = handler.handleCustomerNotFound(
                new CustomerNotFoundException("customer-123"), request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().status());
        assertEquals("Not Found", response.getBody().error());
        assertEquals("Cliente não encontrado: customer-123", response.getBody().message());
        assertEquals("/api/v1/customers/customer-123", response.getBody().path());
        assertEquals("request-123", response.getBody().requestId());
        assertEquals(0, response.getBody().details().size());
    }

    @Test
    void shouldNotExposeUnexpectedExceptionDetails() {
        var request = request();

        var response = handler.handleUnexpected(
                new IllegalStateException("database credentials"), request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Ocorreu um erro interno. Informe o identificador da requisição ao suporte.",
                response.getBody().message());
        assertEquals("/api/v1/customers/customer-123", response.getBody().path());
    }

    private MockHttpServletRequest request() {
        var request = new MockHttpServletRequest("GET", "/api/v1/customers/customer-123");
        return request;
    }
}

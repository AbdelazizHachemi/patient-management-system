package abdelaziz.project.api_gateway.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

class JwtValidationExceptionHandlerTest {

    private final JwtValidationExceptionHandler handler = new JwtValidationExceptionHandler();

    @Test
    void mapsUnauthorizedValidationResponseToUnauthorized() {
        var exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/").build());
        var exception = WebClientResponseException.create(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                HttpHeaders.EMPTY,
                new byte[0],
                null);

        handler.handle(exchange, exception).block();

        assertEquals(HttpStatus.UNAUTHORIZED, exchange.getResponse().getStatusCode());
        assertEquals("Bearer", exchange.getResponse().getHeaders().getFirst("WWW-Authenticate"));
    }

    @Test
    void mapsUnexpectedValidationResponseToBadGateway() {
        var exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/").build());
        var exception = WebClientResponseException.create(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                HttpHeaders.EMPTY,
                new byte[0],
                null);

        handler.handle(exchange, exception).block();

        assertEquals(HttpStatus.BAD_GATEWAY, exchange.getResponse().getStatusCode());
    }

    @Test
    void mapsValidationServiceConnectionFailureToServiceUnavailable() {
        var exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/").build());
        var exception = new WebClientRequestException(
                new IOException("connection refused"),
                HttpMethod.GET,
                URI.create("http://auth-service/validate"),
                HttpHeaders.EMPTY);

        handler.handle(exchange, exception).block();

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, exchange.getResponse().getStatusCode());
    }
}

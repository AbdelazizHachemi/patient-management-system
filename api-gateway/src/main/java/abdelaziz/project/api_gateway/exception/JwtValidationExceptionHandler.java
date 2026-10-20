package abdelaziz.project.api_gateway.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class JwtValidationExceptionHandler implements WebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(JwtValidationExceptionHandler.class);

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }

        if (ex instanceof WebClientResponseException responseException) {
            if (responseException.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                exchange.getResponse().getHeaders().set("WWW-Authenticate", "Bearer");
            } else {
                log.warn("JWT validation service returned an unexpected status: {}",
                        responseException.getStatusCode());
                exchange.getResponse().setStatusCode(HttpStatus.BAD_GATEWAY);
            }
            return exchange.getResponse().setComplete();
        }

        if (ex instanceof WebClientRequestException) {
            log.warn("JWT validation service could not be reached", ex);
            exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
            return exchange.getResponse().setComplete();
        }

        return Mono.error(ex);
    }
}

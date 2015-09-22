package util.spring.http;

import static util.Arrayz.isNullOrZeroLength;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Helper methods for {@link ResponseEntity}'s.
 */
public class ResponseEntities {

    /**
     * Creates a new 404 response with an optional body.
     * If called with no arguments as in {@code _404()}, the response will have
     * an empty body; if a {@code body} argument is given, it will be written
     * to the response body using the configured Spring MVC message converters.
     * @param body optional response body.
     * @return a new 404 response.
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found")
    @SafeVarargs
    public static <T> ResponseEntity<T> _404(T...body) {
        return newError(HttpStatus.NOT_FOUND, body);
    }
    
    private static <T> 
    ResponseEntity<T> newError(HttpStatus status, T[] maybeBody) {
        if (isNullOrZeroLength(maybeBody)) {
            return new ResponseEntity<>(status);
        }
        return new ResponseEntity<>(maybeBody[0], status);
    }
    
}

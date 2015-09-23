package util.spring.http;

import static util.Arrayz.isNullOrZeroLength;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    @SafeVarargs
    public static <T> ResponseEntity<T> _404(T...body) {
        return newError(HttpStatus.NOT_FOUND, body);
    }
    
    /**
     * Creates a new 406 response with an optional body.
     * If called with no arguments as in {@code _406()}, the response will have
     * an empty body; if a {@code body} argument is given, it will be written
     * to the response body using the configured Spring MVC message converters.
     * @param body optional response body.
     * @return a new 406 response.
     */
    @SafeVarargs
    public static <T> ResponseEntity<T> _406(T...body) {
        return newError(HttpStatus.NOT_ACCEPTABLE, body);
    }
     
    private static <T> 
    ResponseEntity<T> newError(HttpStatus status, T[] maybeBody) {
        if (isNullOrZeroLength(maybeBody)) {
            return new ResponseEntity<>(status);
        }
        return new ResponseEntity<>(maybeBody[0], status);
    }
    /* NOTE.
     * HttpStatus is an enum which specifies both the code and reason phrase 
     * (they can be accessed with their respective enum getter methods) but
     * the reason phrase seems to be ignored by the framework when producing
     * a response from a response entity. If you need the reason phrase too,
     * annotate the method with, e.g.
     * 
     *     @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found")
     */
    
}

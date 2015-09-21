package util.spring.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;


public class ResponseEntities {

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found")
    @SafeVarargs
    public static <T> ResponseEntity<T> _404(T...body) {
        return newError(HttpStatus.NOT_FOUND, body);
    }
    
    private static <T> 
    ResponseEntity<T> newError(HttpStatus status, T[] maybeBody) {
        if (maybeBody == null || maybeBody.length == 0) {
            return new ResponseEntity<>(status);
        }
        return new ResponseEntity<>(maybeBody[0], status);
    }
    
}

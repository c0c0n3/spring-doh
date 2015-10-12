package end2end.webq;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class EnqueueShowTripRequestTest extends BaseWebQTest {

    @Test
    public void postSupportedContentType() {
        String jsonRequestBody = buildShowTripRequestJsonBody("gauss", 3);
        ResponseEntity<String> response = postShowTripRequest(jsonRequestBody);
        
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
    
    @Test
    public void postUnsupportedContentType() {
        String jsonRequestBody = buildShowTripRequestJsonBody("gauss", 3);
        ResponseEntity<String> response = 
                post(jsonRequestBody, MediaType.TEXT_PLAIN);
        
        assertThat(response.getStatusCode(), 
                   is(HttpStatus.UNSUPPORTED_MEDIA_TYPE));
    }
    
}

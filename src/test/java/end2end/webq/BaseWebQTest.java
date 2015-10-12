package end2end.webq;

import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import app.run.SpringBootWebQ;


public class BaseWebQTest {

    @BeforeClass
    public static void startSpringBootWebQ() {
        SpringBootWebQ app = new SpringBootWebQ();
        app.run(null);
    }
    
    
    protected RestTemplate httpClient;
    
    @Before
    public void setup() {
        httpClient = new TestRestTemplate();
        
        additionalSetup();
    }
    
    protected void additionalSetup() { }
    
    protected String queueUrl() {
        return "http://localhost:8080/q";
    }
    
    protected ResponseEntity<String> post(
            String requestBody, MediaType contentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);
        HttpEntity<String> request = 
                new HttpEntity<String>(requestBody, headers);
        
        return httpClient.postForEntity(queueUrl(), request, String.class);
    }
    
    protected ResponseEntity<String> postShowTripRequest(String jsonRequestBody) {
        return post(jsonRequestBody, MediaType.APPLICATION_JSON);
    }
    
    protected String buildShowTripRequestJsonBody(
            String tripsterName, int legsTravelled) {
        // {"tripsterName":"gauss", "legsTraveled":"3"}
        return String.format(
                "{\"tripsterName\":\"%s\", \"legsTraveled\":\"%s\"}", 
                tripsterName, legsTravelled);
    }
    
}

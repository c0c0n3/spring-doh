package app.run;

import static util.Arrayz.array;

import java.util.List;

import org.springframework.boot.SpringApplication;

import app.config.HornetQWiring;
import app.config.Profiles;
import app.config.WebWiring;
import app.config.Wiring;
import app.webq.ShowTripRequest;

/**
 * Extends the functionality of the {@link SpringBootWebApp} with an embedded
 * message queue.
 * Build, then run the server in a terminal:
 * <pre>
 * ./gradlew build
 * java -jar build/libs/spring-doh-0.1.0.jar app.run.SpringBootWebQ 
 * </pre>
 * Access the landing page from another terminal
 * <pre>
 * curl http://localhost:8080/ 
 * </pre>
 * and follow the returned instructions to find out about a tripster's trip. 
 * Additionally, you can request a tripster's trip asynchronously by POST-ing
 * a JSON {@link ShowTripRequest} to {@code http://localhost:8080/q}, as in:
 * <pre>
 * curl -H 'Content-Type: application/json' \
 *      -X POST \ 
 *      -d '{"tripsterName":"gauss", "legsTraveled":"3"}' \
 *      http://localhost:8080/q
 * </pre>
 * which will return immediately with an empty HTTP response and put a request 
 * message on the server queue; the message will then processed asynchronously 
 * and the tripster's trip will be displayed on the server's {@code stdout}.
 */
public class SpringBootWebQ extends SpringBootWebApp {

    @Override
    public void run(List<String> appArgs) {
        SpringApplication app = new SpringApplication(
                SpringBootWebApp.class, Wiring.class, 
                WebWiring.class, HornetQWiring.class);
        app.setAdditionalProfiles(getProfiles());
        
        app.run();  // could pass in appArgs if needed  
    }
    
    protected String[] getProfiles() {
        return array(Profiles.ConfigFile, Profiles.WebApp, Profiles.WebQ);
    }
    
}

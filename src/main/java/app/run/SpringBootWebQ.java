package app.run;

import java.util.List;

import org.springframework.boot.SpringApplication;

import app.config.HornetQWiring;
import app.config.Profiles;
import app.config.WebWiring;
import app.config.Wiring;

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
 * Additionally, you can request a tripster's trip asynchronously by prepending
 * {@code q} to the request URL as in:
 * <pre>
 * curl http://localhost:8080/q/gauss?at=3
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
        app.setAdditionalProfiles(
                Profiles.ConfigFile, Profiles.WebApp, Profiles.WebQ);
        
        app.run();  // could pass in appArgs if needed  
    }
    
}

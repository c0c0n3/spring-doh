package app.run;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

import app.config.Profiles;

/**
 * Spring Boot web app to exercise the core functionality.
 * Build, then run the server in a terminal:
 * <pre>
 * ./gradlew build
 * java -jar build/libs/spring-doh-0.1.0.jar app.run.SpringBootWebApp 
 * </pre>
 * Access the landing page from another terminal
 * <pre>
 * curl -O http://localhost:8080/ 
 * </pre>
 * and follow the returned instructions to find out about a tripster's trip.
 */
@SpringBootApplication  // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class SpringBootWebApp implements RunnableApp {

    private void activateProfiles() {
        String active = String.join(",", Profiles.ConfigFile, Profiles.WebApp);
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,
                           active);
    }
    
    @Override
    public void run(List<String> appArgs) {
        activateProfiles();
        SpringApplication.run(SpringBootWebApp.class);  // could pass in appArgs if needed
    }

}

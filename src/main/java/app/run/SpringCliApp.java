package app.run;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import app.config.Profiles;
import app.config.Wiring;
import app.core.trips.TripsterGroup;

/**
 * Simple Spring CLI app to exercise the core functionality.
 * To run it:
 * <pre>
 * ./gradlew build
 * java -jar build/libs/spring-doh-0.1.0.jar app.run.SpringCliApp tripster n 
 * </pre>
 * where {@code tripster} is the name of one of the configured tripsters and
 * {@code n} is an integer.
 */
public class SpringCliApp extends AbstractCliApp {

    @Override
    protected void runApp(String tripsterName, int legsTraveled) {
        AnnotationConfigApplicationContext context = 
                new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles(Profiles.ConfigFile);
        context.register(Wiring.class);
        context.refresh();
        
        try {
            @SuppressWarnings("unchecked")
            TripsterGroup<String> happyBunch = 
                context.getBean(TripsterGroup.class);
            
            happyBunch.showWhereIs(tripsterName, legsTraveled);
        }
        finally {
            context.close();
        }
    }
    
}

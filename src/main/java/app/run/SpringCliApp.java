package app.run;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
        ConfigurableApplicationContext context = 
                new AnnotationConfigApplicationContext(Wiring.class);
        
        @SuppressWarnings("unchecked")
        TripsterGroup<String> happyBunch = context.getBean(TripsterGroup.class);
        
        happyBunch.showWhereIs(tripsterName, legsTraveled);
        
        context.close();
    }
    
}

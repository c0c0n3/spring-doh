package app.run;

import static util.Arrayz.asMutableList;

import java.util.List;

import app.config.Profiles;

/**
 * Extends the {@link SpringBootWebQ} functionality with:
 * <ul>
 *  <li>a log file in the current directory;</li>
 *  <li>Spring Boot Actuator built-in monitoring & management HTTP and JMX 
 *  end-points;</li>
 * </ul>
 * Build, then run the server in a terminal:
 * <pre>
 * ./gradlew build
 * java -jar build/libs/spring-doh-0.1.0.jar app.run.FullyFledgedApp 
 * </pre>
 * View the log file from another terminal:
 * <pre>
 * tail -f app.log 
 * </pre>
 * Access Actuator's end-points, e.g.
 * <pre>
 * curl -H 'Accept: application/json' http://localhost:8080/metrics 
 * </pre>
 * Use {@code jconsole} to look inside the app using JMX; the JMX app's name
 * is {@code FullyFledgedApp}.
 */
public class FullyFledgedApp extends SpringBootWebQ {

    @Override
    protected String[] getProfiles() {
        List<String> ps = asMutableList(super.getProfiles());
        ps.add(Profiles.FullyFledged);
        return ps.toArray(new String[ps.size()]);
    }
    
}

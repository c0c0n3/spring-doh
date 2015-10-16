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
 *  <li>the <a href="https://github.com/codecentric/spring-boot-admin">Spring 
 *  Boot Admin</a> Web application.</li>
 * </ul>
 * Build, then run the {@link SpringBootAdminServer Spring Boot Admin server}
 * in a terminal:
 * <pre>
 * ./gradlew build  
 * java -jar build/libs/spring-doh-0.1.0.jar app.run.SpringBootAdminServer 
 * </pre>
 * Now start the app server in another terminal:
 * <pre>
 * java -jar build/libs/spring-doh-0.1.0.jar app.run.FullyFledgedApp 
 * </pre>
 * If you point your browser to {@code http://localhost:8081/}, you should be
 * able to see the fully fledged app on port {@code 8080} in the management UI.
 * (You may have to wait a few seconds for it to register with the admin server
 * before it can be displayed in the UI.) The admin server sources its data 
 * from the raw Actuator's end-points exposed by the fully fledged app; to
 * access them directly, try e.g.
 * <pre>
 * curl -H 'Accept: application/json' http://localhost:8080/metrics
 * </pre>
 * JMX end-points are also exposed. Use {@code jconsole} to look inside the app 
 * using JMX; the JMX app's name is {@code FullyFledgedApp}.
 * You may also want to view the log file from another terminal:
 * <pre>
 * tail -f app.log 
 * </pre>
 */
public class FullyFledgedApp extends SpringBootWebQ {

    @Override
    protected String[] getProfiles() {
        List<String> ps = asMutableList(super.getProfiles());
        ps.add(Profiles.FullyFledged);
        return ps.toArray(new String[ps.size()]);
    }
    
}

package app.config.items;

import static java.util.Objects.requireNonNull;
import static util.Arrayz.isNullOrZeroLength;

import java.util.Properties;


/**
 * Provides access to the Spring Boot application properties that we use by
 * grouping them into configuration sections (e.g. "{@code logging.*}").
 */
public class SpringBootConfig {

    /**
     * Joins the given components into a property key.
     * E.g. {@code key("a", "b") = "a.b"}.
     * @param components The elements to join.
     * @return the joined components.
     * @throws IllegalArgumentException if the argument is {@code null} or has
     * zero length.
     */
    public static final String key(String...components) {
        if (isNullOrZeroLength(components)) throw new IllegalArgumentException();
        return String.join(".", components);
    }
    
    private final Properties props;
    
    /**
     * Creates an empty configuration.
     */
    public SpringBootConfig() {
        this(new Properties());
    }
    
    /**
     * Creates a configuration from the supplied properties.
     * @param props the Spring Boot application properties to use.
     */
    public SpringBootConfig(Properties props) {
        requireNonNull(props, "props");
        this.props = props;
    }
    
    /**
     * @return the underlying Java props.
     */
    public Properties getProps() {
        return props;
    }
    
    /**
     * Access to the "{@code logging.*}" properties.
     * @return An object to access "{@code logging.*}" properties.
     */
    public SpringBootLogConfig log() {
        return new SpringBootLogConfig(props);
    }
    
    /**
     * Access to the various Spring Boot Actuator properties.
     * @return An object to access Actuator properties.
     */
    public ActuatorConfig actuator() {
        return new ActuatorConfig(props);
    }

    /**
     * Access to the various Spring Boot Admin properties.
     * @return An object to access Spring Boot Admin properties.
     */
    public SpringBootAdminConfig admin() {
        return new SpringBootAdminConfig(props);
    }
    
}

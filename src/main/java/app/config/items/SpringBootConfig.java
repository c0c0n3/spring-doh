package app.config.items;

import static java.util.Objects.requireNonNull;

import java.util.Properties;

/**
 * Provides access to the Spring Boot application properties that we use by
 * grouping them into configuration sections (e.g. "{@code logging.*}").
 */
public class SpringBootConfig {

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
    public SpringBootLogConfig getLogConfig() {
        return new SpringBootLogConfig(props);
    }
    
}

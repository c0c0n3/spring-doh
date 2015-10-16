package app.config.items;

import static app.config.items.SpringBootConfig.key;
import static java.util.Objects.requireNonNull;
import static util.Strings.requireString;

import java.util.Properties;

/**
 * Provides access to the Spring Boot logging configuration properties.
 */
public class SpringBootLogConfig {

    public static final String LogFileKey = "logging.file";
    public static final String LogLevelPrefix = "logging.level";
    
    
    private final Properties props;
    
    public SpringBootLogConfig(Properties springBootAppProps) {
        requireNonNull(springBootAppProps, "springBootAppProps");
        this.props = springBootAppProps;
    }
    
    public SpringBootLogConfig setFileName(String pathname) {
        requireNonNull(pathname, "pathname");
        props.setProperty(LogFileKey, pathname);
        return this;
    }
    
    public SpringBootLogConfig setLogLevel(String packageName, LogLevel level) {
        requireString(packageName, "packageName");
        props.setProperty(key(LogLevelPrefix, packageName), level.name());
        return this;
    }
    
    public SpringBootLogConfig setRootLogLevel(LogLevel level) {
        setLogLevel("ROOT", level);
        return this;
    }
    
}

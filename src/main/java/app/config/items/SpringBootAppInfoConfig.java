package app.config.items;

import static app.config.items.SpringBootConfig.key;
import static java.util.Objects.requireNonNull;
import static util.Strings.requireString;

import java.util.Properties;

/**
 * Provides access to the Spring Boot application info configuration properties.
 */
public class SpringBootAppInfoConfig {

    public static final String AppInfoPrefix = "info.app";
    
    
    private final Properties props;
    
    public SpringBootAppInfoConfig(Properties springBootAppProps) {
        requireNonNull(springBootAppProps, "springBootAppProps");
        this.props = springBootAppProps;
    }
    
    public SpringBootAppInfoConfig setName(String appName) {
        requireString(appName, "appName");
        props.setProperty(key(AppInfoPrefix, "name"), appName);
        return this;
    }
    
    public SpringBootAppInfoConfig setDescription(String appDesc) {
        requireString(appDesc, "appDesc");
        props.setProperty(key(AppInfoPrefix, "description"), appDesc);
        return this;
    }
    
    public SpringBootAppInfoConfig setVersion(String version) {
        requireString(version, "version");
        props.setProperty(key(AppInfoPrefix, "version"), version);
        return this;
    }
    
}

package app.config.items;

import static app.config.items.SpringBootConfig.key;
import static java.util.Objects.requireNonNull;

import java.net.URI;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;


/**
 * Provides access to the <a href="https://github.com/codecentric/spring-boot-admin">
 * Spring Boot Admin</a> configuration properties.
 */
public class SpringBootAdminConfig {

    public static final String SpringBootAdminPrefix = "spring.boot.admin";
    public static final String AdminServerUrlKey = key(SpringBootAdminPrefix, "url");
    
    public static 
    Optional<URI> getAdminServerUrl(Function<String, String> propLookup) {
        requireNonNull(propLookup, "propLookup");
        
        return Optional.ofNullable(propLookup.apply(AdminServerUrlKey))
                       .filter(url -> url != "")
                       .map(URI::create);
    }
    
    private final Properties props;
    
    public SpringBootAdminConfig(Properties springBootAppProps) {
        requireNonNull(springBootAppProps, "springBootAppProps");
        this.props = springBootAppProps;
    }
 
    public void setAdminServerUrl(URI serverRoot) {
        requireNonNull(serverRoot, "serverRoot");
        props.setProperty(AdminServerUrlKey, serverRoot.toASCIIString());
    }
    
    public Optional<URI> getAdminServerUrl() {
        return getAdminServerUrl(props::getProperty);
    }
    
}

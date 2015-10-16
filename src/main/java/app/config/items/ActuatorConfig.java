package app.config.items;

import static app.config.items.SpringBootConfig.key;
import static java.util.Objects.requireNonNull;

import java.util.Properties;
import java.util.stream.Stream;

/**
 * Provides access to the Spring Boot Actuator configuration properties.
 */
public class ActuatorConfig {

    public static final String EndPointsPrefix = "endpoints";
    public static final String ManagementPrefix = "management";
    public static final String SecurityPrefix = "security";
    public static final String JmxPrefix = "jmx";
    
        
    private final Properties props;
    
    public ActuatorConfig(Properties springBootAppProps) {
        requireNonNull(springBootAppProps, "springBootAppProps");
        this.props = springBootAppProps;
    }
    
    public ActuatorConfig setId(ActuatorEndPointName which, String id) {
        props.setProperty(key(EndPointsPrefix, which.name(), "id"), id);
        return this;
    }
    
    public ActuatorConfig setEnabled(ActuatorEndPointName which, boolean enable) {
        props.setProperty(key(EndPointsPrefix, which.name(), "enabled"), 
                          String.valueOf(enable));
        return this;
    }
    
    public ActuatorConfig setEnabled(boolean enableAll) {
        Stream.of(ActuatorEndPointName.values())
              .forEach(name -> setEnabled(name, enableAll));
        return this;
    }
    
    public ActuatorConfig setSensitive(ActuatorEndPointName which, boolean sensitive) {
        props.setProperty(key(EndPointsPrefix, which.name(), "sensitive"), 
                          String.valueOf(sensitive));
        return this;
    }
    
    public ActuatorConfig setSensitive(boolean allSensitive) {
        Stream.of(ActuatorEndPointName.values())
              .forEach(name -> setSensitive(name, allSensitive));
        return this;
    }
    
    public ActuatorConfig setManagementAddress(String host) {
        props.setProperty(key(ManagementPrefix, "address"), host);
        return this;
    }
    /* NB: quoting Spring Boot manual
     * useful if you want to listen only on an internal or ops-facing network, 
     * or to only listen for connections from localhost.
     */
    
    public ActuatorConfig setManagementPort(int number) {
        props.setProperty(key(ManagementPrefix, "port"), 
                          String.valueOf(number));  // NB -1 will disable HTTP
        return this;
    }
    
    public ActuatorConfig setManagementContextPath(String absolutePath) {
        props.setProperty(key(ManagementPrefix, "context-path"), absolutePath);
        return this;
    }
    
    public ActuatorConfig setSecurityEnabled(boolean enable) {
        props.setProperty(key(ManagementPrefix, SecurityPrefix, "enabled"), 
                          String.valueOf(enable));
        return this;
    }
    /* NB: quoting Spring Boot manual
     * If you don’t have Spring Security on the classpath then there is no need 
     * to explicitly disable the management security in this way, and it might 
     * even break the application.
     */
    
    public ActuatorConfig setAdminUserName(String name) {
        props.setProperty(key(SecurityPrefix, "user.name"), name);
        return this;
    }
    
    public ActuatorConfig setAdminPassword(String password) {
        props.setProperty(key(SecurityPrefix, "user.password"), password);
        return this;
    }
    
    public ActuatorConfig setSecurityRole(String role) {
        props.setProperty(key(ManagementPrefix, SecurityPrefix, "role"), 
                          role);
        return this;
    }

    public ActuatorConfig setJmxDomain(String domainUnderWhichToExposeEndPoints) {
        props.setProperty(key(EndPointsPrefix, JmxPrefix, "domain"), 
                          domainUnderWhichToExposeEndPoints);
        return this;
    }
    
    public ActuatorConfig setJmxUniqueNames(boolean enforceUnique) {
        props.setProperty(key(EndPointsPrefix, JmxPrefix, "uniqueNames"), 
                          String.valueOf(enforceUnique));
        return this;
    }
    
    public ActuatorConfig setJmxEnabled(boolean enable) {
        props.setProperty(key("spring", JmxPrefix, "enabled"), 
                          String.valueOf(enable));
        return this;
    }
    
}

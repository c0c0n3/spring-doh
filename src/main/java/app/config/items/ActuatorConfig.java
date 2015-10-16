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
    
    public void setId(ActuatorEndPointName which, String id) {
        props.setProperty(key(EndPointsPrefix, which.name(), "id"), id);
    }
    
    public void setEnabled(ActuatorEndPointName which, boolean enable) {
        props.setProperty(key(EndPointsPrefix, which.name(), "enabled"), 
                          String.valueOf(enable));
    }
    
    public void setEnabled(boolean enableAll) {
        Stream.of(ActuatorEndPointName.values())
              .forEach(name -> setEnabled(name, enableAll));
    }
    
    public void setSensitive(ActuatorEndPointName which, boolean sensitive) {
        props.setProperty(key(EndPointsPrefix, which.name(), "sensitive"), 
                          String.valueOf(sensitive));
    }
    
    public void setSensitive(boolean allSensitive) {
        Stream.of(ActuatorEndPointName.values())
              .forEach(name -> setSensitive(name, allSensitive));
    }
    
    public void setManagementAddress(String host) {
        props.setProperty(key(ManagementPrefix, "address"), host);  
    }
    /* NB: quoting Spring Boot manual
     * useful if you want to listen only on an internal or ops-facing network, 
     * or to only listen for connections from localhost.
     */
    
    public void setManagementPort(int number) {
        props.setProperty(key(ManagementPrefix, "port"), 
                          String.valueOf(number));  // NB -1 will disable HTTP
    }
    
    public void setManagementContextPath(String absolutePath) {
        props.setProperty(key(ManagementPrefix, "context-path"), absolutePath);
    }
    
    public void setSecurityEnabled(boolean enable) {
        props.setProperty(key(ManagementPrefix, SecurityPrefix, "enabled"), 
                          String.valueOf(enable));
    }
    /* NB: quoting Spring Boot manual
     * If you don’t have Spring Security on the classpath then there is no need 
     * to explicitly disable the management security in this way, and it might 
     * even break the application.
     */
    
    public void setAdminUserName(String name) {
        props.setProperty(key(SecurityPrefix, "user.name"), name); 
    }
    
    public void setAdminPassword(String password) {
        props.setProperty(key(SecurityPrefix, "user.password"), password); 
    }
    
    public void setSecurityRole(String role) {
        props.setProperty(key(ManagementPrefix, SecurityPrefix, "role"), 
                          role); 
    }

    public void setJmxDomain(String domainUnderWhichToExposeEndPoints) {
        props.setProperty(key(EndPointsPrefix, JmxPrefix, "domain"), 
                          domainUnderWhichToExposeEndPoints);  
    }
    
    public void setJmxUniqueNames(boolean enforceUnique) {
        props.setProperty(key(EndPointsPrefix, JmxPrefix, "uniqueNames"), 
                          String.valueOf(enforceUnique));  
    }
    
    public void setJmxEnabled(boolean enable) {
        props.setProperty(key("spring", JmxPrefix, "enabled"), 
                          String.valueOf(enable));  
    }
    
}

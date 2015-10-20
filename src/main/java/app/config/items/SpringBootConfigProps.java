package app.config.items;

import static util.config.props.JPropKey.key;

import java.util.stream.Stream;

import util.config.props.JPropAccessor;
import util.config.props.JPropSetter;

/**
 * Accessors for Spring Boot application properties.
 */
public class SpringBootConfigProps {

    public static final String EndPointsPrefix = "endpoints";
    public static final String ManagementPrefix = "management";
    public static final String SecurityPrefix = "security";
    public static final String JmxPrefix = "jmx";
    public static final String AppInfoPrefix = "info.app";
    public static final String LogFileKey = "logging.file";
    public static final String LogLevelPrefix = "logging.level";
    
    
    public static 
    JPropAccessor<String> endpointId(ActuatorEndPointName which) {
        return JPropAccessor.makeString(
                key(EndPointsPrefix, which.name(), "id"));
    }
    
    public static 
    JPropAccessor<Boolean> endpointEnabled(ActuatorEndPointName which) {
        return JPropAccessor.makeBool(
                key(EndPointsPrefix, which.name(), "enabled"));
    }
    
    public static 
    Stream<JPropSetter<Boolean>> endpointsEnabled() {
        return Stream.of(ActuatorEndPointName.values())
                     .map(name -> endpointEnabled(name));
    }
    
    public static 
    JPropAccessor<Boolean> endpointSensitive(ActuatorEndPointName which) {
        return JPropAccessor.makeBool(
                key(EndPointsPrefix, which.name(), "sensitive"));
    }
    
    public static 
    Stream<JPropSetter<Boolean>> endpointsSensitive() {
        return Stream.of(ActuatorEndPointName.values())
                     .map(name -> endpointSensitive(name));
    }
    
    public static JPropAccessor<String> managementAddress() {
        return JPropAccessor.makeString(key(ManagementPrefix, "address"));
    }
    /* NB: quoting Spring Boot manual
     * useful if you want to listen only on an internal or ops-facing network, 
     * or to only listen for connections from localhost.
     */
    
    public static JPropAccessor<Integer> managementPort() {
        return JPropAccessor.makeInt(key(ManagementPrefix, "port"));
    }
    // NB -1 will disable HTTP

    public static JPropAccessor<String> managementContextPath() {
        return JPropAccessor.makeString(key(ManagementPrefix, "context-path"));
    }
    // NB should be absolute path
    
    public static JPropAccessor<Boolean> securityEnabled() {
        return JPropAccessor.makeBool(
                key(ManagementPrefix, SecurityPrefix, "enabled"));
    }
    /* NB: quoting Spring Boot manual
     * If you don’t have Spring Security on the classpath then there is no need 
     * to explicitly disable the management security in this way, and it might 
     * even break the application.
     */
    
    public static JPropAccessor<String> adminUserName() {
        return JPropAccessor.makeString(key(SecurityPrefix, "user.name"));
    }
    
    public static JPropAccessor<String> adminPassword() {
        return JPropAccessor.makeString(key(SecurityPrefix, "user.password"));
    }
    
    public static JPropAccessor<String> securityRole() {
        return JPropAccessor.makeString(
                key(ManagementPrefix, SecurityPrefix, "role"));
    }
    
    public static JPropAccessor<String> jmxDomain() {
        return JPropAccessor.makeString(
                key(EndPointsPrefix, JmxPrefix, "domain"));
    }
    // value is the name of the domain under which to expose end points
    
    public static JPropAccessor<Boolean> jmxUniqueNames() {
        return JPropAccessor.makeBool(
                key(EndPointsPrefix, JmxPrefix, "uniqueNames"));
    }
    // value specifies whether to enforce unique bean names in JMX
    
    public static JPropAccessor<Boolean> jmxEnabled() {
        return JPropAccessor.makeBool(key("spring", JmxPrefix, "enabled"));
    }

    public static JPropAccessor<String> appName() {
        return JPropAccessor.makeString(key(AppInfoPrefix, "name"));
    }
    
    public static JPropAccessor<String> appDescription() {
        return JPropAccessor.makeString(key(AppInfoPrefix, "description"));
    }

    public static JPropAccessor<String> appVersion() {
        return JPropAccessor.makeString(key(AppInfoPrefix, "version"));
    }
    
    public static JPropAccessor<String> logFilePathName() {
        return JPropAccessor.makeString(key(LogFileKey));
    }
    
    public static JPropAccessor<LogLevel> logLevel(String packageName) {
        return JPropAccessor.makeEnum(LogLevel.class, 
                                      key(LogLevelPrefix, packageName)); 
    }
    
    public static JPropAccessor<LogLevel> rootLogLevel() {
        return logLevel("ROOT");
    }
    
}

package app.config.items;

import static java.util.Objects.requireNonNull;
import static util.Strings.requireString;

import java.util.Properties;

public class SpringBootLogConfig {

    public static final String LogFileKey = "logging.file";
    public static final String LogLevelPrefix = "logging.level";
    
    
    private final Properties props;
    
    public SpringBootLogConfig(Properties springBootAppProps) {
        requireNonNull(springBootAppProps, "springBootAppProps");
        this.props = springBootAppProps;
    }
    
    public void setFileName(String pathname) {
        requireNonNull(pathname, "pathname");
        props.setProperty(LogFileKey, pathname);
    }
    
    public void setLogLevel(String packageName, LogLevel level) {
        requireString(packageName, "packageName");
        props.setProperty(LogLevelPrefix + "." + packageName, level.name());
    }
    
    public void setRootLogLevel(LogLevel level) {
        setLogLevel("ROOT", level);
    }
    
}

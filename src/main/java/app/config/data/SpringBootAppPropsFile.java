package app.config.data;

import java.util.stream.Stream;

import util.config.ConfigProvider;
import app.config.items.LogLevel;
import app.config.items.SpringBootConfig;


/**
 * The data that goes into 'config/application-*.properties'.
 */
public class SpringBootAppPropsFile 
    implements ConfigProvider<SpringBootConfig> {

    @Override
    public Stream<SpringBootConfig> readConfig() {
        SpringBootConfig cfg = new SpringBootConfig(); 
        
        cfg.getLogConfig().setFileName("app.log");
        cfg.getLogConfig().setRootLogLevel(LogLevel.INFO);
        
        cfg.getActuatorConfig().setEnabled(true);
        cfg.getActuatorConfig().setSensitive(false);
        
        cfg.getActuatorConfig().setJmxDomain("FullyFledgedApp");
        cfg.getActuatorConfig().setJmxEnabled(true);
        cfg.getActuatorConfig().setJmxUniqueNames(true);
        
        return Stream.of(cfg);
    }
    
}

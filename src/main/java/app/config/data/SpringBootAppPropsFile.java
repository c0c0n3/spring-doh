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
        
        return Stream.of(cfg);
    }

}

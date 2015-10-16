package app.config.data;

import java.net.URI;
import java.net.URISyntaxException;
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
    public Stream<SpringBootConfig> readConfig() throws Exception {
        SpringBootConfig cfg = new SpringBootConfig(); 
        
        cfg.getLogConfig().setFileName("app.log");
        cfg.getLogConfig().setRootLogLevel(LogLevel.INFO);
        
        cfg.getActuatorConfig().setEnabled(true);
        cfg.getActuatorConfig().setSensitive(false);
        
        cfg.getActuatorConfig().setJmxDomain("FullyFledgedApp");
        cfg.getActuatorConfig().setJmxEnabled(true);
        cfg.getActuatorConfig().setJmxUniqueNames(true);
        
        cfg.getAdminConfig().setAdminServerUrl(buildAdminUrl());
        
        return Stream.of(cfg);
    }
    
    private URI buildAdminUrl() throws URISyntaxException {
        int port = 
                new UndertowYmlFile().readConfig().findFirst().get().getPort();
        
        return new URI("http", null, "localhost", port + 1, null, null, null);
    }
    
}

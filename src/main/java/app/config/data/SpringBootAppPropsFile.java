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
        
        cfg.log().setFileName("app.log")
                 .setRootLogLevel(LogLevel.INFO);
        
        cfg.actuator().setEnabled(true)
                      .setSensitive(false);
        
        cfg.actuator().setJmxDomain("FullyFledgedApp")
                      .setJmxEnabled(true)
                      .setJmxUniqueNames(true);
        
        cfg.admin().setAdminServerUrl(buildAdminUrl());
        
        return Stream.of(cfg);
    }
    
    private URI buildAdminUrl() throws URISyntaxException {
        int port = 
                new UndertowYmlFile().readConfig().findFirst().get().getPort();
        
        return new URI("http", null, "localhost", port + 1, null, null, null);
    }
    
}

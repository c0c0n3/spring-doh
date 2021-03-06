package app.config.data;

import static app.config.items.SpringBootConfigProps.*;
import static app.config.items.SpringBootAdminConfigProps.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.stream.Stream;

import util.config.ConfigProvider;
import util.config.props.JProps;
import app.config.items.LogLevel;


/**
 * The data that goes into 'config/application-*.properties'.
 */
public class SpringBootAppPropsFile 
    implements ConfigProvider<Properties> {

    @Override
    public Stream<Properties> readConfig() throws Exception {
        String appName = "FullyFledgedApp";
        JProps cfg = new JProps(new Properties()); 
        
        cfg.set(appName().with(appName));
        
        cfg.set(logFilePathName().with("app.log"));
        cfg.set(rootLogLevel().with(LogLevel.INFO));
        
        cfg.setAll(endpointsEnabled(), true);
        cfg.setAll(endpointsSensitive(), false);
        
        cfg.set(jmxDomain().with(appName));
        cfg.set(jmxEnabled().with(true));
        cfg.set(jmxUniqueNames().with(true));
        
        cfg.set(adminServerUrl().with(buildAdminUrl()));
        
        return Stream.of(cfg.getProps());
    }
    
    private URI buildAdminUrl() throws URISyntaxException {
        int port = 
                new UndertowYmlFile().readConfig().findFirst().get().getPort();
        
        return new URI("http", null, "localhost", port + 1, null, null, null);
    }
    
}

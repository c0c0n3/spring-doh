package app.config;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Reads tripster configuration from an XML file.
 */
@Component
@Profile("file-config")
public class TripsterProvider implements ConfigProvider<List<TripsterConfig>> {

    /**
     * Reads the happy bunch of tripsters from configuration.
     * @return all configured tripsters; never {@code null}.
     */
    @Override
    public List<TripsterConfig> readConfig() throws Exception {
        // TODO read from xml config file instead
        ConfigProvider<List<TripsterConfig>> provider = new DefaultTripsters(); 
        return provider.defaultReadConfig();
    }

}

package app.config.providers;

import java.util.stream.Stream;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import util.config.ConfigProvider;
import app.config.Profiles;
import app.config.data.DefaultTripsters;
import app.config.items.TripsterConfig;

/**
 * Hard-coded {@link TripsterConfig} to use if no external config is provided 
 * or for testing.
 */
@Component
@Profile(Profiles.HardCodedConfig)
public class HardCodedTripsters implements ConfigProvider<TripsterConfig> {
    
    /**
     * The happy bunch of tripsters hard-coded in this configuration.
     * @return all configured tripsters; never {@code null}.
     */
    @Override
    public Stream<TripsterConfig> readConfig() {
        return new DefaultTripsters().readConfig();
    }
    
}

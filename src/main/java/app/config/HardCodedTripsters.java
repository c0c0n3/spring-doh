package app.config;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import app.config.data.DefaultTripsters;

/**
 * Hard-coded {@link TripsterConfig} to use if no external config is provided 
 * or for testing.
 */
@Component
@Profile(Profiles.HardCodedConfig)
public class HardCodedTripsters implements ConfigProvider<List<TripsterConfig>> {
    
    /**
     * The happy bunch of tripsters hard-coded in this configuration.
     * @return all configured tripsters; never {@code null}.
     */
    @Override
    public List<TripsterConfig> readConfig() {
        return DefaultTripsters.tripsters;
    }
    
}

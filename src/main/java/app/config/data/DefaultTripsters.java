package app.config.data;

import static util.Arrayz.array;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import util.config.ConfigProvider;
import app.config.items.TripsterConfig;

/**
 * Hard-coded tripster data to use if no external config is provided or for 
 * testing.
 */
public class DefaultTripsters implements ConfigProvider<TripsterConfig> {

    /**
     * Ventures in numberland.
     * @return 'gauss' config.
     */
    public static TripsterConfig gauss() {
        String[] intsMod5 = IntStream
                           .range(0, 5)
                           .boxed()
                           .map(i -> i + " (mod 5)")
                           .toArray(String[]::new);
        
        return new TripsterConfig("gauss", "Ventures in numberland.", intsMod5);
    }
    
    /**
     * Tours South Africa.
     * @return 'hipster' config.
     */
    public static TripsterConfig hipster() {
        String[] trip = array("Cape Town", "Knysna", "Durbs", "Jozi");
        return new TripsterConfig("hipster", "Tours South Africa.", trip);
    }
    
    /**
     * All the tripsters in this configuration.
     */
    @Override
    public Stream<TripsterConfig> readConfig() {
        return Stream.of(gauss(), hipster());
    }
    
}

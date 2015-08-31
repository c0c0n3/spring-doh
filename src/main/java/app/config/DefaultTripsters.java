package app.config;

import static util.Arrayz.array;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Hard-coded {@link TripsterConfig} to use if no external config is provided 
 * or for testing.
 */
public class DefaultTripsters implements ConfigProvider<List<TripsterConfig>> {

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
        
        return new TripsterConfig("gauss", intsMod5);
    }
    
    /**
     * Tours South Africa.
     * @return 'hipster' config.
     */
    public static TripsterConfig hipster() {
        String[] trip = array("Cape Town", "Knysna", "Durbs", "Jozi");
        return new TripsterConfig("hipster", trip);
    }
    
    /**
     * The happy bunch of tripsters hard-coded in this configuration.
     * @return all configured tripsters; never {@code null}.
     */
    @Override
    public List<TripsterConfig> readConfig() {
        return Arrays.asList(gauss(), hipster());
    }
    
}
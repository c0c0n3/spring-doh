package app.config.data;

import static util.Arrayz.array;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import app.config.TripsterConfig;

/**
 * Hard-coded tripster data to use if no external config is provided or for 
 * testing.
 */
public class DefaultTripsters {

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
    public static List<TripsterConfig> tripsters = 
            Arrays.asList(gauss(), hipster());
    
}

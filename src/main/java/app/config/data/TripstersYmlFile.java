package app.config.data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import app.config.items.TripsterConfig;

/**
 * The data that goes into 'config/tripsters.yml'.
 */
public class TripstersYmlFile {

    /**
     * Crosses the bridges of Königsberg.
     * @return 'euler' config.
     */
    public static TripsterConfig euler() {
        String[] cycleInKoningsberg = IntStream
                                     .of(1, 4, 3, 2)
                                     .boxed()
                                     .map(i -> "land mass " + i)
                                     .toArray(String[]::new);
        return new TripsterConfig("euler", 
                "Crosses the bridges of Königsberg.", cycleInKoningsberg);
    }
 
    /**
     * All the tripsters in this configuration.
     */
    public static List<TripsterConfig> tripsters = 
            Arrays.asList(DefaultTripsters.gauss(), DefaultTripsters.hipster(),
                          euler());

}

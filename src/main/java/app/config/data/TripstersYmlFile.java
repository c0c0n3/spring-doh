package app.config.data;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import util.config.ConfigProvider;
import app.config.items.TripsterConfig;

/**
 * The data that goes into 'config/tripsters.yml'.
 */
public class TripstersYmlFile implements ConfigProvider<TripsterConfig> {

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
    @Override
    public Stream<TripsterConfig> readConfig() {
        return Stream.of(DefaultTripsters.gauss(), DefaultTripsters.hipster(),
                         euler());
    }

}

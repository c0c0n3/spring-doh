package app.config;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.Function;

import util.config.ConfigProvider;
import app.core.cyclic.ArrayCycle;
import app.core.cyclic.Cycle;
import app.core.trips.Tripster;

/**
 * Maps configured {@link TripsterConfig}'s to {@link Tripster}'s.
 */
public class TripsterConfigMapper<T> {
    
    /**
     * Opinionated constructor utility.
     * Keeps config data as strings and uses an {@link ArrayCycle}.
     * @see #fromConfig(ConfigProvider)
     */
    public static TripsterConfigMapper<String> newWithStringArray() {
        return new TripsterConfigMapper<>(
                    entry -> new ArrayCycle<>(entry.getCycle()));
    }
    
    private Function<TripsterConfig, Cycle<T>> toCycle;
    
    /**
     * Creates a new mapper.
     * @param toCycle extracts a {@link Cycle} out of a config entry.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public TripsterConfigMapper(Function<TripsterConfig, Cycle<T>> toCycle) {
        requireNonNull(toCycle, "toCycle");
        
        this.toCycle = toCycle;
    }
    
    private Tripster<T> newTripster(TripsterConfig entry) {
        return new Tripster<>(
                entry.getName(), 
                entry.getDescription(),
                toCycle.apply(entry));
    }
    
    /**
     * Reads in the configured tripsters.
     * @param provider holds the configuration data.
     * @return the tripsters instantiated from configuration data.
     * @throws NullPointerException if the argument is {@code null}.
     * @throws RuntimeException if an error occurs while reading the 
     * configuration.
     */
    public List<Tripster<T>> fromConfig(
            ConfigProvider<List<TripsterConfig>> provider) {
        requireNonNull(provider, "provider");
        
        return provider
              .defaultReadConfig()
              .stream()
              .map(this::newTripster)
              .collect(toList());
    }
    
}

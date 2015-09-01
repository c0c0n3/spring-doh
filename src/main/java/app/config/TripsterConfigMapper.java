package app.config;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.Function;

import app.core.cyclic.ArrayCycle;
import app.core.cyclic.Cycle;
import app.core.cyclic.CycleVisualizer;
import app.core.cyclic.StdoutVisualizer;
import app.core.trips.Tripster;

/**
 * Maps configured {@link TripsterConfig}'s to {@link Tripster}'s.
 */
public class TripsterConfigMapper<T> {
    
    /**
     * Opinionated mapping utility.
     * Keeps config data as strings, uses an {@link ArrayCycle}, and the
     * same {@link CycleVisualizer} is shared by all instantiated {@link
     * Tripster}'s.
     * @param provider holds the configuration data.
     * @param sharedVisualizer visualizer shared by all tripsters returned by
     * this method.
     * @return the tripsters instantiated from configuration data.
     * @throws NullPointerException if any argument is {@code null}.
     * @throws RuntimeException if an error occurs while reading the 
     * configuration.
     * @see #fromConfig(ConfigProvider)
     */
    public static List<Tripster<String>> fromConfig(
            ConfigProvider<List<TripsterConfig>> provider,
            CycleVisualizer<String> sharedVisualizer) {
        return new TripsterConfigMapper<>(
                    entry -> new ArrayCycle<>(entry.getCycle()), 
                    entry -> new StdoutVisualizer<>())
              .fromConfig(provider); 
    }
    
    private Function<TripsterConfig, Cycle<T>> toCycle;
    private Function<TripsterConfig, CycleVisualizer<T>> assignVisualizer;
    
    /**
     * Creates a new mapper.
     * @param toCycle extracts a {@link Cycle} out of a config entry.
     * @param assignVisualizer decides which {@link CycleVisualizer} to assign
     * to the {@link Tripster} created out of the given config entry.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public TripsterConfigMapper(Function<TripsterConfig, Cycle<T>> toCycle,
            Function<TripsterConfig, CycleVisualizer<T>> assignVisualizer) {
        requireNonNull(toCycle, "toCycle");
        requireNonNull(assignVisualizer, "assignVisualizer");
        
        this.toCycle = toCycle;
        this.assignVisualizer = assignVisualizer;
    }
    
    private Tripster<T> newTripster(TripsterConfig entry) {
        return new Tripster<>(
                entry.getName(), 
                entry.getDescription(),
                toCycle.apply(entry),
                assignVisualizer.apply(entry));
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

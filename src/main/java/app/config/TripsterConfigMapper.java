package app.config;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;
import java.util.stream.Stream;

import util.config.ConfigMapper;
import util.config.ConfigProvider;
import app.core.cyclic.ArrayCycle;
import app.core.cyclic.Cycle;
import app.core.trips.Tripster;

/**
 * Maps configured {@link TripsterConfig}'s to {@link Tripster}'s.
 */
public class TripsterConfigMapper<T> 
    implements ConfigMapper<TripsterConfig, Tripster<T>>{
    
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
    
    @Override
    public Stream<Tripster<T>> fromConfig(
            ConfigProvider<TripsterConfig> provider) throws Exception {
        requireNonNull(provider, "provider");
        
        return provider.readConfig().map(this::newTripster);
    }
    
}

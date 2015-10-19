package app.config.mappers;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

import util.config.ConfigReader;
import util.config.ConfigProvider;
import app.config.items.TripsterConfig;
import app.core.cyclic.ArrayCycle;
import app.core.cyclic.Cycle;
import app.core.trips.Tripster;

/**
 * Maps configured {@link TripsterConfig}'s to {@link Tripster}'s.
 */
public class TripsterConfigMapper<T> 
    extends ConfigReader<TripsterConfig, Tripster<T>>{
    
    /**
     * Opinionated constructor utility.
     * Keeps config data as strings and uses an {@link ArrayCycle}.
     */
    public static TripsterConfigMapper<String> newWithStringArray(
            ConfigProvider<TripsterConfig> configSource) {
        return new TripsterConfigMapper<>(
                    configSource,
                    entry -> new ArrayCycle<>(entry.getCycle()));
    }
    
    private final Function<TripsterConfig, Cycle<T>> toCycle;
    
    /**
     * Creates a new mapper.
     * @param configSource reads in the raw tripster config items.
     * @param toCycle extracts a {@link Cycle} out of a config entry.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public TripsterConfigMapper(
            ConfigProvider<TripsterConfig> configSource,
            Function<TripsterConfig, Cycle<T>> toCycle) {
        super(configSource);
        requireNonNull(toCycle, "toCycle");
        this.toCycle = toCycle;
    }
    
    @Override
    protected Tripster<T> mapItem(TripsterConfig entry) {
        return new Tripster<>(
                entry.getName(), 
                entry.getDescription(),
                toCycle.apply(entry));
    }
    
}

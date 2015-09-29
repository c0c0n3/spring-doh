package app.config.mappers;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

import util.config.ConfigItemMapper;
import util.config.ConfigProvider;
import app.config.items.TripsterConfig;
import app.core.cyclic.ArrayCycle;
import app.core.cyclic.Cycle;
import app.core.trips.Tripster;

/**
 * Maps configured {@link TripsterConfig}'s to {@link Tripster}'s.
 */
public class TripsterConfigMapper<T> 
    extends ConfigItemMapper<TripsterConfig, Tripster<T>>{
    
    /**
     * Opinionated constructor utility.
     * Keeps config data as strings and uses an {@link ArrayCycle}.
     * @see #fromConfig(ConfigProvider)
     */
    public static TripsterConfigMapper<String> newWithStringArray() {
        return new TripsterConfigMapper<>(
                    entry -> new ArrayCycle<>(entry.getCycle()));
    }
    
    private final Function<TripsterConfig, Cycle<T>> toCycle;
    
    /**
     * Creates a new mapper.
     * @param toCycle extracts a {@link Cycle} out of a config entry.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public TripsterConfigMapper(Function<TripsterConfig, Cycle<T>> toCycle) {
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

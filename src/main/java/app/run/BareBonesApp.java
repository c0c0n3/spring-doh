package app.run;

import static util.Streams.asList;

import java.util.List;

import app.config.mappers.TripsterConfigMapper;
import app.config.providers.HardCodedTripsters;
import app.core.cyclic.StdoutVisualizer;
import app.core.trips.Tripster;
import app.core.trips.TripsterGroup;
import app.core.trips.TripsterSpotter;

/**
 * Simple CLI app with hard-coded configuration to exercise the core 
 * functionality without requiring any framework support.
 * To run it without the jar:
 * <pre>
 * ./gradlew build
 * java -cp build/classes/main app.Main app.run.BareBonesApp tripster n 
 * </pre>
 * where {@code tripster} is either "hipster" or "gauss" (from hard-coded 
 * config) and {@code n} is an integer.
 */
public class BareBonesApp extends AbstractCliApp {
    
    /**
     * @return our happy bunch of tripsters.
     */
    @SuppressWarnings("unchecked")
    @Override
    protected TripsterSpotter<String> spotter() {
        List<Tripster<String>> happyBunch = asList(
            TripsterConfigMapper.newWithStringArray(new HardCodedTripsters())
                                .defaultReadConfig());

        return new TripsterSpotter<>(new TripsterGroup<>(happyBunch),
                                     new StdoutVisualizer<>());
    }
    
}

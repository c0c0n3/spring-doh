package app.run;

import java.util.List;

import app.config.HardCodedTripsters;
import app.config.TripsterConfigMapper;
import app.core.cyclic.StdoutVisualizer;
import app.core.trips.Tripster;
import app.core.trips.TripsterGroup;

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
    protected TripsterGroup<String> tripsters() {
        List<Tripster<String>> happyBunch = 
                TripsterConfigMapper.fromConfig(
                        new HardCodedTripsters(),
                        new StdoutVisualizer<>());
                                           
        return new TripsterGroup<>(happyBunch);
    }
    
    @Override
    protected void runApp(String tripsterName, int legsTraveled) {
        tripsters().showWhereIs(tripsterName, legsTraveled);
    }
    
}

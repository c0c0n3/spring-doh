package app.run;

import static java.util.stream.Collectors.toList;

import java.util.List;

import app.config.DefaultTripsters;
import app.config.TripsterConfig;
import app.core.cyclic.ArrayCycle;
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
 * where {@code tripster} is either "hipster" or "gauss" (see code below) and
 * {@code n} is an integer.
 */
public class BareBonesApp extends AbstractCliApp {
    
    private Tripster<String> fromConfig(TripsterConfig entry) {
        return new Tripster<>(
                entry.getName(), 
                new ArrayCycle<>(entry.getCycle()),
                new StdoutVisualizer<>());
    }
    
    /**
     * @return our happy bunch of tripsters.
     */
    private TripsterGroup<String> tripsters() {
        List<Tripster<String>> happyBunch = new DefaultTripsters()
                                           .readConfig()
                                           .stream()
                                           .map(this::fromConfig)
                                           .collect(toList());
        return new TripsterGroup<>(happyBunch);
    }
    
    @Override
    protected void usage() {
        System.out.println("Required app arguments: <tripster> <n>");
        System.out.println("where n is an integer and tripster is one of:");
        System.out.println("\t'gauss' (ventures in numberland)");
        System.out.println("\t'hipster' (tours South Africa)");
    }
    
    @Override
    protected void runApp(String tripsterName, int legsTraveled) {
        tripsters().showWhereIs(tripsterName, legsTraveled);
    }
    
}

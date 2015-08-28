package integration.examples;

import static java.util.stream.Collectors.toList;
import static util.Arrayz.array;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import app.core.cyclic.ArrayCycle;
import app.core.cyclic.Cycle;
import app.core.cyclic.StdoutVisualizer;
import app.core.trips.Tripster;
import app.core.trips.TripsterGroup;

/**
 * Simple CLI app with hard-coded configuration to exercise the core 
 * functionality without requiring any framework support.
 * To run it:
 * <pre>
 * ./gradlew build
 * java -cp build/classes/main:build/classes/test integration.examples.BareBonesApp tripster n 
 * </pre>
 * where {@code tripster} is either "hipster" or "gauss" (see code below) and
 * {@code n} is an integer.
 */
public class BareBonesApp {

    /**
     * Ventures in numberland.
     * @return
     */
    private Tripster<String> gauss() {
        String[] intsMod5 = IntStream
                                .range(0, 5)
                                .boxed()
                                .map(i -> i + " (mod 5)")
                                .toArray(String[]::new);
        
        Cycle<String> trip = new ArrayCycle<>(intsMod5);
        return new Tripster<>("gauss", trip, new StdoutVisualizer<>());
    }
    
    /**
     * Tours South Africa.
     * @return
     */
    private Tripster<String> hipster() {
        Cycle<String> trip = new ArrayCycle<>(
                array("Cape Town", "Knysna", "Durbs", "Jozi"));
        return new Tripster<>("hipster", trip, new StdoutVisualizer<>());
    }
    
    /**
     * @return our group of tripsters.
     */
    private TripsterGroup<String> happyBunch() {
        return new TripsterGroup<>(
                Stream.of(gauss(), hipster()).collect(toList())
                );
    }
    
    private void run(String[] args) {
        String tripsterName = args[0];
        int legsTraveled = Integer.parseInt(args[1]);
        
        happyBunch().showWhereIs(tripsterName, legsTraveled);
    }
    
    private void usage() {
        System.out.println("To run this app:");
        System.out.println();
        System.out.println("java -cp build/classes/main:build/classes/test integration.examples.BareBonesApp <tripster> <n>");
        System.out.println();
        System.out.println("where n is an integer and tripster is one of:");
        System.out.println("\t'gauss' (ventures in numberland)");
        System.out.println("\t'hipster' (tours South Africa)");
    }
    
    public static void main(String[] args) {
        BareBonesApp app = new BareBonesApp();
        if (args.length < 2) {
            app.usage();
        }
        else {
            app.run(args);
        }
    }
    
}

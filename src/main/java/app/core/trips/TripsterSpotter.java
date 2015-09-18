package app.core.trips;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

import util.Pair;
import app.core.cyclic.CycleVisualizer;

/**
 * Tells what a happy bunch of tripsters are up to.
 */
public class TripsterSpotter<T> {

    private TripsterGroup<T> tripsters;
    private CycleVisualizer<T> visualizer;
    
    /**
     * Creates a new instance.
     * @param tripsters the happy bunch of tripsters to keep an eye on.
     * @param visualizer shows the trip to some output device.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public TripsterSpotter(TripsterGroup<T> tripsters, 
                           CycleVisualizer<T> visualizer) {
        requireNonNull(tripsters, "tripsters");
        requireNonNull(visualizer, "visualizer");
        
        this.tripsters = tripsters;
        this.visualizer = visualizer;
    }
    
    /**
     * Shows what legs of the trip the named tripster has traveled so far. 
     * @param tripsterName the name of the tripster.
     * @param legsTraveled number of hops away from the tripster's home.
     * @return {@code true} if the tripster was found and the trip information
     * was shown; {@code false} otherwise.
     */
    public boolean showWhereIs(String tripsterName, int legsTraveled) {
        return tripsters
                 .lookup(tripsterName)
                 .map(Tripster::getTrip)
                 .map(t -> { visualizer.show(t, legsTraveled); return 1; })
                 .isPresent();
    }
    
    /**
     * Collects the {@link TripsterGroup#describe() description}  of each 
     * tripster being watched into a string.
     * Each description output on its own line in this format: @code{n (d)},
     * where {@code n} is the tripster's name and {@code d} is the tripster's
     * description.
     * @return the formatted string.
     */
    public String describeTripsters() {
        return tripsters
               .describe()
               .map(this::formatTripsterLine)
               .collect(joining());
    }
    
    private String formatTripsterLine(Pair<String, String> p) {
        String name = p.fst(), desc = p.snd();
        return String.format("\t'%s' (%s)%n", name, desc);
    }
    
    /** 
     * @return the visualizer used to spot tripsties.
     */
    public CycleVisualizer<T> getVisualizer() {
        return visualizer;
    }
    
}

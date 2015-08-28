package app.core.trips;

import static java.util.Objects.requireNonNull;

import app.core.cyclic.Cycle;
import app.core.cyclic.CycleVisualizer;

/**
 * Goes on a trip away from home and eventually gets back home. 
 */
public class Tripster<T> {

    private String name;
    private Cycle<T> trip;
    private CycleVisualizer<T> visualizer;
    
    /**
     * Creates a new tripster who will travel from home to each place in his
     * {@code trip} description, eventually coming back home.
     * @param name this tripster's name.
     * @param trip the legs of the trip; first entry is this tripster's home.
     * @param visualizer shows the trip to some output device.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public Tripster(String name, Cycle<T> trip, CycleVisualizer<T> visualizer) {
        requireNonNull(name, "name");
        requireNonNull(trip, "trip");
        requireNonNull(visualizer, "visualizer");
        
        this.name = name;
        this.trip = trip;
        this.visualizer = visualizer;
    }
    
    /**
     * @return this tripster's name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Shows the legs of the trip this tripster has traveled so far. 
     * @param legsTraveled number hops away from home in the trip.
     */
    public void showTripSoFar(int legsTraveled) {
        visualizer.show(trip, legsTraveled);
    }
    
}

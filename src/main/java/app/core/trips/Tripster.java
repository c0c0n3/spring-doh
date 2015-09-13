package app.core.trips;

import static java.util.Objects.requireNonNull;

import app.core.cyclic.Cycle;

/**
 * Goes on a trip away from home and eventually gets back home. 
 */
public class Tripster<T> {

    private final String name;
    private final String description;
    private final Cycle<T> trip;

    /**
     * Creates a new tripster who will travel from home to each place in his
     * {@code trip} description, eventually coming back home.
     * @param name this tripster's name.
     * @param description this tripster's description.
     * @param trip the legs of the trip; first entry is this tripster's home.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public Tripster(String name, String description, Cycle<T> trip) {
        requireNonNull(name, "name");
        requireNonNull(description, "description");
        requireNonNull(trip, "trip");
        
        this.name = name;
        this.description = description;
        this.trip = trip;
    }
    
    /**
     * @return this tripster's name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return this tripster's description.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @return this tripster's trip.
     */
    public Cycle<T> getTrip() {
        return trip;
    }
    
}

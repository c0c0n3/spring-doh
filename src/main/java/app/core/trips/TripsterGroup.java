package app.core.trips;

import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

/**
 * A bunch of happy tripsters going places.
 */
public class TripsterGroup<T> {

    private Map<String, Tripster<T>> tripsters;
    
    private Map<String, Tripster<T>> buildMap(List<Tripster<T>> ts) {
        requireNonNull(ts, "tripsters");
        
        Collector<Tripster<T>, ?, Map<String, Tripster<T>>> putTripster = 
                toMap(Tripster<T>::getName, identity(), (oldT, newT) -> newT);
        
        return ts.stream().filter(t -> t != null).collect(putTripster);
    }
    
    /**
     * Creates a new group of tripsters.
     * @param tripsters the members of this group.
     */
    public TripsterGroup(List<Tripster<T>> tripsters) {
        this.tripsters = buildMap(tripsters);
    }
    
    /**
     * Shows what legs of the trip the named tripster has traveled so far. 
     * @param tripsterName the name of the tripster.
     * @param legsTraveled number of hops away from the tripster's home.
     */
    public void showWhereIs(String tripsterName, int legsTraveled) {
        requireNonNull(tripsterName, "tripsterName");
        
        Tripster<T> tripster = tripsters.get(tripsterName);
        if (tripster != null) {
            tripster.showTripSoFar(legsTraveled);
        }
    }
    
}

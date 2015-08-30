package app.beans;

import java.util.Collections;
import java.util.List;

import app.core.trips.Tripster;
import app.core.trips.TripsterGroup;

/**
 * Spring bean to hold a {@link TripsterGroup}.
 */
public class TripsterGroupBean<T> {

    private TripsterGroup<T> tripsties;
    
    /**
     * Creates a new instance with an empty group.
     */
    public TripsterGroupBean() {
        setGroup(Collections.emptyList());
    }
    
    /**
     * Sets a new group for the given {@code tripsters}. 
     * @param tripsters the new group's members.
     */
    public void setGroup(List<Tripster<T>> tripsters) {
        tripsties = new TripsterGroup<>(tripsters);
    }
    
    /**
     * Gets the current group.
     * This will be the group created by {@link #setGroup(List) setGroup()} or
     * a group containing no tripsters if {@link #setGroup(List) setGroup} has
     * never been called.
     * @return the current group; never {@code null}.
     */
    public TripsterGroup<T> getGroup() {
        return tripsties;
    }
    
}

package app.beans;

import org.springframework.stereotype.Component;

import app.core.cyclic.Cycle;
import app.core.cyclic.CycleVisualizer;
import app.core.trips.Tripster;

/**
 * Extends {@link Tripster} to make it a Spring bean.
 */
//@Component
public class TripsterBean<T> extends Tripster<T> {

    public TripsterBean(String name, Cycle<T> trip, CycleVisualizer<T> visualizer) {
        super(name, trip, visualizer);
    }

}

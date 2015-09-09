package app.beans;

import java.util.stream.Stream;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import util.Pair;
import app.core.cyclic.Cycle;
import app.core.cyclic.CycleTracker;

/**
 * Adapter to beanify a {@link CycleTracker}.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CycleTrackerBean<T> implements TrackableCycleBean<T> {

    private CycleTracker<T> adaptee;
    
    @Override
    public Stream<Pair<T, Cycle.Position>> iteratedSoFar() {
        return adaptee.iteratedSoFar();
    }

    @Override
    public T next() {
        return adaptee.next();
    }

    @Override
    public Cycle.Position where() {
        return adaptee.where();
    }

    @Override
    public void track(Cycle<T> target) {
        adaptee = new CycleTracker<>(target);
    }

}

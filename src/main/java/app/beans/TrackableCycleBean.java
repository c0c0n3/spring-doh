package app.beans;

import app.core.cyclic.Cycle;
import app.core.cyclic.TrackableCycle;

/**
 * Adapter interface to beanify a cycle tracker.
 * We can't make cycle tracker a bean because it needs to be instantiated with 
 * a {@link Cycle} but it's not possible to know, at configuration time, which 
 * cycle a given tracker will use. 
 * Note that the implementation bean must be a prototype as we need one bean 
 * for each cycle we come across at runtime.
 */
public interface TrackableCycleBean<T> extends TrackableCycle<T> {

    /**
     * Initialization method to set the bean state after it has been wired by
     * Spring. The bean is not usable until this method is called. 
     * @param target the cycle to track.
     * @throws NullPointerException if the argument is {@code null}.
     */
    void track(Cycle<T> target);
    
}
/* NOTE. We could change the tracker definition so that the cycle is not passed
 * into the ctor. But this would make the tracker's implementation more complex
 * as it couldn't be a value object anymore.
 * The key issue in general is that the easy way to do wiring in Spring is at
 * configuration time, i.e. when the container is started, which rules out 
 * creating value object beans whose state is constructed from input values
 * supplied at runtime.
 * Another option would be to create the bean dynamically at runtime via the
 * Spring API, but it seems messier and error prone, see e.g.
 * - http://stackoverflow.com/questions/11606504/registering-beansprototype-at-runtime-in-spring
 * - http://blog.jdriven.com/2015/04/spicy-spring-dynamically-create-your-own-beandefinition/
 * - http://stackoverflow.com/questions/15328904/dynamically-declare-beans-at-runtime-in-spring 
 */
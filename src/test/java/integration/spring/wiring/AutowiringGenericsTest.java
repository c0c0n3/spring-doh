package integration.spring.wiring;

import static util.Arrayz.array;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.config.Profiles;
import app.config.Wiring;
import app.core.cyclic.ArrayCycle;
import app.core.cyclic.CycleVisualizer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AutowiringGenericsTest.class)
@ComponentScan(basePackageClasses={Wiring.class, AutowiringGenericsTest.class})
@ActiveProfiles({Profiles.HardCodedConfig})
public class AutowiringGenericsTest {

    @Autowired
    private CycleVisualizer<String> stringVisualizer;
    
    @Autowired
    private CycleVisualizer<Integer> intVisualizer;
    
    @Autowired
    private TrackerBean<String> stringTracker;
    
    @Autowired
    private TrackerBean<Integer> intTracker;
    
    
    @Test
    public void stringVisualizerNotNull() {
        assertNotNull(stringVisualizer);
    }
    
    @Test
    public void intVisualizerNotNull() {
        assertNotNull(intVisualizer);
    }
    
    @Test
    public void visualizersAreNotSameInstanceEvenWithTypeErasure() {
        Object i = intVisualizer, s = stringVisualizer;
        assertTrue(i != s);   
    }
    /* NB StdoutVisualizerBean is a prototype, so regardless of type erasure
     * we get a fresh object every time. But if it was a singleton, then we'd
     * get the same object! (See test below for tracker bean.)
     * In fact, StdoutVisualizerBean<T> implements CycleVisualizer<T> so you'd 
     * expect StdoutVisualizerBean<String> to match CycleVisualizer<String> and
     * StdoutVisualizerBean<Integer> to match CycleVisualizer<Integer> but type
     * information is not available to Spring at runtime (due to type erasure)
     * when beans are instantiated. So if the bean was a singleton (default) 
     * Spring would create a single bean named 'stdoutVisualizerBean' and return
     * it in both instances.
     */
    
    @Test
    public void visualizersWorkByAccidentAsNotAffectedByTypeErasure() {
        stringVisualizer.show(new ArrayCycle<>(array("x")), 2);
        intVisualizer.show(new ArrayCycle<>(array(1)), 2);
    }
    /* NB this would work even if stringVisualizer == intVisualizer (i.e. the
     * bean was a singleton instead of a prototype) due to type erasure!
     * The horror!
     */
    
    @Test
    public void stringTrackerNotNull() {
        assertNotNull(stringTracker);
    }
    
    @Test
    public void intTrackerNotNull() {
        assertNotNull(intTracker);
    }
    
    @Test
    public void trackersAreSameInstanceDueToTypeErasure() {
        Object i = intTracker, s = stringTracker;
        assertTrue(i == s);  // horror!!!
    }
    
    @Test(expected = ArrayStoreException.class)
    public void trackersDontWorkAsAffectedByTypeErasure() {
        Integer[] intCycle = array(1, 2);
        String[] stringCycle = array("a", "b");
        
        // intTracker and stringTracker are the same object, see test above...
        
        intTracker.track(intCycle);        // sets to work with Integer[]
        stringTracker.track(stringCycle);  // overrides to work with String[]
        
        intTracker.iterate(2);             // no prob here as type not used
        stringTracker.iterate(2);          // ditto
        
        intTracker.collectTracked(Integer[]::new);  // kaboom!
        
        // see JavaConfigGenericsTest for how to fix this issue in the case
        // you have a *finite* set of types to work with...
    }
    
}

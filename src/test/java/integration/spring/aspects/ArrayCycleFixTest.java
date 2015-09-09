package integration.spring.aspects;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.beans.TrackableCycleBean;
import app.config.Profiles;
import app.config.Wiring;
import app.core.cyclic.ArrayCycle;
import app.core.cyclic.Cycle;
import app.core.cyclic.Cycles;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Wiring.class)
@ActiveProfiles(Profiles.HardCodedConfig)
public class ArrayCycleFixTest {

    private static Cycle<Integer> newArrayCycle(Integer...xs) {
        return new ArrayCycle<Integer>(xs);
    }
    
    private static Cycle<Integer> newArrayCycleWithNullArray() {
        return newArrayCycle((Integer[])null);
    }
    
    private static <T> void assertAllNulls(List<T> ts, int expectedSize) {
        assertNotNull(ts);
        assertEquals(expectedSize, ts.size());
        ts.forEach(t -> assertNull(t));
    }
    
    @Autowired
    private TrackableCycleBean<Integer> advisedCyle;
    
    
    @Test(expected = NullPointerException.class)
    public void nullCycleNextThows() {
        Cycle<Integer> target = newArrayCycleWithNullArray();
        target.next();
    }

    @Test(expected = ArithmeticException.class)
    public void emptyCycleNextThows() {
        Cycle<Integer> target = newArrayCycle();
        target.next();
    }
    
    @Test(expected = NullPointerException.class)
    public void nullCycleAdvanceThows() {
        Cycle<Integer> target = newArrayCycleWithNullArray();
        target.advance();
    }

    @Test(expected = ArithmeticException.class)
    public void emptyCycleAdvanceThows() {
        Cycle<Integer> target = newArrayCycle();
        target.advance();
    }
    
    @Test
    public void advisedCycleNextDoesntThowOnNullCycle() {
        advisedCyle.track(newArrayCycleWithNullArray());
        advisedCyle.next();
    }

    @Test(expected = NullPointerException.class)
    public void advisedCycleAdvanceStillThowsOnNullCycle() {
        advisedCyle.track(newArrayCycleWithNullArray());
        advisedCyle.advance();
    }
    /* NB advance is a default method that calls next()
     * this must be another limitation of Spring AOP due to their use of dyn
     * proxies; in fact, calls using 'this' within an advised object can't be
     * advised--see Spring manual: '10.6.1 Understanding AOP proxies'.
     */
    
    @Test
    public void advisedCycleNextDoesntThowOnEmptyCycle() {
        advisedCyle.track(newArrayCycle());
        advisedCyle.next();
    }

    @Test(expected = ArithmeticException.class)
    public void advisedCycleAdvanceStillThowsOnEmptyCycle() {
        advisedCyle.track(newArrayCycle());
        advisedCyle.advance();
    }
    // NB advance is a default method that calls next()
    
    private void assertCollectsNulls(Cycle<Integer> x) {
        advisedCyle.track(x);
        
        List<Integer> shouldAllBeNull = Cycles.collect(2, advisedCyle);
        assertAllNulls(shouldAllBeNull, 2);
    }
    
    private void assertTracksNothing(Cycle<Integer> x) {
        assertCollectsNulls(x);
        
        List<Integer> shouldBeEmpty = Cycles.collectTracked(advisedCyle);
        assertEquals(0, shouldBeEmpty.size());
    }
    
    @Test
    public void advisedCycleCollectsNullsIfNullCycle() {
        assertCollectsNulls(newArrayCycleWithNullArray());
    }
    
    @Test
    public void advisedCycleTracksNothingIfNullCycle() {
        assertTracksNothing(newArrayCycleWithNullArray());
    }
    
    @Test
    public void advisedCycleCollectsNullsIfEmptyCycle() {
        assertCollectsNulls(newArrayCycle());
    }
    
    @Test
    public void advisedCycleTracksNothingIfEmptyCycle() {
        assertTracksNothing(newArrayCycle());
    }
    
}

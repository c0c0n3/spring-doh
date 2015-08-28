package app.core.cyclic;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static util.Arrayz.array;

import java.util.List;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import util.Arrayz;
import util.Pair;

@RunWith(Theories.class)
public class CycleTrackerTest {

    @DataPoints
    public static Integer[][] cycles = new Integer[][] {
        array(), array(10), array(10, 11), array(10, 11, 12, 13, 14)
    };
    
    public static CycleTracker<Integer> newCycleTracker(Integer...ts) {
        Cycle<Integer> target = ArrayCycleTest.newCycle(ts);
        return new CycleTracker<>(target);
    }

    @Theory
    public void assertIterated(Integer...ts) {
        CycleTracker<Integer> target = newCycleTracker(ts);
        assertThat(target.iteratedSoFar().toArray().length, is(0));

        List<Integer[]> intialSegments = Arrayz.op(Integer[]::new).inits(ts);
        for (int k = 0; k < ts.length; ++k) {
            Integer[] expected = intialSegments.get(k+1);

            target.next();
            Integer[] actual = target.iteratedSoFar()
                                     .map(Pair::fst)
                                     .toArray(Integer[]::new);

            assertArrayEquals(expected, actual);
        }
    }
    
}

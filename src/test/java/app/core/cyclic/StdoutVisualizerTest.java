package app.core.cyclic;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static util.Arrayz.*;
import static util.Streams.lines;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.boot.test.OutputCapture;

import util.Pair;

@RunWith(Theories.class)
public class StdoutVisualizerTest {
    
    @DataPoints
    public static int[] lengths = new int[] { -1, 0, 1, 2, 3, 4, 5 };
    
    @DataPoints
    public static Integer[][] cycles = new Integer[][] { 
        array(1), array(1, 2), array(1, 2, 3)
    };
    
    public static <T> CycleTracker<T> newCycleTracker(T[] ts) {
        Cycle<T> target = new ArrayCycle<>(ts);
        return new CycleTracker<>(target);
    }
    
    @SuppressWarnings("unchecked")
    private static <T> 
    Matcher<String>[] buildLineMatchers(CycleTracker<T> tracker) {
        return tracker
              .iteratedSoFar()
              .map(Pair::fst)
              .map(Object::toString)
              .map(CoreMatchers::containsString)
              .toArray(Matcher[]::new);
    }
    
    @Rule
    public OutputCapture capture = new OutputCapture();
    
    private <T> Pair<String[], Matcher<String>[]> run(T[] cycle, int length) {
        CycleTracker<T> tracker = newCycleTracker(cycle);
        CycleVisualizer<T> target = new StdoutVisualizer<>();
        
        target.show(tracker, length);
        
        Matcher<String>[] matchers = buildLineMatchers(tracker);
        String[] outputLines = lines(capture.toString()).toArray(String[]::new); 
        return new Pair<>(outputLines, matchers);
    }
    
    @Theory
    public void oneCycleElementPerLine(Integer[] cycle, int length) {
        Pair<String[], Matcher<String>[]> p = run(cycle, length);
        
        String[] outputLines = p.fst();
        Matcher<String>[] matchers = p.snd();
        
        assertThat(outputLines.length, is(matchers.length));
        for (int k = 0; k < matchers.length; ++k) {
            assertThat(outputLines[k], matchers[k]);
        }
    }

}

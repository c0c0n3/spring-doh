package app.core.cyclic;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static util.Streams.lines;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;

import util.Pair;

public abstract class BaseTextVisualizerTest<T> {

    protected CycleTracker<T> newCycleTracker(T[] ts) {
        Cycle<T> target = new ArrayCycle<>(ts);
        return new CycleTracker<>(target);
    }
    
    @SuppressWarnings("unchecked")
    protected 
    Matcher<String>[] buildLineMatchers(CycleTracker<T> tracker) {
        return tracker
              .iteratedSoFar()
              .map(Pair::fst)
              .map(Object::toString)
              .map(CoreMatchers::containsString)
              .toArray(Matcher[]::new);
    }
    
    protected Pair<String[], Matcher<String>[]> run(T[] cycle, int length) {
        CycleTracker<T> tracker = newCycleTracker(cycle);
        CycleVisualizer<T> target = newVisualizer();
        
        target.show(tracker, length);
        
        Matcher<String>[] matchers = buildLineMatchers(tracker);
        String[] outputLines = lines(capturedOutput()).toArray(String[]::new); 
        return new Pair<>(outputLines, matchers);
    }
    
    protected void assertOneCycleElementPerLine(T[] cycle, int length) {
        Pair<String[], Matcher<String>[]> p = run(cycle, length);
        
        String[] outputLines = p.fst();
        Matcher<String>[] matchers = p.snd();
        
        assertThat(outputLines.length, is(matchers.length));
        for (int k = 0; k < matchers.length; ++k) {
            assertThat(outputLines[k], matchers[k]);
        }
    }
    
    protected abstract CycleVisualizer<T> newVisualizer();
    
    protected abstract String capturedOutput();
    
}

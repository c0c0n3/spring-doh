package app.beans;

import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

import app.core.cyclic.CycleVisualizer;
import app.core.cyclic.TextVisualizerTest;

@RunWith(Theories.class)
public class StringVisualizerBeanTest extends TextVisualizerTest {

    private StringVisualizerBean<Integer> target;
    
    protected CycleVisualizer<Integer> newVisualizer() {
        target = new StringVisualizerBean<>();
        return target;
    }
    
    protected String capturedOutput() {
        return target.getShown();
    }
    
}

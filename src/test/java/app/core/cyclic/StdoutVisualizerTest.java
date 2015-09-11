package app.core.cyclic;

import org.junit.Rule;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;
import org.springframework.boot.test.OutputCapture;


@RunWith(Theories.class)
public class StdoutVisualizerTest extends TextVisualizerTest {
    
    @Rule
    public OutputCapture capture = new OutputCapture();
    
    protected CycleVisualizer<Integer> newVisualizer() {
        return new StdoutVisualizer<>();
    }
    
    protected String capturedOutput() {
        return capture.toString();
    }

}

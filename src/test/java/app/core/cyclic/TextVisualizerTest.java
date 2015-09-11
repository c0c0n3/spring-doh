package app.core.cyclic;

import static util.Arrayz.*;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;


@RunWith(Theories.class)
public class TextVisualizerTest extends BaseTextVisualizerTest<Integer> {
    
    @DataPoints
    public static int[] lengths = new int[] { -1, 0, 1, 2, 3, 4, 5 };
    
    @DataPoints
    public static Integer[][] cycles = new Integer[][] { 
        array(1), array(1, 2), array(1, 2, 3)
    };
    
    
    private StringWriter outputBuffer;
    
    protected CycleVisualizer<Integer> newVisualizer() {
        outputBuffer = new StringWriter();
        PrintWriter out = new PrintWriter(outputBuffer);
        return new TextVisualizer<>(out);
    }
    
    protected String capturedOutput() {
        return outputBuffer.toString();
    }
        
    @Theory
    public void oneCycleElementPerLine(Integer[] cycle, int length) {
        assertOneCycleElementPerLine(cycle, length);        
    }
    
}

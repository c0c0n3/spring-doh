package app.core.cyclic;

import java.io.PrintWriter;

/**
 * Prints a {@link Cycle} path to standard out.
 * Cycle elements are converted to strings by calling their {@code toString}
 * method; {@code null}'s are printed literally as "null". 
 */
public class StdoutVisualizer<T> implements CycleVisualizer<T> {
    
    @Override
    public void show(Cycle<T> target, int length) {
        PrintWriter out = new PrintWriter(System.out);
        TextVisualizer<T> visualizer = new TextVisualizer<>(out);
        visualizer.show(target, length);
    }
        
}

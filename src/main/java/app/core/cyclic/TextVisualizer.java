package app.core.cyclic;

import static java.util.Objects.requireNonNull;

import java.io.PrintWriter;

import util.Pair;
import app.core.cyclic.Cycle.Position;

/**
 * Prints a {@link Cycle} path to an output stream.
 * Cycle elements are converted to strings by calling their {@code toString}
 * method; {@code null}'s are printed literally as "null". 
 */
public class TextVisualizer<T> implements CycleVisualizer<T> {

    /**
     * Format strings used to print cycle elements based on their position in
     * the cycle.
     */
    public enum Format {
        
        Start("\t● %s"), OnWay("\t\t▬▶ %s");
        
        private String format;
        
        private Format(String format) {
            this.format = format + System.getProperty("line.separator");
        }
        
        public String get() {
            return format;
        }
    }
    
    private final PrintWriter out;
    
    /**
     * Creates a new instance to write the formatted cycle path to the given
     * destination stream.
     * @param out where to write the formatted text.
     * @throws NullPointerException if the argument is {@code null}.
     */
    public TextVisualizer(PrintWriter out) {
        requireNonNull(out);
        this.out = out;
    }
    
    @Override
    public void show(Cycle<T> target, int length) {
        if (length <= 0) return;
        requireNonNull(target, "target");
        
        Cycles.streamWithPosition(target)
              .limit(length)
              .forEach(p -> print(p));
        
        out.flush();
    }
    
    private void print(Pair<T, Position> element) {
        if (element == null) {
            out.println("null");
        }
        else {
            String format = lookupFormat(element.snd());
            String formatted = String.format(format, element.fst());  // (*)
            out.print(formatted);
        }
    }
    // (*) String.format will turn a null reference into "null".
    
    private String lookupFormat(Position which) {
        switch (which) {
        case Start: return Format.Start.get();
        case OnWay: return Format.OnWay.get();
        default: return null; // keep compiler happy as it can't do case analysis...
        }
    }
    
}

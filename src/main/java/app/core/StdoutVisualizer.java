package app.core;

import static java.util.Objects.requireNonNull;
import util.Arrayz;
import util.Pair;
import app.core.Cycle.Position;

/**
 * Prints a {@link Cycle} to standard out.
 * Cycle elements are converted to strings by calling their {@code toString}
 * method; {@code null}'s are printed literally as "null". 
 */
public class StdoutVisualizer<T> implements CycleVisualizer<T> {

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
    
    @Override
    public void show(Cycle<T> target, int length) {
        if (length <= 0) return;
        requireNonNull(target, "target");
        
        Cycles.streamWithPosition(target)
              .limit(length)
              .forEach(p -> print(p));
    }
    
    private void print(Pair<T, Position> element) {
        if (element == null) {
            System.out.println("null");
        }
        else {
            String format = lookupFormat(element.snd());
            String formatted = String.format(format, element.fst());  // (*)
            System.out.print(formatted);
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
    
    public static void main(String[] args) {
        String[] elements = Arrayz.array("Cape Town", "Knysna", "Durbs", "Jozi");
        Cycle<String> zaTrip = new ArrayCycle<>(elements);
        StdoutVisualizer<String> printer = new StdoutVisualizer<>();
        
        printer.show(zaTrip, 6);
    }
    
}

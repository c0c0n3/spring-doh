package util;

import static java.util.Objects.requireNonNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Consumer;

/**
 * String utilities
 */
public class Strings {

    /**
     * Is this a {@code null} or empty string?
     * @param x the string to test.
     * @return {@code true} for yes, {@code false} for no. 
     */
    public static boolean isNullOrEmpty(String x) {
        return x == null || x.isEmpty();
    }
    
    /**
     * Gives a {@link Consumer} a {@link PrintWriter} to write some text to.
     * @param writer the consumer that will produce the text.
     * @return the text produced by the consumer.
     * @throws NullPointerException if the argument is {@code null}.
     */
    public static String write(Consumer<PrintWriter> writer) {
        requireNonNull(writer, "writer");
        
        StringWriter buffer = new StringWriter();
        PrintWriter out = new PrintWriter(buffer);
        
        writer.accept(out);
        out.flush();
        
        return buffer.toString();
    }
    
}

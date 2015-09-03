package util;

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
    
    
}

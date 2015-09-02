package util;

import static java.util.Objects.requireNonNull;

/**
 * Helper methods to work with exceptions.
 */
public class Exceptions {

    /**
     * Masks the given exception as a runtime (unchecked) exception and throws
     * it as such, fooling compiler and JVM runtime. Use with extreme care! 
     * @param t the exception to recast to {@link RuntimeException}.
     * @return nothing as {@code t} will be thrown.
     * @see
     * <a href="http://www.philandstuff.com/2012/04/28/sneakily-throwing-checked-exceptions.html">
     * How does it work?</a>
     * @see
     * <a href="http://programmers.stackexchange.com/questions/225931/workaround-for-java-checked-exceptions">
     * Advantages, disadvantages, and limitations.
     * </a>
     */
    public static RuntimeException uncheck(Throwable t) {
        requireNonNull(t, "t");
                
        Exceptions.<RuntimeException> throwAs(t);
        return null;
    }

    @SuppressWarnings("unchecked")
    private static 
    <T extends Throwable> void throwAs(Throwable t) throws T {
        throw (T) t;
    }

}

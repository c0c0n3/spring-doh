package app.beans;

/**
 * Iterates a finite sequence of <code>T</code>'s, starting over from the first
 * element after reaching the last one.
 */
public interface Cycle<T> {
    
    /**
     * @return the next element in the cycle.
     */
    public T next();
    
}

package app.beans;

/**
 * Implements {@link Cycle} to iterate over an array.
 */
public class ArrayCycle<T> implements Cycle<T> {

    private int position;
    private T[] elements;
    
    /**
     * Creates a new instance.
     * @param ts the array to iterate over.
     */
    public ArrayCycle(T[] ts) {
        elements = ts;  // next() will throw if ts == null or ts.len == 0...
        position = -1;
    }
    
    @Override
    public T next() {
        position = (position + 1) % elements.length; // see note below
        return elements[position];
    }
    // NOTE: elements == null      => NullPointerException
    //       elements.length == 0  => ArithmeticException

}

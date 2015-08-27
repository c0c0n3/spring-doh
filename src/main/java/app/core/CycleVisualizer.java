package app.core;

/**
 * Renders a {@link Cycle} on some output device.
 */
public interface CycleVisualizer<T> {

    /**
     * Collects {@code length} elements from the cycle and shows them on an
     * output device.
     * @param target the cycle to get elements from.
     * @param length how many elements to get; negative or zero means "get no
     * elements" so nothing is shown.
     * @throws NullPointerException if {@code null} arguments.
     */
    void show(Cycle<T> target, int length);
    
}

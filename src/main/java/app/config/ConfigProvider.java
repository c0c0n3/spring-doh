package app.config;

import static util.Exceptions.unchecked;

/**
 * Reads some configuration item of type {@code T}.
 */
public interface ConfigProvider<T> {

    /**
     * Reads the configuration item.
     * @return the item.
     * @throws Exception if the item could not be read.
     */
    T readConfig() throws Exception;
    
    /**
     * Calls {@link ConfigProvider#readConfig() readConfig} converting any
     * checked exception into an unchecked one that will bubble up without
     * requiring a 'throws' clause on this method.
     * This is because configuration exceptions are typically non-recoverable
     * (i.e. we can't start the app if we have no sound config) and so it's
     * sort of pointless to have checked exceptions in this case.
     * @return whatever {@code readConfig} would return.
     */
    default T defaultReadConfig() {
        return unchecked(this::readConfig).get();
    }
    
}

package app.config;

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
     * exception into a {@link RuntimeException}.
     * @return whatever {@code readConfig} would return.
     */
    default T defaultReadConfig() {
        try {
            return readConfig();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}

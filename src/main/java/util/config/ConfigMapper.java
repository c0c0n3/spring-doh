package util.config;

import static util.Exceptions.unchecked;

import java.util.stream.Stream;

/**
 * Configuration mappers read configuration items of type {@code T} and 
 * transform that data into objects of type {@code M}.
 */
public interface ConfigMapper<T, M> {

    /**
     * Uses the given provider to read {@code T}'s from configuration and
     * transform them into {@code M}'s.
     * @param provider holds the configuration data.
     * @return the mapped objects; the returned stream shall never be 
     * {@code null}.
     * @throws NullPointerException if the argument is {@code null}.
     * @throws Exception if an error occurred while reading configuration.
     */
    Stream<M> fromConfig(ConfigProvider<T> provider) throws Exception;
    
    /**
     * Calls {@link #fromConfig(ConfigProvider) fromConfig} converting any
     * checked exception into an unchecked one that will bubble up without
     * requiring a 'throws' clause on this method.
     * This is because configuration exceptions are typically non-recoverable
     * (i.e. we can't start the app if we have no sound config) and so it's
     * sort of pointless to have checked exceptions in this case.
     * @return whatever {@code fromConfig} would return.
     */
    default Stream<M> defaultFromConfig(ConfigProvider<T> provider) {
        return unchecked(this::fromConfig).apply(provider);
    }
    
}

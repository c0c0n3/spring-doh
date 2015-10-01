package util.config;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import util.Streams;

/**
 * Wraps a source configuration provider so that if the source produces no 
 * configuration items, a fall-back configuration stream is used. Note that
 * if the source throws an exception, it will be propagated as is without
 * attempting to return the fall-back items in place of the error.
 */
public class FallbackConfigProvider<T> implements ConfigProvider<T> {

    private final ConfigProvider<T> source;
    private final Supplier<Stream<T>> fallback;

    /**
     * Creates a new instance.
     * @param source the source provider.
     * @param fallback fall-back items to use if the source provider produces
     * no items.
     */
    public FallbackConfigProvider(ConfigProvider<T> source,
                                  Supplier<Stream<T>> fallback) {
        requireNonNull(source, "source");
        requireNonNull(fallback, "fallback");
        
        this.source = source;
        this.fallback = fallback;
    }
    
    @Override
    public Stream<T> readConfig() throws Exception {
        Stream<T> sourceConfig = source.readConfig();
        return Optional.ofNullable(sourceConfig)  // (*)
                       .map(Streams::asList)
                       .map(ts -> ts.isEmpty() ? null : ts.stream())
                       .orElseGet(fallback);
    }
    /* (*) readConfig should never return null, but you better look over 
     * your shoulder in Java land...
     */
}

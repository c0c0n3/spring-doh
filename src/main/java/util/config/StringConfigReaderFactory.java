package util.config;

import static util.Exceptions.unchecked;

import java.net.URI;
import java.util.function.Function;

import util.lambda.FunctionE;

/**
 * Factory methods to create configuration providers for common cases where
 * strings are read from an underlying configuration store and transformed
 * into objects.
 */
public class StringConfigReaderFactory {

    /**
     * Creates a boolean configuration reader.
     * String values are read from the provided source configuration store 
     * using Boolean's {@link Boolean#valueOf(String) valueOf} method.
     * @param configSource reads string items from configuration.
     * @return a configuration provider to read boolean items.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public static 
    ConfigProvider<Boolean> makeBool(ConfigProvider<String> configSource) {
        return ConfigReader.newReader(configSource, Boolean::valueOf);
    }
    
    /**
     * Creates an integer configuration reader.
     * String values are read from the provided source configuration store 
     * using Integer's {@link Integer#valueOf(String) valueOf} method.
     * @param configSource reads string items from configuration.
     * @return a configuration provider to read integer items.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public static 
    ConfigProvider<Integer> makeInt(ConfigProvider<String> configSource) {
        return ConfigReader.newReader(configSource, Integer::valueOf);
    }
    
    /**
     * Creates a URI configuration reader.
     * String values are read from the provided source configuration store 
     * using URI's {@link Integer#valueOf(String) valueOf} method.
     * @param configSource reads string items from configuration.
     * @return a configuration provider to read integer items.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public static 
    ConfigProvider<URI> makeURI(ConfigProvider<String> configSource) {
        Function<String, URI> fromString = 
                unchecked((FunctionE<String, URI>) URI::new);
        return ConfigReader.newReader(configSource, fromString);
    }
    
}

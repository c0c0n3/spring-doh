package util.config;

import static java.util.Objects.requireNonNull;
import static util.Exceptions.unchecked;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Configuration mapper to apply a transformation to each item read from a
 * configuration provider.
 */
public abstract class ConfigItemMapper<T, M> implements ConfigMapper<T, M> {

    /**
     * Creates a new instance.
     * @param itemMapper the transformation to apply to each item read from a
     * configuration provider.
     * @throws NullPointerException if the argument is {@code null}.
     */
    public static <T, M> ConfigMapper<T, M> newMapper(Function<T, M> itemMapper) {
        requireNonNull(itemMapper, "itemMapper");
        
        return new ConfigItemMapper<T, M>() {
            @Override
            protected M mapItem(T configEntry) {
                return itemMapper.apply(configEntry);
            }
        };
    }
    
    /**
     * A transformation to apply to each item read from a configuration 
     * provider.
     * @param configEntry the configuration item to transform.
     * @return the transformed item.
     * @throws Exception if a transformation error occurs, e.g. parsing a string
     * into an integer.
     */
    protected abstract M mapItem(T configEntry) throws Exception;
    
    @Override
    public Stream<M> fromConfig(ConfigProvider<T> provider) throws Exception {
        requireNonNull(provider, "provider");
        return provider.readConfig()
                       .map(unchecked(this::mapItem));
    }

}

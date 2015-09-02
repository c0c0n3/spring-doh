package app.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 * Reads tripster configuration from a YAML file.
 */
@Component
@Profile(Profiles.ConfigFile)
public class FileTripsters implements ConfigProvider<List<TripsterConfig>> {

    @Autowired
    private ResourceLoader resourceLoader;
    
    private static <X> Function<X, Optional<X>> maybe(X value) {
        return maybe(Function.identity());
    }
    
    private static <X> Supplier<Optional<X>> maybe(Supplier<X> f) {
        Objects.requireNonNull(f, "f");
        return () -> Optional.ofNullable(f.get());
    }
    
    private static <X, Y> Function<X, Optional<Y>> maybe(Function<X, Y> f) {
        Objects.requireNonNull(f, "f");
        return x -> Optional.ofNullable(f.apply(x));
    }
    
    
    
    
    private Optional<ResourceLoader> ensureLoader(ResourceLoader candidate) {
        return Optional.ofNullable(candidate);
    }
    
    private Optional<Resource> ensureResource(Resource candidate) {
        if (candidate != null && candidate.exists()) {
            return Optional.of(candidate);
        }
        return Optional.empty();
    }
    
    private Optional<InputStream> ensureStream(Resource candidate) throws IOException {
        InputStream in = candidate.getInputStream();
        return Optional.ofNullable(in);
    }
    
    private InputStream findConfigFile() throws IOException {
        InputStream tripstersYmlStream = null;
        if (resourceLoader != null) {
            Resource tripstersYml = resourceLoader.getResource("xxx");
            if (tripstersYml != null && tripstersYml.exists()) {
                tripstersYmlStream = tripstersYml.getInputStream();
            }
        }
        return tripstersYmlStream;
    }
    
    private InputStream resGetInputStream(Resource r) {
        try {
            return r.getInputStream();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    static <X, Y> Function<X, Y> wrap(Function<X, Y> f) {
        return x -> {
            try {
                return f.apply(x);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
    /*
    private void x() throws IOException {
        Optional
        .ofNullable(resourceLoader)
        .map(loader -> loader.getResource("???"))
        .map(resource -> resource.exists() ? resource : null)
        .map(wrap(Resource::getInputStream));
        //.map(resource -> resource.getInputStream());
        
        Optional
        .ofNullable(resourceLoader)
        .map(rl -> rl.getResource("???"))
        .map(this::resGetInputStream);
        //.flatMap(maybe(loader -> loader::getResource));
        
    }
    */
    /**
     * Reads the happy bunch of tripsters from configuration.
     * @return all configured tripsters; never {@code null}.
     */
    @Override
    public List<TripsterConfig> readConfig() throws Exception {
        // TODO read from xml config file instead
        ConfigProvider<List<TripsterConfig>> provider = new HardCodedTripsters(); 
        return provider.defaultReadConfig();
    }

}

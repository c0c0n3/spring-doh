package app.config.providers;

import static util.spring.io.ResourceLocation.classpath;
import static util.spring.io.ResourceLocation.filepathFromCwd;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import app.config.Profiles;
import app.config.items.TripsterConfig;
import util.config.ConfigProvider;
import util.config.YamlConverter;
import util.spring.io.FifoResource;
import util.spring.io.ResourceLocation;
import util.spring.io.ResourceReader;


/**
 * Reads tripster configuration from a YAML file, falling back to hard-coded
 * configuration if no file is avaliable.
 * This provider will first try to read the file from the {@link #PwdConfig
 * current directory}; failing that, it will try to find the file in the
 * {@link #ClasspathConfig classpath}, falling back to hard-code config if
 * not found.
 */
@Component
@Profile(Profiles.ConfigFile)
public class FileTripsters
    extends FifoResource<TripsterConfig>
    implements ConfigProvider<TripsterConfig> {

    /**
     * Location of the YAML file in the classpath.
     */
    public static final ResourceLocation ClasspathConfig = 
            classpath("config", "tripsters.yml");
    
    /**
     * Location of the YAML file in the current directory.
     */
    public static final ResourceLocation PwdConfig = 
            filepathFromCwd("tripsters.yml"); 

    
    @Autowired
    private ResourceLoader resourceLoader;
    
    @Override
    protected ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    @Override
    protected ResourceReader<TripsterConfig> getConverter() {
        return new YamlConverter<TripsterConfig>()::fromYamlList;
    }
    
    /**
     * Reads the happy bunch of tripsters from configuration.
     * @return all configured tripsters; never {@code null}.
     */
    @Override
    public Stream<TripsterConfig> readConfig() throws Exception {
        return read(PwdConfig, ClasspathConfig);
    }
    
    @Override 
    protected Stream<TripsterConfig> getFallback() {
        return new HardCodedTripsters().defaultReadConfig();
    }
    
    /**
     * Reads config from the first available out of the specified locations,
     * falling back to hard-coded config if no file is available.
     */
    public Stream<TripsterConfig> readConfig(ResourceLocation...loci) 
            throws Exception {
        return read(loci);
    }
    
}

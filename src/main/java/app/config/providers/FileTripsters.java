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
import util.config.FallbackConfigProvider;
import util.config.YamlConverter;
import util.spring.config.LociResourceConfigProvider;
import util.spring.io.FifoResourceLoaderAdapter;
import util.spring.io.ResourceLocation;


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
public class FileTripsters implements ConfigProvider<TripsterConfig> {

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
    
    /**
     * Reads the happy bunch of tripsters from configuration.
     * @return all configured tripsters; never {@code null}.
     */
    @Override
    public Stream<TripsterConfig> readConfig() throws Exception {
        return readConfig(PwdConfig, ClasspathConfig);
    }
    
    /**
     * Reads config from the first available out of the specified locations,
     * falling back to hard-coded config if no file is available.
     */
    public Stream<TripsterConfig> readConfig(ResourceLocation...loci) 
            throws Exception {
        ConfigProvider<TripsterConfig> source = 
                new LociResourceConfigProvider<>(
                        new FifoResourceLoaderAdapter(resourceLoader), 
                        new YamlConverter<TripsterConfig>()::fromYamlList, 
                        loci);
        
        ConfigProvider<TripsterConfig> sourceOrFallback =
                new FallbackConfigProvider<>(
                        source, 
                        new HardCodedTripsters()::defaultReadConfig);
        
        return sourceOrFallback.readConfig();
    }
    
}

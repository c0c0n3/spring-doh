package app.config.providers;

import java.util.stream.Stream;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import app.config.Profiles;
import app.config.items.TripsterConfig;
import util.config.YamlConverter;
import util.spring.io.ResourceReader;


/**
 * Reads tripster configuration from a YAML file, falling back to hard-coded
 * configuration if no file is available.
 * This provider will first try to read the file from the current directory; 
 * failing that, it will try to find the file in the class-path, falling back
 * to hard-code config if not found.
 */
@Component
@Profile(Profiles.ConfigFile)
public class FileTripsters extends PriorityConfigProvider<TripsterConfig> {

    public static final String FileName = "tripsters.yml";
    
    @Override
    protected ResourceReader<TripsterConfig> getConverter() {
        return new YamlConverter<TripsterConfig>()::fromYamlList;
    }
    
    @Override 
    public Stream<TripsterConfig> getFallback() {
        return new HardCodedTripsters().defaultReadConfig();
    }

    @Override
    public String getConfigFileName() {
        return FileName;
    }
    
}

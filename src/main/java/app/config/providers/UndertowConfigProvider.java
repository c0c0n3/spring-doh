package app.config.providers;

import java.util.stream.Stream;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import util.config.YamlConverter;
import util.spring.io.ResourceReader;
import app.config.Profiles;
import app.config.data.UndertowYmlFile;
import app.config.items.UndertowConfig;


/**
 * Reads Undertow configuration from a YAML file, falling back to hard-coded
 * configuration if no file is available.
 * This provider will first try to read the file from the current directory; 
 * failing that, it will try to find the file in the class-path, falling back
 * to hard-code config if not found.
 */
@Component
@Profile(Profiles.ConfigFile)
public class UndertowConfigProvider 
    extends PriorityConfigProvider<UndertowConfig> {

    public static final String FileName = "undertow.yml";
    
    @Override
    protected ResourceReader<UndertowConfig> getConverter() {
        return inputStream -> {
            YamlConverter<UndertowConfig> converter = new YamlConverter<>(); 
            UndertowConfig cfg = converter.fromYaml(inputStream, 
                                                    UndertowConfig.class);
            return Stream.of(cfg);
        };
    }
    
    @Override 
    public Stream<UndertowConfig> getFallback() {
        return new UndertowYmlFile().readConfig();
    }

    @Override
    public String getConfigFileName() {
        return FileName;
    }
    
}

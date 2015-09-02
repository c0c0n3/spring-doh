package app.config;

import static java.util.Objects.requireNonNull;

import java.io.InputStream;
import java.io.Writer;
import java.util.List;

import org.yaml.snakeyaml.Yaml;

/**
 * YAML serialization of {@link TripsterConfig}.
 */
public class TripsterConfigYaml {

    public void toYaml(List<TripsterConfig> tripsters, Writer output) {
        requireNonNull(tripsters, "tripsters");
        requireNonNull(output, "output");
        
        new Yaml().dump(tripsters, output);
    }
    
    public String toYaml(List<TripsterConfig> tripsters) {
        requireNonNull(tripsters, "tripsters");
        
        return new Yaml().dump(tripsters);
    }

    @SuppressWarnings("unchecked")
    public List<TripsterConfig> fromYaml(InputStream input) {
        requireNonNull(input, "input");
        
        return new Yaml().loadAs(input, List.class);
    }
    
}

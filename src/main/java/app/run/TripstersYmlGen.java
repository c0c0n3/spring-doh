package app.run;

import static util.Streams.asList;

import java.util.List;

import util.config.YamlConverter;
import app.config.data.TripstersYmlFile;
import app.config.items.TripsterConfig;

/**
 * Run this class redirecting {@code stdout} to 'config/tripsters.yml' to 
 * generate the file. This way we can keep all config data in Java and avoid 
 * any deserialization issue.
 * <pre>
 * java -jar build/libs/spring-doh-0.1.0.jar app.run.TripstersYmlGen > src/main/resources/config/tripsters.yml
 *</pre>
 */
public class TripstersYmlGen implements RunnableApp {

    /**
     * Dumps YAML config to {@code stdout}.
     */
    @Override
    public void run(List<String> appArgs) {
        List<TripsterConfig> fileContents = asList( 
                                        new TripstersYmlFile().readConfig());
        String yaml = new YamlConverter<List<TripsterConfig>>()
                     .toYaml(fileContents);
        System.out.print(yaml); 
    }

}

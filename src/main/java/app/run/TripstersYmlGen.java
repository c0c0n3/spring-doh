package app.run;

import java.util.List;

import app.config.TripsterConfigYaml;
import app.config.data.TripstersYmlFile;

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
        String yaml = new TripsterConfigYaml()
                     .toYaml(TripstersYmlFile.tripsters);
        System.out.print(yaml); 
    }

}

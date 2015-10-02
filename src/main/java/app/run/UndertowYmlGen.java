package app.run;

import java.util.List;

import util.config.YamlConverter;
import app.config.data.UndertowYmlFile;
import app.config.items.UndertowConfig;

/**
 * Run this class redirecting {@code stdout} to 'config/undertow.yml' to 
 * generate the file. This way we can keep all config data in Java and avoid 
 * any deserialization issue.
 * <pre>
 * java -jar build/libs/spring-doh-0.1.0.jar app.run.UndertowYmlGen > src/main/resources/config/undertow.yml
 *</pre>
 */
public class UndertowYmlGen implements RunnableApp {

    /**
     * Dumps YAML config to {@code stdout}.
     */
    @Override
    public void run(List<String> appArgs) {
        UndertowConfig fileContents = new UndertowYmlFile()
                                     .readConfig()
                                     .findFirst()
                                     .get();
        String yaml = new YamlConverter<UndertowConfig>().toYaml(fileContents);
        System.out.print(yaml); 
    }

}

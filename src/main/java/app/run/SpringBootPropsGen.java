package app.run;

import java.io.IOException;
import java.util.List;

import util.Exceptions;
import app.config.Profiles;
import app.config.data.SpringBootAppPropsFile;


/**
 * Run this class redirecting {@code stdout} to 
 * 'config/application-FullyFledged.properties' to generate the file. 
 * (Note that 'FullyFledged' is the value of {@link Profiles#FullyFledged}.)
 * This way we can keep all config data in Java and avoid common issues.
 * <pre>
 * java -jar build/libs/spring-doh-0.1.0.jar app.run.SpringBootPropsGen > src/main/resources/config/application-FullyFledged.properties
 *</pre>
 */
public class SpringBootPropsGen implements RunnableApp {

    private void dumpProps() throws IOException {
        new SpringBootAppPropsFile()
            .readConfig()
            .findFirst()
            .get()
            .getProps()
            .store(System.out, "Spring Boot Properties");
    }
    
    /**
     * Dumps Java props to {@code stdout}.
     * @throws IOException 
     */
    @Override
    public void run(List<String> appArgs) {
        try {
            dumpProps();
        } catch (IOException e) {
            Exceptions.throwAsIfUnchecked(e);
        }
    }

}

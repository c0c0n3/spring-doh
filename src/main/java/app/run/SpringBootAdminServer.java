package app.run;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import app.config.Profiles;
import app.config.items.SpringBootAdminConfig;
import de.codecentric.boot.admin.config.EnableAdminServer;


/**
 * Runs the <a href="https://github.com/codecentric/spring-boot-admin">Spring 
 * Boot Admin</a> server on port {@code p + 1}, where {@code p} is the
 * {@link SpringBootWebApp}'s port.
 */
@SpringBootApplication
@EnableAdminServer
public class SpringBootAdminServer implements RunnableApp {

    @Override
    public void run(List<String> appArgs) {
        SpringApplication app = new SpringApplication(
                                        SpringBootAdminServer.class);
        app.setAdditionalProfiles(Profiles.FullyFledged);
        
        app.run();  // could pass in appArgs if needed
    }
    
    @Bean
    public UndertowEmbeddedServletContainerFactory 
                embeddedServletContainerFactory(Environment env) {
        int port = SpringBootAdminConfig
                  .getAdminServerUrl(k -> env.getProperty(k))
                  .get()
                  .getPort();
        return new UndertowEmbeddedServletContainerFactory(port);
    }
    
}

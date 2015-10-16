package app.run;

import io.undertow.Undertow.Builder;
import io.undertow.UndertowOptions;
import io.undertow.servlet.api.DeploymentInfo;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

import util.config.ConfigProvider;
import util.servlet.http.CharEncodingFilter;
import app.config.Profiles;
import app.config.WebWiring;
import app.config.Wiring;
import app.config.items.UndertowConfig;


/**
 * Spring Boot web app to exercise the core functionality.
 * Build, then run the server in a terminal:
 * <pre>
 * ./gradlew build
 * java -jar build/libs/spring-doh-0.1.0.jar app.run.SpringBootWebApp 
 * </pre>
 * Access the landing page from another terminal
 * <pre>
 * curl http://localhost:8080/ 
 * </pre>
 * and follow the returned instructions to find out about a tripster's trip.
 */
@SpringBootApplication  // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class SpringBootWebApp implements RunnableApp {

    @Override
    public void run(List<String> appArgs) {
        SpringApplication app = new SpringApplication(
                SpringBootWebApp.class, Wiring.class, WebWiring.class);  // (*)
        app.setAdditionalProfiles(Profiles.ConfigFile, Profiles.WebApp);
        
        app.run();  // could pass in appArgs if needed  
    }
    /* NOTES. 
     * 1. Config classes. Spring MVC usual mechanism of configuring a servlet 
     * context through an implementation of WebApplicationInitializer does *not*
     * work with Spring Boot. In fact, Spring Boot does not use Spring's
     * support for Servlet 3 web app configuration (i.e the implementation
     * of ServletContainerInitializer: SpringServletContainerInitializer) 
     * because of possible issues with embedded servers---see: 
     * - https://github.com/spring-projects/spring-boot/issues/321
     * This means our WebInitializer will be ignored, so we need to create the 
     * Spring Boot app with out root config classes.
     * 2. Servlet mappings. Our WebInitializer also specifies the root url to
     * be "/". We don't need to configure this as, by default, if only one
     * servlet is configured, Spring Boot maps it to "/".
     * 3. Filters. The filters specified by our WebInitializer will have to be
     * added, which we do (see below) by instantiating them as beans so that
     * Spring Boot can add them to the context. We need these filters to 
     * complement UTF-8 message converters. 
     * 4. UTF-8 and Message Converters. Spring Boot defaults converters to use 
     * UTF-8, so the code in WebWiring to make the string converter use UTF-8
     * is not needed when using Spring Boot. 
     * 5. UTF-8 and URL's. Undertow uses UTF-8 as default charset for decoding 
     * URL's and query parameters, but its default for response encoding is
     * ISO-8859-1. So we change it to be UTF-8 below and as we're at it, we
     * also explicitly set the URL decoding charset to UTF-8---not needed as
     * it's the default, but makes things clearer.
     */
    
    @Bean
    public Filter requestUtf8CharEncodingFilter() {
        return CharEncodingFilter.Utf8Request();
    }

    @Bean
    public Filter responseUtf8CharEncodingFilter() {
        return CharEncodingFilter.Utf8Response();
    }
    
    @Bean
    public UndertowEmbeddedServletContainerFactory 
                embeddedServletContainerFactory(
                        ConfigProvider<UndertowConfig> cfg) {
        
        int port = cfg.defaultReadConfig().findFirst().get().getPort();
        
        UndertowEmbeddedServletContainerFactory factory = 
                new UndertowEmbeddedServletContainerFactory(port);
        
        factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {
            @Override
            public void customize(Builder builder) {  // (*)
                builder.setServerOption(UndertowOptions.DECODE_URL, true);
                builder.setServerOption(UndertowOptions.URL_CHARSET, 
                                        StandardCharsets.UTF_8.name());
            }
        });
        
        factory.addDeploymentInfoCustomizers(new UndertowDeploymentInfoCustomizer() {
            @Override
            public void customize(DeploymentInfo deployment) {  // (*)
                deployment.setDefaultEncoding(StandardCharsets.UTF_8.name());
            }
        });
        
        return factory;
    }
    /* (*) objects are passed in after Spring Boot has set most of the values, 
     * which allows us to add to or override the settings without having to 
     * redo the entire server configuration.
     */
}

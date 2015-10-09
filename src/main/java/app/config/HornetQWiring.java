package app.config;

import static util.Streams.asList;

import java.util.List;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import app.config.items.TripsterConfig;
import app.config.items.TripstersQConfig;
import app.config.mappers.TripsterConfigMapper;
import app.core.cyclic.StdoutVisualizer;
import app.core.trips.Tripster;
import app.core.trips.TripsterGroup;
import app.core.trips.TripsterSpotter;
import app.webq.TripsterQConsumer;
import app.webq.TripsterQController;
import util.config.ConfigProvider;


/**
 * Additional Spring Boot bean wiring and configuration for the web app that 
 * embeds a HornetQ instance.
 */
@Configuration
@ComponentScan(basePackageClasses={TripsterQController.class})
@Profile(Profiles.WebQ)
public class HornetQWiring {

    @Autowired
    private ConfigProvider<TripstersQConfig> tripstersQProvider;

    @Autowired
    private ConfigProvider<TripsterConfig> tripsties;
    
    
    @Bean
    public TripstersQConfig tripstersQ() {
        return tripstersQProvider.defaultReadConfig().findFirst().get();
    }
    
    @Bean
    public MessageConverter tripsterQMessageConverter() { 
        MappingJackson2MessageConverter converter = 
                new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);                // (1)
        converter.setTypeIdPropertyName("tripsterQMessageBody");  // (2)
        
        return converter;
    }
    /* NOTES.
     * 1. MappingJackson2MessageConverter defaults the character encoding to
     * UTF-8.
     * 2. This has to be a valid Java identifier, otherwise HornetQ will throw
     * when MappingJackson2MessageConverter attempts to set it as a message
     * property. (See MappingJackson2MessageConverter#setTypeIdOnMessage)
     */
    
    @Bean
    public MessageListenerContainer tripstersQListenerContainer(
            ConnectionFactory cf,
            TripsterQConsumer listener) {
        DefaultMessageListenerContainer mlc = 
                new DefaultMessageListenerContainer();
        
        mlc.setConnectionFactory(cf);
        mlc.setDestinationName(tripstersQ().getName());
        
        MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
        adapter.setDefaultListenerMethod(TripsterQConsumer.listnerMethodName());
        adapter.setMessageConverter(tripsterQMessageConverter());
        mlc.setMessageListener(adapter);
        
        return mlc;
    }
    
    @Bean
    public TripsterSpotter<String> tripsterSpotterWithStdoutViz() {
        List<Tripster<String>> happyBunch = asList(
                TripsterConfigMapper.newWithStringArray()
                                    .defaultFromConfig(tripsties));
        
        return new TripsterSpotter<>(new TripsterGroup<>(happyBunch),
                                     new StdoutVisualizer<>());
    }
    
    
}
/* NOTES.
 * 1. HornetQ server configuration. 
   To customize the HornetQ server configuration, you can implement the 
 * HornetQConfigurationCustomizer callback interface (customize method), 
 * e.g.
 * 
 *  @Bean
 *  public HornetQConfigurationCustomizer hornetQConfigurationCustomizer() {
 *      return new HornetQConfig(); // <-- your class implementing the callback
 *  }
 *  
 * The customize method is passed a HornetQ Configuration instance created with 
 * the values in HornetQProperties. For the details, see the source code of:
 * 
 *  - HornetQEmbeddedServerConfiguration
 *  - HornetQEmbeddedConfigurationFactory
 * 
 * HornetQ Configuration is created by Spring Boot pretty much as shown in the
 * HornetQ EmbeddedExample class.
 * 
 * 2. Performance.
 * The HornetQ docs (49.6. Avoiding Anti-Patterns) state that connections, 
 * sessions, consumers, and producers are supposed to be shared, but the
 * Spring JMS template does not. So you shouldn't use it with HornetQ...
 */

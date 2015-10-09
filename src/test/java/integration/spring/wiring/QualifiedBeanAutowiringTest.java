package integration.spring.wiring;


import static org.junit.Assert.*;

import javax.jms.ConnectionFactory;

import org.hornetq.jms.client.HornetQJMSConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import app.config.Profiles;
import app.config.Wiring;
import app.core.trips.TripsterSpotter;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Wiring.class, QualifiedBeanAutowiringTest.class})
@WebAppConfiguration // WebApp profile brings in WebWiring which needs a web context
//@EnableAutoConfiguration  // HornetQWiring needs JMS stuff auto-configured by Spring Boot
@ActiveProfiles({Profiles.HardCodedConfig, Profiles.WebApp, Profiles.WebQ})
public class QualifiedBeanAutowiringTest {
    
    // need to supply it for HornetQWiring's autowired connection factory
    @Bean
    public ConnectionFactory connectionFactory() {
        return new HornetQJMSConnectionFactory();
    }
    
    @Autowired
    @Qualifier("tripsterSpotter")
    private TripsterSpotter<String> webSpotter;
    
    @Autowired
    @Qualifier("tripsterSpotterWithStdoutViz")
    private TripsterSpotter<String> webQSpotter;
    
    
    @Test
    public void webSpotterNotNull() {
        assertNotNull(webSpotter);
    }
    
    @Test
    public void webQSpotterNotNull() {
        assertNotNull(webQSpotter);
    }
    
    @Test
    public void webSpotterDifferentThanWebQSpotter() {
        assertTrue(webSpotter != webQSpotter);
        assertTrue(webSpotter.getVisualizer() != webQSpotter.getVisualizer());
    }
    
}

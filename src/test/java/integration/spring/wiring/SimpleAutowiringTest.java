package integration.spring.wiring;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.beans.StdoutVisualizerBean;
import app.config.Profiles;
import app.config.Wiring;
import app.core.cyclic.CycleVisualizer;
import app.core.trips.TripsterSpotter;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SimpleAutowiringTest.class)
@ComponentScan(basePackageClasses={Wiring.class})
@ActiveProfiles({Profiles.HardCodedConfig})
public class SimpleAutowiringTest {

    @Autowired
    private CycleVisualizer<String> targetBean;
    
    @Autowired
    private StdoutVisualizerBean<String> concreteImplementation;
    
    @Autowired
    private TripsterSpotter<String> interfaceLessBean;

    
    @Test
    public void targetBeanNotNull() {
        assertNotNull(targetBean);
    }
    
    @Test
    public void targetBeanExpectedInstance() {
        assertTrue(targetBean instanceof StdoutVisualizerBean);
    }
    
    @Test
    public void autowireWorksWithConcreteTypeToo() {
        assertNotNull(concreteImplementation);
    }
    
    @Test
    public void autowireWorksEvenWithInterfacelessBean() {
        assertNotNull(interfaceLessBean);
    }
    
}

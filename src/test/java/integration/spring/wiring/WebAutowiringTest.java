package integration.spring.wiring;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.beans.StringVisualizerBean;
import app.config.Profiles;
import app.config.Wiring;
import app.core.cyclic.CycleVisualizer;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Wiring.class)
@ActiveProfiles({Profiles.HardCodedConfig, Profiles.WebApp})
public class WebAutowiringTest {

    @Autowired
    private CycleVisualizer<String> targetBean;
    
    @Test
    public void targetBeanNotNull() {
        assertNotNull(targetBean);
    }
    
    @Test
    public void targetBeanExpectedInstance() {
        assertTrue(targetBean instanceof StringVisualizerBean);
    }
    
}

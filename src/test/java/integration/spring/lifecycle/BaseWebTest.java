package integration.spring.lifecycle;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import app.config.Profiles;
import app.config.WebWiring;
import app.config.Wiring;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(classes = Wiring.class),
        @ContextConfiguration(classes = WebWiring.class)
})
@ActiveProfiles({Profiles.HardCodedConfig, Profiles.WebApp})
public class BaseWebTest {
    
    @Autowired
    protected WebApplicationContext wac;
    
    protected MockMvc mockMvc;
    
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    protected String tellId(String relPath) throws Exception {
        return mockMvc
                .perform(get("/" + relPath))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
    
    @Test
    public void ignoreThis() { }
    // NB avoids the "No runnable methods" exception, while letting us keep
    // all the annotations in this base class.
    
}

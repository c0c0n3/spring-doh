package integration.spring.web;

import static util.Arrayz.array;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import util.servlet.http.CharEncodingFilter;
import app.config.Profiles;
import app.config.TripsterConfig;
import app.config.WebWiring;
import app.config.Wiring;
import app.config.data.DefaultTripsters;
import app.web.TripsterController;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(classes = Wiring.class),
        @ContextConfiguration(classes = WebWiring.class)
})
@ActiveProfiles({Profiles.HardCodedConfig, Profiles.WebApp})
public class TripsterControllerTest {
    
    public static String[] urlFormats = array(
            "/xxx",      // non-existent resource
            "/xxx?at=1", // valid invocation but non-existing resource
            "/%s/",      // trailing slash
            "/%s",       // missing query
            "/%s?",      // missing query param
            "/%s?%s",    // missing query arg
            "/%s?%s=",   // missing query arg value
            "/%s?%s=%s"  // valid invocation, e.g. /hipster?at=3
    );    
    
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private TripsterConfig tripster;
    
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                 .webAppContextSetup(wac)
                 .addFilters(
                         CharEncodingFilter.Utf8Request(), 
                         CharEncodingFilter.Utf8Response())
                 .build();
        tripster = DefaultTripsters.hipster();
    }
    
    private ResultActions perform(String urlFormat) throws Exception {
        String url = String.format(urlFormat, 
                tripster.getName(), TripsterController.LegsTraveledQueryPar, 1);
        
        return mockMvc.perform(get(url))
                    .andDo(print())  // comment this in/out to see/hide Spring dump
        ;
    }
    
    private ResultActions expectContentType(ResultActions x) throws Exception {
        return x
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
        .andExpect(content().encoding("UTF-8"));
    }
    
    private ResultActions expectFirstCycleElement(ResultActions x) throws Exception {
        String fstCycleElement = tripster.getCycle()[0];
        return x.andExpect(content().string(containsString(fstCycleElement)));
    }
    
    @Test
    public void removeMe() {}
    
    //@Test
    public void nonExistentResource() throws Exception {
        perform("/xxx").andExpect(status().is4xxClientError());
    }
    
    //@Test
    public void validInvocation() throws Exception {
        ResultActions x = perform("/%s?%s=%s")
                         .andExpect(status().isOk());
        x = expectContentType(x);
        expectFirstCycleElement(x);
    }
    
    //@Test
    public void t1() throws Exception {
        String url = String.format("/%s?%s=%s", 
                tripster.getName(), TripsterController.LegsTraveledQueryPar, 1);
        String fstCycleElement = tripster.getCycle()[0];
        
        mockMvc
        .perform(get(url))
        // .andDo(print())  // comment this in/out to see/hide Spring dump
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
        .andExpect(content().encoding("UTF-8"))
        .andExpect(content().string(containsString(fstCycleElement)));
    }
    
}

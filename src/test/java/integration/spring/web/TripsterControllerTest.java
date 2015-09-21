package integration.spring.web;

import static util.Arrayz.array;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;

import app.config.TripsterConfig;
import app.config.data.DefaultTripsters;
import app.web.TripsterController;

@RunWith(SpringJUnit4ClassRunner.class)
public class TripsterControllerTest extends BaseWebTest {
    
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

    private TripsterConfig tripster;
    
    @Override
    public void additionalSetup() {
        tripster = DefaultTripsters.hipster();
    }
    
    private ResultActions perform(String urlFormat) throws Exception {
        String url = String.format(urlFormat, 
                tripster.getName(), TripsterController.LegsTraveledQueryPar, 1);
        
        return doGet(url);
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
    
    //@Test
    public void nonExistentResource() throws Exception {
        perform("/xxx").andExpect(status().is4xxClientError());
    }
    
    @Test
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

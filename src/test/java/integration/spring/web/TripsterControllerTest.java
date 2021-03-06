package integration.spring.web;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;

import app.config.data.DefaultTripsters;
import app.config.items.TripsterConfig;
import app.web.TripsterController;

@RunWith(SpringJUnit4ClassRunner.class)
public class TripsterControllerTest extends BaseWebTest {  

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
    
    private ResultActions performAndExpectValidResponse(String urlFormat) 
            throws Exception{
        ResultActions x = perform(urlFormat).andExpect(status().isOk());
        return expectTextUtf8ContentType(x);
    }
    
    private void performAndExpectValidButEmptyResponse(String urlFormat) 
            throws Exception{
        performAndExpectValidResponse(urlFormat)
        .andExpect(content().string(is("")));
    }
    
    private void performAndExpect404(String urlFormat) throws Exception{
        ResultActions x = perform(urlFormat).andExpect(status().is(404));
        expectTextUtf8ContentType(x)
        .andExpect(content().string(not(isEmptyOrNullString())));
    }
    
    @Test
    public void nonExistentResource() throws Exception {
        performAndExpect404("/xxx");
    }
    
    @Test
    public void wellFormedUrlButNonExistentResource() throws Exception {
        performAndExpect404("/xxx?at=1");
    }
    
    @Test
    public void trailingSlash() throws Exception {
        performAndExpectValidButEmptyResponse("/%s/");
    }
    // showTrip is hit b/c query param is defaulted to 0.

    @Test
    public void trailingQuestionMark() throws Exception {
        performAndExpectValidButEmptyResponse("/%s?");
    }
    // showTrip is hit b/c query param is defaulted to 0.
    
    @Test
    public void missingQuery() throws Exception {
        performAndExpectValidButEmptyResponse("/%s");
    }
    // showTrip is hit b/c query param is defaulted to 0.
    
    @Test
    public void missingQueryArg() throws Exception {
        performAndExpectValidButEmptyResponse("/%s?%s");
    }
    // showTrip is hit b/c query param is defaulted to 0.
    
    @Test
    public void missingQueryArgValue() throws Exception {
        performAndExpectValidButEmptyResponse("/%s?%s=");
    }
    // showTrip is hit b/c query param is defaulted to 0.
    
    @Test
    public void wellFormedUrlForExistingResource() throws Exception {
        String fstCycleElement = tripster.getCycle()[0];
        
        performAndExpectValidResponse("/%s?%s=%s")
        .andExpect(content().string(containsString(fstCycleElement)));
    }
    
}

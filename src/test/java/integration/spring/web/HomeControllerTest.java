package integration.spring.web;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;

import app.web.HomeController;

@RunWith(SpringJUnit4ClassRunner.class)
public class HomeControllerTest extends BaseWebTest {

    private ResultActions perform(MediaType...ts) throws Exception {
        return doGet("/" + HomeController.SpotterPath, ts);
    }
    
    @Test
    public void getSpotterRequiresAcceptHeader() throws Exception {
        perform().andExpect(status().is(406));
    }
    
    @Test
    public void getSpotterWithXmlAcceptHeaderCannotSerialize() throws Exception {
        perform(MediaType.APPLICATION_XML)
                .andExpect(status().is(406))
                .andExpect(content().string(isEmptyOrNullString()));
    }
    /* NB Jaxb2RootElementHttpMessageConverter is among the converters, but it
     * will refuse to write an object if its class wasn't annotated with
     * XmlRootElement.
     */
    
    @Test
    public void getSpotterWithXmlAcceptHeaderCannotSerializeButCanReturnTextError() 
            throws Exception {
        ResultActions x = 
            perform(MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN)
                .andExpect(status().is(406))
                .andExpect(content().string(is("● some explanation in unicode ●")));
        expectTextUtf8ContentType(x);
    }
    /* NB Jaxb2RootElementHttpMessageConverter is among the converters, but it
     * will refuse to write an object if its class wasn't annotated with
     * XmlRootElement.
     */
    
    @Test
    public void getSpotterWithJsonAcceptHeaderCanSerialize() throws Exception {
        perform(MediaType.APPLICATION_JSON)
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().string(not(isEmptyOrNullString())));
    }
    
    @Test
    public void getSpotterWithJsonAndXmlAcceptHeaderCanSerialize() 
            throws Exception {
        perform(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(not(isEmptyOrNullString())));
    }
    
}

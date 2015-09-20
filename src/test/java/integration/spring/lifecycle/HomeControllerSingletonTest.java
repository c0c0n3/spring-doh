package integration.spring.lifecycle;

import static org.junit.Assert.*;

import org.junit.Test;

import app.web.HomeController;


public class HomeControllerSingletonTest extends BaseWebTest {
    
    @Test
    public void singletonWhenRetrievedFromWebAppCtx() {
        HomeController c1 = wac.getBean(HomeController.class);
        HomeController c2 = wac.getBean(HomeController.class);
        
        assertNotNull(c1);
        assertNotNull(c2);
        assertTrue(c1 == c2);
    }
    
    @Test
    public void sameInstanceForEachRequest() throws Exception {
        String idOfControllerHandlingFstRequest = 
                tellId(HomeController.TellIdPath);
        String idOfControllerHandlingSndRequest = 
                tellId(HomeController.TellIdPath);
        
        assertEquals(idOfControllerHandlingFstRequest, 
                     idOfControllerHandlingSndRequest);
    }
    
}

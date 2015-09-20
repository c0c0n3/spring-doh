package integration.spring.lifecycle;

import static org.junit.Assert.*;

import org.junit.Test;

import app.web.TripsterController;

public class TripsterControllerRequestTest extends BaseWebTest {
        
    @Test
    public void singletonWhenRetrievedFromWebAppCtx() {
        TripsterController c1 = wac.getBean(TripsterController.class);
        TripsterController c2 = wac.getBean(TripsterController.class);
        
        assertNotNull(c1);
        assertNotNull(c2);
        assertTrue(c1 == c2);
    }
    
    @Test
    public void differentInstancesForEachRequest() throws Exception {
        String idOfControllerHandlingFstRequest = 
                tellId(TripsterController.TellIdPath);
        String idOfControllerHandlingSndRequest = 
                tellId(TripsterController.TellIdPath);
        
        assertNotEquals(idOfControllerHandlingFstRequest, 
                        idOfControllerHandlingSndRequest);
    }
    
    @Test
    public void sameVisualiserPrototypeInstanceForEachRequest() throws Exception {
        String idOfVizInControllerHandlingFstRequest = 
                tellId(TripsterController.TellVisualizerIdPath);
        String idOfVizInControllerHandlingSndRequest = 
                tellId(TripsterController.TellVisualizerIdPath);
        
        assertEquals(idOfVizInControllerHandlingFstRequest, 
                     idOfVizInControllerHandlingSndRequest);
    }
    // NB this is because the spotter bean is a singleton!
    
}

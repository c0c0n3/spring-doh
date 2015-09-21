package app.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static util.spring.http.ResponseEntities._404;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import util.Identifiable;
import app.beans.StringVisualizerBean;
import app.config.Profiles;
import app.core.trips.TripsterSpotter;

@RestController  // includes @ResponseBody: return vals bound to response body.
@RequestMapping("/")
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Profile(Profiles.WebApp)
public class TripsterController implements Identifiable {

    public static final String TripsterNamePathVar = "tripster";
    public static final String LegsTraveledQueryPar = "at";
    public static final String TellIdPath = "tell/id/of/TripsterController";
    public static final String TellVisualizerIdPath = "tell/id/of/TripsterController/visualizer";
    
    @Autowired
    private TripsterSpotter<String> spotter;
    // NB will be shared across requests (!) as spotter is a singleton.
    
    
    @RequestMapping(value = "{" + TripsterNamePathVar + "}", method = GET)
    public ResponseEntity<String> showTrip(
            @PathVariable(value=TripsterNamePathVar) 
            String tripsterName,
            @RequestParam(value=LegsTraveledQueryPar, defaultValue="0")  // (*) 
            int legsTraveled) {
        
        boolean found = spotter.showWhereIs(tripsterName, legsTraveled);
        if (found) {
            StringVisualizerBean<String> visualizer = 
                    (StringVisualizerBean<String>) spotter.getVisualizer();
            String shown = visualizer.getShown();
            
            return ResponseEntity.ok(shown);
        }
        return _404("tripster not found: " + tripsterName);
    }
    /* (*) without a default value the param becomes required and if the request
     * url doesn't have it, Spring MVC produces a 400 response without hitting
     * this method.
     */ 
    
    @RequestMapping(value = TellIdPath, method = GET)
    @Override
    public String id() {
        return Identifiable.super.id();
    }
    
    @RequestMapping(value = TellVisualizerIdPath, method = GET)
    public String visualizerId() {
        return spotter.getVisualizer().id();
    }
    
}

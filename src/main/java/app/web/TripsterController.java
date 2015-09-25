package app.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static util.Either.left;
import static util.Either.right;
import static util.spring.http.ResponseEntities.okOr404;

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
    
    private String visualized() {
        StringVisualizerBean<String> visualizer = 
                (StringVisualizerBean<String>) spotter.getVisualizer();
        return visualizer.getShown();
    }
    
    @RequestMapping(value = "{" + TripsterNamePathVar + "}", method = GET)
    public ResponseEntity<Object> showTrip(
            @PathVariable(value=TripsterNamePathVar) 
            String tripsterName,
            @RequestParam(value=LegsTraveledQueryPar, defaultValue="0")  // (*) 
            int legsTraveled) {
        return okOr404(() -> {
            boolean found = spotter.showWhereIs(tripsterName, legsTraveled);
            return found ? right(visualized()) :
                           left("tripster not found: " + tripsterName);
        });
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

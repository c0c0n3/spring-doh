package app.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.beans.StringVisualizerBean;
import app.config.Profiles;
import app.core.trips.TripsterSpotter;

@RestController  // includes @ResponseBody: return vals bound to response body.
@RequestMapping("/")
@Scope("request")
@Profile(Profiles.WebApp)
public class TripsterController {

    public static final String TripsterNamePathVar = "tripster";
    public static final String LegsTraveledQueryPar = "at";
    
    @Autowired
    private TripsterSpotter<String> spotter;
    // NB will be shared across requests (!) as spotter is a singleton.
    
    
    @RequestMapping(value = "{" + TripsterNamePathVar + "}", method = GET, 
                    produces = "text/plain;charset=UTF-8")
    public String showTrip(
            @PathVariable(value = TripsterNamePathVar) 
            String tripsterName,
            @RequestParam(value=LegsTraveledQueryPar, defaultValue="0") 
            int legsTraveled) {
        
        spotter.showWhereIs(tripsterName, legsTraveled);
        StringVisualizerBean<String> visualizer = 
                (StringVisualizerBean<String>) spotter.getVisualizer();
        
        return visualizer.getShown();
    }
    
}

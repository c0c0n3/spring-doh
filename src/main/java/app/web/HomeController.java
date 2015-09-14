package app.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static app.web.TripsterController.LegsTraveledQueryPar;
import static util.Strings.write;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.config.Profiles;
import app.core.trips.TripsterSpotter;

@RestController  // includes @ResponseBody: return vals bound to response body.
@RequestMapping("/")
@Profile(Profiles.WebApp)
public class HomeController {

    @Autowired
    private TripsterSpotter<String> spotter; 
    // NB will be shared across requests (!) as this controller is a singleton. 
    
    
    @RequestMapping(method = GET)
    public String usage() {
        return write(out -> {
            out.println("To see what a tripster is up to go to:");
            out.format("\t/tripster?%s=n%n", LegsTraveledQueryPar);
            out.println("where n is an integer and tripster is one of:");
            out.println(spotter.describeTripsters());
        });
    }
    
}

package app.config.providers;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import app.config.Profiles;
import app.config.data.DefaultTripstersQ;


/**
 * The tripsters queue configuration as required by HornetQ.
 * This configuration is hard-coded as the queue is only used internally by the
 * web app embedding HornetQ.
 */
@Component
@Profile(Profiles.WebQ)
public class TripstersQConfigProvider extends DefaultTripstersQ {
    
}

package app.webq;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import app.config.Profiles;
import app.config.items.TripstersQConfig;


/**
 * Enqueues a message to request to show what legs of the trip a tripster has 
 * traveled so far; the request will be processed asynchronously by the 
 * {@link TripsterQConsumer}.
 */
@RestController  // includes @ResponseBody: return vals bound to response body.
@RequestMapping("/")
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Profile(Profiles.WebQ)
public class TripsterQController {

    public static final String TripsterQPath = "q";
    
    @Autowired
    private JmsOperations jmsOps;
    
    @Autowired
    private MessageConverter tripsterQMessageConverter;
    
    @Autowired
    private TripstersQConfig tripstersQ;
    
    private MessageCreator msgCreator(ShowTripRequest msg) {
        return new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                    return tripsterQMessageConverter.toMessage(msg, session);
            }
        };
    }
    
    @RequestMapping(value = TripsterQPath, method = POST, 
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public void showTrip(@RequestBody ShowTripRequest msg) {
        jmsOps.send(tripstersQ.getName(), msgCreator(msg));  // (*)
    }
    /* (*) we could have configured the JMSTemplate to use tripsterQMessageConverter
     * by default, so that we could have called:
     * 
     *   jmsOps.convertAndSend(tripstersQ.getName(), msg);
     *   
     * but in general, you may have multiple queues, each requiring a different 
     * converter, so the best option is to use the send method with a message
     * creator as done above.
     */
}

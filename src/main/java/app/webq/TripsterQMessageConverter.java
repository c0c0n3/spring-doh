package app.webq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageType;


/**
 * Spring JMS message converter for the tripsters queue.
 * Incoming JSON is put as is into a text message to add to the queue; when this
 * message is consumed from the queue, the JSON is converted to a {@link 
 * ShowTripRequest}.
 * This avoids converting the incoming JSON to object, then back to JSON for it
 * to be enqueued only to be dserialized once again when the message is fetched
 * from the queue.
 */
public class TripsterQMessageConverter extends MappingJackson2MessageConverter {

    public static final String TypeIdPropertyName = "tripsterQMessageBody";
    
    public TripsterQMessageConverter() {
        setTargetType(MessageType.TEXT);            // (1)
        setTypeIdPropertyName(TypeIdPropertyName);  // (2)
    }
    /* NOTES.
     * 1. MappingJackson2MessageConverter defaults the character encoding to
     * UTF-8.
     * 2. This has to be a valid Java identifier, otherwise HornetQ will throw
     * when MappingJackson2MessageConverter attempts to set it as a message
     * property. (See MappingJackson2MessageConverter#setTypeIdOnMessage)
     */
    
    @Override
    public Message toMessage(Object showTripRequest, Session session)
            throws JMSException, MessageConversionException {
        Message msg = session.createTextMessage(showTripRequest.toString());
        msg.setStringProperty(TypeIdPropertyName, 
                              ShowTripRequest.class.getName());
        return msg;
    }
    
}

package app.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static app.web.TripsterController.LegsTraveledQueryPar;
import static util.Strings.write;
import static util.spring.http.MediaTypes.acceptsMediaType;
import static util.spring.http.ResponseEntities._406;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import util.Identifiable;
import app.config.Profiles;
import app.core.trips.TripsterSpotter;

/**
 * Prints out how to use the web app to find out what a tripster is up to.
 * This controller mimics the 'usage' function of the CLI apps in the {@code 
 * app.run} package.
 * Additionally, you can GET id's and objects so we have something to play with
 * in the tests for web beans life-cycle, content negotiation, and exception
 * handling. 
 */
@RestController  // includes @ResponseBody: return vals bound to response body.
@RequestMapping("/")
@Profile(Profiles.WebApp)
public class HomeController implements Identifiable {

    public static final String TellIdPath = "tell/id/of/HomeController";
    // NB this path must be different than that of TripsterController otherwise
    // Spring will throw an init exception as it can detect duplicated routes. 
    
    public static final String SpotterPath = "objects/spotter";
    
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
    
    @RequestMapping(value = TellIdPath, method = GET)
    @Override
    public String id() {
        return Identifiable.super.id();
    }
    
    @RequestMapping(value = SpotterPath, method = GET, 
                    produces = { MediaType.APPLICATION_JSON_VALUE, 
                                 MediaType.APPLICATION_XML_VALUE })
    public TripsterSpotter<String> getSpotter() {
        return spotter;
    }
    
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)  // (1)
    //@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Not Acceptable")  // (3)
    public ResponseEntity<String> hanldeException(  // (2)
            HttpMediaTypeNotAcceptableException e, 
            HttpServletRequest request,
            HttpServletResponse response) {
        ResponseEntity<String> responseEntity = null;
        if (acceptsMediaType(MediaType.TEXT_PLAIN, request)) {  // (4)
            responseEntity = _406("● some explanation in unicode ●");  
            //responseEntity.getHeaders().setContentType(MediaType.TEXT_PLAIN); // (5)
            response.setContentType(MediaType.TEXT_PLAIN_VALUE);  // (6)
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());  // (7)
        }
        else {
            responseEntity = _406();
        }
        return responseEntity;
    }
    /* NOTES. 
     * 1. The exception will be raised when a getSpotter request comes in with 
     * an Accept header of XML. Even though Jaxb2RootElementHttpMessageConverter 
     * is among our converters, it will refuse to write an object if its class
     * wasn't annotated with @XmlRootElement.
     * 2. The return type of the handler won't be written directly to the 
     * response body using a converter (even if you explicitly annotate this
     * method with @ResponseBody); in fact, it should be void, or a string (to
     * name a view), or a ModelAndView object, etc. If you want to write 
     * something in the response body, using a ResponseEntity is a possible 
     * workaround.
     * 3. Without this exception handler Spring MVC would return a 406 as a
     * result of handling HttpMediaTypeNotAcceptableException; but if you just
     * annotate the method with @ExceptionHandler, then Spring MVC returns a
     * 200. Adding a @ResponseStatus won't work when returning a ResponseEntity:
     * if you comment the annotation back in, you'll get another fat exception
     * (HttpMediaTypeNotAcceptableException), no idea why.
     * 4. Can serve out errors using different media types than those declared
     * by getSpotter (i.e. Json or XML)? If the client accepts it, we'd like 
     * to provide an explanation of what went wrong in plain text. It seems
     * there's no easy way of doing this with message converters. In fact, our
     * body in the response entity will be handled by:
     * 
     *  AbstractMessageConverterMethodProcessor.writeWithMessageConverters(...)
     * 
     * which will select a media type (selectedMediaType) based on the request
     * Accept header and what getSpotter declared it can produce (i.e. 
     * application/json, application/xml) so that if the client says
     *  
     *  Accept: application/xml, text/plain
     *  
     * then the selected media type will be: application/xml. When the string
     * converter is asked if it canWrite(String, application/xml), the answer
     * is yes, so what comes out in the response is our entity body but with a 
     * content type of application/xml!
     * 5. Trying to be explicit about the content type in the response entity
     * will buy you another fat HttpMediaTypeNotAcceptableException.
     * 6. So we set the content type directly in the response.
     * 7. Somewhere in Spring MVC the processing pipeline the character encoding
     * gets set to ISO-8859-1 so we need to override it to produce UTF-8. 
     * (Our char encoding filter will do nothing as the response already has
     * a ISO-8859-1 encoding.)
     */

}

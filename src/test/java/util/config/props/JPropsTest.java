package util.config.props;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static util.Arrayz.array;
import static util.config.props.JPropKey.key;

import java.net.URI;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class JPropsTest {

    private static JPropAccessor<Boolean> boolProp = 
            JPropAccessor.make("p1", Boolean::valueOf);
    private static JPropAccessor<URI> uriProp = 
            new JPropAccessor<>(key("p2"), URI::create, URI::toASCIIString);
    
    private static JProps emptyProps() {
        return new JProps(new Properties());
    }
    
    private static <T> void assertWriteThenReadBackThenRemove(
            JProps props, JPropAccessor<T> prop, T value) {
        props.set(prop, value);
        Optional<T> maybeValue = props.get(prop);
        
        assertNotNull(maybeValue);
        assertTrue(maybeValue.isPresent());
        assertThat(maybeValue.get(), is(value));
        
        String k = prop.getKey().get();
        assertTrue(props.getProps().containsKey(k));
        props.remove(prop);
        assertFalse(props.getProps().containsKey(k));
    }
    
    @DataPoints
    public static Boolean[] bools = array(true, false);
    
    @DataPoints
    public static URI[] uris = Stream.of("urn:1", "urn:2")
                                     .map(URI::create)
                                     .toArray(URI[]::new);
    
    private JProps props;
    
    @Before
    public void setup() {
        props = emptyProps();
    }
    
    @Theory
    public void canWriteThenReadBackThenRemove(Boolean b, URI u) {
        assertWriteThenReadBackThenRemove(props, boolProp, b);
        assertWriteThenReadBackThenRemove(props, uriProp, u);
    }
    
    @Test
    public void writesEmptyButNeverReadsItBack() {
        props.setEmpty(boolProp);
        
        String key = boolProp.getKey().get();
        String rawValue = props.getProps().getProperty(key);
        Optional<Boolean> lookupValue = props.get(boolProp);
        
        assertThat(rawValue, is(""));
        assertFalse(lookupValue.isPresent());
        assertFalse(Boolean.valueOf(""));   // (*)
    }
    /* (*) Boolean.valueOf("") == false, which is why we know JProps must have
     * handled explicitly the raw value of "", otherwise the look up value 
     * would have been Optional.of(false) and lookupValue.isPresent() == true. 
     */
    
    @Test
    public void returnEmptyOptionalIfPropIsNotInDb() {
        Optional<Boolean> lookupValue = props.get(boolProp);
        assertNotNull(lookupValue);
        assertFalse(lookupValue.isPresent());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void throwRuntimeExceptionIfFailsToConvertToObject() {
        props.getProps().setProperty(uriProp.getKey().get(), "   ");
        props.get(uriProp);
    }
    
    @Test(expected = NullPointerException.class)
    public void ctorThrowsIfNullArg() {
        new JProps(null);
    }
    
    @Test(expected = NullPointerException.class)
    public void getThrowsIfNullArg() {
        props.get(null);
    }
    
    @Test(expected = NullPointerException.class)
    public void setThrowsIfNullFirstArg() {
        props.set(null, "");
    }
    
    @Test(expected = NullPointerException.class)
    public void setThrowsIfNullSecondArg() {
        props.set(boolProp, null);
    }
    
    @Test(expected = NullPointerException.class)
    public void setEmptyThrowsIfNullArg() {
        props.setEmpty(null);
    }
    
    @Test(expected = NullPointerException.class)
    public void removeThrowsIfNullArg() {
        props.remove(null);
    }
    
}

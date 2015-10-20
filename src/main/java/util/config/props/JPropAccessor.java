package util.config.props;

import static java.util.Objects.requireNonNull;
import static util.Exceptions.unchecked;

import java.net.URI;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;

import util.config.ConfigProvider;
import util.config.ConfigReader;
import util.config.SingleStringItemConfigProvider;
import util.lambda.FunctionE;


/**
 * Provides access to a typed property in a Java {@link Properties} store.
 */
public class JPropAccessor<T> implements JPropGetter<T>, JPropSetter<T> {
    
    /**
     * Convenience factory method to create an accessor with object to string
     * conversion done using String's {@link String#valueOf(Object) valueOf}
     * method.
     * @param key the property key.
     * @param fromString converts the property raw string value in the Java 
     * {@link Properties} store to a {@code T}.
     * @return a new accessor.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public static <T> JPropAccessor<T> make(JPropKey key, 
                                            Function<String, T> fromString) {
        return new JPropAccessor<>(key, fromString, String::valueOf);
    }
    
    /**
     * Convenience factory method to create a string accessor.
     * String values are read as is from the property store, whereas string 
     * values are written using String's {@link String#valueOf(Object) valueOf} 
     * method.
     * @param key the property key.
     * @return a new accessor.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public static JPropAccessor<String> makeString(JPropKey key) {
        return make(key, Function.identity());
    }
    
    /**
     * Convenience factory method to create a boolean accessor.
     * String values are read from the property store using Boolean's {@link 
     * Boolean#valueOf(String) valueOf} method, whereas boolean values are
     * written using String's {@link String#valueOf(Object) valueOf} method.
     * @param key the property key.
     * @return a new accessor.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public static JPropAccessor<Boolean> makeBool(JPropKey key) {
        return make(key, Boolean::valueOf);
    }
    
    /**
     * Convenience factory method to create an integer accessor.
     * String values are read from the property store using Integer's {@link 
     * Integer#valueOf(String) valueOf} method, whereas integer values are
     * written using String's {@link String#valueOf(Object) valueOf} method.
     * @param key the property key.
     * @return a new accessor.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public static JPropAccessor<Integer> makeInt(JPropKey key) {
        return make(key, Integer::valueOf);
    }
    
    /**
     * Convenience factory method to create an URI accessor.
     * String values are read from the property store using URI's {@link 
     * URI#URI(String) constructor}, whereas URI values are written using URI's 
     * {@link URI#toASCIIString() toASCIIString} method.
     * @param key the property key.
     * @return a new accessor.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public static JPropAccessor<URI> makeURI(JPropKey key) {
        Function<String, URI> fromString = 
                unchecked((FunctionE<String, URI>) URI::new);
        return new JPropAccessor<>(key, fromString, URI::toASCIIString);
    }
    
    /**
     * Convenience factory method to create an enum accessor.
     * String values are read from the property store using the enum's {@link 
     * Enum#valueOf(Class, String) valueOf} method, whereas enum values are 
     * written using the enum's {@link Enum#name() name} method.
     * @param enumType the the enum type for which to create the accessor.
     * @param key the property key.
     * @return a new accessor.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public static <T extends Enum<T>> JPropAccessor<T> makeEnum(
            Class<T> enumType, JPropKey key) {
        requireNonNull(enumType);
        
        Function<String, T> fromString = s -> Enum.valueOf(enumType, s); 
        return new JPropAccessor<>(key, fromString, Enum::name);
    }
    
    
    private final JPropKey key;
    private final Function<String, T> fromString; 
    private final Function<T, String> toString;
    
    /**
     * Creates a new instance to access the property specified by the given 
     * key.
     * @param key the property key.
     * @param fromString converts the property raw string value in the Java 
     * {@link Properties} store to a {@code T}.
     * @param toString converts a {@code T} to the string value to set in the
     * Java {@link Properties} store.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public JPropAccessor(JPropKey key, 
                         Function<String, T> fromString, 
                         Function<T, String> toString) {
        requireNonNull(key, "key");
        requireNonNull(fromString, "fromString");
        requireNonNull(toString, "toString");
        
        this.key = key;
        this.fromString = fromString;
        this.toString = toString;
    }
    
    private String key() {
        return key.get();
    }

    @Override
    public void set(Properties db, T value) {
        requireNonNull(db, "db");
        requireNonNull(value, "value");
        
        String valueStringRep = toString.apply(value);
        db.setProperty(key(), valueStringRep);
    }

    @Override
    public void setEmpty(Properties db) {
        requireNonNull(db, "db");
        db.setProperty(key(), "");
    }

    @Override
    public Optional<String> remove(Properties db) {
        requireNonNull(db, "db");
        
        Optional<String> maybeValue = Optional
                                     .ofNullable(db.getProperty(key()))
                                     .filter(value -> value != "");
        db.remove(key());
        
        return maybeValue;
    }

    @Override
    public Optional<T> get(Properties db) {
        requireNonNull(db, "db");
        
        return makeConfigReader(db::getProperty)
               .defaultReadConfig()
               .findFirst();
    }
    
    /**
     * @return this property's key.
     */
    public JPropKey getKey() {
        return key;
    }
    
    /**
     * Creates a consumer to set this property with the given value.
     * @param value the value to set.
     * @return the consumer to set the value.
     * @throws NullPointerException if the argument is {@code null}.
     */
    public Consumer<Properties> with(T value) {
        requireNonNull(value, "value");  // NB fail early
        return db -> set(db, value);
    }
    
    /**
     * Creates a configuration provider that reads this property's value using
     * the given lookup.
     * @param propLookup given the property key, it returns the corresponding
     * value (if any) from the underlying property store; it may return {@code
     * null} or empty to signal that no value was found.
     * @return a configuration provider to read this property.
     */
    public ConfigProvider<T> makeConfigReader(
            Function<String, String> propLookup) {
        requireNonNull(propLookup, "propLookup");
        
        SingleStringItemConfigProvider source = 
                new SingleStringItemConfigProvider(
                        () -> propLookup.apply(key()));
        return ConfigReader.newReader(source, fromString);
    }
    
}

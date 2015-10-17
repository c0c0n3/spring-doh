package util.config.props;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;


/**
 * Provides access to a typed property in a Java {@link Properties} store.
 */
public class JPropAccessor<T> implements JPropGetter<T>, JPropSetter<T> {

    /**
     * Convenience factory method to create an accessor, defaulting object to
     * string conversion using String's {@link String#valueOf(Object) valueOf}
     * method.
     * @param key the property name.
     * @param fromString converts the property raw string value in the Java 
     * {@link Properties} store to a {@code T}.
     * @return a new accessor.
     * @throws IllegalArgumentException if the key is {@code null} or empty.
     * @throws NullPointerException if {@code fromString} is {@code null}.
     */
    public static <T> JPropAccessor<T> make(String key, 
                                            Function<String, T> fromString) {
        return new JPropAccessor<>(new JPropKey(key), 
                                   fromString, 
                                   String::valueOf);
    }
    
    /**
     * Convenience factory method to create an accessor, defaulting object to
     * string conversion using String's {@link String#valueOf(Object) valueOf}
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
        return Optional.ofNullable(db.getProperty(key()))
                       .filter(value -> value != "")
                       .map(fromString);
    }
    
    /**
     * @return this property's key.
     */
    public JPropKey getKey() {
        return key;
    }
    
}

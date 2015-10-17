package util.config.props;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.Properties;


/**
 * Provides typed access to a Java {@link Properties} store.
 */
public class JProps {

    private final Properties db;
    
    /**
     * Creates a new instance to access the given store.
     * @param db the Java {@link Properties} store.
     * @throws NullPointerException if the argument is {@code null}.
     */
    public JProps(Properties db) {
        requireNonNull(db, "db");
        this.db = db;
    }
    
    /**
     * Uses the given getter to look up a property in the underlying Java
     * {@link Properties} store.
     * @param prop the property getter.
     * @return the property value if found; empty otherwise.
     * @throws NullPointerException if the argument is {@code null}.
     */
    public <T> Optional<T> get(JPropGetter<T> prop) {
        requireNonNull(prop, "prop");
        return prop.get(db);
    }
    
    /**
     * Uses the given setter to store a property in the underlying Java
     * {@link Properties} store.
     * @param prop the property setter.
     * @param value the value to set.
     * @return itself to facilitate use in a fluent API style.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public <T> JProps set(JPropSetter<T> prop, T value) {
        requireNonNull(prop, "prop");
        prop.set(db, value);
        return this;
    }
    
    /**
     * Uses the given setter to store an empty property in the underlying Java
     * {@link Properties} store.
     * @param prop the property setter.
     * @return itself to facilitate use in a fluent API style.
     * @throws NullPointerException if the argument is {@code null}.
     */
    public JProps setEmpty(JPropSetter<?> prop) {
        requireNonNull(prop, "prop");
        prop.setEmpty(db);
        return this;
    }
    
    /**
     * Uses the given setter to remove a property from the underlying Java
     * {@link Properties} store.
     * @param prop the property setter.
     * @return itself to facilitate use in a fluent API style.
     * @throws NullPointerException if the argument is {@code null}.
     */
    public JProps remove(JPropSetter<?> prop) {
        requireNonNull(prop, "prop");
        prop.remove(db);
        return this;
    }
    
    /**
     * @return the underlying properties store.
     */
    public Properties getProps() {
        return db;
    }
    
}

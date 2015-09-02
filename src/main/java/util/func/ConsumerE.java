package util.func;

import java.util.function.Consumer;

/**
 * Same as {@link Consumer} but can throw a checked exception.
 * @see 
 * <a href="http://programmers.stackexchange.com/questions/225931/workaround-for-java-checked-exceptions">
 * this...</a>
 * @see
 * <a href="http://stackoverflow.com/questions/18198176/java-8-lambda-function-that-throws-exception">
 * ...and that.</a>
 */
@FunctionalInterface
public interface ConsumerE<T> extends Consumer<T> {

    @Override
    default void accept(T t) {
        try {
            checkedAccept(t);
        } catch (Exception e) {

        }
    }

    void checkedAccept(T t) throws Exception;
}

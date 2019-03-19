package aeon.selenium;

import java.util.Objects;
import java.util.function.Function;

/**
 * Quad functions. Needs help.
 *
 * @param <A> input A.
 * @param <B> input B.
 * @param <C> input C.
 * @param <R> input R.
 */
@FunctionalInterface
public interface QuadFunction<A, B, C, R> {


    /**
     * Apply the R function. Needs help.
     *
     * @param a in a.
     * @param b in b.
     * @param c in c.
     * @return The output of the R function with inputs a, b, and c.
     */
    R apply(A a, B b, C c);

    /**
     * The default Quad function. Needs help.
     *
     * @param after A superclass of the R function applied to a class extending V.
     * @param <V>   The type of input that the after function is applied to.
     * @return A composition of functions is returned, which is a QuadFunction itself.  The inputs of
     * the returned QuadFunction are the parameters a, b, and c.  The output of the returned QuadFunction
     * is the output of a new function applied to the output of the R function with inputs a, b, and c.
     */
    default <V> QuadFunction<A, B, C, V> andThen(
            Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (A a, B b, C c) -> after.apply(apply(a, b, c));
    }
}

package aeon.core.common.helpers;

import java.util.Objects;
import java.util.function.Function;

/**
 * Quad functions. Needs help.
 * @param <A> input A.
 * @param <B> input B.
 * @param <C> input C.
 * @param <R> input R.
 */
@FunctionalInterface
public interface QuadFunction<A, B, C, R> {


    /**
     * Apply the R function. Needs help.
     * @param a in a.
     * @param b in b.
     * @param c in c.
     * @return
     */
    R apply(A a, B b, C c);

    /**
     * The default Quad functoin. Needs help.
     * @param after
     * @param <V>
     * @return
     */
    default <V> QuadFunction<A, B, C, V> andThen(
            Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (A a, B b, C c) -> after.apply(apply(a, b, c));
    }
}

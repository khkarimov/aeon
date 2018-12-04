package aeon.core.common.web.selectors;

/**
 * Struct for functions for {@link ByJQuery}.
 */
public final class Function {

    private String functionString;

    /**
     * Initializes a new instance of the {@link Function} struct.
     *
     * @param function The parameter.
     */
    public Function(String function) {
        this.functionString = function;
    }

    /**
     * Returns the function string.
     *
     * @return the object.
     */
    public String getFunction() {
        return this.functionString;
    }

    /**
     * Returns a {@link String} that represents the current {@link Function}.
     *
     * @return A {@link String} that represents the current {@link Function}.
     */
    @Override
    public String toString() {
        if (this.functionString == null) {
            return "";
        }

        return this.functionString;
    }

    /**
     * Indicates whether the current object is equal to another object of the same type.
     *
     * @param other An object to compare with this object.
     * @return true if the current object is equal to the {@code other} parameter; otherwise, false.
     */
    public boolean equals(Function other) {
        return this.functionString.equals(other.functionString);
    }

    /**
     * Indicates whether this instance and a specified object are equal.
     *
     * @param obj Another object to compare to.
     * @return true if {@code obj} and this instance are the same type and represent the same value; otherwise, false.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Function && equals((Function) obj);
    }

    /**
     * Returns the hash code for this instance.
     *
     * @return A 32-bit signed integer that is the hash code for this instance.
     */
    @Override
    public int hashCode() {
        return this.functionString.hashCode();
    }
}

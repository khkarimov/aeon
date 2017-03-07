package aeon.core.common.web.selectors;

import aeon.core.common.helpers.StringUtils;

/**
 * Struct for parameters for {@link ByJQuery}.
 */
public final class Parameter {
    private Object object;

    /**
     * Initializes a new instance of the {@link Parameter} struct.
     *
     * @param parameter The parameter.
     */
    public Parameter(Object parameter) {
        object = parameter;
    }

    public Object getObject() {
        return object;
    }

    /**
     * Returns a {@link String} that represents the current {@link Object}.
     *
     * @return A {@link String} that represents the current {@link Object}.
     */
    @Override
    public String toString() {
        if (object == null) {
            return "";
        }

        String objString = (String) ((object instanceof String) ? object : null);
        return objString == null ? String.valueOf(object) : StringUtils.ToQuotedAndEscapedString(objString);
    }

    /**
     * Indicates whether the current object is equal to another object of the same type.
     *
     * @param other An object to compare with this object.
     * @return true if the current object is equal to the {@code other} parameter; otherwise, false.
     */
    public boolean equals(Parameter other) {
        return object.equals(other.object);
    }

    /**
     * Indicates whether this instance and a specified object are equal.
     *
     * @param obj Another object to compare to.
     * @return true if {@code obj} and this instance are the same type and represent the same value; otherwise, false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj instanceof Parameter && equals((Parameter) obj);
    }

    /**
     * Returns the hash code for this instance.
     *
     * @return A 32-bit signed integer that is the hash code for this instance.
     */
    @Override
    public int hashCode() {
        return object.hashCode();
    }
}

package echo.core.common.webobjects;

import echo.core.common.StringUtils;

/**
 * Struct for parameters for <see cref="ByJQuery"/>.
 */
public final class Parameter {
    private Object object;

    /**
     * Initializes a new instance of the <see cref="Parameter"/> struct.
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
     * Returns a <see cref="string"/> that represents the current <see cref="object"/>.
     *
     * @return A <see cref="string"/> that represents the current <see cref="object"/>.
     * <filterpriority>2</filterpriority>
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
     * @return true if the current object is equal to the <paramref name="other"/> parameter; otherwise, false.
     */
    public boolean equals(Parameter other) {
        return object.equals(other.object);
    }

    /**
     * Indicates whether this instance and a specified object are equal.
     *
     * @param obj Another object to compare to. <filterpriority>2</filterpriority>
     * @return true if <paramref name="obj"/> and this instance are the same type and represent the same value; otherwise, false.
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
     * <filterpriority>2</filterpriority>
     */
    @Override
    public int hashCode() {
        return object.hashCode();
    }
}

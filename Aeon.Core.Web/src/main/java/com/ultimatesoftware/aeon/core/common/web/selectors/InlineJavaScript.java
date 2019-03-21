package com.ultimatesoftware.aeon.core.common.web.selectors;

/**
 * Struct for functions for {@link ByJQuery}.
 */
public final class InlineJavaScript {

    private String inlineJavaScriptString;

    /**
     * Initializes a new instance of the {@link InlineJavaScript} struct.
     *
     * @param inlineJavaScript The parameter.
     */
    public InlineJavaScript(String inlineJavaScript) {
        this.inlineJavaScriptString = inlineJavaScript;
    }

    /**
     * Returns the function string.
     *
     * @return the object.
     */
    public String getInlineJavaScript() {
        return this.inlineJavaScriptString;
    }

    /**
     * Returns a {@link String} that represents the current {@link InlineJavaScript}.
     *
     * @return A {@link String} that represents the current {@link InlineJavaScript}.
     */
    @Override
    public String toString() {
        if (this.inlineJavaScriptString == null) {
            return "";
        }

        return this.inlineJavaScriptString;
    }

    /**
     * Indicates whether the current object is equal to another object of the same type.
     *
     * @param other An object to compare with this object.
     * @return true if the current object is equal to the {@code other} parameter; otherwise, false.
     */
    public boolean equals(InlineJavaScript other) {
        return this.inlineJavaScriptString.equals(other.inlineJavaScriptString);
    }

    /**
     * Indicates whether this instance and a specified object are equal.
     *
     * @param obj Another object to compare to.
     * @return true if {@code obj} and this instance are the same type and represent the same value; otherwise, false.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof InlineJavaScript && equals((InlineJavaScript) obj);
    }

    /**
     * Returns the hash code for this instance.
     *
     * @return A 32-bit signed integer that is the hash code for this instance.
     */
    @Override
    public int hashCode() {
        return this.inlineJavaScriptString.hashCode();
    }
}

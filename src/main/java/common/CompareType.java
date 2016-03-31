package common;

public enum CompareType {
    /**
     * Compare options by their text, in ascending numeric and alphabetical order.
     */
    AscendingByText,

    /**
     * Compare options by their values, in ascending numeric and alphabetical order.
     */
    AscendingByValue,

    /**
     * Compare options by their text, in descending numeric and alphabetical order.
     */
    DescendingByText,

    /**
     * Compare options by their values, in descending numeric and alphabetical order.
     */
    DescendingByValue
}
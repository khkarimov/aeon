package aeon.core.common.helpers;

import aeon.core.common.exceptions.ContainsWhiteSpaceException;

/**
 * Created by DionnyS on 3/31/2016.
 */
public class StringUtils {

    public static String toQuotedAndEscapedString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value");
        }

        // Note that the below has been optimized for performance (e.g., no regular expressions).
        StringBuilder stringBuilder = new StringBuilder("\"");
        stringBuilder.setLength(1);
        int length = value.length() - 1;
        char c;
        int i = 0;
        for (; i < length; ++i) {
            c = value.charAt(i);
            switch (c) {
                case '\\':
                    if (value.charAt(i + 1) == '\\' && value.charAt(i + 2) == '\'') {
                        i += 2;
                        stringBuilder.append("\\\\'");
                    } else if (value.charAt(i + 1) == '\'') {
                        ++i;
                        stringBuilder.append("\\'");
                    } else {
                        stringBuilder.append("\\\\");
                    }

                    break;
                case '\"':
                    stringBuilder.append("\\\"");
                    break;
                default:
                    stringBuilder.append(c);
                    break;
            }
        }

        if (i == length) {
            c = value.charAt(length);
            switch (c) {
                case '\\':
                    stringBuilder.append("\\\\");
                    break;
                case '\"':
                    stringBuilder.append("\\\"");
                    break;
                default:
                    stringBuilder.append(c);
                    break;
            }
        }

        stringBuilder.append('\"');
        return stringBuilder.toString();
    }

    /**
     * Normalizes all line breaks to LF, as well as all spaces to breaking spaces.
     * <p>
     *
     * @param value The current instance.
     * @return The normalized string value;
     */
    public static String normalizeSpacing(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value");
        }
        StringBuilder stringBuilder = new StringBuilder(value.length());
        stringBuilder.setLength(0);
        int length = value.length() - 1;
        int i = 0;
        char currentChar;
        for (; i < length; ++i) {
            currentChar = value.charAt(i);
            switch (currentChar) {
                case '\r':
                    if (value.charAt(i + 1) == '\n') {
                        ++i;
                    }

                    stringBuilder.append('\n');
                    break;
                case '\u00a0':
                    stringBuilder.append(' ');
                    break;
                case '\u200e':
                case '\u200f':
                case '\u201f':
                    break;
                default:
                    stringBuilder.append(currentChar);
                    break;
            }
        }

        if (i == length) {
            currentChar = value.charAt(length);
            stringBuilder.append(currentChar == '\r' ? '\n' : currentChar);
        }
        return stringBuilder.toString();
    }

    /**
     * Used, for example, in JavaScript to reduce whitespace.
     *
     * @param value The current instance
     * @return A string where all the spacing is the same, i.e. tabs, new line, etc..
     */
    public static String minimizeWhiteSpace(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value");
        }
        StringBuilder stringBuilder = new StringBuilder(value.length());
        stringBuilder.setLength(0);
        boolean inSpaces = false;
        for (char currentChar : value.toCharArray()) {
            //SR- vertical tab character is not checked, since \v is not a valid char
            if (inSpaces) {
                if (currentChar != ' ' && currentChar != '\t' && currentChar != '\r' && currentChar != '\n' && currentChar != '\f') {
                    inSpaces = false;
                    stringBuilder.append(currentChar);
                }
            } else if (currentChar == ' ' || currentChar == '\t' || currentChar == '\r' || currentChar == '\n' || currentChar == '\f') {
                inSpaces = true;
                stringBuilder.append(' ');
            } else {
                stringBuilder.append(currentChar);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Used for exact comparisons, ignoring the spacing differences.
     *
     * @param value         The current instance.
     * @param expectedValue The value your comparing against the current instance.
     * @return Whether or not the current instance matches the expected value.
     */
    public static boolean is(String value, String expectedValue) {
        if (value == null) {
            throw new IllegalArgumentException("value");
        }
        if (expectedValue == null) {
            throw new IllegalArgumentException("expectedValue");
        }
        return normalizeSpacing(value).equals(normalizeSpacing(expectedValue));
    }


    /**
     * Used for partial comparisons, including differences in case. It ignores
     * differences in spacing.
     *
     * @param value         The current instance.
     * @param expectedValue The value your comparing against the current instance.
     * @return Whether or not the current instance equals the expected value.
     */
    public static boolean like(String value, String expectedValue) {
        return like(value, expectedValue, true);
    }

    /**
     * Used for partial comparisons, ignoring differences in case and
     * spacing differences.
     *
     * @param value         The current instance.
     * @param expectedValue The value your comparing against the current instance.
     * @return Whether or not the current instance equals the expected value.
     */
    public static boolean like(String value, String expectedValue, boolean caseSensitive) {
        if (value == null) {
            throw new IllegalArgumentException("value");
        }
        if (expectedValue == null) {
            throw new IllegalArgumentException("expectedValue");
        }
        if (expectedValue.length() == 0) {
            throw new IllegalArgumentException("zero length expected value");
        }
        if (caseSensitive) {
            return contains(normalizeSpacing(value), normalizeSpacing(expectedValue));
        } else {
            return containsIgnoreCase(normalizeSpacing(value), normalizeSpacing(expectedValue).toLowerCase());
        }
    }

    /**
     * Indicates whether the current instance contains the specified string.
     *
     * @param value The current instance.
     * @param word  The word for which to search.
     * @return Whether or not the current instance contains the word.
     */
    private static boolean contains(String value, String word) {
        return value.contains(word);
    }

    /**
     * Indicates whether the current instance contains the specified string, ignores case.
     *
     * @param value The current instance.
     * @param word  The word for which to search.
     * @return Whether or not the current instance contains the word.
     */
    private static boolean containsIgnoreCase(String value, String word){
        return contains(value.toLowerCase(), word.toLowerCase());
    }

    /**
     * Indicates whether the current instance contains the specified string as a word surrounded by spaces.
     *
     * @param value The current instance.
     * @param word  The word for which to search.
     * @return Whether or not the current instance contains the word.
     */
    public static boolean containsWord(String value, String word) {
        if (value == null) {
            throw new IllegalArgumentException("value");
        }
        if (word == null) {
            throw new IllegalArgumentException("word");
        }
        String normalizedSpacinValue = minimizeWhiteSpace(value);
        return normalizedSpacinValue.contains(' ' + word + ' ') || normalizedSpacinValue.startsWith(word) || normalizedSpacinValue.endsWith(word);
    }

    /**
     * Asserts the the current instance contains no whitespace.
     *
     * @param value The current instance.
     * @return The current instance, if not exception is thrown.
     */
    public static String assertNoWhiteSpace(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value");
        }
        if (value.contains(" ")) {
            throw new ContainsWhiteSpaceException(value);
        }
        return value;
    }

    /**
     * Removes the text specified.
     *
     * @param value  The current instance.
     * @param values Values to remove from the current instance.
     * @return The current instance with values removed.
     */
    public static String remove(String value, Object... values) {
        if (value == null) {
            throw new IllegalArgumentException("value");
        }
        if (values == null) {
            return value;
        }
        for (Object val : values) {
            if (value.indexOf((String) val) != -1) {
                value.replace((String) val, "");
            }
        }
        return value;
    }
}

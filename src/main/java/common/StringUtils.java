package common;

/**
 * Created by DionnyS on 3/31/2016.
 */
public class StringUtils {
    public static String ToQuotedAndEscapedString(String value) {
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
}
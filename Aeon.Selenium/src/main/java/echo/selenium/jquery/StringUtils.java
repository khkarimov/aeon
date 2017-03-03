package echo.selenium.jquery;

/**
 * Created by DionnyS on 4/14/2016.
 */
public class StringUtils {
    public static String MinimizeWhiteSpace(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value");
        }

        // Note that the below has been optimized for performance (e.g., no regular expressions).
        StringBuilder stringBuilder = new StringBuilder(value.length());
        boolean inSpaces = false;
        stringBuilder.setLength(0);
        for (char c : value.toCharArray()) {
            // We explicitly list each whitespace character rather than use IEnumerable for performance reasons.
            // Note that the next two conditional statements MUST use the same set of whitespace characters.
            if (inSpaces) {
                if (c != ' ' && c != '\t' && c != '\r' && c != '\n' && c != '\f') {
                    inSpaces = false;
                    stringBuilder.append(c);
                }
            } else if (c == ' ' || c == '\t' || c == '\r' || c == '\n' || c == '\f') {
                inSpaces = true;
                stringBuilder.append(' ');
            } else {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
    }
}

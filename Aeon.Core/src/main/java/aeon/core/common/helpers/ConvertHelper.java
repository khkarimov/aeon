package aeon.core.common.helpers;

import aeon.core.framework.abstraction.controls.web.WebControl;

import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Converts a base data type to another base data type.
 */
public final class ConvertHelper {
    /**
     * Converts a configuration element to type {@code type}.
     *
     * @param value The value.
     * @param type  The type to which to convert.
     * @return The converted value.
     */
    public static Object fromStringToObject(String value, java.lang.Class type) {
        if (value == null) {
            return null;
        }

        if (type == null) {
            throw new IllegalArgumentException("type");
        }

        if (type.isEnum()) {
            return Enum.valueOf(type, value);
        }

        if (type == String.class) {
            return value;
        }

        if (type == Boolean.class) {
            return Boolean.parseBoolean(value);
        }

        if (type == Byte.class) {
            return Byte.parseByte(value);
        }

        if (type == Character.class) {
            return value.charAt(0);
        }

        if (type == Double.class) {
            return Double.parseDouble(value);
        }

        if (type == Float.class) {
            return Float.parseFloat(value);
        }

        if (type == Integer.class) {
            return Integer.parseInt(value);
        }

        if (type == Long.class) {
            return Long.parseLong(value);
        }

        if (type == Byte.class) {
            return Byte.parseByte(value);
        }

        if (type == Short.class) {
            return Short.parseShort(value);
        }

        if (type == Integer.class) {
            return Integer.parseInt(value);
        }

        if (type == Long.class) {
            return Long.parseLong(value);
        }

        if (type == Short.class) {
            return Short.parseShort(value);
        }

        if (type == URI.class) {
            return URI.create(value);
        }

        throw new UnsupportedOperationException();
    }

    public static String scriptReturnValueToReadableString(Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof Collection<?> && ((Collection<?>) value).size() > 0) {
            try {
                Collection<WebControl> elements = (Collection<WebControl>) value;

                return String.format(
                        "%1$s: [\"%2$s\"]",
                        value.getClass(),
                        String.join("\", \"", elements.stream()
                                .map(x -> x.getSelector().toString())
                                .collect(Collectors.toList())
                        ));
            } catch (Exception e) {
                return value.toString();
            }
        }

        return value.toString();
    }
}

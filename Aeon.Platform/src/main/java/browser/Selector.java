package browser;

/**
 * Class to hold IByWeb arguments.
 */
public class Selector {

    private String value;
    private String type;

    /**
     * Creates a Web Selector.
     * @param value Value
     * @param type Type
     */
    public Selector(String value, String type) {
        this.value = value;
        this.type = type;
    }

    /**
     * Get the value.
     * @return Value
     */
    public String getValue() {
        return value;
    }

    /**
     * Get the type.
     * @return Type
     */
    public String getType() {
        return type;
    }
}

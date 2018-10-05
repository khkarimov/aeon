package browser;

/**
 * Class to define IByWeb arguments.
 */
public class ByWebArgs {

    private String selector;
    private String type;

    /**
     * Constructs a IByWeb argument block.
     * @param selector Selector
     * @param type Type
     */
    public ByWebArgs(String selector, String type) {
        this.selector = selector;
        this.type = type;
    }

    /**
     * Get the selector.
     * @return Selector
     */
    public String getSelector() {
        return selector;
    }

    /**
     * Get the type.
     * @return Type
     */
    public String getType() {
        return type;
    }
}

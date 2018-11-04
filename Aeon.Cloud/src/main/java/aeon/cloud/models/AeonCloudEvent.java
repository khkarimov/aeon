package aeon.cloud.models;

/**
 * Aeon Cloud event payload.
 */
public class AeonCloudEvent {

    private String type;
    private Object payload;

    /**
     * Constructor with all properties.
     *
     * @param type    The type of the event.
     * @param payload The payload of the event.
     */
    public AeonCloudEvent(String type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    /**
     * Getter for the type.
     *
     * @return The type of the event.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Getter for the event payload.
     *
     * @return The payload of the event.
     */
    public Object getPayload() {
        return this.payload;
    }
}

package aeon.platform.http.models;

/**
 * Class to create a response body.
 */
public class ResponseBody {

    private boolean success;
    private String data;
    private String failureMessage;

    /**
     * Constructs a response body.
     * @param success True if command was successfully executed, false otherwise
     * @param data Data returned from command execution
     * @param failureMessage Exception message
     */
    public ResponseBody(boolean success, String data, String failureMessage) {
        this.success = success;
        this.data = data;
        this.failureMessage = failureMessage;
    }

    /**
     * Get the success flag.
     * @return Success
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     * Get the data.
     * @return Data
     */
    public String getData() {
        return data;
    }

    /**
     * Get the failure message.
     * @return Failure message
     */
    public String getFailureMessage() {
        return failureMessage;
    }
}

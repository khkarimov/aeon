package aeon.platform.http.models;

import org.json.simple.JSONObject;

/**
 * Class to create a response body.
 */
public class ResponseBody {

    private String sessionId;
    private boolean success;
    private String data;
    private String failureMessage;

    /**
     * Constructs a response body.
     *
     * @param sessionId      Session ID
     * @param success        True if command was successfully executed, false otherwise
     * @param data           Data returned from command execution
     * @param failureMessage Exception message
     */
    public ResponseBody(String sessionId, boolean success, String data, String failureMessage) {
        this.sessionId = sessionId;
        this.success = success;
        this.data = data;
        this.failureMessage = failureMessage;
    }

    /**
     * Get the session ID.
     *
     * @return Session ID
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Get the success flag.
     *
     * @return Success
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     * Get the data.
     *
     * @return Data
     */
    public String getData() {
        return data;
    }

    /**
     * Get the failure message.
     *
     * @return Failure message
     */
    public String getFailureMessage() {
        return failureMessage;
    }

    @Override
    public String toString() {
        JSONObject responseJson = new JSONObject();
        responseJson.put("sessionId", sessionId);
        responseJson.put("success", success);
        responseJson.put("data", data);
        responseJson.put("failureMessage", failureMessage);

        return responseJson.toString();
    }
}

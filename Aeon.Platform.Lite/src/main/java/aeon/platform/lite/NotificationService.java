package aeon.platform.lite;

import javax.ws.rs.core.Response;

public class NotificationService {

    public enum EventType {
        COMMAND_EXECUTED
    }

    public NotificationService() {

    }

    void notify(EventType eventType, String callbackUrl, Object payload) {
        if (callbackUrl == null || callbackUrl.isEmpty()) {
            return;
        }

        // fix
        Response response = Response.status(Response.Status.OK).entity(payload).build();
        //post for entity
    }
}

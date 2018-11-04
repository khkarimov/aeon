package aeon.cloud.services;

import aeon.cloud.models.AeonCloudEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Notification service.
 * <p>
 * Notifies services via callback URL.
 */
@Service
public class NotificationService {

    private final RestTemplate restTemplate;

    /**
     * Event types.
     */
    public enum EventType {
        RUNNER_DEPLOYED,
        RUNNER_FAILED,
        RUNNER_DELETED,
        RUNNER_DELETION_FAILED
    }

    /**
     * Constructor.
     *
     * @param restTemplate The REST template to use.
     */
    @Autowired
    public NotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Send payload to callback URL.
     *
     * @param eventType   The type of the event.
     * @param callbackUrl The callback URL to send the event to.
     * @param payload     The payload of the event.
     */
    void notify(EventType eventType, String callbackUrl, Object payload) {

        if (callbackUrl == null || callbackUrl.isEmpty()) {
            return;
        }

        AeonCloudEvent event = new AeonCloudEvent(eventType.toString(), payload);

        this.restTemplate.postForEntity(callbackUrl, event, Object.class);
    }
}

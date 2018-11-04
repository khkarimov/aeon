package aeon.cloud.services;

import aeon.cloud.models.AeonCloudEvent;
import aeon.cloud.models.Runner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceTests {

    private NotificationService notificationService;

    @Mock
    private RestTemplate restTemplate;

    private Runner runner;

    private ArgumentCaptor<AeonCloudEvent> argument = ArgumentCaptor.forClass(AeonCloudEvent.class);

    @Before
    public void setUp() {
        notificationService = new NotificationService(this.restTemplate);

        this.runner = new Runner("id", "name", "tenant");
    }

    @Test
    public void notifyTest() {
        // Arrange

        // Act
        this.notificationService.notify(
                NotificationService.EventType.RUNNER_DEPLOYED,
                "callback",
                this.runner
        );

        // Assert
        verify(this.restTemplate, times(1))
                .postForEntity(eq("callback"), argument.capture(), eq(Object.class));
        assertEquals(NotificationService.EventType.RUNNER_DEPLOYED.toString(), argument.getValue().getType());
        assertEquals(this.runner, argument.getValue().getPayload());
    }

    @Test
    public void notifyWithNullCallbackTest() {
        // Arrange

        // Act
        this.notificationService.notify(
                NotificationService.EventType.RUNNER_DEPLOYED,
                null,
                this.runner
        );

        // Assert
        verify(this.restTemplate, times(0))
                .postForEntity(anyString(), any(), any());
    }

    @Test
    public void notifyWithEmptyCallbackTest() {
        // Arrange

        // Act
        this.notificationService.notify(
                NotificationService.EventType.RUNNER_DEPLOYED,
                "",
                this.runner
        );

        // Assert
        verify(this.restTemplate, times(0))
                .postForEntity(anyString(), any(), any());
    }
}

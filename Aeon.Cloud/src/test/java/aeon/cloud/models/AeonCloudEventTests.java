package aeon.cloud.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AeonCloudEventTests {

    @Test
    public void aeonCloudEventTest() {

        Runner runner = new Runner("runnerId", "runnerName", "tenant");
        AeonCloudEvent aeonCloudEvent = new AeonCloudEvent("eventType", runner);

        assertEquals("eventType", aeonCloudEvent.getType());
        assertEquals(runner, aeonCloudEvent.getPayload());
    }
}

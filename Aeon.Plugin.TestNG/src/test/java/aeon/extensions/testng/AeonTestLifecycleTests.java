package aeon.extensions.testng;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AeonTestLifecycleTests {

    private TestNgListener testNgListener;

    @Before
    public void setup() {
        testNgListener = new TestNgListener();
    }

    @Test
    public void test() {

        // Arrange

        // Act

        // Assert
        assertNotNull(testNgListener);
    }
}

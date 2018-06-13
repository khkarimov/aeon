import aeon.extensions.reporting.Report;
import aeon.extensions.reporting.Scenario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportTests {
    @Test
    public void reportPropertiesTest() {
        //Arrange
        Report report = new Report();
        report.setSuiteName("suite");
        report.setTotalTime("10");
        report.addFailed();
        report.addPass();
        report.addSkipped();
        report.addBroken();
        report.getScenarioBeans().add(new Scenario());

        //Act

        //Assert
        assertAll("properties",
                () -> assertEquals("suite", report.getSuiteName()),
                () -> assertEquals("10", report.getTotalTime()),
                () -> assertEquals(1, report.getFailed()),
                () -> assertEquals(1, report.getPassed()),
                () -> assertEquals(1, report.getSkipped()),
                () -> assertEquals(1, report.getBroken()),
                () -> assertEquals(1, report.getScenarioBeans().size()),
                () -> assertTrue(report.isSuiteBroken()),
                () -> assertTrue(report.isSuiteFailed()),
                () -> assertTrue(report.isSuitePassed()),
                () -> assertTrue(report.isSuiteSkipped()));
    }

    @Test
    public void reportToStringTest() {
        //Arrange
        Report report = new Report();
        report.addPass();

        //Act
        String str = report.toString();

        //Assert
        assertEquals("Report{total=1, passed=1, failed=0}", str);
    }
}

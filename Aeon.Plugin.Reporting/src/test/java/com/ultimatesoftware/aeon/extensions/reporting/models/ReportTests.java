package com.ultimatesoftware.aeon.extensions.reporting.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReportTests {

    private Report report;

    @BeforeEach
    void setup() {
        report = new Report();
    }

    @Test
    void testReport_onInstantiation_initializesFields() {

        // Arrange

        // Act

        // Assert
        assertNotNull(this.report.getCounts());
        assertNotNull(this.report.getSequence());
        assertNotNull(this.report.getTimer());
    }

    @Test
    void testNameGetterAndSetter() {

        // Arrange
        report.setName("suiteName");

        // Act
        String name = report.getName();

        // Assert
        assertEquals("suiteName", name);
    }

    @Test
    void testCorrelationIdGetterAndSetter() {

        // Arrange
        report.setCorrelationId("correlationId");

        // Act
        String correlationId = report.getCorrelationId();

        // Assert
        assertEquals("correlationId", correlationId);
    }

    @Test
    void testUrlGetterAndSetter() {

        // Arrange
        report.setURL("url");

        // Act
        String url = report.getURL();

        // Assert
        assertEquals("url", url);
    }

    @Test
    void testSequenceGetterAndSetter() {

        // Arrange
        TestCase testCase = new TestCase();
        List<TestCase> testCases = Collections.singletonList(testCase);
        report.setSequence(testCases);

        // Act
        List<TestCase> sequence = report.getSequence();

        // Assert
        assertEquals(testCase, sequence.get(0));
    }
}

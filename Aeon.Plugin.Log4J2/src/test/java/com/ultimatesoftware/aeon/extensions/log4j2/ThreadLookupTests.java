package com.ultimatesoftware.aeon.extensions.log4j2;

import org.apache.logging.log4j.core.LogEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.management.ManagementFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ThreadLookupTests {

    private ThreadLookup threadLookup;

    @Mock
    private LogEvent logEvent;

    @BeforeEach
    void setup() {
        this.threadLookup = new ThreadLookup();
    }

    @Test
    void lookup_isCalledWithLogEvent_returnsProcessAndThreadId() {

        // Arrange
        String processId = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        long threadId = Thread.currentThread().getId();
        String expectedName = processId + "-" + threadId;

        // Act
        String name = this.threadLookup.lookup(this.logEvent, "key");

        // Assert
        assertEquals(expectedName, name);
    }

    @Test
    void lookup_isCalledWithoutLogEvent_returnsProcessAndThreadId() {

        // Arrange
        String processId = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        long threadId = Thread.currentThread().getId();
        String expectedName = processId + "-" + threadId;

        // Act
        String name = this.threadLookup.lookup("key");

        // Assert
        assertEquals(expectedName, name);
    }
}

package com.ultimatesoftware.aeon.extensions.log4j2;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;

import java.lang.management.ManagementFactory;

/**
 * Allows for log files per process/thread.
 */
@Plugin(name = "thread", category = StrLookup.CATEGORY)
public class ThreadLookup implements StrLookup {

    @Override
    public String lookup(String key) {
        String processId = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        long threadId = Thread.currentThread().getId();
        return processId + "-" + threadId;
    }

    @Override
    public String lookup(LogEvent event, String key) {
        return this.lookup(key);
    }
}

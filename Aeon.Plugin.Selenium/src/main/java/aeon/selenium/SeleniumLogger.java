package aeon.selenium;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SeleniumLogger {

    private String browserPath;
    private String clientPath;
    private String driverPath;
    private String performancePath;
    private String serverPath;
    private static Logger log4j = LogManager.getLogger(SeleniumAdapter.class);
    private boolean alreadyLoggedAll = false;
    private Set<String> availableLogTypes;
    private WebDriver webDriver;

    SeleniumLogger(String directory, WebDriver webDriver) {
        browserPath = directory + "/browser.log";
        clientPath = directory + "/client.log";
        driverPath = directory + "/driver.log";
        performancePath = directory + "/performance.log";
        serverPath = directory + "/server.log";
        this.webDriver = webDriver;
        availableLogTypes = webDriver.manage().logs().getAvailableLogTypes();
    }

    void logAllToFiles() {
        if(alreadyLoggedAll) return;
        alreadyLoggedAll = true;
        if(isLogTypeAvailable(LogType.BROWSER)) {
            logToFile(browserPath, getBrowserLogs());
        }
        if(isLogTypeAvailable(LogType.CLIENT)) {
            logToFile(clientPath, getClientLogs());
        }
        if(isLogTypeAvailable(LogType.DRIVER)) {
            logToFile(driverPath, getDriverLogs());
        }
        if(isLogTypeAvailable(LogType.PERFORMANCE)) {
            logToFile(performancePath, getPerformanceLogs());
        }
        if(isLogTypeAvailable(LogType.SERVER)) {
            logToFile(serverPath, getServerLogs());
        }
    }

    List<String> getBrowserLogs() {
        return getLog(LogType.BROWSER);
    }

    List<String> getClientLogs() {
        return getLog(LogType.CLIENT);
    }

    List<String> getDriverLogs() {
        return getLog(LogType.DRIVER);
    }

    List<String> getPerformanceLogs() {
        return getLog(LogType.PERFORMANCE);
    }

    List<String> getServerLogs() {
        return getLog(LogType.SERVER);
    }

    private boolean isLogTypeAvailable(String logType) {
        return availableLogTypes.contains(logType);
    }

    private List<String> getLog(String logType) {
        if(isLogTypeAvailable(logType)) {
            List<LogEntry> logEntries = webDriver.manage().logs().get(logType).getAll();
            return logEntries.stream().map(log -> log.toJson().toString()).collect(Collectors.toList());
        } else {
            throw new RuntimeException("Turn on " + logType + " logs in test.properties.");
        }
    }

    private void logToFile(String filename, List<String> logs) {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            for(String log : logs){
                fileWriter.write(log);
                fileWriter.write('\n');
            }
            fileWriter.close();
        } catch(IOException e) {
            log4j.error("Couldn't write Selenium log entries to " + filename);
            log4j.error(e);
        }
    }
}

package echo.core.common.logging;

import java.util.UUID;

import java.awt.Image;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class Log implements ILog {
    private static final Logger logger = LogManager.getLogger(Log.class);

    @Override
    public void Debug(UUID guid, String message) {
        logger.debug(guid.toString() + ": " + message);
    }

    @Override
    public void Error(UUID guid, String message) {
        logger.error(guid.toString() + ": " + message);
    }

    @Override
    public void Error(String message, RuntimeException e) {
        logger.error(e);
    }

    @Override
    public void Error(UUID guid, String message, Iterable<String> runningProcesses) {
        logger.error(guid.toString() + ": " + message, runningProcesses);
    }

    @Override
    public void Error(UUID guid, String message, Image screenshot, Iterable<String> runningProcesses) {
        logger.error(guid.toString() + ": " + message, screenshot, runningProcesses);
    }

    @Override
    public void Info(UUID guid, String message) {
        logger.info(guid.toString() + ": " + message);
    }

    @Override
    public void Trace(UUID guid, String message) {
        logger.trace(guid.toString() + ": " + message);
    }

    @Override
    public void Trace(UUID guid, String message, String pageSource) {
        logger.trace(guid.toString() + ": " + message + " " + pageSource);
    }
}

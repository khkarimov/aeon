package com.ultimatesoftware.aeon.extensions.selenium;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * File download helper.
 */
public class FileDownloadHelper {

    private static Logger log = LoggerFactory.getLogger(FileDownloadHelper.class);

    /**
     * Download video from Selenium Hub.
     * @param seleniumHubUrl Selenium Hub URL.
     * @param sessionId Selenium Grid Session ID.
     * @return Absolute path to downloaded video.
     */
    public String downloadVideo(URL seleniumHubUrl, String sessionId) {

        URL downloadUrl;
        try {
            downloadUrl = new URL(
                    seleniumHubUrl.getProtocol(),
                    seleniumHubUrl.getHost(),
                    seleniumHubUrl.getPort(),
                    "/grid/admin/HubVideoDownloadServlet?sessionId=" + sessionId);
        } catch (MalformedURLException e) {
            log.error("Error creating video download URL", e);

            return null;
        }

        File tempFile;
        try {
            // Check for correct response and content type for graceful failure
            HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();

            String contentType = connection.getContentType();
            connection.disconnect();

            // The actual file is webm, but connection grabs it as mp4
            if (!contentType.equals("video/mp4")) {
                log.trace("Test video not downloaded: Either this grid does not support video, or the given sessionId is invalid.");

                return null;
            }

            tempFile = File.createTempFile("video-", ".webm");
            tempFile.deleteOnExit();

        } catch (IOException e) {
            log.error("Error checking for response type for video download from Selenium Grid.", e);

            return null;
        }

        try (ReadableByteChannel readableByteChannel = Channels.newChannel(downloadUrl.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {

            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

        } catch (IOException e) {
            log.error("Error downloading video from Selenium Grid.", e);

            return null;
        }

        log.info("Video downloaded from Selenium Grid: {}", tempFile.getAbsolutePath());

        return tempFile.getAbsolutePath();
    }
}

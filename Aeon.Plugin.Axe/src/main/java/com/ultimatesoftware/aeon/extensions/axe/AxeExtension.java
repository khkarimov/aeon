package com.ultimatesoftware.aeon.extensions.axe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultimatesoftware.aeon.core.common.exceptions.UnableToTakeScreenshotException;
import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.core.extensions.ITestExecutionExtension;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IWebAdapter;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import com.ultimatesoftware.aeon.extensions.accessibility.IAccessibilityExtension;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Accessibility extension for Axe.
 */
@Extension
public class AxeExtension implements ITestExecutionExtension, IAccessibilityExtension {

    private final IConfiguration configuration;
    private final CloseableHttpClient httpClient;

    private IWebAdapter adapter;
    private String correlationId;

    static Logger log = LoggerFactory.getLogger(AxeExtension.class);

    AxeExtension(IConfiguration configuration, CloseableHttpClient httpClient) {
        this.configuration = configuration;
        this.httpClient = httpClient;
    }

    /**
     * Creates an instance for this extension.
     *
     * @return An instance of this extension.
     */
    public static Object createInstance() {

        IConfiguration configuration = new AxeConfiguration();

        try {
            configuration.loadConfiguration();
        } catch (IllegalAccessException | IOException e) {
            log.warn("Could not load plugin configuration.");
        }

        return new AxeExtension(configuration, HttpClients.createDefault());
    }

    @Override
    public void onStartUp(Configuration configuration, String correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public void onBeforeStart(String correlationId, String suiteName) {
        // Not needed
    }

    @Override
    public void onBeforeLaunch(Configuration configuration) {
        // Not needed
    }

    @Override
    public void onAfterLaunch(Configuration configuration, IAdapter adapter) {
        this.adapter = (IWebAdapter) adapter;
    }

    @Override
    public void onBeforeTest(String name, String... tags) {
        // Not needed
    }

    @Override
    public void onSucceededTest() {
        // Not needed
    }

    @Override
    public void onSkippedTest(String name, String... tags) {
        // Not needed
    }

    @Override
    public void onFailedTest(String reason, Throwable e) {
        // Not needed
    }

    @Override
    public void onBeforeStep(String message) {
        // Not needed
    }

    @Override
    public void onDone() {
        // Not needed
    }

    @Override
    public void onExecutionEvent(String eventName, Object payload) {
        // Not needed
    }

    @Override
    public void runAccessibilityTests(String pageName) {
        this.adapter.executeScript(this.getAxeJS());
        String reportRetrievalScript = "var callback = arguments[arguments.length - 1]; axe.run().then(function(result){callback(result);});";
        Map<String, Object> accessibilityReport = (Map<String, Object>) this.adapter.executeAsyncScript(reportRetrievalScript);

        String screenshot = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write((BufferedImage) this.adapter.getScreenshot(), "png", baos);
            screenshot = DatatypeConverter.printBase64Binary(baos.toByteArray());
        } catch (IOException | IllegalArgumentException | UnableToTakeScreenshotException e) {
            log.error("Unable to get screenshot", e);
        }

        this.sendReport(accessibilityReport, pageName, screenshot);
    }

    /**
     * Gets the Ajax Waiter as a string.
     *
     * @return the content of the buffered reader as a string.
     */
    private String getAxeJS() {
        try (InputStream scriptReader = AxeExtension.class.getResourceAsStream("/axe.min.js")) {
            return new BufferedReader(new InputStreamReader(scriptReader)).lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new AxeException("Unable to inject Axe JavaScript", e);
        }
    }

    private void sendReport(Map<String, Object> accessibilityReport, String pageName, String screenshot) {
        AxeReport report = new AxeReport();
        report.setTeam(this.configuration.getString(AxeConfiguration.Keys.TEAM, ""));
        report.setProduct(this.configuration.getString(AxeConfiguration.Keys.PRODUCT, ""));
        report.setPage(pageName);
        report.setBranch(this.configuration.getString(AxeConfiguration.Keys.BRANCH, ""));
        report.setBuildNumber(this.configuration.getString(AxeConfiguration.Keys.BUILD_NUMBER, ""));
        report.setCorrelationId(this.correlationId);
        report.setScreenshot(screenshot);
        report.setReport(accessibilityReport);

        if (report.getTeam().isEmpty() || report.getProduct().isEmpty() || pageName == null || report.getPage().isEmpty()) {
            throw new AxeException("Team, product and page names need to be specified in order to submit an Axe report.");
        }

        String url = this.configuration.getString(AxeConfiguration.Keys.SERVER_URL, "");
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        try {
            httpPost.setEntity(new StringEntity(new ObjectMapper().writeValueAsString(report), "UTF-8"));
        } catch (JsonProcessingException e) {
            throw new AxeException("Could not serialize Axe report.", e);
        }

        this.executeRequest(httpPost, url);

        log.info("Axe report successfully uploaded for page {} and correlation ID {}.", pageName, this.correlationId);
    }

    private void executeRequest(HttpPost post, String url) {
        try (CloseableHttpResponse response = httpClient.execute(post)) {
            int statusCode = response.getStatusLine().getStatusCode();
            String body = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()
                    )
            ).lines().collect(Collectors.joining("\n"));
            if (statusCode != HttpStatus.SC_OK) {
                String message = String.format("Did not receive successful status code for Axe report upload. Received %s with body: %s", statusCode, body);
                throw new AxeException(message);
            }
        } catch (IOException e) {
            throw new AxeException(String.format("Could not upload report to Axe report server (%s): %s.", url, e.getMessage()), e);
        }
    }
}

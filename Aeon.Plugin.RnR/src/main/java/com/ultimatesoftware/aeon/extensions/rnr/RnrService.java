package com.ultimatesoftware.aeon.extensions.rnr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import com.ultimatesoftware.aeon.extensions.reporting.HtmlReport;
import com.ultimatesoftware.aeon.extensions.reporting.ReportingConfiguration;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for uploading reports to RocknRoly.
 */
class RnrService {

    private String browser;
    private String environmentUrl;

    private String product;
    private String team;
    private String type;
    private String branch;
    private String rnrUrl;
    private String reportsDirectory;

    private final CloseableHttpClient httpClient;

    static Logger log = LoggerFactory.getLogger(RnrService.class);

    /**
     * Instantiates a new object of {@link RnrService}.
     *
     * @param configuration The Reporting plugin configuration object.
     */
    RnrService(CloseableHttpClient httpClient, IConfiguration configuration) {
        this.httpClient = httpClient;
        this.browser = configuration.getString("aeon.browser", "");
        this.environmentUrl = configuration.getString("aeon.environment", "");
        this.product = configuration.getString(RnrConfiguration.Keys.PRODUCT, "");
        this.team = configuration.getString(RnrConfiguration.Keys.TEAM, "");
        this.type = configuration.getString(RnrConfiguration.Keys.TYPE, "");
        this.branch = configuration.getString(RnrConfiguration.Keys.BRANCH, "");
        this.rnrUrl = configuration.getString(RnrConfiguration.Keys.RNR_URL, "");
        this.reportsDirectory = configuration.getString(ReportingConfiguration.Keys.REPORTS_DIRECTORY, "");
    }

    /**
     * Uploads a report to RocknRoly.
     *
     * @param jsonFileName     The name of the JSON file of the report to upload.
     * @param angularReportUrl The URL of the HTML report to link.
     * @param correlationId    The correlation ID to use for the report.
     * @return The RocknRoly URL of the uploaded report.
     */
    String uploadToRnr(String jsonFileName, String angularReportUrl, String correlationId) {

        if (!allConfigFieldsAreSet()) {
            log.info("Not all RnR properties are set, cancelling upload to RnR.");
            return null;
        }

        File resultsJsonFile = new File(jsonFileName);

        HttpEntity multiPartEntity = buildRnrMultiPartEntity(angularReportUrl, resultsJsonFile, correlationId);

        String fullRequestUrl = rnrUrl + "/testsuite";

        HttpPost post = new HttpPost(fullRequestUrl);
        post.setEntity(multiPartEntity);

        boolean success = executeRequest(post, fullRequestUrl);

        if (!success) {
            return null;
        }

        return rnrUrl + "/" + correlationId;
    }

    /**
     * Creates the JSON report file.
     *
     * @param jsonReport    The JSON string to write to a file.
     * @param correlationId The correlation ID of the report.
     * @return The name and path of the created JSON report file.
     */
    String createJsonReportFile(String jsonReport, String correlationId) {
        String jsonReportFileName = reportsDirectory
                + "/report-" + correlationId + ".json";
        HtmlReport.writeFile(jsonReport, jsonReportFileName);
        return jsonReportFileName;
    }

    private boolean allConfigFieldsAreSet() {
        return !(product.isEmpty()
                || team.isEmpty()
                || type.isEmpty()
                || branch.isEmpty());
    }

    private HttpEntity buildRnrMultiPartEntity(String angularReportUrl, File resultsJsonFile, String correlationId) {
        Map<String, String> rnrMetaMap = buildRnrMetaMap(angularReportUrl);
        File rnrMetaFile = buildRnrMetaFile(rnrMetaMap);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("namespace", team);
        builder.addTextBody("project", product);
        builder.addTextBody("type", type);
        builder.addTextBody("branch", branch);
        builder.addTextBody("correlationId", correlationId);
        builder.addBinaryBody("file", resultsJsonFile,
                ContentType.APPLICATION_OCTET_STREAM, "results.js");

        if (rnrMetaFile != null) {
            builder.addBinaryBody("metaFile", rnrMetaFile,
                    ContentType.APPLICATION_OCTET_STREAM, "meta.rnr");
        }

        return builder.build();
    }

    private Map<String, String> buildRnrMetaMap(String reportUrl) {
        Map<String, String> rnrMetaMap = new HashMap<>();
        rnrMetaMap.put("screenshots", reportUrl);
        rnrMetaMap.put("app", environmentUrl);
        rnrMetaMap.put("browser", browser);
        rnrMetaMap.put("aeon.version", Aeon.getVersion());
        return rnrMetaMap;
    }

    private File buildRnrMetaFile(Map<String, String> rnrMetaMap) {
        try {
            String rnrMeta = new ObjectMapper().writeValueAsString(rnrMetaMap);

            File rnrMetaFile = File.createTempFile("rnr-", "-meta.rnr");
            rnrMetaFile.deleteOnExit();
            PrintWriter out = new PrintWriter(rnrMetaFile);
            out.println(rnrMeta);
            out.close();

            return rnrMetaFile;
        } catch (JsonProcessingException e) {
            log.error("Could not write JSON results.", e);
            return null;
        } catch (IOException e) {
            log.error("Could not write meta RnR file.", e);
            return null;
        }
    }

    private boolean executeRequest(HttpPost post, String fullRequestUrl) {
        try (CloseableHttpResponse response = httpClient.execute(post)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_ACCEPTED) {
                log.error("Did not receive successful status code for RnR upload. Received: {}.", statusCode);
                return false;
            }
            return true;
        } catch (IOException e) {
            log.error(String.format("Could not upload report to RnR (%s): %s.", fullRequestUrl, e.getMessage()), e);
            return false;
        }
    }
}

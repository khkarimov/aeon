package aeon.extensions.reporting.services;

import aeon.core.common.interfaces.IConfiguration;
import aeon.extensions.reporting.ReportingConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
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
public class RnrService {

    private String browser;
    private String environmentUrl;

    private String product;
    private String team;
    private String type;
    private String branch;
    private String rnrUrl;

    private static Logger log = LoggerFactory.getLogger(RnrService.class);

    /**
     * Sets the Reporting plugin and Aeon configuration.
     *
     * @param configuration     The Reporting plugin configuration object.
     * @param aeonConfiguration The Aeon configuration object.
     */
    public void setConfiguration(IConfiguration configuration, IConfiguration aeonConfiguration) {
        this.browser = aeonConfiguration.getString("aeon.browser", "");
        this.environmentUrl = aeonConfiguration.getString("aeon.environment", "");
        this.product = configuration.getString(ReportingConfiguration.Keys.PRODUCT, "");
        this.team = configuration.getString(ReportingConfiguration.Keys.TEAM, "");
        this.type = configuration.getString(ReportingConfiguration.Keys.TYPE, "");
        this.branch = configuration.getString(ReportingConfiguration.Keys.BRANCH, "");
        this.rnrUrl = configuration.getString(ReportingConfiguration.Keys.RNR_URL, "");
    }

    /**
     * Uploads a report to RocknRoly.
     *
     * @param jsonFileName     The name of the JSON file of the report to upload.
     * @param angularReportUrl The URL of the HTML report to link.
     * @param correlationId    The correlation ID to use for the report.
     * @return The RocknRoly URL of the uploaded report.
     */
    public String uploadToRnr(String jsonFileName, String angularReportUrl, String correlationId) {

        if (!allConfigFieldsAreSet()) {
            log.trace("Not all RnR properties are set, cancelling upload to RnR.");
            return null;
        }

        File resultsJsonFile = new File(jsonFileName);

        HttpEntity multiPartEntity = buildRnrMultiPartEntity(angularReportUrl, resultsJsonFile, correlationId);

        HttpClient client = HttpClientBuilder.create().build();

        String fullRequestUrl = rnrUrl + "/testsuite";

        HttpPost post = new HttpPost(fullRequestUrl);
        post.setEntity(multiPartEntity);

        boolean success = executeRequest(client, post, fullRequestUrl);

        if (!success) {
            return null;
        }

        return rnrUrl + "/" + correlationId;
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

    private boolean executeRequest(HttpClient client, HttpPost post, String fullRequestUrl) {
        try {
            HttpResponse response = client.execute(post);
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

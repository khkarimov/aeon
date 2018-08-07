package aeon.extensions.reporting.services;

import aeon.core.common.interfaces.IConfiguration;
import aeon.extensions.reporting.ReportDetails;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class RnrService {

    private String browser;
    private String environmentUrl;

    private String product;
    private String team;
    private String type;
    private String branch;
    private String rnrUrl;

    private static Logger log = LogManager.getLogger(RnrService.class);

    public RnrService(IConfiguration aeonConfiguration, IConfiguration pluginConfiguration) {
        this.browser = aeonConfiguration.getString("aeon.browser", "");
        this.environmentUrl = aeonConfiguration.getString("aeon.environment", "");

        this.product = pluginConfiguration.getString(ReportingConfiguration.Keys.PRODUCT, "");
        this.team = pluginConfiguration.getString(ReportingConfiguration.Keys.TEAM, "");
        this.type = pluginConfiguration.getString(ReportingConfiguration.Keys.TYPE, "");
        this.branch = pluginConfiguration.getString(ReportingConfiguration.Keys.BRANCH, "");
        this.rnrUrl = pluginConfiguration.getString(ReportingConfiguration.Keys.RNR_URL, "");
    }

    public String uploadToRnR(String jsonFileName, String angularReportUrl, String correlationId) {

        if (!allConfigFieldsAreSet()) {
            log.trace("Not all RnR properties are set, cancelling upload to RnR.");
            return null;
        }

        File resultsJsonFile = new File(jsonFileName);
        log.info("RESULTSJSONFILE EXISTS: " + resultsJsonFile.exists());

        HttpEntity multiPartEntity = buildRnrMultiPartEntity(angularReportUrl, resultsJsonFile, correlationId);

        HttpClient client = HttpClientBuilder.create().build();

        String fullRequestUrl = rnrUrl + "/testsuite";

        HttpPost post = new HttpPost(fullRequestUrl);
        post.setEntity(multiPartEntity);

        boolean success = executeRequest(client, post, fullRequestUrl);

        String rnrResultUrl = rnrUrl + "/" + correlationId;

        return rnrResultUrl;
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
                ContentType.APPLICATION_OCTET_STREAM, "results.json");
        builder.addBinaryBody("metaFile", rnrMetaFile,
                ContentType.APPLICATION_OCTET_STREAM, "meta.rnr");

        return builder.build();
    }

    private Map<String, String> buildRnrMetaMap(String reportUrl) {
        Map<String, String> rnrMetaMap = new HashMap<>();
        rnrMetaMap.put("screenshots", reportUrl);
        rnrMetaMap.put("app", browser);
        rnrMetaMap.put("browser", environmentUrl);
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
                log.error(String.format("Did not receive successful status code for RnR upload. Received: %d.", statusCode));
                return false;
            }
            return true;
        } catch (IOException e) {
            log.error(String.format("Could not upload report to RnR (%s): %s.", fullRequestUrl, e.getMessage()), e);
            return false;
        }
    }
}

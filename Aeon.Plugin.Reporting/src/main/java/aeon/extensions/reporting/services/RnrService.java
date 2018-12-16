package aeon.extensions.reporting.services;

import aeon.extensions.reporting.ReportingConfiguration;
import aeon.extensions.reporting.ReportingPlugin;
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

public class RnrService {

    private static String browser = ReportingPlugin.aeonConfiguration.getString("aeon.browser", "");
    private static String environmentUrl = ReportingPlugin.aeonConfiguration.getString("aeon.environment", "");

    private static String product = ReportingPlugin.configuration.getString(ReportingConfiguration.Keys.PRODUCT, "");
    private static String team = ReportingPlugin.configuration.getString(ReportingConfiguration.Keys.TEAM, "");
    private static String type = ReportingPlugin.configuration.getString(ReportingConfiguration.Keys.TYPE, "");
    private static String branch = ReportingPlugin.configuration.getString(ReportingConfiguration.Keys.BRANCH, "");
    private static String rnrUrl = ReportingPlugin.configuration.getString(ReportingConfiguration.Keys.RNR_URL, "");

    private static Logger log = LoggerFactory.getLogger(RnrService.class);

    public static String uploadToRnr(String jsonFileName, String angularReportUrl, String correlationId) {

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

        String rnrResultUrl = rnrUrl + "/" + correlationId;

        log.info("RnR URL: " + rnrResultUrl);

        return rnrResultUrl;
    }

    private static boolean allConfigFieldsAreSet() {
        return !(product.isEmpty()
                || team.isEmpty()
                || type.isEmpty()
                || branch.isEmpty());
    }

    private static HttpEntity buildRnrMultiPartEntity(String angularReportUrl, File resultsJsonFile, String correlationId) {
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
        builder.addBinaryBody("metaFile", rnrMetaFile,
                ContentType.APPLICATION_OCTET_STREAM, "meta.rnr");

        return builder.build();
    }

    private static Map<String, String> buildRnrMetaMap(String reportUrl) {
        Map<String, String> rnrMetaMap = new HashMap<>();
        rnrMetaMap.put("screenshots", reportUrl);
        rnrMetaMap.put("app", browser);
        rnrMetaMap.put("browser", environmentUrl);
        return rnrMetaMap;
    }

    private static File buildRnrMetaFile(Map<String, String> rnrMetaMap) {
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

    private static boolean executeRequest(HttpClient client, HttpPost post, String fullRequestUrl) {
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

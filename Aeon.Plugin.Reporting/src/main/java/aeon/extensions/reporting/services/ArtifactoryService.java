package aeon.extensions.reporting.services;

import aeon.core.common.interfaces.IConfiguration;
import aeon.extensions.reporting.ReportingConfiguration;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

import static org.apache.http.HttpHeaders.USER_AGENT;

public class ArtifactoryService {

    private String artifactoryUrl;
    private String artifactoryPath;
    private String username;
    private String password;

    private static Logger log = LogManager.getLogger(ArtifactoryService.class);


    public ArtifactoryService(IConfiguration pluginConfiguration) {
        this.artifactoryUrl = pluginConfiguration.getString(ReportingConfiguration.Keys.ARTIFACTORY_URL, "");
        this.artifactoryPath = pluginConfiguration.getString(ReportingConfiguration.Keys.ARTIFACTORY_PATH, "");
        this.username = pluginConfiguration.getString(ReportingConfiguration.Keys.ARTIFACTORY_USERNAME, "");
        this.password = pluginConfiguration.getString(ReportingConfiguration.Keys.ARTIFACTORY_PASSWORD, "");
    }

    public String uploadToArtifactory(String filePathName) {

        if (!allConfigFieldsAreSet()) {
            log.info("Not all artifactory properties set, cancelling file upload.");
            return null;
        }

        String fileName = getFileName(filePathName);
        String fullRequestUrl = getFullRequestUrl(fileName);

        InputStreamEntity fileEntity = buildFileEntity(filePathName);
        if (fileEntity == null) return null;

        HttpPut put = buildHttpPut(fullRequestUrl, fileEntity);
        HttpClient client = buildHttpClient();

        boolean success = executeRequest(client, put);
        if (!success) return null;

        return fullRequestUrl;
    }

    private boolean allConfigFieldsAreSet() {
        return !(artifactoryUrl.isEmpty()
                || artifactoryPath.isEmpty()
                || username.isEmpty()
                || password.isEmpty());
    }

    private String getFileName(String filePathName) {
        return filePathName.substring(filePathName.lastIndexOf("/")+1);
    }

    private String getFullRequestUrl(String fileName) {
        return artifactoryUrl + "/" + artifactoryPath + "/" + fileName;
    }

    private CredentialsProvider getCredentials(String username, String password) {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        provider.setCredentials(AuthScope.ANY, credentials);

        return provider;
    }

    private HttpClient buildHttpClient() {
        CredentialsProvider provider = getCredentials(username, password);
        return HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();
    }

    private InputStreamEntity buildFileEntity(String filePathName) {
        InputStreamEntity fileEntity;
        File file = new File(filePathName);
        try {
            InputStream inputStream = new FileInputStream(filePathName);
            fileEntity = new InputStreamEntity(inputStream, file.length());
        } catch (FileNotFoundException e) {
            log.error(String.format("Could not find file %s to upload to artifactory.", filePathName));
            return null;
        }
        return fileEntity;
    }

    private HttpPut buildHttpPut(String fullRequestUrl, InputStreamEntity fileEntity) {
        HttpPut put = new HttpPut(fullRequestUrl);
        put.setHeader("User-Agent", USER_AGENT);
        put.setHeader("Content-type", "text/html");
        put.setEntity(fileEntity);
        return put;
    }

    private boolean executeRequest(HttpClient client, HttpPut put) {
        try {
            HttpResponse response = client.execute(put);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_CREATED) {
                log.error(String.format("Did not receive successful status code for artifactory upload. Received: %d", statusCode));
                return false;
            }
            return true;
        } catch (IOException e) {
            log.error(String.format("Could not upload report file to artifactory: %s", e.getMessage()), e);

            return false;
        }
    }
}

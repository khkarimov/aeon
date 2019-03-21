package com.ultimatesoftware.aeon.extensions.reporting.services;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.extensions.reporting.ReportingConfiguration;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static org.apache.http.HttpHeaders.USER_AGENT;

/**
 * Service for uploading files to Artifactory.
 */
public class ArtifactoryService {

    private String artifactoryUrl;
    private String artifactoryPath;
    private String username;
    private String password;

    private static Logger log = LoggerFactory.getLogger(ArtifactoryService.class);

    /**
     * Sets the Reporting plugin configuration.
     *
     * @param configuration The Reporting plugin configuration object.
     */
    public void setConfiguration(IConfiguration configuration) {

        artifactoryUrl = configuration.getString(ReportingConfiguration.Keys.ARTIFACTORY_URL, "");
        artifactoryPath = configuration.getString(ReportingConfiguration.Keys.ARTIFACTORY_PATH, "");
        username = configuration.getString(ReportingConfiguration.Keys.ARTIFACTORY_USERNAME, "");
        password = configuration.getString(ReportingConfiguration.Keys.ARTIFACTORY_PASSWORD, "");
    }

    /**
     * Uploads a file to artifactory.
     *
     * @param filePathName File path of the file to upload
     * @return The target URL for the uploaded file.
     */
    public String uploadToArtifactory(String filePathName) {

        if (!allConfigFieldsAreSet()) {
            log.info("Not all artifactory properties set, cancelling file upload.");
            return null;
        }

        File file = new File(filePathName);
        String fileName = file.getName();
        String fullRequestUrl = getFullRequestUrl(fileName);

        FileEntity fileEntity = new FileEntity(file);

        HttpPut put = buildHttpPut(fullRequestUrl, fileEntity);
        HttpClient client = buildHttpClient();

        boolean success = executeRequest(client, put);
        if (!success) {
            return null;
        }

        return fullRequestUrl;
    }

    private boolean allConfigFieldsAreSet() {
        return !(artifactoryUrl.isEmpty()
                || artifactoryPath.isEmpty()
                || username.isEmpty()
                || password.isEmpty());
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

    private HttpPut buildHttpPut(String fullRequestUrl, FileEntity fileEntity) {
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
                log.error("Did not receive successful status code for artifactory upload. Received: {}.", statusCode);

                return false;
            }
            return true;
        } catch (IOException e) {
            log.error(String.format("Could not upload report file to artifactory: %s", e.getMessage()), e);

            return false;
        }
    }
}

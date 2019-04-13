package com.ultimatesoftware.aeon.extensions.artifactory;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.core.extensions.IUploadListenerExtension;
import com.ultimatesoftware.aeon.core.extensions.IUploaderExtension;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import org.apache.http.impl.client.HttpClientBuilder;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Uploader extension for uploading files to Artifactory.
 */
@Extension
public class ArtifactoryUploaderExtension implements IUploaderExtension {

    private final ArtifactoryService artifactoryService;

    private static Logger log = LoggerFactory.getLogger(ArtifactoryUploaderExtension.class);

    ArtifactoryUploaderExtension(ArtifactoryService artifactoryService) {
        this.artifactoryService = artifactoryService;
    }

    /**
     * Creates an instance for this extension.
     *
     * @return An instance of this extension.
     */
    public static Object createInstance() {
        IConfiguration configuration = new ArtifactoryConfiguration();

        try {
            configuration.loadConfiguration();
        } catch (IllegalAccessException | IOException e) {
            log.warn("Could not load plugin configuration.");
        }

        return new ArtifactoryUploaderExtension(
                new ArtifactoryService(HttpClientBuilder.create(), configuration));
    }

    @Override
    public String onUploadRequested(String fileName, String type, String label) {
        String url = this.artifactoryService.uploadToArtifactory(fileName);

        if (url == null) {
            return null;
        }

        List<IUploadListenerExtension> extensions = Aeon.getExtensions(IUploadListenerExtension.class);
        for (IUploadListenerExtension extension : extensions) {
            extension.onUploadSucceeded(url, type, label);
        }

        return url;
    }
}

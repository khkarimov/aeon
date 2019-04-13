package com.ultimatesoftware.aeon.extensions.artifactory;

import com.ultimatesoftware.aeon.core.extensions.IUploadListenerExtension;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.pf4j.PluginManager;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ArtifactoryUploaderExtensionTests {

    private ArtifactoryUploaderExtension artifactoryUploaderExtension;

    @Mock
    private ArtifactoryService artifactoryService;

    @Mock
    private PluginManager pluginManager;

    @Mock
    private IUploadListenerExtension listener1;

    @Mock
    private IUploadListenerExtension listener2;

    @BeforeEach
    void setup() {
        this.artifactoryUploaderExtension = new ArtifactoryUploaderExtension(this.artifactoryService);

        Aeon.setPluginManager(this.pluginManager);
    }

    @Test
    void createInstance_createsAnInstanceSuccessfully() {

        // Arrange

        // Act
        Object extension = ArtifactoryUploaderExtension.createInstance();

        // Assert
        assertEquals(ArtifactoryUploaderExtension.class, extension.getClass());
    }

    @Test
    void onUploadRequested_calledWithReport_callsArtifactoryServiceAndNotifiesPlugins() {

        // Arrange
        when(this.artifactoryService.uploadToArtifactory("fileName")).thenReturn("url");
        when(this.pluginManager.getExtensions(IUploadListenerExtension.class))
                .thenReturn(Arrays.asList(this.listener1, this.listener2));

        // Act
        this.artifactoryUploaderExtension.onUploadRequested("fileName", "report", "label");

        // Assert
        verify(this.artifactoryService, times(1)).uploadToArtifactory("fileName");
        verify(this.listener1, times(1)).onUploadSucceeded("url", "report", "label");
        verify(this.listener2, times(1)).onUploadSucceeded("url", "report", "label");
    }

    @Test
    void onUploadRequested_whenUploadFails_doesNotNotifyListeners() {

        // Arrange
        when(this.artifactoryService.uploadToArtifactory("fileName")).thenReturn(null);

        // Act
        this.artifactoryUploaderExtension.onUploadRequested("fileName", "report", "label");

        // Assert
        verify(this.artifactoryService, times(1)).uploadToArtifactory("fileName");
        verify(this.listener1, times(0)).onUploadSucceeded(any(), any(), any());
        verify(this.listener2, times(0)).onUploadSucceeded(any(), any(), any());
    }
}

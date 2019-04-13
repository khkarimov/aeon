package com.ultimatesoftware.aeon.core.extensions;

import org.pf4j.ExtensionPoint;

/**
 * The interface for the Upload Listener Extension.
 */
public interface IUploadListenerExtension extends ExtensionPoint {

    /**
     * Is called when a file was successfully uploaded.
     *
     * @param url   Location of the uploaded file.
     * @param type  Type of the file requested to be uploaded (e.g "report" or "video").
     * @param label Optional label of the uploaded file (may be null).
     */
    void onUploadSucceeded(String url, String type, String label);
}

package com.ultimatesoftware.aeon.core.extensions;

import org.pf4j.ExtensionPoint;

/**
 * The interface for the Uploader Extension.
 */
public interface IUploaderExtension extends ExtensionPoint {

    /**
     * Is called when Aeon is requesting the upload of a file.
     *
     * @param fileName Name and path of the file requested to be uploaded.
     * @param type     Type of the file requested to be uploaded (e.g "report" or "video").
     * @param label    Optional label for the uploaded file (may be null).
     * @return URL of the uploaded file.
     */
    String onUploadRequested(String fileName, String type, String label);
}

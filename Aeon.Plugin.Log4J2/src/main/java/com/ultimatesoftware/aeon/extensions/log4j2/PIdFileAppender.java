package com.ultimatesoftware.aeon.extensions.log4j2;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender;
import org.apache.logging.log4j.core.appender.FileManager;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.net.Advertiser;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * PId File Appender.
 */
@Plugin(name = PIdFileAppender.PLUGIN_NAME, category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class PIdFileAppender extends AbstractOutputStreamAppender<FileManager> {

    public static final String PLUGIN_NAME = "PIdFileAppender";

    /**
     * Builds PIdAppender instance.
     *
     * @param <B> the type to build
     */
    public static class PIdAppenderBuilder<B extends Builder<B>> extends AbstractOutputStreamAppender.Builder<B>
            implements org.apache.logging.log4j.core.util.Builder<PIdFileAppender> {

        @PluginBuilderAttribute
        @Required
        private String fileName;

        @PluginBuilderAttribute
        private boolean append = true;

        @PluginBuilderAttribute
        private boolean locking;

        @PluginBuilderAttribute
        private boolean advertise;

        @PluginBuilderAttribute
        private String advertiseUri;

        @PluginBuilderAttribute
        private boolean createOnDemand;

        @PluginBuilderAttribute
        private String filePermissions;

        @PluginBuilderAttribute
        private String fileOwner;

        @PluginBuilderAttribute
        private String fileGroup;

        @Override
        public PIdFileAppender build() {
            boolean bufferedIo = isBufferedIo();
            final int bufferSize = getBufferSize();
            if (locking && bufferedIo) {
                LOGGER.warn("Locking and buffering are mutually exclusive. No buffering will occur for {}", fileName);
                bufferedIo = false;
            }
            if (!bufferedIo && bufferSize > 0) {
                LOGGER.warn("The bufferSize is set to {} but bufferedIo is false: {}", bufferSize, bufferedIo);
            }
            final Layout<? extends Serializable> layout = getOrCreateLayout();
            this.fileName = appendIdToFileName(fileName);
            FileManager manager = FileManager.getFileManager(fileName, append, locking, bufferedIo, createOnDemand,
                    advertiseUri, layout, bufferSize, filePermissions, fileOwner, fileGroup, getConfiguration());
            if (manager == null) {
                return null;
            }
            return new PIdFileAppender(getName(), layout, getFilter(), manager, fileName, isIgnoreExceptions(),
                    !bufferedIo || isImmediateFlush(), advertise ? getConfiguration().getAdvertiser() : null);
        }

        /**
         * Function returns a PIdAppenderBuilder with the provided advertise as a boolean.
         *
         * @param advertise a boolean of advertise.
         * @return the PIdAppenderBuilder with the new advertise.
         */
        public B withAdvertise(final boolean advertise) {
            this.advertise = advertise;
            return asBuilder();
        }

        /**
         * Function returns a PIdAppenderBuilder with the provided advertiseUri as a string.
         *
         * @param advertiseUri a string representing advertise uri.
         * @return the PIdAppenderBuilder with the new advertise uri.
         */
        public B withAdvertiseUri(final String advertiseUri) {
            this.advertiseUri = advertiseUri;
            return asBuilder();
        }

        /**
         * Function returns a PIdAppenderBuilder with the provided append as a boolean.
         *
         * @param append a boolean of append.
         * @return the PIdAppenderBuilder with the new append.
         */
        public B withAppend(final boolean append) {
            this.append = append;
            return asBuilder();
        }

        /**
         * Function returns a PIdAppenderBuilder with the provided file name as a string.
         *
         * @param fileName a string representing file name.
         * @return the PIdAppenderBuilder with the new file name.
         */
        public B withFileName(final String fileName) {
            this.fileName = fileName;
            return asBuilder();
        }

        /**
         * Function returns a PIdAppenderBuilder with the provided create on demand as a boolean.
         *
         * @param createOnDemand a boolean of create on demand.
         * @return the PIdAppenderBuilder with the new create on demand.
         */
        public B withCreateOnDemand(final boolean createOnDemand) {
            this.createOnDemand = createOnDemand;
            return asBuilder();
        }

        /**
         * Function returns a PIdAppenderBuilder with the provided locking as a boolean.
         *
         * @param locking a boolean of locking.
         * @return the PIdAppenderBuilder with the new locking.
         */
        public B withLocking(final boolean locking) {
            this.locking = locking;
            return asBuilder();
        }

        /**
         * Returns the file permissions.
         *
         * @return The file permissions.
         */
        public String getFilePermissions() {
            return filePermissions;
        }

        /**
         * Returns the file owner.
         *
         * @return The file owner.
         */
        public String getFileOwner() {
            return fileOwner;
        }

        /**
         * Returns the file group.
         *
         * @return The file group.
         */
        public String getFileGroup() {
            return fileGroup;
        }

        /**
         * Function returns a PIdAppenderBuilder with the provided file permissions.
         *
         * @param filePermissions the file permissions to use.
         * @return the PIdAppenderBuilder with the new file permissions.
         */
        public B withFilePermissions(final String filePermissions) {
            this.filePermissions = filePermissions;
            return asBuilder();
        }

        /**
         * Function returns a PIdAppenderBuilder with the provided file owner.
         *
         * @param fileOwner the file owner to use.
         * @return the PIdAppenderBuilder with the new file owner.
         */
        public B withFileOwner(final String fileOwner) {
            this.fileOwner = fileOwner;
            return asBuilder();
        }

        /**
         * Function returns a PIdAppenderBuilder with the provided file group.
         *
         * @param fileGroup the file group to use.
         * @return the PIdAppenderBuilder with the new file group.
         */
        public B withFileGroup(final String fileGroup) {
            this.fileGroup = fileGroup;
            return asBuilder();
        }
    }


    /**
     * Function which returns a new PIdAppenderBuilder.
     *
     * @param <B> the type to build
     * @return the new PIdAppenderBuilder
     */
    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return new PIdAppenderBuilder<B>().asBuilder();
    }

    private String fileName;

    private final Advertiser advertiser;

    private final Object advertisement;

    private PIdFileAppender(final String name, final Layout<? extends Serializable> layout, final Filter filter,
                            FileManager manager, String filename, final boolean ignoreExceptions,
                            final boolean immediateFlush, final Advertiser advertiser) {
        super(name, layout, filter, ignoreExceptions, immediateFlush, manager);
        this.fileName = filename;
        if (advertiser != null) {
            final Map<String, String> configuration = new HashMap<>(layout.getContentFormat());
            configuration.putAll(manager.getContentFormat());
            configuration.put("contentType", layout.getContentType());
            configuration.put("name", name);
            advertisement = advertiser.advertise(configuration);
        } else {
            advertisement = null;
        }
        this.advertiser = advertiser;
    }

    private static String appendIdToFileName(String oldFileName) {
        String pId = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        int dotIndex = oldFileName.lastIndexOf('.');
        if (dotIndex != -1) {
            return oldFileName.substring(0, dotIndex) + "-" + pId + oldFileName.substring(dotIndex);
        } else {
            return oldFileName + "-" + pId;
        }
    }

    /**
     * Returns the file name this appender is associated with.
     *
     * @return the file name.
     */
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public boolean stop(final long timeout, final TimeUnit timeUnit) {
        setStopping();
        super.stop(timeout, timeUnit, false);
        if (advertiser != null) {
            advertiser.unadvertise(advertisement);
        }
        setStopped();
        return true;
    }

}

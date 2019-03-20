package aeon.core.common.mobile;

import aeon.core.common.web.interfaces.IBrowserType;

/**
 * The app on which to automate tests.
 */
public enum AppType implements IBrowserType {

    IOS_HYBRID_APP("IOSHybridApp"),
    ANDROID_HYBRID_APP("AndroidHybridApp");

    private final String key;

    AppType(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

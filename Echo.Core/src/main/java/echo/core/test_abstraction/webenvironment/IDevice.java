package echo.core.test_abstraction.webenvironment;

import echo.core.common.mobile.DeviceType;

/**
 * The device interface.
 */
public interface IDevice {
    /**
     * Gets or sets the name of the device.
     */
    String getName();
    void setName(String value);

    /**
     * Gets or sets the Type of the device.
     */
    DeviceType getType();
    void setType(DeviceType value);

    /**
     * Gets or sets the Platform Version of the device.
     */
    String getVersion();
    void setVersion(String value);

    /**
     * Gets or sets the Unique Identifier of the device.
     */
    String getIdentifier();
    void setIdentifier(String value);
}

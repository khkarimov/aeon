package com.ultimatesoftware.aeon.core.command.execution.commands.mobile;

import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IMobileDriver;

/**
 * Sets the GPS location on a mobile device.
 */
public class SetGeoLocationCommand extends MobileCommand {

    private double latitude;
    private double longitude;
    private double altitude;

    /**
     * Initializes a new instance of the {@link SetGeoLocationCommand} class.
     * @param latitude Latitude Coordinate
     * @param longitude Longitude Coordinate
     * @param altitude Altitude
     */
    public SetGeoLocationCommand(double latitude, double longitude, double altitude) {
        super(Resources.getString("MobileSetGeoLocationCommand_Info"));
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    /**
     * The method which provides the logic for the command.
     */
    @Override
    protected void driverDelegate(IMobileDriver driver) {
        driver.mobileSetGeoLocation(latitude, longitude, altitude);
    }
}

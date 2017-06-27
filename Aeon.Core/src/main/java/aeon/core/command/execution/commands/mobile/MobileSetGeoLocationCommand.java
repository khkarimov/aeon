package aeon.core.command.execution.commands.mobile;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Sets the GPS location on a mobile device.
 */
public class MobileSetGeoLocationCommand extends Command {

    private double latitude;
    private double longitude;
    private double altitude;

    /**
     * Initializes a new instance of the {@link MobileSetGeoLocationCommand} class.
     * @param latitude Latitude Coordinate
     * @param longitude Longitude Coordinate
     * @param altitude Altitude
     */
    public MobileSetGeoLocationCommand(double latitude, double longitude, double altitude) {
        super(Resources.getString("MobileSetGeoLocationCommand_Info"));
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    /**
     * The method which provides the logic for the command.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        ((IWebDriver) driver).mobileSetGeoLocation(latitude, longitude, altitude);
    }
}

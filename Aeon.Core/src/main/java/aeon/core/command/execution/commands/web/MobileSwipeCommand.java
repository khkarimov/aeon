package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

public class MobileSwipeCommand extends Command {

    private int startx;
    private int starty;
    private int endx;
    private int endy;
    private int duration;

    /**
     * Initializes a new instance of the {@link MobileSwipeCommand} class.
     */
    public MobileSwipeCommand(int startx, int starty, int endx, int endy, int duration) {
        super(Resources.getString("MobileSwipeCommand_Info"));
        this.startx = startx;
        this.starty = starty;
        this.endx = endx;
        this.endy = endy;
        this.duration = duration;
    }

    /**
     * The method which provides the logic for the command.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        ((IWebDriver) driver).mobileSwipe(startx, starty, endx, endy, duration);
    }
}

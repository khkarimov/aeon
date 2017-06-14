package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.ArrayList;
import java.util.Locale;

public class MobileSwipesCommand extends Command {

    private int startx;
    private int starty;
    private int endx;
    private int endy;
    private int duration;

    /**
     * Initializes a new instance of the {@link MobileSwipesCommand} class.
     */
    public MobileSwipesCommand(int startx, int starty, int endx, int endy, int duration) {
        super(Resources.getString("MobileSwipesCommand_Info"));
        this.startx = startx;
        this.starty = starty;
        this.endx = endx;
        this.endy = endy;
        this.duration = duration;
    }

    @Override
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        ((IWebDriver) driver).mobileSwipes(startx, starty, endx, endy, duration);
    }
}

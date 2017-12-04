package aeon.core.command.execution.commands.mobile;

import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IMobileDriver;

/**
 * Command for swiping on the screen.
 */
public class SwipeCommand extends MobileCommand {

    private boolean horizontally;
    private boolean leftOrDown;

    /**
     * Constructor for the command.
     *
     * @param horizontally Whether to swipe horizontally or vertically.
     * @param leftOrDown Whether to swipe left (when horizontally is true) or down (when horizontally is false).
     */
    public SwipeCommand(boolean horizontally, boolean leftOrDown) {
        super(Resources.getString("SwipeCommand_Info"));
        this.horizontally = horizontally;
        this.leftOrDown = leftOrDown;
    }

    @Override
    protected void driverDelegate(IMobileDriver driver) {
        driver.swipe(horizontally, leftOrDown);
    }
}

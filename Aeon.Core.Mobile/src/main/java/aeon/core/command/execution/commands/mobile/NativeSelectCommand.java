package aeon.core.command.execution.commands.mobile;

import aeon.core.common.Resources;
import aeon.core.common.mobile.selectors.MobileSelectOption;
import aeon.core.framework.abstraction.drivers.IMobileDriver;

/**
 * Command for selecting a value from a native drop down list.
 */
public class NativeSelectCommand extends MobileCommand {

    private MobileSelectOption selectOption;
    private String value;

    /**
     * Constructor.
     *
     * @param selectOption Type based on which an option should be selected.
     * @param value        The value to select.
     */
    public NativeSelectCommand(String selectOption, String value) {
        super(String.format(Resources.getString("NativeSelectCommand_Info"), value));
        this.selectOption = MobileSelectOption.valueOf(selectOption);
        this.value = value;
    }

    /**
     * @return selectOption
     */
    public MobileSelectOption getSelectOption() {

        return selectOption;
    }

    /**
     * @return value
     */
    public String getValue() {

        return value;
    }

    @Override
    protected void driverDelegate(IMobileDriver driver) {
        driver.mobileSelect(selectOption, value);
    }
}

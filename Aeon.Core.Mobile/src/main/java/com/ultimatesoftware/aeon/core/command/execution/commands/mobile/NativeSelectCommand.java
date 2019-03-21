package com.ultimatesoftware.aeon.core.command.execution.commands.mobile;

import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.mobile.selectors.MobileSelectOption;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IMobileDriver;

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
     * Getter for MobileSelectOption.
     *
     * @return The selectOption property {@link MobileSelectOption}.
     */
    public MobileSelectOption getSelectOption() {

        return selectOption;
    }

    /**
     * Getter for Value.
     *
     * @return The value property.
     */
    public String getValue() {

        return value;
    }

    @Override
    protected void driverDelegate(IMobileDriver driver) {
        driver.mobileSelect(selectOption, value);
    }
}

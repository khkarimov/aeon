package main.sample.samplegrid;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Table;

/**
 * Models the sample table.
 */
public class MyGrid extends Table<MyGridHeaders> {

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     * @param selector The selector that identifies the table.
     * @param gridHeaders The corresponding Table Actions object.
     */
    public MyGrid(AutomationInfo automationInfo, IByWeb selector, MyGridHeaders gridHeaders) {
        super(automationInfo, selector, gridHeaders);
    }
}

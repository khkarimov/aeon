package main.sample.samplegrid;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Table;

/**
 * Models the sample table.
 */
public class MaterialTableContainer extends Table<MaterialTable> {

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     * @param selector       The selector that identifies the table.
     * @param materialTable  The corresponding Table Actions object.
     */
    public MaterialTableContainer(AutomationInfo automationInfo, IByWeb selector, MaterialTable materialTable) {
        super(automationInfo, selector, materialTable);
    }
}

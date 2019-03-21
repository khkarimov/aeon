package main.sample.samplegrid;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.TableContainer;

/**
 * Models the sample table.
 */
public class MaterialTableContainer extends TableContainer<MaterialTable> {

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     * @param selector       The selector that identifies the table.
     * @param materialTable  The corresponding MaterialTable object.
     */
    public MaterialTableContainer(AutomationInfo automationInfo, IByWeb selector, MaterialTable materialTable) {
        super(automationInfo, selector, materialTable);
    }
}

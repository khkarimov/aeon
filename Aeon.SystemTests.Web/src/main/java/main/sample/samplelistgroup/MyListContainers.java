package main.sample.samplelistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.ListContainer;

/**
 * Model for the sample list group.
 */
public class MyListContainers extends ListContainer<MyComponentList> {

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     * @param selector       The selector that identifies the list group.
     * @param gridHeaders    The corresponding List Group Actions object.
     */
    public MyListContainers(AutomationInfo automationInfo, IByWeb selector, MyComponentList gridHeaders) {
        super(automationInfo, selector, gridHeaders);
    }
}

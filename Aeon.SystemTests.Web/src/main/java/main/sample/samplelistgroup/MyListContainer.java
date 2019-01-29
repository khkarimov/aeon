package main.sample.samplelistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.ListContainer;

/**
 * Model for the sample list group.
 */
public class MyListContainer extends ListContainer<MyListGroupActions> {

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     * @param selector       The selector that identifies the list group.
     * @param gridHeaders    The corresponding List Group Actions object.
     */
    public MyListContainer(AutomationInfo automationInfo, IByWeb selector, MyListGroupActions gridHeaders) {
        super(automationInfo, selector, gridHeaders);
    }
}

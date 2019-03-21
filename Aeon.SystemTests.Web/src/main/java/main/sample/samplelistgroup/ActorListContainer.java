package main.sample.samplelistgroup;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.ListContainer;

/**
 * Model for the sample list container.
 */
public class ActorListContainer extends ListContainer<ActorList> {

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     * @param selector       The selector that identifies the list group.
     * @param actor          The corresponding Actor object.
     */
    public ActorListContainer(AutomationInfo automationInfo, IByWeb selector, ActorList actor) {
        super(automationInfo, selector, actor);
    }
}

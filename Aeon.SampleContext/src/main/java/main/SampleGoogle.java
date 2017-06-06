package main;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.*;
import main.samplegrid.MyGrid;
import main.samplegrid.MyGridHeaders;

/**
 * Created by jacoble on 6/5/2017.
 */
public class SampleGoogle {
    private AutomationInfo info;

    public TextBox formTextBox;

    public SampleGoogle(AutomationInfo info) {
        formTextBox = new TextBox(info, By.cssSelector("#lst-ib"));
    }
}

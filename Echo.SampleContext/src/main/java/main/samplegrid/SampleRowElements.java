package main.samplegrid;

import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.context.elementtypes.Label;
import echo.core.test_abstraction.context.elementtypes.Link;
import echo.core.test_abstraction.context.elementtypes.TextBox;

/**
 * Created by AdamC on 4/13/2016.
 */
public class SampleRowElements extends SampleRowActions {
    public Link IdLink;
    public Label NameLabel;
    public TextBox descriptionTextBox;

    public SampleRowElements(){
        IdLink = new Link(By.CssSelector("[id*=link]"));
        NameLabel = new Label(By.CssSelector("[id*=label]"));
        descriptionTextBox = new TextBox(By.CssSelector("[id*=textbox]"));
    }
}

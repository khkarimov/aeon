package main.pages;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Button;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Label;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Link;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.TextBox;
import com.ultimatesoftware.aeon.core.testabstraction.models.WebPage;

/**
 * Models the UltiHome login page.
 */
public class HomePage extends WebPage {

    public final TextBox searchBox;
    public final Button searchBtn;
    public final Link wikiOption;
    public final Label text;

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     */
    public HomePage(AutomationInfo automationInfo) {
        super(automationInfo);

        searchBox = new TextBox(automationInfo, By.cssSelector("[name='q']"));
        searchBtn = new Button(automationInfo, By.cssSelector("[name='btnK']"));
        wikiOption = new Link(automationInfo, By.jQuery(".LC20lb:contains(Software testing - Wikipedia)"));
        text = new Label(automationInfo, By.cssSelector("#mw-content-text > div > p:nth-child(7)"));
    }
}

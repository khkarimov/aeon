package main.pagewithiframeandnestediframe.innerpages;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Button;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.TextBox;
import com.ultimatesoftware.aeon.core.testabstraction.models.WebPage;

/**
 * Wikipedia's main page within an iFrame.
 */
public class InnerIFramePage extends WebPage {
    public final TextBox wikiSearchTextBox;
    public final Button searchButton;
    public final Button wikiLogo;

    /**
     * Constructor for the page object.
     *
     * @param automationInfo The automation info object to use.
     */
    public InnerIFramePage(AutomationInfo automationInfo) {
        super(automationInfo, By.cssSelector("iframe[id*=TopContentFrame]"), By.cssSelector("iframe[id*=ContentFrame]"));

        wikiSearchTextBox = new TextBox(automationInfo, By.cssSelector("#searchInput"), this.switchMechanism);
        searchButton = new Button(automationInfo, By.cssSelector("#searchButton"), this.switchMechanism);
        wikiLogo = new Button(automationInfo, By.cssSelector(".mw-wiki-logo"), this.switchMechanism);
    }
}

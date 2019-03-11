package main.pagewithiframeandnestediframe.innerpages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.TextBox;
import aeon.core.testabstraction.product.WebProduct;

/**
 * Wikipedia's main page within an iFrame.
 */
public class InnerIFramePage extends WebProduct {
    public TextBox wikiSearchTextBox;
    public Button searchButton;
    public Button wikiLogo;

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     */

    public InnerIFramePage(AutomationInfo automationInfo) {
        wikiSearchTextBox = new TextBox(automationInfo, By.cssSelector("#searchInput"), By.cssSelector("iframe[id*=TopContentFrame]"), By.cssSelector("iframe[id*=ContentFrame]"));
        searchButton = new Button(automationInfo, By.cssSelector("#searchButton"), By.cssSelector("iframe[id*=TopContentFrame]"), By.cssSelector("iframe[id*=ContentFrame]"));
        wikiLogo = new Button(automationInfo, By.cssSelector(".mw-wiki-logo"), By.cssSelector("iframe[id*=TopContentFrame]"), By.cssSelector("iframe[id*=ContentFrame]"));
    }
}

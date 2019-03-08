package main.iframe.pages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.TextBox;
import aeon.core.testabstraction.product.WebProduct;

/**
 * Wikipedia's main page within an iFrame.
 */
public class IFramePage extends WebProduct {
    public TextBox wikiSearchTextBox;
    public Button searchButton;
    public Button wikiLogo;
    public Button popupButton;

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     */

    //Useful information:
    //When selecting by cssSelector, the # means it is looking for an id
    //The . means it is looking for a class
    public IFramePage(AutomationInfo automationInfo) {
        wikiSearchTextBox = new TextBox(automationInfo, By.cssSelector("#searchInput"), By.cssSelector("iframe[id*=ContentFrame]"));
        searchButton = new Button(automationInfo, By.cssSelector("#searchButton"), By.cssSelector("iframe[id*=ContentFrame]"));
        wikiLogo = new Button(automationInfo, By.cssSelector(".mw-wiki-logo"), By.cssSelector("iframe[id*=ContentFrame]"));
        popupButton = new Button(automationInfo, By.cssSelector("#popup-button"));
    }
}

package main.pagewithiframe.pages;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Button;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.TextBox;
import com.ultimatesoftware.aeon.core.testabstraction.product.WebProduct;

/**
 * Wikipedia's main page within an iFrame.
 */
public class PageWithIFrame extends WebProduct {
    public TextBox wikiSearchTextBox;
    public Button searchButton;
    public Button wikiLogo;
    public Button popupButton;

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     */
    public PageWithIFrame(AutomationInfo automationInfo) {
        wikiSearchTextBox = new TextBox(automationInfo, By.cssSelector("#searchInput"), By.cssSelector("iframe[id*=ContentFrame]"));
        searchButton = new Button(automationInfo, By.cssSelector("#searchButton"), By.cssSelector("iframe[id*=ContentFrame]"));
        wikiLogo = new Button(automationInfo, By.cssSelector(".mw-wiki-logo"), By.cssSelector("iframe[id*=ContentFrame]"));
        popupButton = new Button(automationInfo, By.cssSelector("#popup-button"));
    }
}

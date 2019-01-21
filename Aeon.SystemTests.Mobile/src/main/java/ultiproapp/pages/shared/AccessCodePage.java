package ultiproapp.pages.shared;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.Link;
import aeon.core.testabstraction.elements.web.TextBox;
import aeon.core.testabstraction.models.Page;

/**
 * Access Code Page.
 */
public class AccessCodePage extends Page {

    public TextBox accessCodeInputField;
    public Label continueLabel;
    public Button continueButton;
    public Label invalidCodeLabel;
    public Link whatIsACompanyAccessCode;
    public Link supportLink;

    /**
     * Constructs an access code page.
     *
     * @param info Automation info
     */
    public AccessCodePage(AutomationInfo info) {
        accessCodeInputField = new TextBox(info, By.cssSelector("ion-input[data-automation=\"access-code-input\"]"));
        continueLabel = new Label(info, By.cssSelector("[data-automation=\"continue-button\"] span"));
        continueButton = new Button(info, By.cssSelector("[data-automation=\"continue-button\"]"));
        invalidCodeLabel = new Label(info, By.cssSelector("div.item-message p"));
        whatIsACompanyAccessCode = new Link(info, By.cssSelector("ion-button[data-automation=\"what-is-company-access-code-button\"]"));
        supportLink = new Link(info, By.cssSelector(".login-graphic-footer ion-button[data-automation=\"support-button\"]"));
    }
}

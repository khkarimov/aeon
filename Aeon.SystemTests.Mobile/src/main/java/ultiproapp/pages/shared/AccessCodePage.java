package ultiproapp.pages.shared;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Button;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Label;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Link;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.TextBox;
import com.ultimatesoftware.aeon.core.testabstraction.models.Page;

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
     * @param automationInfo Automation info
     */
    public AccessCodePage(AutomationInfo automationInfo) {
        accessCodeInputField = new TextBox(automationInfo, By.cssSelector("ion-input[data-automation=\"access-code-input\"]"));
        continueLabel = new Label(automationInfo, By.cssSelector("[data-automation=\"continue-button\"] span"));
        continueButton = new Button(automationInfo, By.cssSelector("[data-automation=\"continue-button\"]"));
        invalidCodeLabel = new Label(automationInfo, By.cssSelector("div.item-message p"));
        whatIsACompanyAccessCode = new Link(automationInfo, By.cssSelector("ion-button[data-automation=\"what-is-company-access-code-button\"]"));
        supportLink = new Link(automationInfo, By.cssSelector("ion-button[data-automation=\"support-button\"]"));
    }
}

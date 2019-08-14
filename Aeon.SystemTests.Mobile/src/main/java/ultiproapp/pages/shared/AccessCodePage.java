package ultiproapp.pages.shared;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Link;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.TextBox;
import com.ultimatesoftware.aeon.core.testabstraction.models.MobilePage;

/**
 * Access Code Page.
 */
public class AccessCodePage extends MobilePage {

    public final TextBox accessCodeInputField;
    public final Link whatIsACompanyAccessCode;
    public final Link supportLink;

    /**
     * Constructs an access code page object.
     *
     * @param automationInfo The Automation info object to use.
     */
    public AccessCodePage(AutomationInfo automationInfo) {
        super(automationInfo);

        accessCodeInputField = new TextBox(automationInfo, By.cssSelector("ion-input[data-automation=\"access-code-input\"]"));
        whatIsACompanyAccessCode = new Link(automationInfo, By.cssSelector("ion-button[data-automation=\"what-is-company-access-code-button\"]"));
        supportLink = new Link(automationInfo, By.cssSelector("ion-button[data-automation=\"support-button\"]"));
    }
}

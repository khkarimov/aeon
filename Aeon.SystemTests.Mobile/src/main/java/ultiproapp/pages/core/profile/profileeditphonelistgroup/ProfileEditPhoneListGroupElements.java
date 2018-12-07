package ultiproapp.pages.core.profile.profileeditphonelistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.*;

public class ProfileEditPhoneListGroupElements extends ListGroupElements {

    public Label phoneNumberLabel;
    public TextBox phoneNumberInput;
    public Label phoneExtLabel;
    public TextBox phoneExtInput;
    public Label phoneTypeLabel;
    public Select phoneTypeSelect;
    public Label phoneCountryLabel;
    public Select phoneCountrySelect;
    public Label phonePrivacyLabel;
    public Button phonePrivacyToggle;

    public ProfileEditPhoneListGroupElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {

        super(info, selector, switchMechanism);
        phoneNumberLabel = new Label(info, selector.toJQuery().find("[data-automation=\"phone-number\"] ion-label"));
        phoneNumberInput = new TextBox(info, selector.toJQuery().find("[data-automation=\"phone-number\"] ion-input"));
        phoneExtLabel = new Label(info, selector.toJQuery().find("[data-automation=\"phone-ext\"] ion-label"));
        phoneExtInput = new TextBox(info, selector.toJQuery().find("[data-automation=\"phone-ext\"] ion-input"));
        phoneTypeLabel = new Label(info, selector.toJQuery().find("[data-automation=\"phone-type\"] ion-label"));
        phoneTypeSelect = new Select(info, selector.toJQuery().find("[data-automation=\"phone-type\"] ion-select"));
        phoneCountryLabel = new Label(info, selector.toJQuery().find("[data-automation=\"phone-country\"] ion-label"));
        phoneCountrySelect = new Select(info, selector.toJQuery().find("[data-automation=\"phone-country\"] ion-select"));
        phonePrivacyLabel = new Label(info, selector.toJQuery().find("[data-automation=\"phone-privacy\"] ion-label"));
        phonePrivacyToggle = new Button(info, selector.toJQuery().find("[data-automation=\"phone-privacy\"] ion-toggle"));

    }

}

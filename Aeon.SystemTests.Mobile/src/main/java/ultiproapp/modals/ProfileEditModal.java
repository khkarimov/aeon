package ultiproapp.modals;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.*;
import aeon.core.testabstraction.models.Page;
import ultiproapp.pages.core.profile.profileeditphonelistgroup.ProfileEditPhoneListGroupActions;

public class ProfileEditModal extends Page {


    public Button closeButton;
    public Label title;
    public Button submitButton;
    public Label nameHeaderLabel;
    public Label firstNameLabel;
    public TextBox firstNameField;
    public Label preferredNameLabel;
    public TextBox preferredNameField;
    public Label lastNameLabel;
    public TextBox lastNameField;
    public Label addressCountryLabel;
    public Select addressCountryName;
    public Select addressCountrySelect;
    public Label addressLine1Label;
    public TextBox addressLine1Field;
    public Label addressLine2Label;
    public TextBox addressLine2Field;
    public Label addressCityLabel;
    public TextBox addressCityField;
    public Label addressStateLabel;
    public Select addressStateName;
    public Select addressStateSelect;
    public Label addressZipLabel;
    public TextBox addressZipField;
    public Label emailHeaderLabel;
    public TextBox primaryEmailInput;
    public TextBox secondaryEmailInput;
    public ListGroup<ProfileEditPhoneListGroupActions> phoneNumbersList;

    public ProfileEditModal(AutomationInfo info) {

        closeButton = new Button(info, By.cssSelector("[data-automation=\"form-close-button\"]"));
        title = new Label(info, By.cssSelector("[data-automation=\"form-title\"]"));
        submitButton = new Button(info, By.cssSelector("[data-automation=\"form-submit-button\"]"));
        nameHeaderLabel = new Label(info, By.cssSelector("[data-automation=\"name-section-label\"]"));
        firstNameLabel = new Label(info, By.cssSelector("[data-automation=\"first-name-item\"] ion-label"));
        firstNameField = new TextBox(info, By.cssSelector("[data-automation=\"first-name-item\"] ion-input"));
        preferredNameLabel = new Label(info, By.cssSelector("[data-automation=\"preferred-name-item\"] ion-label"));
        preferredNameField = new TextBox(info, By.cssSelector("[data-automation=\"preferred-name-item\"] ion-input]"));
        lastNameLabel = new Label(info, By.cssSelector("[data-automation=\"last-name-item\"] ion-label"));
        lastNameField = new TextBox(info, By.cssSelector("[data-automation=\"last-name-item\"] ion-input"));
        addressCountryLabel = new Label(info, By.cssSelector("[data-automation=\"address-country\"] ion-label"));
        addressCountryName = new Select(info, By.cssSelector("[data-automation=\"address-country\"] ion-select"));
        addressCountrySelect = new Select(info, By.cssSelector("[data-automation=\"address-country\"] ion-select-option"));
        addressLine1Label = new Label(info, By.cssSelector("[data-automation=\"address-line1\"] ion-label"));
        addressLine1Field = new TextBox(info, By.cssSelector("[data-automation=\"address-line1\"] ion-input"));
        addressLine2Label = new Label(info, By.cssSelector("[data-automation=\"address-line2\"] ion-label"));
        addressLine2Field = new TextBox(info, By.cssSelector("[data-automation=\"address-line2\"] ion-input"));
        addressCityLabel = new Label(info, By.cssSelector("[data-automation=\"address-city\"] ion-label"));
        addressCityField = new TextBox(info, By.cssSelector("[data-automation=\"address-city\"] ion-input"));
        addressStateLabel = new Label(info, By.cssSelector("[data-automation=\"address-state\"] ion-label"));
        addressStateName = new Select(info, By.cssSelector("[data-automation=\"address-state\"] ion-select"));
        addressStateSelect = new Select(info, By.cssSelector("[data-automation=\"address-state\"] ion-select-option"));
        addressZipLabel = new Label(info, By.cssSelector("[data-automation=\"address-zip\"] ion-label"));
        addressZipField = new TextBox(info, By.cssSelector("[data-automation=\"address-zip\"] ion-input"));
        emailHeaderLabel = new Label(info, By.cssSelector("[data-automation=\"email-section-label\"] ion-label"));
        primaryEmailInput = new TextBox(info, By.cssSelector("[data-automation=\"Primary\"] ion-input"));
        secondaryEmailInput = new TextBox(info, By.cssSelector("[data-automation=\"Alternate\"] ion-input"));
        phoneNumbersList = new ListGroup<>(info, By.cssSelector("[data-automation=\"profile-edit-form\"]"), new ProfileEditPhoneListGroupActions());


    }

}

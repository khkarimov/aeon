package ultiproapp.pages.core.profile.profileeditphonelistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

public class ProfileEditPhoneListGroupActions extends ListGroupActions<ProfileEditPhoneListGroupActions, ProfileEditPhoneListGroupElements>  {

    public ProfileEditPhoneListGroupActions() {

        super(ProfileEditPhoneListGroupActions.class, ProfileEditPhoneListGroupElements.class);
        this.rowSelector = "[data-automation=\"phone-edit-item\"]";

    }

    public ProfileEditPhoneListGroupActions phoneLabel(String value) {

        return findRow(value, "[data-automation=\"phone-number\"] ion-label");

    }

    public ProfileEditPhoneListGroupActions phoneInput(String value) {

        return findRow(value, "[data-automation=\"phone-number\"] ion-input");

    }

    public ProfileEditPhoneListGroupActions phoneExtLabel(String value) {

        return findRow(value, "[data-automation=\"phone-ext\"] ion-label");

    }

    public ProfileEditPhoneListGroupActions phoneExtInput(String value) {

        return findRow(value, "[data-automation=\"phone-ext\"] ion-input");

    }

    public ProfileEditPhoneListGroupActions phoneTypeLabel(String value) {

        return findRow(value, "[data-automation=\"phone-type\"] ion-label");

    }

    public ProfileEditPhoneListGroupActions phoneTypeInput(String value) {

        return findRow(value, "[data-automation=\"phone-type\"] ion-select");

    }

    public ProfileEditPhoneListGroupActions phoneCountryLabel(String value) {

        return findRow(value, "[data-automation=\"phone-country\"] ion-label");

    }

    public ProfileEditPhoneListGroupActions phoneCountryOption(String value) {

        return findRow(value, "[data-automation=\"phone-country\"] ion-select");

    }

    public ProfileEditPhoneListGroupActions phonePrivacyLabel(String value) {

        return findRow(value, "[data-automation=\"phone-privacy\"] ion-label");

    }

    public ProfileEditPhoneListGroupActions phonePrivacyToggle(String value) {

        return findRow(value, "[data-automation=\"phone-privacy\"] ion-toggle]");

    }

}

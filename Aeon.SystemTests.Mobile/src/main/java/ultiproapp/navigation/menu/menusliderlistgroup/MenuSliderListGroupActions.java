package ultiproapp.navigation.menu.menusliderlistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

public class MenuSliderListGroupActions extends ListGroupActions<MenuSliderListGroupActions, MenuSliderListGroupElements> {

    public MenuSliderListGroupActions() {

        super(MenuSliderListGroupActions.class, MenuSliderListGroupElements.class);
        this.rowSelector = "ion-item[class*=\"item-label\"]";

    }

    public MenuSliderListGroupActions label(String value) {

        return findRow(value, "ion-label[class*=\"ion-label\"]");

    }

}

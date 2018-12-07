package ultiproapp.navigation.menu;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.ListGroup;
import ultiproapp.navigation.menu.menusliderlistgroup.MenuSliderListGroupActions;

public class MenuSlider {

    public Button menuButton;
    public ListGroup<MenuSliderListGroupActions> menuSliderList;

    public MenuSlider(AutomationInfo info) {

        menuButton = new Button(info, By.cssSelector("ion-menu-button"));
        menuSliderList = new ListGroup<>(info, By.cssSelector("ion-menu"), new MenuSliderListGroupActions());

    }

}

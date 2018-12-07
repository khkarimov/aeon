package ultiproapp.navigation;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;

public class TabBar {

    public Button homeBarButton;
    public Button inboxBarButton;
    public Button teamBarButton;
    public Button searchBarButton;
    public Button menuBarButton;

    public TabBar(AutomationInfo info) {

        homeBarButton = new Button(info, By.cssSelector("[data-automation=\"mobile.tabs.home\"]"));
        inboxBarButton = new Button(info, By.cssSelector("[data-automation=\"mobile.tabs.inbox\"]"));
        teamBarButton = new Button(info, By.cssSelector("[data-automation=\"mobile.tabs.team\"]"));
        searchBarButton = new Button(info, By.cssSelector("[data-automation=\"mobile.tabs.search\"]"));
        menuBarButton = new Button(info, By.cssSelector("[data-automation=\"mobile.tabs.menu\"]"));

    }

}

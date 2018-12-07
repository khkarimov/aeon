package ultiproapp.pages.shared.teampages.myteamlistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

public class MyTeamListGroupActions extends ListGroupActions<MyTeamListGroupActions, MyTeamListGroupElements> {

    public MyTeamListGroupActions() {

        super(MyTeamListGroupActions.class, MyTeamListGroupElements.class);
        this.rowSelector = "[data-automation=\"team-member-item\"]";

    }

    public MyTeamListGroupActions employeeName(String value) {

        return findRow(value, "[data-automation=\"employee-name\"]");

    }

    public MyTeamListGroupActions employeeTitle(String value) {

        return findRow(value, "[data-automation=\"employee-title\"]");

    }

}

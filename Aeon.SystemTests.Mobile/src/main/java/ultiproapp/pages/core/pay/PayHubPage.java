package ultiproapp.pages.core.pay;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroup;
import aeon.core.testabstraction.models.Page;
import ultiproapp.pages.core.pay.payhubpaystatementlistgroup.PayHubPayStatementListGroupActions;
import ultiproapp.pages.core.pay.payhubtaxeslistgroup.PayHubTaxesListGroupActions;

public class PayHubPage extends Page {

    private String parentPageSelector = "upa-pay-hub:not([hidden]) ";

    public Button backButton;
    public Label titleLabel;
    // TODO: Modal Pie Chart
    public Label payStatementHeaderLabel;
    public Button payStatementViewAllButton;
    public Label noPayStatementLabel;
    public ListGroup<PayHubPayStatementListGroupActions> payHubPayStatementList;
    public Label taxesHeaderLabel;
    public Button taxesViewAllButton;
    public ListGroup<PayHubTaxesListGroupActions> payHubTaxesList;
    public Label noTaxFormLabel;

    public PayHubPage(AutomationInfo info) {

        backButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"back-button\"]"));
        titleLabel = new Label(info, By.cssSelector(parentPageSelector + "ion-title[data-automation=\"pay-hub-title\"]"));
        payStatementHeaderLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"pay-statement-header-label\"]"));
        payStatementViewAllButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"pay-statement-view-all-button\"]"));
        noPayStatementLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"no-pay-placeholder\"] [data-automation=\"label\"]"));
        payHubPayStatementList = new ListGroup<>(info, By.cssSelector("ion-slides[data-automation=\"pay-statements\"] div"), new PayHubPayStatementListGroupActions());
        taxesHeaderLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"taxes-header-label\"]"));
        taxesViewAllButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"navigate-taxes-button\"]"));
        payHubTaxesList = new ListGroup<>(info, By.cssSelector("[data-automation=\"taxes\"]"), new PayHubTaxesListGroupActions());
        noTaxFormLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"no-tax-forms-placeholder\"]"));

    }

}

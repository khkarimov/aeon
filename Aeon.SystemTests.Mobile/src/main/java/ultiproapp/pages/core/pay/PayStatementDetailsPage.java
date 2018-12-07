package ultiproapp.pages.core.pay;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroup;
import aeon.core.testabstraction.models.Page;
import ultiproapp.pages.core.pay.paystatementdetailssummarylistgroup.PayStatementDetailsSummaryListGroupActions;

public class PayStatementDetailsPage extends Page {

    private String parentPageSelector = "upa-pay-statement-details:not([hidden]) ";

    public Button backButton;
    public Label titleLabel;
    public Label subTitleLabel;
    public Button pdfButton;
    // TODO: Pay Insight Banner
    public Button currentPayButton;
    public Button yearToDateButton;
    public Label paySummaryHeaderLabel;
    public ListGroup<PayStatementDetailsSummaryListGroupActions> currentPaySummaryList;
    public ListGroup<PayStatementDetailsSummaryListGroupActions> yearToDatePaySummaryList;
    public Label noPTOLabel;
    public Label noDirectDepositLabel;
    public Label noEarningsLabel;
    public Label noDeductionsLabel;
    public Label noTaxesLabel;

    public PayStatementDetailsPage(AutomationInfo info) {

        backButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"back-button\"]"));
        titleLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"pay-statement-details-title\"]"));
        subTitleLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"pay-statement-details-subtitle\"]"));
        pdfButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"pay-pdf-button\"]"));
        currentPayButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"mobile.pay.summary.currentPay\"]"));
        yearToDateButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"mobile.pay.yearToDate\"]"));
        paySummaryHeaderLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"pay-summary-header\"]"));
        currentPaySummaryList = new ListGroup<>(info, By.cssSelector("[data-automation=\"mobile.pay.summary.currentPay\"] [data-automation=\"pay-summary-list\"]"), new PayStatementDetailsSummaryListGroupActions());
        yearToDatePaySummaryList = new ListGroup<>(info, By.cssSelector("[data-automation=\"mobile.pay.yearToDate\"] [data-automation=\"pay-summary-list\"]"), new PayStatementDetailsSummaryListGroupActions());
        noPTOLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"pay-no-pto-placeholder\"]"));
        noDirectDepositLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"pay-no-direct-deposits-placeholder\"]"));
        noEarningsLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"pay-no-earnings-placeholder\"]"));
        noDeductionsLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"pay-no-deductions-placeholder\"]"));
        noTaxesLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"pay-no-taxes-placeholder\"]"));

    }

}

package ultiproapp.pages.core.devandfeedback;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Image;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroup;
import aeon.core.testabstraction.models.Page;
import ultiproapp.pages.core.devandfeedback.devandfeedbackhubcompetencieslistgroup.DevAndFeedbackHubCompetenciesListGroupActions;
import ultiproapp.pages.core.devandfeedback.devandfeedbackhubgoalslistgroup.DevAndFeedbackHubGoalsListGroupActions;
import ultiproapp.pages.core.devandfeedback.devandfeedbackhubnoteslistgroup.DevAndFeedbackHubNotesListGroupActions;

public class DevAndFeedbackHubPage extends Page {

    private String parentPageSelector = "upa-career-development-hub:not([hidden]) ";

    public Image avatar;
    public Button backButton;
    public Label titleLabel;
    public Label nameSubLabel;
    public Label goalsHeaderLabel;
    public Button goalsViewAllButton;
    public ListGroup<DevAndFeedbackHubGoalsListGroupActions> goalsSummaryList;
    public Label competenciesHeaderLabel;
    public Button competenciesViewAllButton;
    public ListGroup<DevAndFeedbackHubCompetenciesListGroupActions> competenciesSummaryList;
    public Label notesHeaderLabel;
    public Button notesViewAllButton;
    public ListGroup<DevAndFeedbackHubNotesListGroupActions> notesSummaryList;
    public Label ultiProLearningLabel;
    public Label ultiProLearningSubLabel;

    public DevAndFeedbackHubPage(AutomationInfo info) {

        avatar = new Image(info, By.cssSelector(parentPageSelector + "[data-automation=\"app-bar-avatar\"]"));
        backButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"back-button\"]"));
        titleLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"app-bar-title\"]"));
        nameSubLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"app-bar-name\"]"));
        goalsHeaderLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"goals-list-header\"] ion-label"));
        goalsViewAllButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"goals-list-header\"] ion-button"));
        goalsSummaryList = new ListGroup<>(info, By.cssSelector("[data-automation=\"goals-summary-list\"]"), new DevAndFeedbackHubGoalsListGroupActions());
        competenciesHeaderLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"competency-list-header\"] ion-label"));
        competenciesViewAllButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"competency-list-header\"] ion-button"));
        competenciesSummaryList = new ListGroup<>(info, By.cssSelector("[data-automation=\"competency-summary-list\"]"), new DevAndFeedbackHubCompetenciesListGroupActions());
        notesHeaderLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"note-list-header\"] ion-label"));
        notesViewAllButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"note-list-header\"] ion-button"));
        notesSummaryList = new ListGroup<>(info, By.cssSelector("[data-automation=\"note-summary-list\"]"), new DevAndFeedbackHubNotesListGroupActions());
        ultiProLearningLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"ultipro-learning-item\"] h2"));
        ultiProLearningSubLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"ultipro-learning-item\"] p"));

    }

}

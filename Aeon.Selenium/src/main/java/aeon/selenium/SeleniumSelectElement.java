package aeon.selenium;

import aeon.core.framework.abstraction.controls.web.WebControl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SeleniumSelectElement extends WebControl {
    private Select underlyingSelectElement;
    private static Logger log = LogManager.getLogger(SeleniumSelectElement.class);

    public SeleniumSelectElement(Select selectElement) {
        underlyingSelectElement = selectElement;
    }

    protected final Select getUnderlyingSelectElement() {
        return underlyingSelectElement;
    }

    public final boolean IsMultiple(UUID guid) {
        log.trace("SelectElement.get_IsMultiple();");
        return getUnderlyingSelectElement().isMultiple();
    }

    public final List<WebControl> GetAllSelectedOptions(UUID guid) {
        log.trace("SelectElement.get_AllSelectedOptions();");
        return underlyingSelectElement.getAllSelectedOptions().stream()
                .map(e -> new SeleniumElement(e))
                .collect(Collectors.toList());
    }

    public final WebControl GetSelectedOption(UUID guid) {
        log.trace("SelectElement.get_SelectedOption();");
        return new SeleniumElement(getUnderlyingSelectElement().getFirstSelectedOption());
    }

    public final String GetSelectedOptionText(UUID guid){
        log.trace("SelectElement.get_SelectedOptionText();");
        return getUnderlyingSelectElement().getFirstSelectedOption().getText();
    }

    public final List<WebControl> GetOptions(UUID guid) {
        log.trace("SelectElement.get_Options();");
        return underlyingSelectElement.getOptions().stream()
                .map(e -> new SeleniumElement(e))
                .collect(Collectors.toList());
    }

    public final void DeselectAll(UUID guid) {
        log.trace("SelectElement.DeselectAll();");
        getUnderlyingSelectElement().deselectAll();
    }

    public final void DeselectByIndex(UUID guid, int index) {
        log.trace(String.format("SelectElement.DeselectByIndex(%1$s);", index));

        getUnderlyingSelectElement().deselectByIndex(index);
    }

    public final void DeselectByText(UUID guid, String text) {
        log.trace(String.format("SelectElement.DeselectByText(%1$s);", text));
        getUnderlyingSelectElement().deselectByVisibleText(text);
    }

    public final void DeselectByValue(UUID guid, String value) {
        log.trace(String.format("SelectElement.DeselectByValue(%1$s);", value));
        getUnderlyingSelectElement().deselectByValue(value);
    }

    public final void SelectByIndex(UUID guid, int index) {
        log.trace(String.format("SelectElement.SelectByIndex(%1$s);", index));
        getUnderlyingSelectElement().selectByIndex(index);
    }

    public final void SelectByText(UUID guid, String text) {
        log.trace(String.format("SelectElement.SelectByText(%1$s);", text));
        getUnderlyingSelectElement().selectByVisibleText(text);
    }

    public final void SelectByValue(UUID guid, String value) {
        log.trace(String.format("SelectElement.SelectByValue(%1$s);", value));
        getUnderlyingSelectElement().selectByValue(value);
    }
}

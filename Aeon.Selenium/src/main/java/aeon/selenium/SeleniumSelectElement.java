package aeon.selenium;

import aeon.core.framework.abstraction.controls.web.WebControl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
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

    public final boolean isMultiple() {
        log.trace("SelectElement.get_IsMultiple();");
        return getUnderlyingSelectElement().isMultiple();
    }

    public final List<WebControl> getAllSelectedOptions() {
        log.trace("SelectElement.get_AllSelectedOptions();");
        return underlyingSelectElement.getAllSelectedOptions().stream()
                .map(e -> new SeleniumElement(e))
                .collect(Collectors.toList());
    }

    public final WebControl getSelectedOption() {
        log.trace("SelectElement.get_SelectedOption();");
        return new SeleniumElement(getUnderlyingSelectElement().getFirstSelectedOption());
    }

    public final String getSelectedOptionText(){
        log.trace("SelectElement.get_SelectedOptionText();");
        return getUnderlyingSelectElement().getFirstSelectedOption().getText();
    }

    public final List<WebControl> getOptions() {
        log.trace("SelectElement.get_Options();");
        return underlyingSelectElement.getOptions().stream()
                .map(e -> new SeleniumElement(e))
                .collect(Collectors.toList());
    }

    public final void deselectAll() {
        log.trace("SelectElement.deselectAll();");
        getUnderlyingSelectElement().deselectAll();
    }

    public final void deselectByIndex(int index) {
        log.trace(String.format("SelectElement.deselectByIndex(%1$s);", index));

        getUnderlyingSelectElement().deselectByIndex(index);
    }

    public final void deselectByText(String text) {
        log.trace(String.format("SelectElement.deselectByText(%1$s);", text));
        getUnderlyingSelectElement().deselectByVisibleText(text);
    }

    public final void deselectByValue(String value) {
        log.trace(String.format("SelectElement.deselectByValue(%1$s);", value));
        getUnderlyingSelectElement().deselectByValue(value);
    }

    public final void selectByIndex(int index) {
        log.trace(String.format("SelectElement.selectByIndex(%1$s);", index));
        getUnderlyingSelectElement().selectByIndex(index);
    }

    public final void selectByText(String text) {
        log.trace(String.format("SelectElement.selectByText(%1$s);", text));
        getUnderlyingSelectElement().selectByVisibleText(text);
    }

    public final void selectByValue(String value) {
        log.trace(String.format("SelectElement.selectByValue(%1$s);", value));
        getUnderlyingSelectElement().selectByValue(value);
    }
}

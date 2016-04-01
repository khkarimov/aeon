package framework_interaction.selenium;

import common.logging.ILog;
import framework_abstraction.webdriver.IWebElementAdapter;
import framework_abstraction.webdriver.IWebSelectElementAdapter;
import org.openqa.selenium.support.ui.Select;

import java.util.*;
import java.util.stream.Collectors;

public class SeleniumSelectElement implements IWebSelectElementAdapter {
    private Select underlyingSelectElement;
    private ILog log;

    public SeleniumSelectElement(Select selectElement, ILog log) {
        underlyingSelectElement = selectElement;
        this.log = log;
    }

    protected final Select getUnderlyingSelectElement() {
        return underlyingSelectElement;
    }

    protected final ILog getLog() {
        return log;
    }

    public final boolean IsMultiple(UUID guid) {
        log.Trace(guid, "SelectElement.get_IsMultiple();");
        return getUnderlyingSelectElement().isMultiple();
    }

    public final List<IWebElementAdapter> GetAllSelectedOptions(UUID guid) {
        log.Trace(guid, "SelectElement.get_AllSelectedOptions();");
        return underlyingSelectElement.getAllSelectedOptions().stream()
                .map(e -> new SeleniumElement(e, log))
                .collect(Collectors.toList());
    }

    public final IWebElementAdapter GetSelectedOption(UUID guid) {
        log.Trace(guid, "SelectElement.get_SelectedOption();");
        return new SeleniumElement(getUnderlyingSelectElement().getFirstSelectedOption(), getLog());
    }

    public final List<IWebElementAdapter> GetOptions(UUID guid) {
        log.Trace(guid, "SelectElement.get_Options();");
        return underlyingSelectElement.getOptions().stream()
                .map(e -> new SeleniumElement(e, log))
                .collect(Collectors.toList());
    }

    public final void DeselectAll(UUID guid) {
        log.Trace(guid, "SelectElement.DeselectAll();");
        getUnderlyingSelectElement().deselectAll();
    }

    public final void DeselectByIndex(UUID guid, int index) {
        log.Trace(guid, String.format("SelectElement.DeselectByIndex(%1$s);", index));

        getUnderlyingSelectElement().deselectByIndex(index);
    }

    public final void DeselectByText(UUID guid, String text) {
        log.Trace(guid, String.format("SelectElement.DeselectByText(%1$s);", text));
        getUnderlyingSelectElement().deselectByVisibleText(text);
    }

    public final void DeselectByValue(UUID guid, String value) {
        log.Trace(guid, String.format("SelectElement.DeselectByValue(%1$s);", value));
        getUnderlyingSelectElement().deselectByValue(value);
    }

    public final void SelectByIndex(UUID guid, int index) {
        log.Trace(guid, String.format("SelectElement.SelectByIndex(%1$s);", index));
        getUnderlyingSelectElement().selectByIndex(index);
    }

    public final void SelectByText(UUID guid, String text) {
        log.Trace(guid, String.format("SelectElement.SelectByText(%1$s);", text));
        getUnderlyingSelectElement().selectByVisibleText(text);
    }

    public final void SelectByValue(UUID guid, String value) {
        log.Trace(guid, String.format("SelectElement.SelectByValue(%1$s);", value));
        getUnderlyingSelectElement().selectByValue(value);
    }
}

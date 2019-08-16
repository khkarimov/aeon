package com.ultimatesoftware.aeon.core.extensions;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.ICommandExecutionFacade;
import com.ultimatesoftware.aeon.core.command.execution.WebCommandExecutionFacade;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.ClickCommand;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.SetCommand;
import com.ultimatesoftware.aeon.core.common.interfaces.IBy;
import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import com.ultimatesoftware.aeon.core.common.web.selectors.ByJQuery;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class WebProductTypeExtensionTests {
    private WebProductTypeExtension webProductTypeExtension;

    @Mock
    private AutomationInfo automationInfo;

    @Mock
    private Configuration configuration;

    private Map<String, String> selectorMap;

    @BeforeEach
    void setUp() {
        this.webProductTypeExtension = new WebProductTypeExtension();
        this.selectorMap = new HashMap<>();
        this.selectorMap.put("value", "#id.class");
    }

    @Test
    void createCommandExecutionFacade_automationInfo_setsCommandExecutionFacade() {
        //Arrange
        when(automationInfo.getConfiguration()).thenReturn(configuration);

        //Act
        ICommandExecutionFacade resultCommandExecutionFacade = webProductTypeExtension.createCommandExecutionFacade(automationInfo);

        //Assert
        verify(automationInfo, times(1)).setCommandExecutionFacade(any(WebCommandExecutionFacade.class));
        assertNotNull(resultCommandExecutionFacade);
    }

    @Test
    void createSelector_valueIsNull_returnsNull() {
        //Arrange
        Map<String, String> nullValueSelector = new HashMap<>();

        //Act
        IBy createSelectorResult = webProductTypeExtension.createSelector(nullValueSelector);

        //Assert
        assertNull(createSelectorResult);
    }

    @Test
    void createSelector_unknownType_returnsNull() {
        //Arrange
        this.selectorMap.put("type", "unknown");

        //Act
        IBy createSelectorResult = webProductTypeExtension.createSelector(this.selectorMap);

        //Assert
        assertNull(createSelectorResult);
    }

    @Test
    void createSelector_cssType_returnsIBy() {
        //Arrange
        this.selectorMap.put("type", "css");
        this.selectorMap.put("value", "#id.class");

        //Act
        By createSelectorResult = (By) webProductTypeExtension.createSelector(this.selectorMap);

        //Assert
        assertEquals(By.class, createSelectorResult.getClass());
        assertEquals("#id.class", createSelectorResult.toString());
    }

    @Test
    void createSelector_dataType_returnsIBy() {
        //Arrange
        this.selectorMap.put("type", "data");
        this.selectorMap.put("value", "test-button");

        //Act
        By createSelectorResult = (By) webProductTypeExtension.createSelector(this.selectorMap);

        //Assert
        assertEquals(By.class, createSelectorResult.getClass());
        assertEquals("[data-automation=\"test-button\"]", createSelectorResult.toString());
    }

    @Test
    void createSelector_daType_returnsIBy() {
        //Arrange
        this.selectorMap.put("type", "da");
        this.selectorMap.put("value", "test-button");

        //Act
        By createSelectorResult = (By) webProductTypeExtension.createSelector(this.selectorMap);

        //Assert
        assertEquals(By.class, createSelectorResult.getClass());
        assertEquals("[da=\"test-button\"]", createSelectorResult.toString());
    }

    @Test
    void createSelector_jqueryType_returnsByJQuery() {
        //Arrange
        this.selectorMap.put("type", "jquery");
        this.selectorMap.put("value", "#id.class:contains(test)");

        //Act
        ByJQuery createSelectorResult = (ByJQuery) webProductTypeExtension.createSelector(this.selectorMap);

        //Assert
        assertEquals(ByJQuery.class, createSelectorResult.getClass());
        assertEquals("$(\"#id.class:contains(test)\")", createSelectorResult.toString());
    }

    @Test
    void createSelector_jqueryParentsTypeWithoutParent_returnsNull() {
        //Arrange
        this.selectorMap.put("type", "jqueryParents");
        this.selectorMap.put("value", "#id.class:contains(test)");

        //Act
        IBy createSelectorResult = webProductTypeExtension.createSelector(this.selectorMap);

        //Assert
        assertNull(createSelectorResult);
    }

    @Test
    void createSelector_jqueryFilterTypeWithoutParent_returnsNull() {
        //Arrange
        this.selectorMap.put("type", "jqueryFilter");
        this.selectorMap.put("value", "#id.class:contains(test)");

        //Act
        IBy createSelectorResult = webProductTypeExtension.createSelector(this.selectorMap);

        //Assert
        assertNull(createSelectorResult);
    }

    @Test
    void createCommand_invalidCommandClass_returnsNull() {
        // Arrange

        // Act
        Object result = this.webProductTypeExtension.createCommand("something", new ArrayList<>());

        // Assert
        assertNull(result);
    }

    @Test
    void createCommand_validCommandClass_returnsCommand() {
        // Arrange
        this.selectorMap.put("type", "jquery");
        this.selectorMap.put("value", "#element-id");

        // Act
        Object result = this.webProductTypeExtension.createCommand(
                "ClickCommand",
                Collections.singletonList(this.selectorMap));

        // Assert
        assertEquals(ClickCommand.class, result.getClass());
    }

    @Test
    void createCommand_validCommandClassButNullArguments_returnsNull() {
        // Arrange
        this.selectorMap.put("type", "jquery");
        this.selectorMap.put("value", "#element-id");

        // Act
        Object result = this.webProductTypeExtension.createCommand(
                "ClickCommand",
                null);

        // Assert
        assertNull(result);
    }

    @Test
    void createCommand_validCommandClassButInvalidSelector_returnsNull() {
        // Arrange

        // Act
        Object result = this.webProductTypeExtension.createCommand(
                "ClickCommand",
                Collections.singletonList(new Object()));

        // Assert
        assertNull(result);
    }

    @Test
    void createCommand_validCommandClassWithWebSelectOption_returnsCommand() {
        // Arrange
        this.selectorMap.put("type", "jquery");
        this.selectorMap.put("value", "#element-id");

        // Act
        Object result = this.webProductTypeExtension.createCommand(
                "SetCommand",
                Arrays.asList(this.selectorMap, "TEXT", "test"));

        // Assert
        assertEquals(SetCommand.class, result.getClass());
    }

    @Test
    void createCommand_validCommandClassWithParentCssSelector_returnsCommand() {
        // Arrange
        this.selectorMap.put("type", "css");
        this.selectorMap.put("value", "#element-id");
        Map<String, String> selector = new HashMap<>();
        selector.put("type", "css");
        selector.put("value", ".test-class");
        List<Map<String, String>> selectors = Arrays.asList(this.selectorMap, selector);

        // Act
        ClickCommand result = (ClickCommand) this.webProductTypeExtension.createCommand(
                "ClickCommand",
                Collections.singletonList(selectors.toArray(new Map[0])));

        // Assert
        assertEquals("#element-id .test-class", result.getSelector().toString());
    }

    @Test
    void createCommand_validCommandClassWithJQuerySelectorAndParentCssSelector_returnsCommand() {
        // Arrange
        this.selectorMap.put("type", "css");
        this.selectorMap.put("value", "#element-id");
        Map<String, String> selector = new HashMap<>();
        selector.put("type", "jQuery");
        selector.put("value", ".test-class:contains(test)");
        List<Map<String, String>> selectors = Arrays.asList(this.selectorMap, selector);

        // Act
        ClickCommand result = (ClickCommand) this.webProductTypeExtension.createCommand(
                "ClickCommand",
                Collections.singletonList(selectors.toArray(new Map[0])));

        // Assert
        assertEquals("$(\"#element-id\").find($(\".test-class:contains(test)\"))", result.getSelector().toString());
    }

    @Test
    void createCommand_validCommandClassWithJQuerySelectorAndParentJQuerySelector_returnsCommand() {
        // Arrange
        this.selectorMap.put("type", "jQuery");
        this.selectorMap.put("value", "#element-id:contains(tester)");
        Map<String, String> selector = new HashMap<>();
        selector.put("type", "jQuery");
        selector.put("value", ".test-class:contains(test)");
        List<Map<String, String>> selectors = Arrays.asList(this.selectorMap, selector);

        // Act
        ClickCommand result = (ClickCommand) this.webProductTypeExtension.createCommand(
                "ClickCommand",
                Collections.singletonList(selectors.toArray(new Map[0])));

        // Assert
        assertEquals("$(\"#element-id:contains(tester)\").find($(\".test-class:contains(test)\"))", result.getSelector().toString());
    }

    @Test
    void createCommand_validCommandClassWithDataAutomationSelectorAndParentCssSelector_returnsCommand() {
        // Arrange
        this.selectorMap.put("type", "css");
        this.selectorMap.put("value", "#element-id");
        Map<String, String> selector = new HashMap<>();
        selector.put("type", "data");
        selector.put("value", "test-button");
        List<Map<String, String>> selectors = Arrays.asList(this.selectorMap, selector);

        // Act
        ClickCommand result = (ClickCommand) this.webProductTypeExtension.createCommand(
                "ClickCommand",
                Collections.singletonList(selectors.toArray(new Map[0])));

        // Assert
        assertEquals("#element-id [data-automation=\"test-button\"]", result.getSelector().toString());
    }

    @Test
    void createCommand_validCommandClassWithDaSelectorAndParentCssSelector_returnsCommand() {
        // Arrange
        this.selectorMap.put("type", "css");
        this.selectorMap.put("value", "#element-id");
        Map<String, String> selector = new HashMap<>();
        selector.put("type", "da");
        selector.put("value", "test-button");
        List<Map<String, String>> selectors = Arrays.asList(this.selectorMap, selector);

        // Act
        ClickCommand result = (ClickCommand) this.webProductTypeExtension.createCommand(
                "ClickCommand",
                Collections.singletonList(selectors.toArray(new Map[0])));

        // Assert
        assertEquals("#element-id [da=\"test-button\"]", result.getSelector().toString());
    }

    @Test
    void createCommand_validCommandClassWithJQueryParentsSelector_returnsCommand() {
        // Arrange
        this.selectorMap.put("type", "css");
        this.selectorMap.put("value", "#element-id");
        Map<String, String> selector = new HashMap<>();
        selector.put("type", "jqueryParents");
        selector.put("value", ".class-name");
        List<Map<String, String>> selectors = Arrays.asList(this.selectorMap, selector);

        // Act
        ClickCommand result = (ClickCommand) this.webProductTypeExtension.createCommand(
                "ClickCommand",
                Collections.singletonList(selectors.toArray(new Map[0])));

        // Assert
        assertEquals("$(\"#element-id\").parents(\".class-name\")", result.getSelector().toString());
    }

    @Test
    void createCommand_validCommandClassWithJQueryFilterSelector_returnsCommand() {
        // Arrange
        this.selectorMap.put("type", "css");
        this.selectorMap.put("value", "#element-id");
        Map<String, String> selector = new HashMap<>();
        selector.put("type", "jqueryFilter");
        selector.put("value", ".class-name");
        List<Map<String, String>> selectors = Arrays.asList(this.selectorMap, selector);

        // Act
        ClickCommand result = (ClickCommand) this.webProductTypeExtension.createCommand(
                "ClickCommand",
                Collections.singletonList(selectors.toArray(new Map[0])));

        // Assert
        assertEquals("$(\"#element-id\").filter(\".class-name\")", result.getSelector().toString());
    }
}

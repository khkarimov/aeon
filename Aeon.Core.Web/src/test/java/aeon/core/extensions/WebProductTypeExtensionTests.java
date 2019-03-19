package aeon.core.extensions;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.common.interfaces.IBy;
import aeon.core.testabstraction.product.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@RunWith(MockitoJUnitRunner.class)
public class WebProductTypeExtensionTests {
    private WebProductTypeExtension webProductTypeExtension;

    @Mock
    private AutomationInfo automationInfo;

    @Mock
    private Configuration configuration;

    private Map<String, String> selectorMap;

    @Before
    public void setUp() {
        this.webProductTypeExtension = new WebProductTypeExtension();
        this.selectorMap = new HashMap();
        this.selectorMap.put("value", "some value");
    }

    @Test
    public void createCommandExecutionFacade_automationInfo_setsCommandExecutionFacade() {
        //Arrange
        when(automationInfo.getConfiguration()).thenReturn(configuration);

        //Act
        ICommandExecutionFacade resultCommandExecutionFacade = webProductTypeExtension.createCommandExecutionFacade(automationInfo);

        //Assert
        verify(automationInfo, times(1)).getConfiguration();
        Assert.assertNotNull(resultCommandExecutionFacade);
    }

    @Test
    public void createSelector_valueIsNull_returnsNull() {
        //Arrange
        Map<String, String> nullValueSelector = new HashMap<>();

        //Act
        IBy createSelectorResult = webProductTypeExtension.createSelector(nullValueSelector);

        //Assert
        Assert.assertNull(createSelectorResult);
    }

    @Test
    public void createSelector_unknownType_returnsNull() {
        //Arrange
        this.selectorMap.put("type", "unknown");

        //Act
        IBy createSelectorResult = webProductTypeExtension.createSelector(this.selectorMap);

        //Assert
        Assert.assertNull(createSelectorResult);
    }

    @Test
    public void createSelector_cssType_returnsIBy() {
        //Arrange
        this.selectorMap.put("type", "css");

        //Act
        IBy createSelectorResult = webProductTypeExtension.createSelector(this.selectorMap);

        //Assert
        Assert.assertThat(createSelectorResult, instanceOf(IBy.class));
    }

    @Test
    public void createSelector_dataType_returnsIBy() {
        //Arrange
        this.selectorMap.put("type", "data");

        //Act
        IBy createSelectorResult = webProductTypeExtension.createSelector(this.selectorMap);

        //Assert
        Assert.assertThat(createSelectorResult, instanceOf(IBy.class));
    }

    @Test
    public void createSelector_daType_returnsIBy() {
        //Arrange
        this.selectorMap.put("type", "da");

        //Act
        IBy createSelectorResult = webProductTypeExtension.createSelector(this.selectorMap);

        //Assert
        Assert.assertThat(createSelectorResult, instanceOf(IBy.class));
    }

    @Test
    public void createSelector_jqueryType_returnsIBy() {
        //Arrange
        this.selectorMap.put("type", "jquery");

        //Act
        IBy createSelectorResult = webProductTypeExtension.createSelector(this.selectorMap);

        //Assert
        Assert.assertThat(createSelectorResult, instanceOf(IBy.class));
    }
}

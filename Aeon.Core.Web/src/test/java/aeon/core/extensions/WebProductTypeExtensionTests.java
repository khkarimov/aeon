package aeon.core.extensions;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
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

    @Mock
    private WebCommandExecutionFacade commandExecutionFacade;


    @Before
    public void setUp() {
        this.webProductTypeExtension = new WebProductTypeExtension();
    }

    @Test
    public void createCommandExecutionFacade_automationInfo_getsConfiguration() {
        //Arrange
        when(automationInfo.getConfiguration()).thenReturn(configuration);

        //Act
        webProductTypeExtension.createCommandExecutionFacade(automationInfo);

        //Assert
        verify(automationInfo, times(1)).getConfiguration();

    }

    @Test
    public void createCommandExecutionFacade_automationInfo_setsCommandExecutionFacade() {
//        the weird test:
//        org.mockito.exceptions.misusing.UnfinishedVerificationException:
//        Missing method call for verify(mock) here:

        //Arrange
        when(automationInfo.getConfiguration()).thenReturn(configuration);

        //Act
        webProductTypeExtension.createCommandExecutionFacade(automationInfo);

        //Assert
        verify(automationInfo, times(1)).setCommandExecutionFacade(commandExecutionFacade);
    }

    @Test
    public void createSelector_valueIsNull_returnsNull() {
        //Arrange
        Map<String, String> nullValueSelector = new HashMap<>();

        //Act


        //Assert
        Assert.assertNull(webProductTypeExtension.createSelector(nullValueSelector));


    }

    @Test
    public void createSelector_unknownType_returnsNull() {
        //Arrange
        Map<String, String> unknownType = makeMapWithType("unknown");

        //Act

        //Assert
        Assert.assertNull(webProductTypeExtension.createSelector(unknownType));
    }

    @Test
    public void createSelector_cssType_returnsIBy() {
        //Arrange
        Map<String, String> type = makeMapWithType("css");

        //Act

        //Assert
        Assert.assertThat(webProductTypeExtension.createSelector(type), instanceOf(IBy.class));
    }

    @Test
    public void createSelector_dataType_returnsIBy() {
        //Arrange
        Map<String, String> type = makeMapWithType("data");

        //Act

        //Assert
        Assert.assertThat(webProductTypeExtension.createSelector(type), instanceOf(IBy.class));
    }

    @Test
    public void createSelector_daType_returnsIBy() {
        //Arrange
        Map<String, String> type = makeMapWithType("da");

        //Act

        //Assert
        Assert.assertThat(webProductTypeExtension.createSelector(type), instanceOf(IBy.class));
    }

    @Test
    public void createSelector_jqueryType_returnsIBy() {
        //Arrange
        Map<String, String> type = makeMapWithType("jquery");

        //Act

        //Assert
        Assert.assertThat(webProductTypeExtension.createSelector(type), instanceOf(IBy.class));
    }


    private Map<String, String> makeMapWithType(String type) {
        Map<String, String> ans = new HashMap();
        ans.put("value", "some value");
        ans.put("type", type);

        return ans;
    }
}

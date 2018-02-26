package aeon.core.test.controls.web;

import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class WebControlTests {

   private WebControl webControl;

   @Mock
   private IByWeb selector;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        webControl = new WebControl();
    }

    @Test
    public void getAndSetForSelectorTest(){
        // Arrange

        // Act
        webControl.setSelector(selector);
        IByWeb expectedSelector = webControl.getSelector();

        // Assert
        Assert.assertEquals(selector, expectedSelector);
    }
}
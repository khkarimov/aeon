package aeon.core.test.controls.web;

import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WebControlTests {

    private WebControl webControl;

    @Mock
    private IByWeb selector;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @BeforeEach
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
        Assertions.assertEquals(selector, expectedSelector);
    }
}
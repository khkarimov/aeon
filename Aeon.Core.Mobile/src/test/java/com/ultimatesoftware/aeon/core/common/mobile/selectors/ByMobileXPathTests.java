package com.ultimatesoftware.aeon.core.common.mobile.selectors;

import com.ultimatesoftware.aeon.core.common.exceptions.NativeSelectorException;
import com.ultimatesoftware.aeon.core.common.mobile.interfaces.IByMobile;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.common.web.selectors.ByJQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByMobileXPathTests {

    private ByMobileXPath command;

    private String mySelector = "My Selector";

    @BeforeEach
    public void setup() {
        command = new ByMobileXPath(mySelector);
    }


    @Test
    public void createMobileSelector_whenSelectorIsCreatedUsingID_returnsCorrectId() {
        //Arrange
        String myId = "new ID";

        //Act
        IByMobile myNewSelector = ByMobileId.id(myId);

        //Assert
        assertEquals(myId, ((ByMobileId) myNewSelector).getSelector());
    }

    @Test
    public void useMobileSelector_whenGettingSelector_returnsCorrectId() {
        //Arrange

        //Act
        String selector = command.getSelector();

        //Assert
        assertEquals(mySelector, selector);
    }

    @Test
    public void useMobileSelector_whenGettingToString_returnsCorrectId() {
        //Arrange

        //Act
        String selector = command.toString();

        //Assert
        assertEquals(mySelector, selector);
    }


    @Test
    public void useMobileSelector_whenUsingAnIByWebSelector_throwsException() {
        //Arrange
        IByWeb myFindSelector = new ByJQuery("#my-jquery-selector");

        //Act and Assert
        Assertions.assertThrows(NativeSelectorException.class, () -> {
            command.find(myFindSelector);
        });
    }

    @Test
    public void useMobileSelector_whenRetrievingJquerySelector_throwsException() {

        //Act and Assert
        Assertions.assertThrows(NativeSelectorException.class, () -> {
            command.toJQuery();
        });
    }
}

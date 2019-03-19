package aeon.core.common.mobile.selectors;

import aeon.core.common.exceptions.NativeSelectorException;
import aeon.core.common.mobile.interfaces.IByMobile;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.common.web.selectors.ByJQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class ByMobileTests {

    private ByMobile command;

    private String mySelector = "My Selector";

    @BeforeEach
    public void setup() {
        command = new ByMobile(mySelector);
    }


    @Test
    public void createMobileSelector_whenSelectorIsCreatedUsingID_returnsCorrectId() {
        //Arrange
        String myId = "new ID";

        //Act
        IByMobile myNewSelector = ByMobileId.id(myId);

        //Assert
        assertEquals("When instantiating using id, returns correct id", ((ByMobileId) myNewSelector).getSelector(), myId);
    }

    @Test
    public void useMobileSelector_whenGettingSelector_returnsCorrectId() {
        //Arrange

        //Act
        String selector = command.getSelector();

        //Assert
        assertEquals("When instantiating using constructor, returns correct id", selector, mySelector);
    }

    @Test
    public void useMobileSelector_whenGettingToString_returnsCorrectId() {
        //Arrange

        //Act
        String selector = command.toString();

        //Assert
        assertEquals("When instantiating using constructor, returns correct id", selector, mySelector);
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

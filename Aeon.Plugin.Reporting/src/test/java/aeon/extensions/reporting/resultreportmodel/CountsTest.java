package aeon.extensions.reporting.resultreportmodel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CountsTest {

    private Counts counts;

    @BeforeEach
    public void setUp() {
        counts =  new Counts();
    }

    @Test
    void getPassed_noDataStored_returnsZero() {
        //Arrange
        //We do not have data

        //Act
        int data = counts.getPassed();

        //Assert
        assertEquals(0, data);
    }

    @Test
    void setPassed_dataStored_returnsTen() {
        //Arrange
        counts.setPassed(10);

        //Act
        int data = counts.getPassed();

        //Assert
        assertEquals(10, data);
    }

    @Test
    void getFailed_noDataStored_returnsZero() {
        //Arrange
        //We do not have data

        //Act
        int data = counts.getFailed();

        //Assert
        assertEquals(0, data);
    }

    @Test
    void setFailed_dataStored_returnsFive() {
        //Arrange
        counts.setFailed(5);

        //Act
        int data = counts.getFailed();

        //Assert
        assertEquals(5, data);
    }

    @Test
    void getDisabled_noDataStored_returnsZero() {
        //Arrange
        //We do not have data

        //Act
        int data = counts.getDisabled();

        //Assert
        assertEquals(0, data);
    }

    @Test
    void setDisabled_dataStored_returnsOne() {
        //Arrange
        counts.setDisabled(1);

        //Act
        int data = counts.getDisabled();

        //Assert
        assertEquals(1, data);
    }
}
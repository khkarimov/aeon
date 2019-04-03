package com.ultimatesoftware.aeon.extensions.reporting.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountsTest {

    private Counts counts;

    @BeforeEach
    void setUp() {
        counts = new Counts();
    }

    @Test
    void getPassed_noDataStored_returnsZero() {
        //Arrange

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

    @Test
    void getTotal_dataStored_returnsTotalNumberOfTests() {
        // Arrange
        counts.setDisabled(2);
        counts.setFailed(3);
        counts.setPassed(4);

        // Act
        int total = counts.getTotal();

        // Assert
        assertEquals(9, total);
    }
}

package com.ultimatesoftware.aeon.core.common;

import com.ultimatesoftware.aeon.core.common.web.ClientRects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ClientRectsTests {

    // Variables
    private ClientRects rect;

    @BeforeEach
    void setUp() {
        rect = new ClientRects(1, 8, 2, 6);
    }

    @Test
    void toString_ReturnsCorrectly() {
        // Arrange
        String expectedString = "Top: 1 Bottom: 8 Left: 2 Right: 6";

        // Act
        String actualString = rect.toString();

        // Assert
        assertEquals(expectedString, actualString);
    }

    @Test
    void clientRects_ConstructorImplementsCorrectly() {
        // Arrange
        rect = new ClientRects(rect);

        // Act
        int top = rect.getTop();
        int bottom = rect.getBottom();
        int left = rect.getLeft();
        int right = rect.getRight();

        // Assert
        assertEquals(1, top);
        assertEquals(0, bottom);
        assertEquals(0, left);
        assertEquals(0, right);
    }

    @Test
    void getWidthTest() {
        // Arrange
        int expectedWidth = 4;

        // Act
        int actualWidth = rect.getWidth();

        // Assert
        assertEquals(expectedWidth, actualWidth);
    }

    @Test
    void getHeightTest() {
        // Arrange
        int expectedHeight = 7;

        // Act
        int actualHeight = rect.getHeight();

        // Assert
        assertEquals(expectedHeight, actualHeight);
    }

    @Test
    void getHorizontalCenterTest() {
        // Arrange
        int expectedHorizontalCenter = 4;

        // Act
        int actualHorizontalCenter = rect.getHorizontalCenter();

        // Assert
        assertEquals(expectedHorizontalCenter, actualHorizontalCenter);
    }

    @Test
    void getVerticalCenterTest() {
        // Arrange
        int expectedVerticalCenter = 4;

        // Act
        int actualVerticalCenter = rect.getVerticalCenter();

        // Assert
        assertEquals(expectedVerticalCenter, actualVerticalCenter);
    }

    @Test
    void getHorizontalMiddleTest() {
        // Arrange
        int expectedHorizontalMiddle = 2;

        // Act
        int actualHorizontalMiddle = rect.getHorizontalMiddle();

        // Assert
        assertEquals(expectedHorizontalMiddle, actualHorizontalMiddle);
    }

    @Test
    void getVerticalMiddleTest() {
        // Arrange
        int expectedVerticalMiddle = 3;

        // Act
        int actualVerticalMiddle = rect.getVerticalMiddle();

        // Assert
        assertEquals(expectedVerticalMiddle, actualVerticalMiddle);
    }

}

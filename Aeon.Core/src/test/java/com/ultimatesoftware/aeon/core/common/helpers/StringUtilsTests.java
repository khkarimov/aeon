package com.ultimatesoftware.aeon.core.common.helpers;

import com.ultimatesoftware.aeon.core.common.exceptions.ContainsWhiteSpaceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTests {

    private String value = null;

    @Test
    void toQuotedAndEscapedString_valueIsNull_throwsException() {

        //Arrange
        this.value = null;

        // Act
        Executable executable = () -> StringUtils.toQuotedAndEscapedString(this.value);

        //Assert
        Exception exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("value", exception.getMessage());
    }

    @Test
    void toQuotedAndEscapedString_valueIsSimpleString_returnsQuotedString() {

        //Arrange
        this.value = "string";

        // Act
        String result = StringUtils.toQuotedAndEscapedString(this.value);

        //Assert
        assertEquals("\"string\"", result);
    }

    @Test
    void toQuotedAndEscapedString_valueIsStringWithSingleBackSlash_returnsQuotedString() {

        //Arrange
        this.value = "st\\ring";

        // Act
        String result = StringUtils.toQuotedAndEscapedString(this.value);

        //Assert
        assertEquals("\"st\\\\ring\"", result);
    }

    @Test
    void toQuotedAndEscapedString_valueIsStringWithEscapedSingleQuote_returnsQuotedString() {

        //Arrange
        this.value = "st\\'ring";

        // Act
        String result = StringUtils.toQuotedAndEscapedString(this.value);

        //Assert
        assertEquals("\"st\\'ring\"", result);
    }

    @Test
    void toQuotedAndEscapedString_valueIsStringWithDoubleEscapedSingleQuote_returnsQuotedString() {

        //Arrange
        this.value = "st\\\\'ring";

        // Act
        String result = StringUtils.toQuotedAndEscapedString(this.value);

        //Assert
        assertEquals("\"st\\\\'ring\"", result);
    }

    @Test
    void toQuotedAndEscapedString_valueIsStringWithDoubleQuote_returnsQuotedString() {

        //Arrange
        this.value = "st\"ring";

        // Act
        String result = StringUtils.toQuotedAndEscapedString(this.value);

        //Assert
        assertEquals("\"st\\\"ring\"", result);
    }

    @Test
    void toQuotedAndEscapedString_valueIsStringWithDoubleQuoteAndEndsWithBackSlash_returnsQuotedString() {

        //Arrange
        this.value = "st\"ring\\";

        // Act
        String result = StringUtils.toQuotedAndEscapedString(this.value);

        //Assert
        assertEquals("\"st\\\"ring\\\\\"", result);
    }

    @Test
    void toQuotedAndEscapedString_valueIsStringWithDoubleQuoteAndEndsWithQuote_returnsQuotedString() {

        //Arrange
        this.value = "st\"ring\"";

        // Act
        String result = StringUtils.toQuotedAndEscapedString(this.value);

        //Assert
        assertEquals("\"st\\\"ring\\\"\"", result);
    }

    @Test
    void normalizeSpacing_valueIsNull_throwsException() {

        //Arrange
        this.value = null;

        // Act
        Executable executable = () -> StringUtils.normalizeSpacing(this.value);

        //Assert
        Exception exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("value", exception.getMessage());
    }

    @Test
    void normalizeSpacing_valueIsNotNull_convertsLineBreaksAndSpaces() {

        //Arrange
        this.value = "\rtest\rline2\r\ntest2 test2  test4\u00a0test5\u200etest7\u200ftest8\u201ftest9";
        String expectedValue = "\ntest\nline2\ntest2 test2  test4 test5test7test8test9";

        // Act
        String result = StringUtils.normalizeSpacing(this.value);

        //Assert
        assertEquals(expectedValue, result);
    }

    @Test
    void normalizeSpacing_valueIsNotNullAndLastCharacterIsReturnLineBreak_convertsLineBreaksAndSpaces() {

        //Arrange
        this.value = "\rtest\rline2\r\ntest2 test2  test4\u00a0test5\u200etest7\u200ftest8\u201ftest9\r";
        String expectedValue = "\ntest\nline2\ntest2 test2  test4 test5test7test8test9\n";

        // Act
        String result = StringUtils.normalizeSpacing(this.value);

        //Assert
        assertEquals(expectedValue, result);
    }

    @Test
    void minimizeWhiteSpace_valueIsNull_throwsException() {

        //Arrange
        this.value = null;

        // Act
        Executable executable = () -> StringUtils.minimizeWhiteSpace(this.value);

        //Assert
        Exception exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("value", exception.getMessage());
    }

    @Test
    void minimizeWhiteSpace_valueContainsMultipleSimpleSpaces_reducesSpaces() {

        //Arrange
        this.value = "test2    test2";

        // Act
        String result = StringUtils.minimizeWhiteSpace(this.value);

        //Assert
        assertEquals("test2 test2", result);
    }

    @Test
    void minimizeWhiteSpace_valueContainsMultipleDifferentTypesOfSpacesStartingWithSimpleSpace_reducesSpaces() {

        //Arrange
        this.value = "test2 \t\r\n\ftest2";

        // Act
        String result = StringUtils.minimizeWhiteSpace(this.value);

        //Assert
        assertEquals("test2 test2", result);
    }

    @Test
    void minimizeWhiteSpace_valueContainsMultipleDifferentTypesOfSpacesStartingWithTab_reducesSpaces() {

        //Arrange
        this.value = "test2\t\r\n\f test2";

        // Act
        String result = StringUtils.minimizeWhiteSpace(this.value);

        //Assert
        assertEquals("test2 test2", result);
    }

    @Test
    void minimizeWhiteSpace_valueContainsMultipleDifferentTypesOfSpacesStartingWithReturnLineBreak_reducesSpaces() {

        //Arrange
        this.value = "test2\r\n\f \ttest2";

        // Act
        String result = StringUtils.minimizeWhiteSpace(this.value);

        //Assert
        assertEquals("test2 test2", result);
    }

    @Test
    void minimizeWhiteSpace_valueContainsMultipleDifferentTypesOfSpacesStartingWithLineBreak_reducesSpaces() {

        //Arrange
        this.value = "test2\n\f \t\rtest2";

        // Act
        String result = StringUtils.minimizeWhiteSpace(this.value);

        //Assert
        assertEquals("test2 test2", result);
    }

    @Test
    void minimizeWhiteSpace_valueContainsMultipleDifferentTypesOfSpacesStartingWithFormFeed_reducesSpaces() {

        //Arrange
        this.value = "test2\f \t\r\ntest2";

        // Act
        String result = StringUtils.minimizeWhiteSpace(this.value);

        //Assert
        assertEquals("test2 test2", result);
    }

    @Test
    void isNot_valueIsNull_throwsException() {

        //Arrange
        this.value = null;

        // Act
        Executable executable = () -> StringUtils.isNot(this.value, "");

        //Assert
        Exception exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("value", exception.getMessage());
    }

    @Test
    void isNot_expectedValueIsNull_throwsException() {

        //Arrange
        this.value = null;

        // Act
        Executable executable = () -> StringUtils.isNot("", this.value);

        //Assert
        Exception exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("expectedValue", exception.getMessage());
    }

    @Test
    void isNot_stringsWithDifferentTypesOfSpacesButOtherwiseEqual_returnsFalse() {

        //Arrange

        // Act
        boolean result = StringUtils.isNot("test2\u00a0\u00a0test2", "test2  test2");

        //Assert
        assertFalse(result);
    }

    @Test
    void isNot_unequalStrings_returnsTrue() {

        //Arrange

        // Act
        boolean result = StringUtils.isNot("test2", "test");

        //Assert
        assertTrue(result);
    }

    @Test
    void like_valueIsNull_throwsException() {

        //Arrange
        this.value = null;

        // Act
        Executable executable = () -> StringUtils.like(this.value, "");

        //Assert
        Exception exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("value", exception.getMessage());
    }

    @Test
    void like_expectedValueIsNull_throwsException() {

        //Arrange
        this.value = null;

        // Act
        Executable executable = () -> StringUtils.like("", this.value);

        //Assert
        Exception exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("expectedValue", exception.getMessage());
    }

    @Test
    void like_expectedValueIsEmpty_throwsException() {

        //Arrange

        // Act
        Executable executable = () -> StringUtils.like("", "");

        //Assert
        Exception exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("zero length expected value", exception.getMessage());
    }

    @Test
    void like_stringsAlikeWithDifferentTypesOfSpaces_returnsTrue() {

        //Arrange

        // Act
        boolean result = StringUtils.like("eetest2\u00a0\u00a0test2ee", "test2  test2");

        //Assert
        assertTrue(result);
    }

    @Test
    void like_notAlikeStrings_returnsFalse() {

        //Arrange

        // Act
        boolean result = StringUtils.like("test2", "te2");

        //Assert
        assertFalse(result);
    }

    @Test
    void like_alikeStringsButCaseSensitiveCheck_returnsFalse() {

        //Arrange

        // Act
        boolean result = StringUtils.like("test2", "tEst2");

        //Assert
        assertFalse(result);
    }

    @Test
    void like_alikeStringsWithCaseInsensitiveCheck_returnsTrue() {

        //Arrange

        // Act
        boolean result = StringUtils.like("eetest2\u00a0\u00a0test2ee", "eetest2  test2EE", false);

        //Assert
        assertTrue(result);
    }

    @Test
    void containsWord_valueIsNull_throwsException() {

        //Arrange
        this.value = null;

        // Act
        Executable executable = () -> StringUtils.containsWord(this.value, "");

        //Assert
        Exception exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("value", exception.getMessage());
    }

    @Test
    void containsWord_wordIsNull_throwsException() {

        //Arrange
        this.value = null;

        // Act
        Executable executable = () -> StringUtils.containsWord("", this.value);

        //Assert
        Exception exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("word", exception.getMessage());
    }

    @Test
    void containsWord_wordIsIncluded_returnsTrue() {

        //Arrange
        this.value = null;

        // Act
        boolean result = StringUtils.containsWord("word1  \tword2 word3", "word2");

        //Assert
        assertTrue(result);
    }

    @Test
    void containsWord_wordIsAtTheBeginning_returnsTrue() {

        //Arrange
        this.value = null;

        // Act
        boolean result = StringUtils.containsWord("word1  \tword2 word3", "word1");

        //Assert
        assertTrue(result);
    }

    @Test
    void containsWord_wordIsAtTheEnd_returnsTrue() {

        //Arrange
        this.value = null;

        // Act
        boolean result = StringUtils.containsWord("word1  \tword2 word3", "word3");

        //Assert
        assertTrue(result);
    }

    @Test
    void containsWord_wordIsNotIncluded_returnsFalse() {

        //Arrange
        this.value = null;

        // Act
        boolean result = StringUtils.containsWord("wor1  \tword2 word3", "word");

        //Assert
        assertFalse(result);
    }

    @Test
    void assertNoWhiteSpace_valueIsNull_throwsException() {

        //Arrange
        this.value = null;

        // Act
        Executable executable = () -> StringUtils.assertNoWhiteSpace(this.value);

        //Assert
        Exception exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("value", exception.getMessage());
    }

    @Test
    void assertNoWhiteSpace_containsWhiteSpace_throwsException() {

        //Arrange
        this.value = "test2 test2";

        // Act
        Executable executable = () -> StringUtils.assertNoWhiteSpace(this.value);

        //Assert
        Exception exception = assertThrows(ContainsWhiteSpaceException.class, executable);
        assertEquals("String \"test2 test2\" cannot contain whitespace characters.", exception.getMessage());
    }

    @Test
    void assertNoWhiteSpace_doesNotContainWhiteSpace_returnsValue() {

        //Arrange
        this.value = "test2test2";

        // Act
        String result = StringUtils.assertNoWhiteSpace(this.value);

        //Assert
        assertEquals("test2test2", result);
    }

    @Test
    void isBlank_valueIsNull_returnsTrue() {

        //Arrange
        this.value = null;

        // Act
        boolean result = StringUtils.isBlank(this.value);

        //Assert
        assertTrue(result);
    }

    @Test
    void isBlank_valueIsEmpty_returnsTrue() {

        //Arrange
        this.value = "";

        // Act
        boolean result = StringUtils.isBlank(this.value);

        //Assert
        assertTrue(result);
    }

    @Test
    void isBlank_valueIsOnlyWhiteSpace_returnsTrue() {

        //Arrange
        this.value = "   ";

        // Act
        boolean result = StringUtils.isBlank(this.value);

        //Assert
        assertTrue(result);
    }

    @Test
    void isBlank_valueIsOnlyWhiteSpaceLike_returnsTrue() {

        //Arrange
        this.value = "   \t\n";

        // Act
        boolean result = StringUtils.isBlank(this.value);

        //Assert
        assertTrue(result);
    }

    @Test
    void isBlank_valueContainsNonWhiteSpaceCharacter_returnsFalse() {

        //Arrange
        this.value = "  c  ";

        // Act
        boolean result = StringUtils.isBlank(this.value);

        //Assert
        assertFalse(result);
    }

    @Test
    void isNotBlank_valueIsNull_returnsFalse() {

        //Arrange
        this.value = null;

        // Act
        boolean result = StringUtils.isNotBlank(this.value);

        //Assert
        assertFalse(result);
    }

    @Test
    void isNotBlank_valueIsEmpty_returnsFalse() {

        //Arrange
        this.value = "";

        // Act
        boolean result = StringUtils.isNotBlank(this.value);

        //Assert
        assertFalse(result);
    }

    @Test
    void isNotBlank_valueIsOnlyWhiteSpace_returnsFalse() {

        //Arrange
        this.value = "   ";

        // Act
        boolean result = StringUtils.isNotBlank(this.value);

        //Assert
        assertFalse(result);
    }

    @Test
    void isNotBlank_valueIsOnlyWhiteSpaceLike_returnsFalse() {

        //Arrange
        this.value = "   \t\n";

        // Act
        boolean result = StringUtils.isNotBlank(this.value);

        //Assert
        assertFalse(result);
    }

    @Test
    void isNotBlank_valueContainsNonWhiteSpaceCharacter_returnsTrue() {

        //Arrange
        this.value = "  c  ";

        // Act
        boolean result = StringUtils.isNotBlank(this.value);

        //Assert
        assertTrue(result);
    }
}

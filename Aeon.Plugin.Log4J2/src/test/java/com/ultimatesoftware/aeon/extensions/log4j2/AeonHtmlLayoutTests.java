package com.ultimatesoftware.aeon.extensions.log4j2;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class AeonHtmlLayoutTests {

    // Variables
    private AeonHtmlLayout layoutObject;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")
            .withZone(ZoneId.systemDefault());

    // Mocks
    @Mock
    private LogEvent event;

    @Mock
    private Message message;

    // Setup Methods
    @BeforeEach
    void setup() {
        layoutObject = AeonHtmlLayout.createDefaultLayout();
    }

    //Test Methods
    @Test
    void toSerializable_WhenLevelIsDebug_ReturnsExpectedString() {

        // Arrange
        when(event.getTimeMillis()).thenReturn(20L);
        when(event.getLevel()).thenReturn(Level.DEBUG);
        when(event.getThreadName()).thenReturn("TestThread");
        when(event.getLoggerName()).thenReturn("TestLogger");
        when(event.getMessage()).thenReturn(message);
        when(message.getFormattedMessage()).thenReturn("TestMessage");

        String expected = "\n<tr>\n" +
                "<td>" + dateTimeFormatter.format(Instant.ofEpochMilli(event.getTimeMillis())) + "</td>\n" +
                "<td title=\"TestThread thread\">TestThread</td>\n" +
                "<td title=\"Level\"><font color=\"#339933\">DEBUG</font></td>\n" +
                "<td title=\"TestLogger logger\">TestLogger</td>\n" +
                "<td title=\"Message\">TestMessage</td>\n" +
                "</tr>\n";

        // Act
        String serial = layoutObject.toSerializable(event);

        // Assert
        assertEquals(expected, serial);
    }

    @Test
    void toSerializable_WhenLevelIsWarn_ReturnsExpectedString() {

        // Arrange
        when(event.getTimeMillis()).thenReturn(20L);
        when(event.getLevel()).thenReturn(Level.WARN);
        when(event.getThreadName()).thenReturn("TestThread");
        when(event.getLoggerName()).thenReturn("TestLogger");
        when(event.getMessage()).thenReturn(message);
        when(message.getFormattedMessage()).thenReturn("TestMessage");

        String expected = "\n<tr>\n" +
                "<td>" + dateTimeFormatter.format(Instant.ofEpochMilli(event.getTimeMillis())) + "</td>\n" +
                "<td title=\"TestThread thread\">TestThread</td>\n" +
                "<td title=\"Level\"><font color=\"#993300\"><strong>WARN</strong></font></td>\n" +
                "<td title=\"TestLogger logger\">TestLogger</td>\n" +
                "<td title=\"Message\">TestMessage</td>\n" +
                "</tr>\n";

        // Act
        String serial = layoutObject.toSerializable(event);

        // Assert
        assertEquals(expected, serial);
    }

    @Test
    void toSerializable_WhenLevelIsOther_ReturnsExpectedString() {

        // Arrange
        when(event.getTimeMillis()).thenReturn(20L);
        when(event.getLevel()).thenReturn(Level.ALL);
        when(event.getThreadName()).thenReturn("TestThread");
        when(event.getLoggerName()).thenReturn("TestLogger");
        when(event.getMessage()).thenReturn(message);
        when(message.getFormattedMessage()).thenReturn("TestMessage");

        String expected = "\n<tr>\n" +
                "<td>" + dateTimeFormatter.format(Instant.ofEpochMilli(event.getTimeMillis())) + "</td>\n" +
                "<td title=\"TestThread thread\">TestThread</td>\n" +
                "<td title=\"Level\">ALL</td>\n" +
                "<td title=\"TestLogger logger\">TestLogger</td>\n" +
                "<td title=\"Message\">TestMessage</td>\n" +
                "</tr>\n";

        // Act
        String serial = layoutObject.toSerializable(event);

        // Assert
        assertEquals(expected, serial);
    }

    @Test
    void toSerializable_WhenLoggerIsEmpty_ReturnsExpectedString() {

        // Arrange
        when(event.getTimeMillis()).thenReturn(20L);
        when(event.getLevel()).thenReturn(Level.ALL);
        when(event.getThreadName()).thenReturn("TestThread");
        when(event.getLoggerName()).thenReturn("");
        when(event.getMessage()).thenReturn(message);
        when(message.getFormattedMessage()).thenReturn("TestMessage");

        String expected = "\n<tr>\n" +
                "<td>" + dateTimeFormatter.format(Instant.ofEpochMilli(event.getTimeMillis())) + "</td>\n" +
                "<td title=\"TestThread thread\">TestThread</td>\n" +
                "<td title=\"Level\">ALL</td>\n" +
                "<td title=\"root logger\">root</td>\n" +
                "<td title=\"Message\">TestMessage</td>\n" +
                "</tr>\n";

        // Act
        String serial = layoutObject.toSerializable(event);

        // Assert
        assertEquals(expected, serial);
    }

    @Test
    void toSerializable_WhenThrowableExists_ReturnsExpectedString() throws InterruptedException {

        // Arrange
        Throwable thrown = mock(Throwable.class);

        when(event.getTimeMillis()).thenReturn(20L);
        when(event.getLevel()).thenReturn(Level.ALL);
        when(event.getThreadName()).thenReturn("TestThread");
        when(event.getLoggerName()).thenReturn("TestLogger");
        when(event.getThrown()).thenReturn(thrown);
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            PrintWriter pw = (PrintWriter) args[0];
            pw.print("StackTrace\nStackTrace\nStackTrace\n");
            throw new RuntimeException();
        }).when(thrown).printStackTrace(any(PrintWriter.class));
        when(event.getMessage()).thenReturn(message);
        when(message.getFormattedMessage()).thenReturn("TestMessage");

        String expected = "\n" +
                "<tr>\n" +
                "<td>" + dateTimeFormatter.format(Instant.ofEpochMilli(event.getTimeMillis())) + "</td>\n" +
                "<td title=\"TestThread thread\">TestThread</td>\n" +
                "<td title=\"Level\">ALL</td>\n" +
                "<td title=\"TestLogger logger\">TestLogger</td>\n" +
                "<td title=\"Message\">TestMessage</td>\n" +
                "</tr>\n" +
                "<tr><td bgcolor=\"#993300\" style=\"color:White; font-size : small;\" colspan=\"6\">StackTrace\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;StackTrace\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;StackTrace\n" +
                "</td></tr>\n";

        // Act
        String serial = layoutObject.toSerializable(event);

        // Assert
        assertEquals(expected, serial);
    }

    @Test
    void toSerializable_WhenLocationInfoIsTrue_ReturnsExpectedString() {

        // Arrange
        layoutObject = AeonHtmlLayout.createLayout(true, "Log4j Log Messages",
                null, StandardCharsets.UTF_8, "", "arial,sans-serif");

        when(event.getTimeMillis()).thenReturn(20L);
        when(event.getSource()).thenReturn(new StackTraceElement("", "", "FileName", 0));
        when(event.getLevel()).thenReturn(Level.ALL);
        when(event.getThreadName()).thenReturn("TestThread");
        when(event.getLoggerName()).thenReturn("TestLogger");
        when(event.getMessage()).thenReturn(message);
        when(message.getFormattedMessage()).thenReturn("TestMessage");

        String expected = "\n" +
                "<tr>\n" +
                "<td>" + dateTimeFormatter.format(Instant.ofEpochMilli(event.getTimeMillis())) + "</td>\n" +
                "<td title=\"TestThread thread\">TestThread</td>\n" +
                "<td title=\"Level\">ALL</td>\n" +
                "<td title=\"TestLogger logger\">TestLogger</td>\n" +
                "<td>FileName:0</td>\n" +
                "<td title=\"Message\">TestMessage</td>\n" +
                "</tr>\n";

        // Act
        String serial = layoutObject.toSerializable(event);

        // Assert
        assertEquals(expected, serial);
    }

    @Test
    void toSerializable_WhenMessageParametersExist_ReturnsExpectedString() throws IOException {

        // Arrange
        BufferedImage image = new BufferedImage(1, 1, 1);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        String data = Base64.getEncoder().encodeToString(baos.toByteArray());

        when(event.getTimeMillis()).thenReturn(20L);
        when(event.getLevel()).thenReturn(Level.ALL);
        when(event.getThreadName()).thenReturn("TestThread");
        when(event.getLoggerName()).thenReturn("TestLogger");
        when(event.getMessage()).thenReturn(message);
        when(message.getParameters()).thenReturn(new Object[]{image});
        when(message.getFormattedMessage()).thenReturn("TestMessage");

        String expected = "\n" +
                "<tr>\n" +
                "<td>" + dateTimeFormatter.format(Instant.ofEpochMilli(event.getTimeMillis())) + "</td>\n" +
                "<td title=\"TestThread thread\">TestThread</td>\n" +
                "<td title=\"Level\">ALL</td>\n" +
                "<td title=\"TestLogger logger\">TestLogger</td>\n" +
                "<td title=\"Message\">TestMessage<img src=\"data:image/png;base64," + data + "\" /></td>\n" +
                "</tr>\n";

        // Act
        String serial = layoutObject.toSerializable(event);

        // Assert
        assertEquals(expected, serial);
    }

    @Test
    void toSerializable_WhenContextStackExists_ReturnsExpectedString() {

        // Arrange
        ThreadContext.ContextStack stack = mock(ThreadContext.ContextStack.class);

        when(event.getTimeMillis()).thenReturn(20L);
        when(event.getLevel()).thenReturn(Level.ALL);
        when(event.getThreadName()).thenReturn("TestThread");
        when(event.getLoggerName()).thenReturn("TestLogger");
        when(event.getMessage()).thenReturn(message);
        when(event.getContextStack()).thenReturn(stack);
        when(stack.toString()).thenReturn("ContextStackString");
        when(message.getFormattedMessage()).thenReturn("TestMessage");

        String expected = "\n" +
                "<tr>\n" +
                "<td>" + dateTimeFormatter.format(Instant.ofEpochMilli(event.getTimeMillis())) + "</td>\n" +
                "<td title=\"TestThread thread\">TestThread</td>\n" +
                "<td title=\"Level\">ALL</td>\n" +
                "<td title=\"TestLogger logger\">TestLogger</td>\n" +
                "<td title=\"Message\">TestMessage</td>\n" +
                "</tr>\n" +
                "<tr><td bgcolor=\"#EEEEEE\" style=\"font-size : small;\" colspan=\"6\" title=\"Nested Diagnostic Context\">NDC: ContextStackString</td></tr>\n";

        // Act
        String serial = layoutObject.toSerializable(event);

        // Assert
        assertEquals(expected, serial);
    }

    @Test
    void toSerializable_WhenContextDataExists_ReturnsExpectedString() {

        // Arrange
        ReadOnlyStringMap data = mock(ReadOnlyStringMap.class);

        when(event.getTimeMillis()).thenReturn(20L);
        when(event.getLevel()).thenReturn(Level.ALL);
        when(event.getThreadName()).thenReturn("TestThread");
        when(event.getLoggerName()).thenReturn("TestLogger");
        when(event.getMessage()).thenReturn(message);
        when(event.getContextData()).thenReturn(data);
        when(data.toString()).thenReturn("ContextDataString");
        when(message.getFormattedMessage()).thenReturn("TestMessage");

        String expected = "\n" +
                "<tr>\n" +
                "<td>" + dateTimeFormatter.format(Instant.ofEpochMilli(event.getTimeMillis())) + "</td>\n" +
                "<td title=\"TestThread thread\">TestThread</td>\n" +
                "<td title=\"Level\">ALL</td>\n" +
                "<td title=\"TestLogger logger\">TestLogger</td>\n" +
                "<td title=\"Message\">TestMessage</td>\n" +
                "</tr>\n" +
                "<tr><td bgcolor=\"#EEEEEE\" style=\"font-size : small;\" colspan=\"6\" title=\"Mapped Diagnostic Context\">MDC: ContextDataString</td></tr>\n";

        // Act
        String serial = layoutObject.toSerializable(event);

        // Assert
        assertEquals(expected, serial);
    }

    @Test
    void getHeader_WhenLocationInfoDoesNotExist_ReturnsExpectedString() {

        // Arrange
        String expected = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\"/>\n" +
                "<title>Log4j Log Messages</title>\n" +
                "<style type=\"text/css\">\n" +
                "<!--\n" +
                "body, table {font-family:arial,sans-serif; font-size: medium;}\n" +
                "th {background: #336699; color: #FFFFFF; text-align: left;}\n" +
                "-->\n" +
                "</style>\n" +
                "</head>\n" +
                "<body bgcolor=\"#FFFFFF\" topmargin=\"6\" leftmargin=\"6\">\n" +
                "<hr size=\"1\" noshade=\"noshade\">\n" +
                "Log session start time " + new java.util.Date() + "<br>\n" +
                "<br>\n" +
                "<table cellspacing=\"0\" cellpadding=\"4\" border=\"1\" bordercolor=\"#224466\" width=\"100%\">\n" +
                "<tr>\n" +
                "<th>Time</th>\n" +
                "<th>Thread</th>\n" +
                "<th>Level</th>\n" +
                "<th>Logger</th>\n" +
                "<th>Message</th>\n" +
                "</tr>\n";

        // Act
        byte[] output = layoutObject.getHeader();
        String decoded = new String(output, StandardCharsets.UTF_8);

        // Assert
        assertEquals(expected, decoded);
    }

    @Test
    void getHeader_WhenLocationInfoExists_ReturnsExpectedString() {

        // Arrange
        layoutObject = layoutObject.newBuilder()
                .withCharset(StandardCharsets.UTF_8)
                .withContentType(null)
                .withFontName("arial,sans-serif")
                .withLocationInfo(true)
                .withTitle("Log4j Log Messages")
                .withFontSize(AeonHtmlLayout.FontSize.LARGE)
                .build();

        String expected = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\"/>\n" +
                "<title>Log4j Log Messages</title>\n" +
                "<style type=\"text/css\">\n" +
                "<!--\n" +
                "body, table {font-family:arial,sans-serif; font-size: x-large;}\n" +
                "th {background: #336699; color: #FFFFFF; text-align: left;}\n" +
                "-->\n" +
                "</style>\n" +
                "</head>\n" +
                "<body bgcolor=\"#FFFFFF\" topmargin=\"6\" leftmargin=\"6\">\n" +
                "<hr size=\"1\" noshade=\"noshade\">\n" +
                "Log session start time " + new java.util.Date() + "<br>\n" +
                "<br>\n" +
                "<table cellspacing=\"0\" cellpadding=\"4\" border=\"1\" bordercolor=\"#224466\" width=\"100%\">\n" +
                "<tr>\n" +
                "<th>Time</th>\n" +
                "<th>Thread</th>\n" +
                "<th>Level</th>\n" +
                "<th>Logger</th>\n" +
                "<th>File:Line</th>\n" +
                "<th>Message</th>\n" +
                "</tr>\n";

        // Act
        byte[] output = layoutObject.getHeader();
        String decoded = new String(output, StandardCharsets.UTF_8);

        // Assert
        assertEquals(expected, decoded);
    }

    @Test
    void getFooter_WhenCalled_ReturnsEmptyByteArray() {

        // Arrange
        byte[] expected = new byte[]{};

        // Act
        byte[] output = layoutObject.getFooter();

        // Assert
        assertArrayEquals(expected, output);
    }

    @Test
    void getContentType_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        String contentType = layoutObject.getContentType();

        // Assert
        assertEquals("text/html; charset=UTF-8", contentType);
    }

    @Test
    void getFontSize_WhenValidSizePassedIn_ReturnsFontSizeOfTypeString() {

        // Arrange

        // Act
        AeonHtmlLayout.FontSize size = AeonHtmlLayout.FontSize.getFontSize("large");

        // Assert
        assertEquals("LARGE", size.toString());
    }

    @Test
    void getFontSize_WhenInvalidStringPassedIn_ReturnsFontSizeOfSmall() {

        // Arrange

        // Act
        AeonHtmlLayout.FontSize size = AeonHtmlLayout.FontSize.getFontSize("invalid");

        // Assert
        assertEquals("SMALL", size.toString());
    }

    @Test
    void getFontSize_WhenNoParametersPassed_ReturnsCurrentFontSize() {

        // Arrange
        AeonHtmlLayout.FontSize current = AeonHtmlLayout.FontSize.SMALLER;

        // Act
        String size = current.getFontSize();

        // Assert
        assertEquals("smaller", size);
    }

    @Test
    void larger_WhenFontSizeIsLessThanXXLARGE_ReturnsLargerFontSize() {

        // Arrange
        AeonHtmlLayout.FontSize current = AeonHtmlLayout.FontSize.SMALLER;

        // Act
        AeonHtmlLayout.FontSize size = current.larger();

        // Assert
        assertEquals("XXSMALL", size.toString());
    }

    @Test
    void larger_WhenFontSizeIsXXLARGE_ReturnsFontSize() {

        // Arrange
        AeonHtmlLayout.FontSize current = AeonHtmlLayout.FontSize.XXLARGE;

        // Act
        AeonHtmlLayout.FontSize size = current.larger();

        // Assert
        assertEquals("XXLARGE", size.toString());
    }

}

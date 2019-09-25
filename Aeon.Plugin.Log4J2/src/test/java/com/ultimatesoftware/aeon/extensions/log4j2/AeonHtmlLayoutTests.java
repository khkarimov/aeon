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

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class AeonHtmlLayoutTests {

    // Variables
    private AeonHtmlLayout layoutObject;

    // Mocks
    @Mock
    private LogEvent event;

    @Mock
    private Message message;

    // Setup Methods
    @BeforeEach
    void setup() {
        layoutObject = layoutObject.createDefaultLayout();
    }

    //Test Methods
    @Test
    void toSerializable_LevelDebug() {

        // Arrange
        when(event.getTimeMillis()).thenReturn(20L);
        when(event.getLevel()).thenReturn(Level.DEBUG);
        when(event.getThreadName()).thenReturn("TestThread");
        when(event.getLoggerName()).thenReturn("TestLogger");
        when(event.getMessage()).thenReturn(message);
        when(message.getFormattedMessage()).thenReturn("TestMessage");

        String expected = "\n<tr>\n" +
                "<td>19:00:00.020</td>\n" +
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
    void toSerializable_LevelWarn() {

        // Arrange
        when(event.getTimeMillis()).thenReturn(20L);
        when(event.getLevel()).thenReturn(Level.WARN);
        when(event.getThreadName()).thenReturn("TestThread");
        when(event.getLoggerName()).thenReturn("TestLogger");
        when(event.getMessage()).thenReturn(message);
        when(message.getFormattedMessage()).thenReturn("TestMessage");

        String expected = "\n<tr>\n" +
                "<td>19:00:00.020</td>\n" +
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
    void toSerializable_LevelOther() {

        // Arrange
        when(event.getTimeMillis()).thenReturn(20L);
        when(event.getLevel()).thenReturn(Level.ALL);
        when(event.getThreadName()).thenReturn("TestThread");
        when(event.getLoggerName()).thenReturn("TestLogger");
        when(event.getMessage()).thenReturn(message);
        when(message.getFormattedMessage()).thenReturn("TestMessage");

        String expected = "\n<tr>\n" +
                "<td>19:00:00.020</td>\n" +
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
    void toSerializable_EmptyLogger() {

        // Arrange
        when(event.getTimeMillis()).thenReturn(20L);
        when(event.getLevel()).thenReturn(Level.ALL);
        when(event.getThreadName()).thenReturn("TestThread");
        when(event.getLoggerName()).thenReturn("");
        when(event.getMessage()).thenReturn(message);
        when(message.getFormattedMessage()).thenReturn("TestMessage");

        String expected = "\n<tr>\n" +
                "<td>19:00:00.020</td>\n" +
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
    void toSerializable_ThrowableNotNull() throws InterruptedException {

        // Arrange
        when(event.getTimeMillis()).thenReturn(20L);
        when(event.getLevel()).thenReturn(Level.ALL);
        when(event.getThreadName()).thenReturn("TestThread");
        when(event.getLoggerName()).thenReturn("TestLogger");
        when(event.getThrown()).thenReturn(new Throwable("ThrowMessage"));
        when(event.getMessage()).thenReturn(message);
        when(message.getFormattedMessage()).thenReturn("TestMessage");

        String expected = "\n" +
                "<tr>\n" +
                "<td>19:00:00.020</td>\n" +
                "<td title=\"TestThread thread\">TestThread</td>\n" +
                "<td title=\"Level\">ALL</td>\n" +
                "<td title=\"TestLogger logger\">TestLogger</td>\n" +
                "<td title=\"Message\">TestMessage</td>\n" +
                "</tr>\n" +
                "<tr><td bgcolor=\"#993300\" style=\"color:White; font-size : small;\" colspan=\"6\">java.lang.Throwable: ThrowMessage\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat com.ultimatesoftware.aeon.extensions.log4j2.AeonHtmlLayoutTests.toSerializable_ThrowableNotNull(AeonHtmlLayoutTests.java:157)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.lang.reflect.Method.invoke(Method.java:498)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.commons.util.ReflectionUtils.invokeMethod(ReflectionUtils.java:513)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.jupiter.engine.execution.ExecutableInvoker.invoke(ExecutableInvoker.java:115)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.lambda$invokeTestMethod$6(TestMethodTestDescriptor.java:170)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.jupiter.engine.execution.ThrowableCollector.execute(ThrowableCollector.java:40)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.invokeTestMethod(TestMethodTestDescriptor.java:166)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:113)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:58)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor$NodeExecutor.lambda$executeRecursively$3(HierarchicalTestExecutor.java:113)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.engine.support.hierarchical.SingleTestExecutor.executeSafely(SingleTestExecutor.java:66)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor$NodeExecutor.executeRecursively(HierarchicalTestExecutor.java:108)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor$NodeExecutor.execute(HierarchicalTestExecutor.java:79)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor$NodeExecutor.lambda$executeRecursively$2(HierarchicalTestExecutor.java:121)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:175)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.Iterator.forEachRemaining(Iterator.java:116)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.Spliterators$IteratorSpliterator.forEachRemaining(Spliterators.java:1801)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:482)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:472)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:485)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor$NodeExecutor.lambda$executeRecursively$3(HierarchicalTestExecutor.java:121)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.engine.support.hierarchical.SingleTestExecutor.executeSafely(SingleTestExecutor.java:66)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor$NodeExecutor.executeRecursively(HierarchicalTestExecutor.java:108)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor$NodeExecutor.execute(HierarchicalTestExecutor.java:79)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor$NodeExecutor.lambda$executeRecursively$2(HierarchicalTestExecutor.java:121)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:175)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.Iterator.forEachRemaining(Iterator.java:116)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.Spliterators$IteratorSpliterator.forEachRemaining(Spliterators.java:1801)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:482)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:472)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:485)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor$NodeExecutor.lambda$executeRecursively$3(HierarchicalTestExecutor.java:121)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.engine.support.hierarchical.SingleTestExecutor.executeSafely(SingleTestExecutor.java:66)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor$NodeExecutor.executeRecursively(HierarchicalTestExecutor.java:108)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor$NodeExecutor.execute(HierarchicalTestExecutor.java:79)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor.execute(HierarchicalTestExecutor.java:55)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine.execute(HierarchicalTestEngine.java:43)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:170)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:154)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:90)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.gradle.api.internal.tasks.testing.junitplatform.JUnitPlatformTestClassProcessor$CollectAllTestClassesExecutor.processAllTestClasses(JUnitPlatformTestClassProcessor.java:92)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.gradle.api.internal.tasks.testing.junitplatform.JUnitPlatformTestClassProcessor$CollectAllTestClassesExecutor.access$100(JUnitPlatformTestClassProcessor.java:77)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.gradle.api.internal.tasks.testing.junitplatform.JUnitPlatformTestClassProcessor.stop(JUnitPlatformTestClassProcessor.java:73)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.gradle.api.internal.tasks.testing.SuiteTestClassProcessor.stop(SuiteTestClassProcessor.java:61)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.lang.reflect.Method.invoke(Method.java:498)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:35)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:24)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.gradle.internal.dispatch.ContextClassLoaderDispatch.dispatch(ContextClassLoaderDispatch.java:32)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.gradle.internal.dispatch.ProxyDispatchAdapter$DispatchingInvocationHandler.invoke(ProxyDispatchAdapter.java:93)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat com.sun.proxy.$Proxy2.stop(Unknown Source)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.gradle.api.internal.tasks.testing.worker.TestWorker.stop(TestWorker.java:131)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.lang.reflect.Method.invoke(Method.java:498)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:35)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:24)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.gradle.internal.remote.internal.hub.MessageHubBackedObjectConnection$DispatchWrapper.dispatch(MessageHubBackedObjectConnection.java:155)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.gradle.internal.remote.internal.hub.MessageHubBackedObjectConnection$DispatchWrapper.dispatch(MessageHubBackedObjectConnection.java:137)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.gradle.internal.remote.internal.hub.MessageHub$Handler.run(MessageHub.java:404)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.gradle.internal.concurrent.ExecutorPolicy$CatchAndRecordFailures.onExecute(ExecutorPolicy.java:63)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.gradle.internal.concurrent.ManagedExecutorImpl$1.run(ManagedExecutorImpl.java:46)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat org.gradle.internal.concurrent.ThreadFactoryImpl$ManagedThreadRunnable.run(ThreadFactoryImpl.java:55)\n" +
                "<br />&nbsp;&nbsp;&nbsp;&nbsp;\tat java.lang.Thread.run(Thread.java:748)\n" +
                "</td></tr>\n";

        // Act
        String serial = layoutObject.toSerializable(event);
        System.out.println(serial);

        // Assert
        assertEquals(expected, serial);
    }

    @Test
    void toSerializable_LocationInfoTrue() {

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
                "<td>19:00:00.020</td>\n" +
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
    void toSerializable_ParametersExist() {

        // Arrange
        BufferedImage image = new BufferedImage(1, 1, 1);

        when(event.getTimeMillis()).thenReturn(20L);
        when(event.getLevel()).thenReturn(Level.ALL);
        when(event.getThreadName()).thenReturn("TestThread");
        when(event.getLoggerName()).thenReturn("TestLogger");
        when(event.getMessage()).thenReturn(message);
        when(message.getParameters()).thenReturn(new Object[]{image});
        when(message.getFormattedMessage()).thenReturn("TestMessage");

        String expected = "\n" +
                "<tr>\n" +
                "<td>19:00:00.020</td>\n" +
                "<td title=\"TestThread thread\">TestThread</td>\n" +
                "<td title=\"Level\">ALL</td>\n" +
                "<td title=\"TestLogger logger\">TestLogger</td>\n" +
                "<td title=\"Message\">TestMessage<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAIAAACQd1PeAAAADElEQVR42mNgYGAAAAAEAAHI6uv5AAAAAElFTkSuQmCC\" /></td>\n" +
                "</tr>\n";

        // Act
        String serial = layoutObject.toSerializable(event);

        // Assert
        assertEquals(expected, serial);
    }

    @Test
    void toSerializable_ContextStackExists() {

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
                "<td>19:00:00.020</td>\n" +
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
    void toSerializable_ContextDataExists() {

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
                "<td>19:00:00.020</td>\n" +
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
    void getHeader() {

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
    void getHeader_LocationInfoTrue() {

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
    void getFooter() {

        // Arrange
        byte[] expected = new byte[]{};

        // Act
        byte[] output = layoutObject.getFooter();

        // Assert
        assertArrayEquals(expected, output);
    }

    @Test
    void getContentType() {

        // Arrange

        // Act
        String contentType = layoutObject.getContentType();

        // Assert
        assertEquals("text/html; charset=UTF-8", contentType);
    }

    @Test
    void getFontSize() {

        // Arrange

        // Act
        AeonHtmlLayout.FontSize size = AeonHtmlLayout.FontSize.getFontSize("large");

        // Assert
        assertEquals("LARGE", size.toString());
    }

}

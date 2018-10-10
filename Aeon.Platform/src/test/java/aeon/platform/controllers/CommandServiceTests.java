package aeon.platform.controllers;

import aeon.platform.services.CommandService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.Constructor;

import static org.mockito.Mockito.*;

public class CommandServiceTests {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule public ExpectedException expectedException = ExpectedException.none();

    private CommandService commandService;

    @Before
    public void setUp() {
        commandService = new CommandService();
    }

    @Test
    public void createConstructorTest() throws Exception {
        Constructor constructor = commandService.createConstructor("GoToUrlCommand");

        Assert.assertEquals("aeon.core.command.execution.commands.web.GoToUrlCommand", constructor.getName());
    }

    @Test
    public void createConstructorQuitCommandTest() throws Exception {
        Constructor constructor = commandService.createConstructor("QuitCommand");

        Assert.assertEquals("aeon.core.command.execution.commands.QuitCommand", constructor.getName());
    }

    @Test
    public void createConstructorBadInputTest() throws Exception {
        Constructor constructor = null;

        try {
            constructor = commandService.createConstructor("a");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("No valid command entered.", e.getMessage());
        }

        Assert.assertNull(constructor);
    }
}

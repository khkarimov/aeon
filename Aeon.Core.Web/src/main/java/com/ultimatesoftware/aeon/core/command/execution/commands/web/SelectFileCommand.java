package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Selects a file for inputs of type file.
 */
public class SelectFileCommand extends WebControlCommand {

    private String path;

    /**
     * Initializes a new instance of the {@link SelectFileCommand} class.
     *
     * @param selector    The selector.
     * @param initializer The command initializer.
     * @param path        The path of the file to select.
     */
    public SelectFileCommand(IByWeb selector, ICommandInitializer initializer, String path) {
        super(String.format(Locale.getDefault(), Resources.getString("SelectFileCommand_Info"), path), selector, initializer);
        this.path = path;
    }


    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.selectFile(control, path);
    }
}

package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.CommandWithReturn;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.exceptions.ScreenshotException;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * Get screenshot.
 */
public class GetScreenshotCommand extends CommandWithReturn {

    /**
     * Initializes a new instance of the {@link GetScreenshotCommand} class.
     */
    public GetScreenshotCommand() {
        super(Resources.getString("GetScreenshotCommand_Info"));
    }

    @Override
    protected Object commandDelegate(IDriver driver) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write((BufferedImage) driver.getScreenshot(), "png", baos);
        } catch (IOException e) {
            throw new ScreenshotException("Unable to convert screenshot.");
        }

        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}

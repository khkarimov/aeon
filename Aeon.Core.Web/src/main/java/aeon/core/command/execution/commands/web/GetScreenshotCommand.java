package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.common.exceptions.ScreenshotException;
import aeon.core.framework.abstraction.drivers.IDriver;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write((BufferedImage) driver.getScreenshot(), "png", baos);
        } catch (IOException e) {
            throw new ScreenshotException("Unable to convert screenshot.");
        }

        return DatatypeConverter.printBase64Binary(baos.toByteArray());
    }
}

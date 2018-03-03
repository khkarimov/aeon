package aeon.extensions.reporting;

import gui.ava.html.image.generator.HtmlImageGenerator;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

    private static org.apache.logging.log4j.Logger log = LogManager.getLogger(Utils.class);

    public static File htmlToPngFile(String html, String filePath) {
        log.info("Converting HTML file to Png");
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        File file = new File(filePath);
        imageGenerator.loadHtml(html);
        imageGenerator.saveAsImage(file);

        return file;
    }

    public static void deleteFiles(String filePath) {
        log.trace("Deleting file: " + filePath);
        Path outFilePath = Paths.get(filePath);
        try {
            Files.deleteIfExists(outFilePath);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static String getResourcesPath() {
        String resourcePath;
        resourcePath = System.getProperty("user.dir") + "/src/test/resources/";

        return resourcePath;
    }
}

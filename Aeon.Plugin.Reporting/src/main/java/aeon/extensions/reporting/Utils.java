package aeon.extensions.reporting;

import gui.ava.html.image.generator.HtmlImageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Utils {

    private static Logger log = LoggerFactory.getLogger(Utils.class);

    public static File htmlToPngFile(String html, String filePath) {
        log.trace("Converting HTML file to Png");
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        File file = new File(filePath);
        file.deleteOnExit();
        file.getParentFile().mkdirs();
        imageGenerator.loadHtml(html);
        imageGenerator.saveAsImage(file);

        return file;
    }

    public static String getResourcesPath() {
        String resourcePath;
        resourcePath = System.getProperty("user.dir") + "/src/test/resources/";

        return resourcePath;
    }

    public static String getTime(long time) {
        int seconds = (int) (time / 1000);
        if (seconds >= 60) {
            int minutes = seconds / 60;
            if (minutes >= 60) {
                int hours = minutes / 60;
                minutes = minutes % 60;
                return hours + " hours" + minutes + " minutes";
            }
            seconds = seconds % 60;
            return minutes + " minutes " + seconds + " seconds";
        } else {
            return seconds + " seconds";
        }
    }
}

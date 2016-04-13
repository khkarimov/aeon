package echo.core.test_abstraction.context;

import echo.core.common.BrowserType;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class Echo {
    public static <T extends Product> T Launch(Class<T> tClass, BrowserType browserType) {
        try {
            return tClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

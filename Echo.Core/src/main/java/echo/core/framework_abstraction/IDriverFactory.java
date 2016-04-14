package echo.core.framework_abstraction;

import echo.core.common.logging.ILog;
import echo.core.test_abstraction.settings.ISettingsProvider;
import echo.core.test_abstraction.webenvironment.Parameters;

/**
 * Created by DionnyS on 4/13/2016.
 */
public interface IDriverFactory {
    IDriver createDriver(Parameters parameters, ILog log);
    ISettingsProvider getSettingsProvider();
}
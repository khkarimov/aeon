//package echo.core.test_abstraction.driver;
//
//import echo.core.common.exceptions.UnableToCreateDriverException;
//import echo.core.common.logging.ILog;
//import echo.core.framework_abstraction.IWebDriver;
//import echo.core.framework_abstraction.IDriverFactory;
//import echo.core.framework_abstraction.webdriver.IWebDriverAdapter;
//import echo.core.framework_interaction.selenium.SeleniumWebDriverFactory;
//import echo.core.test_abstraction.settings.ISettingsProvider;
//import echo.core.test_abstraction.webenvironment.Parameters;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//
///**
// * Created by DionnyS on 4/13/2016.
// */
//public class Selenium implements IWebDriver {
//    IWebDriverAdapter driver;
//
//    public Selenium(Parameters parameters, ILog log) {
//        try {
//            SeleniumWebDriverFactory factory = new SeleniumWebDriverFactory(
//                    null, // JavaScriptFlowExecutor
//                    log,
//                    parameters.getString("chromeDirectory"),
//                    parameters.getBoolean("enableSeleniumGrid"),
//                    parameters.getString("browserAcceptedLanguageCodes"),
//                    parameters.getString("ieDirectory"),
//                    new URL(parameters.getString("seleniumHubUri")),
//                    parameters.getBoolean("ensureCleanEnvironment"),
//                    parameters.getBoolean("moveMouseToOrigin"),
//                    parameters.getBoolean("ensureCleanSession"),
//                    parameters.getString("proxyLocation"));
//
//            this.driver = factory.createDriver(parameters);
//
//        } catch (MalformedURLException e) {
//            throw new UnableToCreateDriverException(e);
//        }
//    }
//
//    public ISettingsProvider getSettingsProvider() {
//        return new SeleniumSettings();
//    }
//}

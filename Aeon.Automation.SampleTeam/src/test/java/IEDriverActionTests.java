import aeon.core.common.KeyboardKey;
import aeon.core.common.exceptions.*;
import aeon.core.common.web.BrowserSize;
import aeon.core.common.web.AppRuntime;
import aeon.core.common.web.WebSelectOption;
import aeon.core.framework.abstraction.controls.web.IWebCookie;
import aeon.core.testabstraction.product.Configuration;
import main.Sample;
import org.hamcrest.core.IsInstanceOf;
import org.joda.time.DateTime;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.Calendar;
import java.util.Date;

import static aeon.core.testabstraction.product.Aeon.launch;

public class IEDriverActionTests {
    private static Sample product;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //region Setup and Teardown
    @BeforeClass
    public static void setUp() {
    }

    @AfterClass
    public static void tearDown() {
        product.browser.quit();
    }

    @Before
    public void beforeTests() {
        product = launch(Sample.class, AppRuntime.InternetExplorer);
        String environment = product.getConfig(Configuration.Keys.ENVIRONMENT,
                "/" + System.getProperty("user.dir").replace('\\', '/') + "/Test-Sample-Context/index.html");
        String protocol = product.getConfig(Configuration.Keys.PROTOCOL, "file");
        product.browser.goToUrl(protocol + "://" + environment);
    }

    @After
    public void afterTests() {
        product.browser.quit();
    }
    //endregion

    @Test
    public void testAddCookie_ModifyCookie_DeleteCookie_GetCookie() {
        product.browser.goToUrl("http://ci.mia.ucloud.int");
        IWebCookie cookie = new IWebCookie() {
            String name = "CookieName";
            String domain = "ci.mia.ucloud.int";
            String value = "CookieValue";
            Date expiration = getNextYear();
            String path = "/";
            boolean secure = false;
            boolean session = true;

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getValue() {
                return value;
            }

            @Override
            public String getPath() {
                return path;
            }

            @Override
            public String getDomain() {
                return domain;
            }

            @Override
            public Date getExpiration() {
                return expiration;
            }

            @Override
            public boolean getSecure() {
                return secure;
            }

            @Override
            public boolean getSession() {
                return session;
            }

            private Date getNextYear() {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date(Long.parseLong("18000000")));
                int yearsSinceEpoch = DateTime.now().getYear() - cal.get(Calendar.YEAR);
                cal.add(Calendar.YEAR, yearsSinceEpoch + 1);
                return cal.getTime();
            }
        };
        product.browser.addCookie(cookie);
        IWebCookie secondCookie = product.browser.getCookie(cookie.getName());
        assert (secondCookie.getName().equals(cookie.getName()));
        assert (secondCookie.getDomain().equals(cookie.getDomain()));
        assert (secondCookie.getValue().equals(cookie.getValue()));
        assert (secondCookie.getSecure() == cookie.getSecure());
        assert (secondCookie.getPath().equals(cookie.getPath()));
        assert (secondCookie.getExpiration().equals(cookie.getExpiration()));

        product.browser.modifyCookie(cookie.getName(), "CookieNewValue");
        secondCookie = product.browser.getCookie(cookie.getName());
        assert (secondCookie.getValue().equals("CookieNewValue"));
        product.browser.deleteCookie(cookie.getName());
    }

    @Test
    public void testBlur() {
        //used to be set command
        product.startPage.alertTitleTextBox.click();
        product.startPage.alertTitleTextBox.blur();
    }

    @Test
    public void testCheck_UnCheck() {
        product.startPage.testCheckbox.check();
        product.startPage.testCheckbox.uncheck();
    }

    @Test
    public void testClickAndHold() {
        product.startPage.start.clickAndHold(5000);
    }

    @Test
    public void testDismissAlertWhenThereIsAnAlert() {
        product.startPage.openAlertButton.click();
        product.browser.verifyAlertExists();
        product.browser.dismissAlert();
    }

    @Test
    public void testDoubleClick() {
        product.startPage.ultimateLogoImage.doubleClick();
        String src = product.startPage.ultimateLogoImageDoubleClick.getElementAttribute("src").toString();
        assert(src.contains("ultimate-image.png"));
        //the ultimate logo should appear in the image element "dbl-click-image"
    }

    @Test
    public void testDragAndDrop() {
        product.browser.goToUrl("http://www.dhtmlgoodies.com/scripts/drag-drop-nodes/drag-drop-nodes-demo2.html");
        product.startPage.draggableListItem.dragAndDrop("ul[id='box2']");
    }

    @Test
    public void testGetAlertText() {
        product.startPage.openAlertButton.click();
        String text = product.browser.getAlertText();

        assert(text.equals("Send some keys"));
        product.browser.dismissAlert();
        thrown.expectCause(IsInstanceOf.instanceOf(NoAlertException.class));
        product.browser.getAlertText();
    }

    @Test
    public void testWindowResizing_GoBack_GoForward_ScrollToEnd_ScrollToTop() {
        product.browser.resize(BrowserSize.TabletLandscape);
        product.browser.resize(BrowserSize.SmallTabletLandscape);
        product.browser.resize(BrowserSize.MobileLandscape);
        product.browser.maximize();
        product.browser.goToUrl("http://www.tutorialspoint.com");
        product.browser.scrollToEnd();
        product.browser.scrollToTop();
        product.browser.goBack();
        product.browser.goForward();
    }

    @Test
    public void testMouseOver_MouseOut_Refresh() {
        product.startPage.start.mouseOver();
        product.startPage.start.mouseOut();
        product.browser.refresh();
    }

    @Test
    public void testPressKeyboardKey() {
        product.startPage.formTextBox.pressKeyboardKey(KeyboardKey.SPACE);
        product.startPage.formTextBox.pressKeyboardKey(KeyboardKey.SPACE);
    }

    @Test
    public void testRightClick() {
        product.startPage.dateLabel.rightClick();
        String validationText = product.startPage.reactionLabel.getElementAttribute("textContent").toString();
        assert(validationText.equals("right click"));
    }

    @Test
    public void testSetWithNonSelectElement_Clear() {
        product.startPage.formTextBox.set("set the value to this");
        product.startPage.formTextBox.clear();
    }

    @Ignore
    public void testUploadFile() {
        product.startPage.testFileDialogInput.uploadFileDialog("asdasd#@$@#$");
    }

    @Test
    public void testSwitchToMainWindow() {
        product.browser.verifyTitle("Material Design Lite");
        product.startPage.popupButton.click();
        product.browser.switchToWindowByTitle("Google");
        product.browser.verifyTitle("Google");
        product.browser.switchToMainWindow();
        product.browser.verifyTitle("Material Design Lite");
        product.browser.switchToWindowByTitle("Google");
        product.browser.close();
        product.browser.switchToMainWindow(true);
        product.startPage.popupButton.click();
        product.browser.switchToWindowByTitle("Google");
        thrown.expectCause(IsInstanceOf.instanceOf(NotAllPopupWindowsClosedException.class));
        product.browser.switchToMainWindow(true);
    }

    @Test
    public void testSwitchToWindowByTitle() {
        product.browser.verifyTitle("Material Design Lite");
        product.startPage.popupButton.click();
        product.browser.switchToWindowByTitle("Google");
        product.browser.verifyTitle("Google");
        thrown.expectCause(IsInstanceOf.instanceOf(NoSuchWindowException.class));
        product.browser.switchToWindowByTitle("Some Fake Title");
    }

    @Test
    public void testSwitchToWindowByUrl() {
        product.browser.verifyTitle("Material Design Lite");
        product.startPage.popupButton.click();
        product.browser.switchToWindowByUrl("https://www.google.com/");
        product.browser.verifyTitle("Google");
        thrown.expectCause(IsInstanceOf.instanceOf(NoSuchWindowException.class));
        product.browser.switchToWindowByUrl("www.fake.com");
    }

    @Test
    public void testSet_WithSelect(){
        product.startPage.lexoDropDown.set(WebSelectOption.Value, "10");
        product.startPage.lexoDropDown.set(WebSelectOption.Text, "dog");
        product.startPage.lexoDropDown.set(WebSelectOption.Text, "zebra");
    }

    @Test
    public void testSetValueByJavaScript(){
        product.startPage.formTextBox.setTextByJavaScript("set text by javascript is working");
    }

    @Ignore
    public void testWaiter(){
        product.startPage.start.click();
        product.startPage.smileyFace1.click();
    }

    @Test
    public void testSetByDivJavaScript() {
        product.startPage.divWindow.setDivValueByJavaScript("Hello World Haha");
        product.startPage.divWindow.is("Hello World Haha");
        product.startPage.bodyTag.setDivValueByJavaScript("Hello World Haha");
        product.startPage.bodyTag.is("Hello World Haha");
    }

    @Test
    public void testSetByBodyJavaScript() {
        product.startPage.bodyTag.setBodyValueByJavaScript("Hello World Haha");
        product.startPage.bodyTag.is("Hello World Haha");
    }
}

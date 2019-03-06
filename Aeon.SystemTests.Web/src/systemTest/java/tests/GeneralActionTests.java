package tests;

import aeon.core.common.KeyboardKey;
import aeon.core.common.exceptions.NoSuchCookieException;
import aeon.core.framework.abstraction.controls.web.IWebCookie;
import aeon.core.testabstraction.product.WebConfiguration;
import categories.EdgeNotSupported;
import categories.SafariNotSupported;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class GeneralActionTests extends SampleBaseTest {

    @Category({EdgeNotSupported.class, SafariNotSupported.class})
    @Test
    public void testAddCookie_ModifyCookie_DeleteCookie_GetCookie() {
        if (product.getConfig(WebConfiguration.Keys.BROWSER, "").equals("InternetExplorer")) {
            return;
        }

        product.browser.goToUrl("http://bing.com");
        IWebCookie cookie = new IWebCookie() {
            String name = "CookieName";
            String domain = ".bing.com";
            String value = "CookieValue";
            Date expiration = getNextYear();
            String path = "/";
            boolean secure = true;
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
                int yearsSinceEpoch = LocalDate.now().getYear() - cal.get(Calendar.YEAR);
                cal.add(Calendar.YEAR, yearsSinceEpoch + 1);
                return cal.getTime();
            }
        };

        product.browser.addCookie(cookie);
        IWebCookie secondCookie = product.browser.getCookie(cookie.getName());
        assertEquals(cookie.getName(), secondCookie.getName());
        assertEquals(cookie.getDomain(), secondCookie.getDomain());
        assertEquals(cookie.getValue(), secondCookie.getValue());
        assertEquals(cookie.getSecure(), secondCookie.getSecure());
        assertEquals(cookie.getPath(), secondCookie.getPath());
        assertEquals(cookie.getExpiration(), secondCookie.getExpiration());

        String cookieNewValue = "NewCookieValue";
        product.browser.modifyCookie(cookie.getName(), cookieNewValue);
        secondCookie = product.browser.getCookie(cookie.getName());
        assertEquals(cookieNewValue, secondCookie.getValue());
        product.browser.deleteCookie(cookie.getName());
        thrown.expect(IsInstanceOf.instanceOf(NoSuchCookieException.class));
        product.browser.getCookie(cookie.getName());
    }

    @Test
    public void testWaiter() {
        product.startPage.start.click();
        product.startPage.smileyFace3.click();
    }

    @Test
    public void testBlur() {
        //used to be set command
        product.startPage.alertTitleTextBox.set("");
        product.startPage.alertTitleTextBox.blur();
    }

    @Test
    public void testCheck_UnCheck() {
        product.startPage.testCheckbox.check();
        product.startPage.testCheckbox.uncheck();
    }

    @Test
    public void testPressKeyboardKey() {
        product.startPage.formTextBox.pressKeyboardKey(KeyboardKey.SPACE);
        product.startPage.formTextBox.pressKeyboardKey(KeyboardKey.SPACE);
    }

    @Test
    public void testSelectFileDialog_OpenFileDialog() {
        String path = "Test-Sample-Context/HeatLogo.jpg";
        product.startPage.testFileDialogInput.selectFile(path);
    }
}

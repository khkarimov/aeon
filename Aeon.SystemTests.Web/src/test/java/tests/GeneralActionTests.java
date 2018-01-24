package tests;

import aeon.core.common.KeyboardKey;
import aeon.core.common.exceptions.NoSuchCookieException;
import aeon.core.common.web.BrowserType;
import aeon.core.framework.abstraction.controls.web.IWebCookie;
import aeon.core.testabstraction.product.Configuration;
import categories.GridNotSupported;
import org.hamcrest.core.IsInstanceOf;
import org.joda.time.DateTime;
import org.junit.*;
import org.junit.experimental.categories.Category;

import java.util.Calendar;
import java.util.Date;

public class GeneralActionTests extends SampleBaseTest{

    @Ignore
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
        assert(secondCookie.getName().equals(cookie.getName()));
        assert(secondCookie.getDomain().equals(cookie.getDomain()));
        assert(secondCookie.getValue().equals(cookie.getValue()));
        assert(secondCookie.getSecure() == cookie.getSecure());
        assert(secondCookie.getPath().equals(cookie.getPath()));
        assert(secondCookie.getExpiration().equals(cookie.getExpiration()));

        product.browser.modifyCookie(cookie.getName(), "CookieNewValue");
        secondCookie = product.browser.getCookie(cookie.getName());
        assert(secondCookie.getValue().equals("CookieNewValue"));
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
        product.startPage.alertTitleTextBox.click();
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
    public void testGetBrowserType() {
        assert (product.browser.getBrowserType().equals(BrowserType.valueOf(product.getConfig(Configuration.Keys.BROWSER, ""))));
    }

    @Test
    public void testSelectFileDialog_OpenFileDialog() {
        String path = "Test-Sample-Context/HeatLogo.jpg";
        product.startPage.testFileDialogInput.selectFile(path);
    }
}

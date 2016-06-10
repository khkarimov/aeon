/**
 * Created by SebastianR on 6/6/2016.
 */

import main.Sample;
import org.junit.*;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import static echo.core.common.web.BrowserType.Chrome;
import static echo.core.common.web.BrowserType.Firefox;
import static echo.core.common.web.BrowserType.InternetExplorer;
import static echo.core.test_abstraction.product.Echo.Launch;

public class CommandTesting {

    private static Sample product;


    @Before
    public void SetUp(){
        product = Launch(Sample.class, Chrome);
        //Make sure to change to where you are keeping the fake test site
        product.Browser.GoToUrl("file:///C:/Users/Administrator/Desktop/Test%20Sample%20Context/index.html");
    }

    @After
    public void TearDown(){
        product.Browser.Quit();
    }

    @Test
    public void TestAcceptAlertWhenThereIsAnAlert(){
        product.Browser.VerifyAlertNotExists();
        product.StartPage.OpenAlertButton.Click();
        product.Browser.VerifyAlertExists();
        product.Browser.SendKeysToAlert("Tester of Alerts");
        System.out.println(product.Browser.GetAlertText());
        product.Browser.AcceptAlert();
    }

    @Test
    public void TestDismissAlertWhenThereIsAnAlert(){
        product.StartPage.OpenAlertButton.Click();
        product.Browser.VerifyAlertExists();
        product.Browser.DismissAlert();
    }

    @Test
    public void TestBlurTextBox(){
        product.StartPage.AlertTitleTextBox.Set("Alert Test Title");
        product.StartPage.AlertTitleTextBox.Blur();
    }

    @Test
    public void TestRightClick(){
        //product.StartPage.OpenAlertButton.RightClick();
        product.StartPage.AlertTitleTextBox.RightClick();
    }

    @Test
    public void TestOpenFileDialog(){
        product.StartPage.TestFileDialogInput.OpenFileDialog();
        //product.StartPage.AlertTitleTextBox.RightClick();
    }

}

/**
 * Created by SebastianR on 6/6/2016.
 */

import echo.core.common.exceptions.TimeoutExpiredException;
import echo.core.common.helpers.Sleep;
import echo.core.common.web.selectors.By;
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
        product = Launch(Sample.class, Firefox);
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

    @Test(expected = TimeoutExpiredException.class)
    public void TestDragAndDrop(){
        product.Browser.Maximize();
        product.StartPage.UltimateLogoImage.DragAndDrop(By.CssSelector("div[id='secondDrop']"));
        product.StartPage.UltimateLogoImage.DoubleClick();
    }
//region Testing MouseOver and MouseOut commands
    @Test
    public void MouseOverButton_ButtonTextChangesColor() {
        product.StartPage.Start.MouseOver();
        product.StartPage.Start.MouseOut();
    }

    @Test
    public void MouseOverImage_ImageChanges(){
        product.Browser.ScrollToEnd();
        product.StartPage.UltimateLogoImage.MouseOver();
        product.StartPage.UltimateLogoImage.MouseOut();
    }

    @Test
    public void MouseOverLink_TextChangesColor(){
        product.StartPage.TryLink.MouseOver();
        product.StartPage.TryLink.MouseOut();
        product.StartPage.CheckboxLabel.MouseOver();
    }
//endregion
}

/**
 * Created by SebastianR on 6/6/2016.
 */

import echo.core.common.exceptions.TimeoutExpiredException;
import echo.core.common.helpers.Sleep;
import echo.core.common.web.BrowserSize;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.selectors.By;
import main.Sample;
import org.junit.*;
import org.junit.Test;

import static echo.core.common.web.BrowserType.Firefox;
import static echo.core.test_abstraction.product.Echo.Launch;

public class CommandTesting {

    private static Sample product;


    @Before
    public void SetUp(){
        product = Launch(Sample.class, Firefox);
        product.Browser.GoToUrl("file:///" + System.getProperty("user.dir").replace('\\', '/') + "/Test%20Sample%20Context/index.html");
        product.Browser.Maximize();
    }

    @After
    public void TearDown(){
        product.Browser.Quit();
    }

    @Test
    public void TestSendKeysToAlert_AcceptAlertWhenThereIsAnAlert(){
        product.Browser.VerifyAlertNotExists();
        product.StartPage.OpenAlertButton.Click();
        product.Browser.VerifyAlertExists();
        product.Browser.SendKeysToAlert("Tester of Alerts");
        product.Browser.AcceptAlert();
    }

//region Ignore Test
    @Ignore
    public void TestDismissAlertWhenThereIsAnAlert(){
        product.StartPage.OpenAlertButton.Click();
        product.Browser.VerifyAlertExists();
        product.Browser.DismissAlert();
    }

    @Ignore
    public void TestBlurTextBox(){
        product.StartPage.AlertTitleTextBox.Set("Alert Test Title");
        product.StartPage.AlertTitleTextBox.Blur();
    }

    @Ignore
    public void MouseOverButton_ButtonTextChangesColor() {
        product.StartPage.Start.MouseOver();
        product.StartPage.Start.MouseOut();
    }
//endregion

    @Test
    public void TestHasOptionsCommandsWith50000() {
        String [] texts = {"option49999", "option8"};
        String [] shouldfail = {"Klingon", "Clicky Noises", "Reptilian Hissing"};
        String [] values = {"1", "2"};
        String [] valuesShouldFail = {"-12", "Blue"};
        product.StartPage.DropDown.Click();
        product.StartPage.DropDown.HasOptions(texts, null,  WebSelectOption.Text);
        product.StartPage.DropDown.HasOptions(values, null,  WebSelectOption.Value);
        product.StartPage.DropDown.DoesNotHaveOptions(shouldfail, null, WebSelectOption.Text);
        product.StartPage.DropDown.DoesNotHaveOptions(valuesShouldFail, null, WebSelectOption.Value);
    }

    @Test
    public void TestOpenFileDialog(){
        product.StartPage.TestFileDialogInput.OpenFileDialog();
        System.out.println();
        //product.StartPage.AlertTitleTextBox.RightClick();

    public void TestWindowResizingAndNavigation() {
        product.Browser.Resize(BrowserSize.TabletLandscape);
        product.Browser.Resize(BrowserSize.SmallTabletLandscape);
        product.Browser.Resize(BrowserSize.MobileLandscape);
        product.Browser.Maximize();
        product.Browser.GoToUrl("http://www.tutorialspoint.com");
        product.Browser.ScrollToEnd();
        product.Browser.ScrollToTop();
        product.Browser.GoBack();
        product.Browser.GoForward();
    }

    @Test

    public void TestSelectFileDialog(){
        product.StartPage.TestFileDialogInput.OpenFileDialog();
        product.StartPage.TestFileDialogInput.SelectFileDialog("C:\\Projects\\distrib\\neW3.txt");
        product.StartPage.TestFileDialogInput.UploadFileDialog("C:\\Projects\\distrib\\NEW2.txt");
        System.out.println();
    }

    @Test
    public void TestDragAndDrop(){
        product.Browser.Maximize();
        product.StartPage.UltimateLogoImage.DragAndDrop(By.CssSelector("div[id='secondDrop']"));
        product.StartPage.UltimateLogoImage.DoubleClick();
        product.StartPage.UltimateLogoImage.MouseOut();
    }

    @Test
    public void TestDragAndDropNotUsingHTML5Events(){
        product.Browser.GoToUrl("http://www.dhtmlgoodies.com/scripts/drag-drop-nodes/drag-drop-nodes-demo2.html");
        product.StartPage.DraggableListItem.DragAndDrop(By.CssSelector("ul[id='box2']"));
    }

    @Test
    public void TestNotEnabled(){
        product.StartPage.DisabledButton.IsDisabled();
    }

    @Ignore
    public void TestOpenFileDialog(){
        product.StartPage.TestFileDialogInput.OpenFileDialog();
    }
}



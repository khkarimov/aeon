/**
 * Created by SebastianR on 6/6/2016.
 */

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
        //Make sure to change to where you are keeping the fake test site
        product.Browser.GoToUrl("file:///C:/Users/Administrator/Desktop/Test%20Sample%20Context/index.html");
    }

    @After
    public void TearDown(){
        product.Browser.Quit();
    }

    @Test
    public void TestAcceptAlertWhenThereIsAnAlert(){
        product.StartPage.OpenAlertButton.Click();
        product.Browser.VerifyAlertExists();
        product.Browser.AcceptAlert();
    }

    @Test
    public void TestDragAndDropOnASiteThatDoesntUseHTMl5(){
        product.Browser.GoToUrl("http://www.dhtmlgoodies.com/scripts/drag-drop-nodes/drag-drop-nodes-demo2.html");
        product.StartPage.ListItem.DragAndDrop(By.CssSelector("ul[id='box2']"));
    }

}

package tests;

import aeon.core.common.web.WebSelectOption;
import org.junit.jupiter.api.Test;

public class SetTests extends SampleBaseTest{

    @Test
    public void setByDivJavaScript() {
        product.startPage.divWindow.setDivValueByJavaScript("Hello World Haha");
        product.startPage.divWindow.is("Hello World Haha");
        product.startPage.bodyTag.setDivValueByJavaScript("Hello World Haha");
        product.startPage.bodyTag.is("Hello World Haha");
    }

    @Test
    public void setByBodyJavaScript() {
        product.startPage.bodyTag.setBodyValueByJavaScript("Hello World Haha");
        product.startPage.bodyTag.is("Hello World Haha");
    }

    @Test
    public void testSet_WithSelect() {
        product.startPage.lexoDropDown.set(WebSelectOption.Value, "10");
        product.startPage.lexoDropDown.set(WebSelectOption.Text, "dog");
        product.startPage.lexoDropDown.set(WebSelectOption.Text, "zebra");
    }

    @Test
    public void testSetValueByJavaScript() {
        product.startPage.formTextBox.setTextByJavaScript("set text by javascript is working");
    }

    @Test
    public void testSetWithNonSelectElementClear() {
        product.startPage.formTextBox.set("set the value to this");
        product.startPage.formTextBox.clear();
    }
}

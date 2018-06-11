package tests;

import aeon.core.testabstraction.product.Aeon;
import categories.UbuntuTests;
import categories.WindowsTests;
import org.junit.*;
import org.junit.experimental.categories.Category;

import static org.junit.Assume.assumeNotNull;

public class ReportingPluginTestCases extends SampleBaseTest {

    @Test
    public void thisShouldSucceed() {
        Assert.assertEquals(4, 4);
    }

    @Test
    public void thisShouldFail1984() {
        Assert.assertEquals(4, 5);
    }

    @Test
    public void anotherSuccess(){
        Assert.assertEquals(true, true);
    }

    @Test
    public void thisShouldBeSkipped() {
        String nullString = null;
        assumeNotNull(nullString);
    }

//    @Test
//    public void repeatedSuccess1() { Assert.assertEquals(4, 4); }
//
//    @Test
//    public void repeatedSuccess2() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess3() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess4() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess5() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess6() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess7() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess8() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess9() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess10() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess11() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess12() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess13() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess14() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess15() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess16() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess17() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess18() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess19() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess20() { Assert.assertEquals(4, 4); }
//    @Test
//    public void repeatedSuccess21() { Assert.assertEquals(4, 4); }
//
    @Test
    public void failureWithLongException() throws Exception {
        String exceptionMessage = "";
        for (int i = 0; i < 100; i++)
        {
            exceptionMessage += "this is a long exception !@#$%^&*()&nbsp%20%20apple";
        }
        throw new Exception(exceptionMessage);
    }

    @Test
    public void javascriptInjectionInError() throws Exception {
        String jscript = "THIS APPEARS BEFORE A SCRIPT<script>alert(document.cookie);</script> THIS APPEARS AFTER A SCRIPT";
        throw new Exception(jscript);

    }

    @Test
    public void htmlInErrorMessage() throws Exception {
        String html = "<img src='http://via.placeholder.com/350x150'><input type='file'>HMMMM</input>";
        throw new Exception(html);
    }

}

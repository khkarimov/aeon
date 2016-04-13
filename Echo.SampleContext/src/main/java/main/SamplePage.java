package main;

import echo.core.common.webobjects.By;
import echo.core.test_abstraction.context.Element;
import echo.core.test_abstraction.context.Page;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class SamplePage extends Page {
    public Element Submit;

    public SamplePage() {
        Submit = new Element(By.CssSelector("[id*=submit]"));
    }
}

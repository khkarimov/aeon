package main.sample.pages;

import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.Element;
import aeon.core.testabstraction.models.Page;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class SamplePage extends Page {

    public Element submit;

    public SamplePage() {
        submit = new Element(By.cssSelector("[id*=submit]"));
    }
}

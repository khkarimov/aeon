package main;

import aeon.core.common.web.selectors.By;
import aeon.core.test_abstraction.elements.Element;
import aeon.core.test_abstraction.models.Page;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class SamplePage extends Page {
    public Element Submit;

    public SamplePage() {
        Submit = new Element(By.CssSelector("[id*=submit]"));
    }
}

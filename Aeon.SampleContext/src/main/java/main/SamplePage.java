package main;

import aeon.core.common.web.selectors.by;
import aeon.core.testabstraction.elements.Element;
import aeon.core.testabstraction.models.Page;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class SamplePage extends Page {
    public Element Submit;

    public SamplePage() {
        Submit = new Element(by.CssSelector("[id*=submit]"));
    }
}

package echo.core.test_abstraction.context.elementtypes;

import echo.core.common.webobjects.interfaces.IBy;
import echo.core.test_abstraction.context.Element;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by AdamC on 4/13/2016.
 */
public class TextBox extends Element{
    public TextBox(IBy selector) {
        super(selector);
    }

    public void Set(String value){
        throw new NotImplementedException();
    }

    public void Blur(){
        throw new NotImplementedException();
    }
}

package main.samplegrid;

import echo.core.test_abstraction.context.controls.RowActions;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by AdamC on 4/13/2016.
 */
public class SampleRowActions extends RowActions{

    public SampleRowElements ByName(String value){
        throw new NotImplementedException();
    }

    public SampleRowElements ById(String value){
        throw new NotImplementedException();
    }
}

package echo.core.test_abstraction.elements.factories;

import echo.core.test_abstraction.elements.Element;

import java.lang.reflect.Type;

/**
 * Created by RafaelT on 6/10/2016.
 */
public interface IElementFactory {
    Element create(Type elementType, String selector);
}

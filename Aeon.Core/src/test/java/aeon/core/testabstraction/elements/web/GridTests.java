package aeon.core.testabstraction.elements.web;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.Type;

public class GridTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private RowActions rowBy;

    @Test
    public void goodConstructorGrid() {
        Class T = rowBy.getClass();
        System.out.printf("T: " + T);
        Grid grid = Mockito.mock(Grid.class, Mockito.CALLS_REAL_METHODS);

    }
}

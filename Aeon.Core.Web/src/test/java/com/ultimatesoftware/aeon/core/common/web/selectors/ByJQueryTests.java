package com.ultimatesoftware.aeon.core.common.web.selectors;


import com.ultimatesoftware.aeon.core.common.web.JQueryStringType;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ByJQueryTests {

    // Variables
    private ByJQuery byJQuery;

    @BeforeEach
    void setUp() {
        byJQuery = new ByJQuery("test");
    }

    @Test
    void append_NoAppendicies_ReturnsExpectedString() {

        // Arrange
        ByJQuery appendee = new ByJQuery("appendee");

        // Act
        ByJQuery appended = byJQuery.append(appendee);

        // Assert
        assertEquals("$(\"appendee\")", appended.toString());
    }

    @Test
    void append_AppendeeNull_ThrowsIllegalArgumentException() {

        // Arrange

        // Act
        Executable action = () -> byJQuery.append(null);

        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, action);
        assertEquals("appendee", exception.getMessage());
    }

    @Test
    void append_AppendicesNull_ThrowsIllegalArgumentException() {

        // Arrange
        ByJQuery appendee = new ByJQuery("appendee");

        // Act
        Executable action = () -> byJQuery.append(appendee, null);

        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, action);
        assertEquals("appendices", exception.getMessage());
    }

    @Test
    void append_OneAppendix_ReturnsExpectedString() {

        // Arrange
        ByJQuery appendee = new ByJQuery("appendee");
        ByJQuery appendix = new ByJQuery("appendix");

        // Act
        ByJQuery appended = byJQuery.append(appendee, appendix);

        // Assert
        assertEquals("$(\"appendee\").find(\"appendix\")", appended.toString());
    }

    @Test
    void append_TwoAppendices_ReturnsExpectedString() {

        // Arrange
        ByJQuery appendee = new ByJQuery("appendee");
        ByJQuery appendix = new ByJQuery("appendix");
        ByJQuery appendix2 = new ByJQuery("appendix2");

        // Act
        ByJQuery appended = byJQuery.append(appendee, appendix, appendix2);

        // Assert
        assertEquals("$(\"appendee\").find(\"appendix\").find(\"appendix2\")", appended.toString());
    }

    @Test
    void opAddition_QueryAndIntPassed_ReturnsExpectedString() {

        // Arrange
        ByJQuery obj = new ByJQuery("selector");

        // Act
        ByJQueryWithArithmeticOperatorOverload returned = byJQuery.opAddition(obj, 5);

        // Assert
        assertEquals("$(\"selector\") + 5", returned.toString());
    }

    @Test
    void opSubtraction_QueryAndIntPassed_ReturnsExpectedString() {

        // Arrange
        ByJQuery obj = new ByJQuery("selector");

        // Act
        ByJQueryWithArithmeticOperatorOverload returned = byJQuery.opSubtraction(obj, 5);

        // Assert
        assertEquals("$(\"selector\") - 5", returned.toString());
    }

    @Test
    void toJQuery_WhenCalled_ReturnsExpectedByJQuery() {

        // Arrange


        // Act
        ByJQuery returned = byJQuery.toJQuery();

        // Assert
        assertEquals(byJQuery, returned);
    }

    @Test
    void add_StringPassed_ReturnsExpectedString() {

        // Arrange


        // Act
        ByJQuery returned = byJQuery.add("selector");

        // Assert
        assertEquals("$(\"test\").add(\"selector\")", returned.toString());
    }

    @Test
    void add_ByJQueryPassed_ReturnsExpectedString() {

        // Arrange
        ByJQuery obj = new ByJQuery("selector");

        // Act
        ByJQuery returned = byJQuery.add(obj);

        // Assert
        assertEquals("$(\"test\").add($(\"selector\"))", returned.toString());
    }

    @Test
    void andSelf_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.andSelf();

        // Assert
        assertEquals("$(\"test\").andSelf()", returned.toString());
    }

    @Test
    void attr_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.attr("attributeName");

        // Assert
        assertEquals("$(\"test\").attr(\"attributeName\")", returned.toString());
    }

    @Test
    void children_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.children("selector");

        // Assert
        assertEquals("$(\"test\").children(\"selector\")", returned.toString());
    }

    @Test
    void children_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.children();

        // Assert
        assertEquals("$(\"test\").children()", returned.toString());
    }

    @Test
    void closest_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.closest("selector");

        // Assert
        assertEquals("$(\"test\").closest(\"selector\")", returned.toString());
    }

    @Test
    void closest_ByJQueryPassed_ReturnsExpectedString() {

        // Arrange
        ByJQuery obj = new ByJQuery("selector");

        // Act
        ByJQuery returned = byJQuery.closest(obj);

        // Assert
        assertEquals("$(\"test\").closest($(\"selector\"))", returned.toString());
    }

    @Test
    void contents_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.contents();

        // Assert
        assertEquals("$(\"test\").contents()", returned.toString());
    }

    @Test
    void end_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.end();

        // Assert
        assertEquals("$(\"test\").end()", returned.toString());
    }

    @Test
    void eq_IntPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.eq(5);

        // Assert
        assertEquals("$(\"test\").eq(5)", returned.toString());
    }

    @Test
    void eq_ByJQueryWithArithmeticOpeatorOverloadPassed_ReturnsExpectedString() {

        // Arrange
        ByJQueryWithArithmeticOperatorOverload index = new ByJQueryWithArithmeticOperatorOverload("5");

        // Act
        ByJQuery returned = byJQuery.eq(index);

        // Assert
        assertEquals("$(\"test\").eq(5)", returned.toString());
    }

    @Test
    void filter_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.filter("selector");

        // Assert
        assertEquals("$(\"test\").filter(\"selector\")", returned.toString());
    }

    @Test
    void filter_InlineJavaScriptPassed_ReturnsExpectedString() {

        // Arrange
        InlineJavaScript function = new InlineJavaScript("selector");

        // Act
        ByJQuery returned = byJQuery.filter(function);

        // Assert
        assertEquals("$(\"test\").filter(selector)", returned.toString());
    }

    @Test
    void filter_ByJQueryPassed_ReturnsExpectedString() {

        // Arrange
        ByJQuery query = new ByJQuery("selector");

        // Act
        ByJQuery returned = byJQuery.filter(query);

        // Assert
        assertEquals("$(\"test\").filter($(\"selector\"))", returned.toString());
    }

    @Test
    void find_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.find("selector");

        // Assert
        assertEquals("$(\"test\").find(\"selector\")", returned.toString());
    }

    @Test
    void shadowDom_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.shadowDom("selector");

        // Assert
        assertEquals("$($(\"test\")[0].shadowRoot.childNodes).find(\"selector\")", returned.toString());
    }

    @Test
    void shadowRoot_returnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.shadowRoot();

        // Assert
        assertEquals("$($(\"test\")[0].shadowRoot.childNodes)", returned.toString());
    }

    @Test
    void find_ByJQueryPassed_ReturnsExpectedString() {

        // Arrange
        ByJQuery obj = new ByJQuery("selector");

        // Act
        ByJQuery returned = byJQuery.find(obj);

        // Assert
        assertEquals("$(\"test\").find($(\"selector\"))", returned.toString());
    }

    @Test
    void find_IByWebPassed_ReturnsExpectedString() {

        // Arrange
        IByWeb selector = new By("selector");

        // Act
        IByWeb returned = byJQuery.find(selector);

        // Assert
        assertEquals("$(\"test\").find($(\"selector\"))", returned.toString());
    }

    @Test
    void first_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.first();

        // Assert
        assertEquals("$(\"test\").first()", returned.toString());
    }

    @Test
    void has_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.has("selector");

        // Assert
        assertEquals("$(\"test\").has(\"selector\")", returned.toString());
    }

    @Test
    void html_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.html();

        // Assert
        assertEquals("$(\"test\").html()", returned.toString());
    }

    @Test
    void index_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.index();

        // Assert
        assertEquals("$(\"test\").index()", returned.toString());
    }

    @Test
    void index_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.index("selector");

        // Assert
        assertEquals("$(\"test\").index(\"selector\")", returned.toString());
    }

    @Test
    void index_ByJQueryPassed_ReturnsExpectedString() {

        // Arrange
        ByJQuery obj = new ByJQuery("selector");

        // Act
        ByJQuery returned = byJQuery.index(obj);

        // Assert
        assertEquals("$(\"test\").index($(\"selector\"))", returned.toString());
    }

    @Test
    void indexOf_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.indexOf("searchValue");

        // Assert
        assertEquals("$(\"test\").indexOf(\"searchValue\")", returned.toString());
    }

    @Test
    void indexOf_ByJQueryIntPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.indexOf("searchValue", 5);

        // Assert
        assertEquals("$(\"test\").indexOf(\"searchValue\",5)", returned.toString());
    }

    @Test
    void is_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.is("selector");

        // Assert
        assertEquals("$(\"test\").is(\"selector\")", returned.toString());
    }

    @Test
    void is_ByJQueryPassed_ReturnsExpectedString() {

        // Arrange
        ByJQuery obj = new ByJQuery("selector");

        // Act
        ByJQuery returned = byJQuery.is(obj);

        // Assert
        assertEquals("$(\"test\").is($(\"selector\"))", returned.toString());
    }

    @Test
    void last_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.last();

        // Assert
        assertEquals("$(\"test\").last()", returned.toString());
    }

    @Test
    void next_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.next();

        // Assert
        assertEquals("$(\"test\").next()", returned.toString());
    }

    @Test
    void next_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.next("selector");

        // Assert
        assertEquals("$(\"test\").next(\"selector\")", returned.toString());
    }

    @Test
    void nextAll_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.nextAll();

        // Assert
        assertEquals("$(\"test\").nextAll()", returned.toString());
    }

    @Test
    void nextAll_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.nextAll("selector");

        // Assert
        assertEquals("$(\"test\").nextAll(\"selector\")", returned.toString());
    }

    @Test
    void nextUntil_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.nextUntil("selector");

        // Assert
        assertEquals("$(\"test\").nextUntil(\"selector\")", returned.toString());
    }

    @Test
    void nextUntil_TwoStringsPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.nextUntil("selector", "filter");

        // Assert
        assertEquals("$(\"test\").nextUntil(\"selector\",\"filter\")", returned.toString());
    }

    @Test
    void nextUntil_ByJQueryPassed_ReturnsExpectedString() {

        // Arrange
        ByJQuery obj = new ByJQuery("selector");

        // Act
        ByJQuery returned = byJQuery.nextUntil(obj);

        // Assert
        assertEquals("$(\"test\").nextUntil($(\"selector\"))", returned.toString());
    }

    @Test
    void nextUntil_ByJQueryStringPassed_ReturnsExpectedString() {

        // Arrange
        ByJQuery obj = new ByJQuery("selector");

        // Act
        ByJQuery returned = byJQuery.nextUntil(obj, "filter");

        // Assert
        assertEquals("$(\"test\").nextUntil($(\"selector\"),\"filter\")", returned.toString());
    }

    @Test
    void not_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.not("selector");

        // Assert
        assertEquals("$(\"test\").not(\"selector\")", returned.toString());
    }

    @Test
    void not_ByJQueryPassed_ReturnsExpectedString() {

        // Arrange
        ByJQuery obj = new ByJQuery("selector");

        // Act
        ByJQuery returned = byJQuery.not(obj);

        // Assert
        assertEquals("$(\"test\").not($(\"selector\"))", returned.toString());
    }

    @Test
    void offsetParent_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.offsetParent();

        // Assert
        assertEquals("$(\"test\").offsetParent()", returned.toString());
    }

    @Test
    void parent_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.parent();

        // Assert
        assertEquals("$(\"test\").parent()", returned.toString());
    }

    @Test
    void parent_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.parent("selector");

        // Assert
        assertEquals("$(\"test\").parent(\"selector\")", returned.toString());
    }

    @Test
    void parents_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.parents();

        // Assert
        assertEquals("$(\"test\").parents()", returned.toString());
    }

    @Test
    void parents_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.parents("selector");

        // Assert
        assertEquals("$(\"test\").parents(\"selector\")", returned.toString());
    }

    @Test
    void parentsUntil_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.parentsUntil("selector");

        // Assert
        assertEquals("$(\"test\").parentsUntil(\"selector\")", returned.toString());
    }

    @Test
    void parentsUntil_TwoStringsPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.parentsUntil("selector", "filter");

        // Assert
        assertEquals("$(\"test\").parentsUntil(\"selector\",\"filter\")", returned.toString());
    }

    @Test
    void parentsUntil_ByJQueryPassed_ReturnsExpectedString() {

        // Arrange
        ByJQuery obj = new ByJQuery("selector");

        // Act
        ByJQuery returned = byJQuery.parentsUntil(obj);

        // Assert
        assertEquals("$(\"test\").parentsUntil($(\"selector\"))", returned.toString());
    }

    @Test
    void parentsUntil_ByJQueryStringPassed_ReturnsExpectedString() {

        // Arrange
        ByJQuery obj = new ByJQuery("selector");

        // Act
        ByJQuery returned = byJQuery.parentsUntil(obj, "filter");

        // Assert
        assertEquals("$(\"test\").parentsUntil($(\"selector\"),\"filter\")", returned.toString());
    }

    @Test
    void prev_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.prev();

        // Assert
        assertEquals("$(\"test\").prev()", returned.toString());
    }

    @Test
    void prev_StringCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.prev("selector");

        // Assert
        assertEquals("$(\"test\").prev(\"selector\")", returned.toString());
    }

    @Test
    void prevAll_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.prevAll();

        // Assert
        assertEquals("$(\"test\").prevAll()", returned.toString());
    }

    @Test
    void prevAll_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.prevAll("selector");

        // Assert
        assertEquals("$(\"test\").prevAll(\"selector\")", returned.toString());
    }

    @Test
    void prevUntil_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.prevUntil("selector");

        // Assert
        assertEquals("$(\"test\").prevUntil(\"selector\")", returned.toString());
    }

    @Test
    void prevUntil_TwoStringsPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.prevUntil("selector", "filter");

        // Assert
        assertEquals("$(\"test\").prevUntil(\"selector\",\"filter\")", returned.toString());
    }

    @Test
    void prevUntil_ByJQueryPassed_ReturnsExpectedString() {

        // Arrange
        ByJQuery obj = new ByJQuery("selector");

        // Act
        ByJQuery returned = byJQuery.prevUntil(obj);

        // Assert
        assertEquals("$(\"test\").prevUntil($(\"selector\"))", returned.toString());
    }

    @Test
    void prevUntil_ByJQueryStringPassed_ReturnsExpectedString() {

        // Arrange
        ByJQuery obj = new ByJQuery("selector");

        // Act
        ByJQuery returned = byJQuery.prevUntil(obj, "filter");

        // Assert
        assertEquals("$(\"test\").prevUntil($(\"selector\"),\"filter\")", returned.toString());
    }

    @Test
    void siblings_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.siblings();

        // Assert
        assertEquals("$(\"test\").siblings()", returned.toString());
    }

    @Test
    void siblings_StringPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.siblings("selector");

        // Assert
        assertEquals("$(\"test\").siblings(\"selector\")", returned.toString());
    }

    @Test
    void slice_IntPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.slice(5);

        // Assert
        assertEquals("$(\"test\").slice(5)", returned.toString());
    }

    @Test
    void slice_ByJQueryWithArithmeticOperatorOverloadPassed_ReturnsExpectedString() {

        // Arrange
        ByJQueryWithArithmeticOperatorOverload start = new ByJQueryWithArithmeticOperatorOverload("5");

        // Act
        ByJQuery returned = byJQuery.slice(start);

        // Assert
        assertEquals("$(\"test\").slice(5)", returned.toString());
    }

    @Test
    void slice_TwoIntsPassed_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.slice(5, 6);

        // Assert
        assertEquals("$(\"test\").slice(5,6)", returned.toString());
    }

    @Test
    void slice_ByJQueryWithArithmeticOperatorOverloadIntSPassed_ReturnsExpectedString() {

        // Arrange
        ByJQueryWithArithmeticOperatorOverload start = new ByJQueryWithArithmeticOperatorOverload("5");

        // Act
        ByJQuery returned = byJQuery.slice(start, 6);

        // Assert
        assertEquals("$(\"test\").slice(5,6)", returned.toString());
    }

    @Test
    void slice_IntByJQueryWithArithmeticOperatorOverloadPassed_ReturnsExpectedString() {

        // Arrange
        ByJQueryWithArithmeticOperatorOverload end = new ByJQueryWithArithmeticOperatorOverload("6");

        // Act
        ByJQuery returned = byJQuery.slice(5, end);

        // Assert
        assertEquals("$(\"test\").slice(5,6)", returned.toString());
    }

    @Test
    void slice_TwoByJQueryWithArithmeticOperatorOverloadsPassed_ReturnsExpectedString() {

        // Arrange
        ByJQueryWithArithmeticOperatorOverload start = new ByJQueryWithArithmeticOperatorOverload("5");
        ByJQueryWithArithmeticOperatorOverload end = new ByJQueryWithArithmeticOperatorOverload("6");

        // Act
        ByJQuery returned = byJQuery.slice(start, end);

        // Assert
        assertEquals("$(\"test\").slice(5,6)", returned.toString());
    }

    @Test
    void toLowerCase_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.toLowerCase();

        // Assert
        assertEquals("$(\"test\").toLowerCase()", returned.toString());
    }

    @Test
    void text_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.text();

        // Assert
        assertEquals("$(\"test\").text()", returned.toString());
    }

    @Test
    void val_WhenCalled_ReturnsExpectedString() {

        // Arrange

        // Act
        ByJQuery returned = byJQuery.val();

        // Assert
        assertEquals("$(\"test\").val()", returned.toString());
    }


    @Test
    void toString_JQueryStringTypePassed_ReturnsExpectedString() {

        // Arrange

        // Act
        String asSubchain = byJQuery.toString(JQueryStringType.AS_SUBCHAIN);
        String blurElement = byJQuery.toString(JQueryStringType.BLUR_ELEMENT);
        String clickInvisibleElement = byJQuery.toString(JQueryStringType.CLICK_INVISIBLE_ELEMENT);
        String fireChangeEvent = byJQuery.toString(JQueryStringType.FIRE_CHANGE_EVENT);
        String fireDoubleClick = byJQuery.toString(JQueryStringType.FIRE_DOUBLE_CLICK);
        String getClientRects = byJQuery.toString(JQueryStringType.GET_CLIENT_RECTS);
        String hasNumberOfOptions = byJQuery.toString(JQueryStringType.HAS_NUMBER_OF_OPTIONS);
        String justTheJquerySelector = byJQuery.toString(JQueryStringType.JUST_THE_JQUERY_SELECTOR);
        String mouseOut = byJQuery.toString(JQueryStringType.MOUSE_OUT);
        String mouseOver = byJQuery.toString(JQueryStringType.MOUSE_OVER);
        String returnElementArray = byJQuery.toString(JQueryStringType.RETURN_ELEMENT_ARRAY);
        String scrollElementIntoView = byJQuery.toString(JQueryStringType.SCROLL_ELEMENT_INTO_VIEW);
        String setBodyText = byJQuery.toString(JQueryStringType.SET_BODY_TEXT);
        String setDivText = byJQuery.toString(JQueryStringType.SET_DIV_TEXT);
        String setMaskedInputText = byJQuery.toString(JQueryStringType.SET_MASKED_INPUT_TEXT);
        String showContextMenu = byJQuery.toString(JQueryStringType.SHOW_CONTEXT_MENU);
        String showElement = byJQuery.toString(JQueryStringType.SHOW_ELEMENT);
        String setElementText = byJQuery.toString(JQueryStringType.SET_ELEMENT_TEXT);
        Executable action = () -> byJQuery.toString(JQueryStringType.GET_OPTIONS);

        // Assert
        assertEquals("find(\"test\")", asSubchain);
        assertEquals("var a=$(\"test\");if(a.length>0){a[0].focus();a[0].blur();}", blurElement);
        assertEquals("var a=$(\"test\");if(a.length>0){a.first().mousedown();a.first().mouseup();a[0].click()}return a.length;", clickInvisibleElement);
        assertEquals("var a=$(\"test\");if(a.length>0)a.change();return a.length;", fireChangeEvent);
        assertEquals("$(\"test\").dblclick()", fireDoubleClick);
        assertEquals("var rects = $(\"test\")[0].getClientRects(); var arr = [rects.length, rects[0].bottom, rects[0].left, rects[0].right, rects[0].top]; return arr;", getClientRects);
        assertEquals("var a=$(\"test\");return a.length>0?a.children().length:-1;", hasNumberOfOptions);
        assertEquals("$(\"test\")", justTheJquerySelector);
        assertEquals("var a=$(\"test\");if(a.length>0)a.mouseout();return a.length;", mouseOut);
        assertEquals("var a=$(\"test\");if(a.length>0)a.mouseover();return a.length;", mouseOver);
        assertEquals("return $.makeArray($(\"test\"));", returnElementArray);
        assertEquals("var a=$(\"test\");if(a.length>0)a[0].scrollIntoView(false);return a.length;", scrollElementIntoView);
        assertEquals("var a=$(\"test\");if(a.length>0)a.text(%1$s);return a.length;", setBodyText);
        assertEquals("var a=$(\"test\");if(a.length>0)a.html(%1$s);return a.length;", setDivText);
        assertEquals("var a=$(\"test\");if(a.length>0)a.val(%1$s).blur();return a.length;", setMaskedInputText);
        assertEquals("var a=$(\"test\");if(a.length>0)a.trigger({type:'mousedown', which: 3});return a.length;", showContextMenu);
        assertEquals("var a=$(\"test\");if(a.length>0)a.show();return a.length;", showElement);
        assertEquals("var a=$(\"test\");if(a.length>0)a.val(%1$s);return a.length;", setElementText);

        assertThrows(UnsupportedOperationException.class, action);
    }
}

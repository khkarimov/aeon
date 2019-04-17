package com.ultimatesoftware.aeon.core.common.web.selectors;

import com.ultimatesoftware.aeon.core.common.web.JQueryStringType;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByJQuery;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for selecting elements with jquery.
 */
public class ByJQuery implements IByWeb, IByJQuery {

    /**
     * Gets the function name.
     */
    private String function;
    /**
     * Gets the parameters for the function.
     */
    private Iterable<Parameter> parameters;
    /**
     * Gets the previous function.
     */
    private ByJQuery predecessor;
    /**
     * Defining constant strings.
     */
    private final String filter = "filter";
    private final String index = "index";
    private final String nextUntil = "nextUntil";
    private final String parentsUntil = "parentsUntil";
    private final String prevUntil = "prevUntil";
    private final String slice = "slice";

    /**
     * Accepts a string containing a CSS selector which is then used to match a set of elements.
     *
     * @param selector A string containing a selector expression.
     */
    public ByJQuery(String selector) {
        setFunction("$");
        setParameters(new Parameter(selector));
        setPredecessor(null);
    }

    /**
     * Accepts an existing jquery object which is then used to match a set of elements.
     *
     * @param obj An existing jquery object to clone.
     */

    public ByJQuery(ByJQuery obj) {
        setFunction("$");
        setParameters(new Parameter(obj));
        setPredecessor(null);
    }

    /**
     * Initializes a new instance of the {@link ByJQuery} class.
     *
     * @param predecessor The previous function.
     * @param function    The function name.
     * @param parameters  The parameters for the function.
     */
    protected ByJQuery(ByJQuery predecessor, String function, Object... parameters) {
        setFunction(function);
        setParameters(Arrays.stream(parameters).map(Parameter::new).collect(Collectors.toList()));
        setPredecessor(predecessor);
    }

    /**
     * Appends {@link ByJQuery} objects to {@code appendee}.
     *
     * @param appendee   The first {@link ByJQuery} object in the chain.
     * @param appendices The next {@link ByJQuery} objects in the chain.
     * @return The {@link ByJQuery} object for chaining.
     * @throws IllegalArgumentException If {@code appendee} is {@code null}.
     */
    public static ByJQuery append(ByJQuery appendee, ByJQuery... appendices) {
        if (appendee == null) {
            throw new IllegalArgumentException("appendee");
        }

        if (appendices == null) {
            throw new IllegalArgumentException("appendices");
        }

        appendee = appendee.clone();
        if (appendices.length == 0) {
            return appendee;
        }

        List<ByJQuery> jqAppendices =
                Arrays.stream(appendices).map(ByJQuery::clone).collect(Collectors.toList());

        for (int i = 0; i < jqAppendices.size() - 1; ++i) {
            changeLastPredecessor(appendices[i], appendices[i + 1]);
        }

        changeLastPredecessor(appendee, appendices[appendices.length - 1]);
        return appendices[appendices.length - 1];
    }

    private static void changeLastPredecessor(ByJQuery appendee, ByJQuery changee) {
        ByJQuery current = changee;
        while (current.predecessor != null) {
            current = current.predecessor;
        }
        current.predecessor = appendee;
        current.function = "find";
    }

    /**
     * For those jquery commands which return an integer.
     *
     * @param obj      A {@link ByJQuery} object representing a function which returns an integer.
     * @param constant A constant integer.
     * @return a new {@link ByJQueryWithArithmeticOperatorOverload} with the completed addition.
     */
    public static ByJQueryWithArithmeticOperatorOverload opAddition(ByJQuery obj, int constant) {
        return new ByJQueryWithArithmeticOperatorOverload(String.format("%1$s + %2$s", obj, constant));
    }

    /**
     * For those jquery commands which return an integer.
     *
     * @param obj      A {@link ByJQuery} object representing a function which returns an integer.
     * @param constant A constant integer.
     * @return The {@link ByJQueryWithArithmeticOperatorOverload} object for chaining.
     */
    public static ByJQueryWithArithmeticOperatorOverload opSubtraction(ByJQuery obj, int constant) {
        return new ByJQueryWithArithmeticOperatorOverload(String.format("%1$s - %2$s", obj, constant));
    }

    /**
     * Gets the function name.
     *
     * @return the function name.
     */
    protected final String getFunction() {
        return function;
    }

    /**
     * Sets the function name.
     *
     * @param value the new function name.
     */
    private void setFunction(String value) {
        function = value;
    }

    /**
     * Gets the parameters.
     *
     * @return the parameters.
     */
    protected final Iterable<Parameter> getParameters() {
        return parameters;
    }

    /**
     * Sets the parameters.
     *
     * @param value the new parameters.
     */
    private void setParameters(Parameter... value) {
        parameters = Arrays.asList(value);
    }

    /**
     * Sets the parameters.
     *
     * @param value the new parameters.
     */
    private void setParameters(Iterable<Parameter> value) {
        parameters = value;
    }

    /**
     * Gets the predecessor {@link ByJQuery}.
     *
     * @return the {@link ByJQuery} predecessor.
     */
    protected final ByJQuery getPredecessor() {
        return predecessor;
    }

    /**
     * Sets the predecessor {@link ByJQuery}.
     *
     * @param value the new predecessor.
     */
    private void setPredecessor(ByJQuery value) {
        predecessor = value;
    }

    /**
     * Converts the current instance to {@link ByJQuery}.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    public final ByJQuery toJQuery() {
        return this;
    }

    /**
     * Clones the {@link ByJQuery} object by performing a deep copy.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery clone() {
        List<Object> objects = new ArrayList<>();
        parameters.forEach(objects::add);
        return new ByJQuery(predecessor == null ? null : predecessor.clone(), function, objects);
    }

    /**
     * Returns a {@link String} that represents the current {@link Object}.
     *
     * @return A {@link String} that represents the current {@link Object}.
     */
    @Override
    public String toString() {
        List<String> parameterStrings = new ArrayList<>();
        parameters.forEach(x -> parameterStrings.add(x.toString()));
        String joinedParameters = String.join(",", parameterStrings);
        return predecessor == null ? String.format("%1$s(%2$s)", function, joinedParameters) : String.format("%1$s.%2$s(%3$s)", predecessor, function, joinedParameters);
    }

    /**
     * Returns a {@link String} that represents the current {@link Object}.
     *
     * @param type The type of string to which to convert.
     * @return A {@link String} that represents the current {@link Object}.
     */
    @Override
    public final String toString(JQueryStringType type) {
        switch (type) {
            case AS_SUBCHAIN:
                return toString().replaceFirst("\\$\\(", "find(");
            case BLUR_ELEMENT:
                return String.format("var a=%1$s;if(a.length>0){a[0].focus();a[0].blur();}", toString());
            case CLICK_INVISIBLE_ELEMENT:
                return String.format("var a=%1$s;if(a.length>0){a.first().mousedown();a.first().mouseup();a[0].click()}return a.length;", toString());
            case FIRE_CHANGE_EVENT:
                return String.format("var a=%1$s;if(a.length>0)a.change();return a.length;", toString());
            case JUST_THE_JQUERY_SELECTOR:
                return toString();
            case MOUSE_OUT:
                return String.format("var a=%1$s;if(a.length>0)a.mouseout();return a.length;", toString());
            case MOUSE_OVER:
                return String.format("var a=%1$s;if(a.length>0)a.mouseover();return a.length;", toString());
            case RETURN_ELEMENT_ARRAY:
                return String.format("return $.makeArray(%1$s);", toString());
            case SCROLL_ELEMENT_INTO_VIEW:
                return String.format("var a=%1$s;if(a.length>0)a[0].scrollIntoView(false);return a.length;", toString());
            case SET_ELEMENT_TEXT:
                return String.format("var a=%1$s;if(a.length>0)a.val(%%1$s);return a.length;", toString());
            case SET_BODY_TEXT:
                return String.format("var a=%1$s;if(a.length>0)a.text(%%1$s);return a.length;", toString());
            case SET_DIV_TEXT:
                return String.format("var a=%1$s;if(a.length>0)a.html(%%1$s);return a.length;", toString());
            case SHOW_ELEMENT:
                return String.format("var a=%1$s;if(a.length>0)a.show();return a.length;", toString());
            case SET_MASKED_INPUT_TEXT:
                return String.format("var a=%1$s;if(a.length>0)a.val(%%1$s).blur();return a.length;", toString());
            case HAS_NUMBER_OF_OPTIONS:
                return String.format("var a=%1$s;return a.length>0?a.children().length:-1;", toString());
            case GET_CLIENT_RECTS:
                return String.format("var rects = %1$s[0].getClientRects(); var arr = [rects.length, rects[0].bottom, rects[0].left, rects[0].right, rects[0].top]; return arr;", toString());
            case SHOW_CONTEXT_MENU:
                return String.format("var a=%1$s;if(a.length>0)a.trigger({type:'mousedown', which: 3});return a.length;", toString());
            case FIRE_DOUBLE_CLICK:
                return String.format("%1s.dblclick()", toString());
            default:
                throw new UnsupportedOperationException();
        }
    }

    /**
     * Add elements to the set of matched elements.
     *
     * @param selector A string representing a selector expression to find additional elements to add to the set of matched elements.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery add(String selector) {
        return new ByJQuery(this, "add", selector);
    }

    /**
     * Add elements to the set of matched elements.
     *
     * @param obj An existing jquery object to add to the set of matched elements.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery add(ByJQuery obj) {
        return new ByJQuery(this, "add", obj);
    }

    /**
     * Add the previous set of elements on the stack to the current set.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery andSelf() {
        return new ByJQuery(this, "andSelf");
    }

    /**
     * Get the value of an attribute for the first element in the set of matched elements.
     *
     * @param attributeName The name of the attribute to get.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery attr(String attributeName) {
        return new ByJQuery(this, "attr", attributeName);
    }

    /**
     * Get the children of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery children(String selector) {
        return new ByJQuery(this, "children", selector);
    }

    /**
     * Get the children of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery children() {
        return new ByJQuery(this, "children");
    }

    /**
     * Get the first element that matches the selector, beginning at the current element and progressing up through the DOM tree.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery closest(String selector) {
        return new ByJQuery(this, "closest", selector);
    }

    /**
     * Get the first element that matches the selector, beginning at the current element and progressing up through the DOM tree.
     *
     * @param obj A jquery object to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery closest(ByJQuery obj) {
        return new ByJQuery(this, "closest", obj);
    }

    /**
     * Get the children of each element in the set of matched elements, including text and comment nodes.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery contents() {
        return new ByJQuery(this, "contents");
    }

    /**
     * End the most recent filtering operation in the current chain and return the set of matched elements to its previous state.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery end() {
        return new ByJQuery(this, "end");
    }

    /**
     * Reduce the set of matched elements to the one at the specified index.
     *
     * @param index An integer indicating the 0-based position of the element.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery eq(int index) {
        return new ByJQuery(this, "eq", index);
    }

    /**
     * Reduce the set of matched elements to the one at the specified index.
     *
     * @param index An integer indicating the 0-based position of the element.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery eq(ByJQueryWithArithmeticOperatorOverload index) {
        return new ByJQuery(this, "eq", index);
    }

    /**
     * Reduce the set of matched elements to those that match the selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery filter(String selector) {
        return new ByJQuery(this, filter, selector);
    }

    /**
     * Reduce the set of matched elements to those that pass the function's test.
     *
     * @param function A string containing a function expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery filter(InlineJavaScript function) {
        return new ByJQuery(this, filter, function);
    }

    /**
     * Reduce the set of matched elements to those that match the selector or pass the function's test.
     *
     * @param obj An existing jquery object to match the current set of elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery filter(ByJQuery obj) {
        return new ByJQuery(this, filter, obj);
    }

    /**
     * Get the descendants of each element in the current set of matched elements, filtered by a selector, jquery object, or element.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery find(String selector) {
        return new ByJQuery(this, "find", selector);
    }

    /**
     * Get the descendants of each element in the current set of matched elements, filtered by a selector, jquery object, or element.
     *
     * @param obj A jquery object to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery find(ByJQuery obj) {
        return new ByJQuery(this, "find", obj);
    }

    @Override
    public final IByWeb find(IByWeb selector) {
        return find(selector.toJQuery());
    }

    /**
     * Reduce the set of matched elements to the first in the set.
     *
     * @return new instance of a {@link ByJQuery} object that is first.
     */
    @Override
    public final ByJQuery first() {
        return new ByJQuery(this, "first");
    }

    /**
     * Reduce the set of matched elements to those that have a descendant that matches the selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery has(String selector) {
        return new ByJQuery(this, "has", selector);
    }

    /**
     * Get the HTML contents of the first element in the set of matched elements.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery html() {
        return new ByJQuery(this, "html");
    }

    /**
     * Search for a given element from among the matched elements.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery index() {
        return new ByJQuery(this, index);
    }

    /**
     * Search for a given element from among the matched elements.
     *
     * @param selector A selector representing a jquery collection in which to look for an element.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery index(String selector) {
        return new ByJQuery(this, index, selector);
    }

    /**
     * Search for a given element from among the matched elements.
     *
     * @param obj The first element within the jquery object to look for.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery index(ByJQuery obj) {
        return new ByJQuery(this, index, obj);
    }

    /**
     * Returns the index within the calling <code>String</code> object of the first occurrence of the specified value, returns <code>-1</code> if the value is not found.
     *
     * @param searchValue A string representing the value to search for.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery indexOf(String searchValue) {
        return new ByJQuery(this, "indexOf", searchValue);
    }

    /**
     * Returns the index within the calling {@link String} object of the first occurrence of the specified value, starting the search at {@code fromIndex}, returns {@code -1} if the value is not found.
     *
     * @param searchValue A string representing the value to search for.
     * @param fromIndex   The location within the calling string to start the search from. It can be any integer between 0 and the length of the string. The default value is 0.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery indexOf(String searchValue, int fromIndex) {
        return new ByJQuery(this, "indexOf", searchValue, fromIndex);
    }

    /**
     * Check the current matched set of elements against a selector, element, or jquery object and return true if at least one of these elements matches the given arguments.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery is(String selector) {
        return new ByJQuery(this, "is", selector);
    }

    /**
     * check the current matched set of elements against a selector, element, or jquery object and return true if at least one of these elements matches the given arguments.
     *
     * @param obj An existing jquery object to match the current set of elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery is(ByJQuery obj) {
        return new ByJQuery(this, "is", obj);
    }

    /**
     * Reduce the set of matched elements to the final one in the set.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery last() {
        return new ByJQuery(this, "last");
    }

    /**
     * Get the immediately following sibling of each element in the set of matched elements. If a selector is provided, it retrieves the next sibling only if it matches that selector.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery next() {
        return new ByJQuery(this, "next");
    }

    /**
     * Get the immediately following sibling of each element in the set of matched elements. If a selector is provided, it retrieves the next sibling only if it matches that selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery next(String selector) {
        return new ByJQuery(this, "next", selector);
    }

    /**
     * Get all following siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery nextAll() {
        return new ByJQuery(this, "nextAll");
    }

    /**
     * Get all following siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery nextAll(String selector) {
        return new ByJQuery(this, "nextAll", selector);
    }

    /**
     * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jquery object passed.
     *
     * @param selector A string containing a selector expression to indicate where to stop matching following sibling elements.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery nextUntil(String selector) {
        return new ByJQuery(this, nextUntil, selector);
    }

    /**
     * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jquery object passed.
     *
     * @param selector A string containing a selector expression to indicate where to stop matching following sibling elements.
     * @param filter   A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery nextUntil(String selector, String filter) {
        return new ByJQuery(this, nextUntil, selector, filter);
    }

    /**
     * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jquery object passed.
     *
     * @param obj A jquery object indicating where to stop matching following sibling elements.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery nextUntil(ByJQuery obj) {
        return new ByJQuery(this, nextUntil, obj);
    }

    /**
     * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jquery object passed.
     *
     * @param obj    A jquery object indicating where to stop matching following sibling elements.
     * @param filter A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery nextUntil(ByJQuery obj, String filter) {
        return new ByJQuery(this, nextUntil, obj, filter);
    }

    /**
     * remove elements from the set of matched elements.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery not(String selector) {
        return new ByJQuery(this, "not", selector);
    }

    /**
     * remove elements from the set of matched elements.
     *
     * @param obj An existing jquery object to match the current set of elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery not(ByJQuery obj) {
        return new ByJQuery(this, "not", obj);
    }


    /**
     * Get the closest ancestor element that is positioned.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery offsetParent() {
        return new ByJQuery(this, "offsetParent");
    }

    /**
     * Get the parent of each element in the current set of matched elements, optionally filtered by a selector.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery parent() {
        return new ByJQuery(this, "parent");
    }

    /**
     * Get the parent of each element in the current set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery parent(String selector) {
        return new ByJQuery(this, "parent", selector);
    }

    /**
     * Get the ancestors of each element in the current set of matched elements, optionally filtered by a selector.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery parents() {
        return new ByJQuery(this, "parents");
    }

    /**
     * Get the ancestors of each element in the current set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery parents(String selector) {
        return new ByJQuery(this, "parents", selector);
    }

    /**
     * Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jquery object.
     *
     * @param selector A string containing a selector expression to indicate where to stop matching following sibling elements.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery parentsUntil(String selector) {
        return new ByJQuery(this, parentsUntil, selector);
    }

    /**
     * Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jquery object.
     *
     * @param selector A string containing a selector expression to indicate where to stop matching following sibling elements.
     * @param filter   A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery parentsUntil(String selector, String filter) {
        return new ByJQuery(this, parentsUntil, selector, filter);
    }

    /**
     * Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jquery object.
     *
     * @param obj A jquery object indicating where to stop matching following sibling elements.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery parentsUntil(ByJQuery obj) {
        return new ByJQuery(this, parentsUntil, obj);
    }

    /**
     * Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jquery object.
     *
     * @param obj    A jquery object indicating where to stop matching following sibling elements.
     * @param filter A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery parentsUntil(ByJQuery obj, String filter) {
        return new ByJQuery(this, parentsUntil, obj, filter);
    }

    /**
     * Get the immediately preceding sibling of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery prev() {
        return new ByJQuery(this, "prev");
    }

    /**
     * Get the immediately preceding sibling of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery prev(String selector) {
        return new ByJQuery(this, "prev", selector);
    }

    /**
     * Get all preceding siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery prevAll() {
        return new ByJQuery(this, "prevAll");
    }

    /**
     * Get all preceding siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery prevAll(String selector) {
        return new ByJQuery(this, "prevAll", selector);
    }

    /**
     * Get all preceding siblings of each element up to but not including the element matched by the selector, DOM node, or jquery object.
     *
     * @param selector A string containing a selector expression to indicate where to stop matching preceding sibling elements.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery prevUntil(String selector) {
        return new ByJQuery(this, prevUntil, selector);
    }

    /**
     * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jquery object passed.
     *
     * @param selector A string containing a selector expression to indicate where to stop matching preceding sibling elements.
     * @param filter   A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery prevUntil(String selector, String filter) {
        return new ByJQuery(this, prevUntil, selector, filter);
    }

    /**
     * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jquery object passed.
     *
     * @param obj A jquery object indicating where to stop matching preceding sibling elements.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery prevUntil(ByJQuery obj) {
        return new ByJQuery(this, prevUntil, obj);
    }

    /**
     * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jquery object passed.
     *
     * @param obj    A jquery object indicating where to stop matching preceding sibling elements.
     * @param filter A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery prevUntil(ByJQuery obj, String filter) {
        return new ByJQuery(this, prevUntil, obj, filter);
    }

    /**
     * Get the siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery siblings() {
        return new ByJQuery(this, "siblings");
    }

    /**
     * Get the siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery siblings(String selector) {
        return new ByJQuery(this, "siblings", selector);
    }

    /**
     * Reduce the set of matched elements to a subset specified by a range of indices.
     *
     * @param start An integer indicating the 0-based position at which the elements begin to be selected. If negative, it indicates an offset from the end of the set.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery slice(int start) {
        return new ByJQuery(this, slice, start);
    }

    /**
     * Reduce the set of matched elements to a subset specified by a range of indices.
     *
     * @param start An integer indicating the 0-based position at which the elements begin to be selected. If negative, it indicates an offset from the end of the set.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery slice(ByJQueryWithArithmeticOperatorOverload start) {
        return new ByJQuery(this, slice, start);
    }

    /**
     * Get the siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param start An integer indicating the 0-based position at which the elements begin to be selected. If negative, it indicates an offset from the end of the set.
     * @param end   An integer indicating the 0-based position at which the elements stop being selected. If negative, it indicates an offset from the end of the set. If omitted, the range continues until the end of the set.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery slice(int start, int end) {
        return new ByJQuery(this, slice, start, end);
    }

    /**
     * Get the siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param start An integer indicating the 0-based position at which the elements begin to be selected. If negative, it indicates an offset from the end of the set.
     * @param end   An integer indicating the 0-based position at which the elements stop being selected. If negative, it indicates an offset from the end of the set. If omitted, the range continues until the end of the set.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery slice(ByJQueryWithArithmeticOperatorOverload start, int end) {
        return new ByJQuery(this, slice, start, end);
    }

    /**
     * Get the siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param start An integer indicating the 0-based position at which the elements begin to be selected. If negative, it indicates an offset from the end of the set.
     * @param end   An integer indicating the 0-based position at which the elements stop being selected. If negative, it indicates an offset from the end of the set. If omitted, the range continues until the end of the set.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery slice(int start, ByJQueryWithArithmeticOperatorOverload end) {
        return new ByJQuery(this, slice, start, end);
    }

    /**
     * Get the siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param start An integer indicating the 0-based position at which the elements begin to be selected. If negative, it indicates an offset from the end of the set.
     * @param end   An integer indicating the 0-based position at which the elements stop being selected. If negative, it indicates an offset from the end of the set. If omitted, the range continues until the end of the set.
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery slice(ByJQueryWithArithmeticOperatorOverload start, ByJQueryWithArithmeticOperatorOverload end) {
        return new ByJQuery(this, slice, start, end);
    }

    /**
     * Returns the calling string value converted to lowercase.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery toLowerCase() {
        return new ByJQuery(this, "toLowerCase");
    }

    /**
     * Get the combined text contents of each element in the set of matched elements, including their descendants.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery text() {
        return new ByJQuery(this, "text");
    }

    /**
     * Get the current value of the first element in the set of matched elements.
     *
     * @return The {@link ByJQuery} object for chaining.
     */
    @Override
    public final ByJQuery val() {
        return new ByJQuery(this, "val");
    }
}

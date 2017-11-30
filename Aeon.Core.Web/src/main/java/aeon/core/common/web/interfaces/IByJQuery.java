package aeon.core.common.web.interfaces;

import aeon.core.common.web.JQueryStringType;
import aeon.core.common.web.selectors.ByJQuery;
import aeon.core.common.web.selectors.ByJQueryWithArithmeticOperatorOverload;
import com.sun.javafx.fxml.expression.Expression;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Interface for ByJQuery commands.
 */
public interface IByJQuery {

    /**
     * Returns a {@link String} that represents the current {@link Object}.
     *
     * @param type The type of string to which to convert.
     * @return A {@link String} that represents the current {@link Object}.
     */
    String toString(JQueryStringType type);

    /**
     * Add elements to the set of matched elements.
     *
     * @param selector A string representing a selector expression to find additional elements to add to the set of matched elements.
     * @return {@link ByJQuery} for matched elements.
     */
    ByJQuery add(String selector);

    /**
     * Add elements to the set of matched elements.
     *
     * @param obj An existing jquery object to add to the set of matched elements.
     * @return The {@link ByJQuery} for the matched elements.
     */
    ByJQuery add(ByJQuery obj);

    /**
     * Add the previous set of elements on the stack to the current set.
     *
     * @return The {@link ByJQuery} combining the previous and current set of matched elements.
     */
    ByJQuery andSelf();

    /**
     * Get the value of an attribute for the first element in the set of matched elements.
     *
     * @param attributeName The name of the attribute to get.
     * @return The {@link ByJQuery} of the matched element.
     */
    ByJQuery attr(String attributeName);

    /**
     * Get the children of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} for the children of the matched elements.
     */
    ByJQuery children(String selector);

    /**
     * Get the children of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @return The {@link ByJQuery} for the children of the matched elements.
     */
    ByJQuery children();

    /**
     * Get the first element that matches the selector, beginning at the current element and progressing up through the DOM tree.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery} of the first element matching given selector.
     */
    ByJQuery closest(String selector);

    /**
     * Get the first element that matches the selector, beginning at the current element and progressing up through the DOM tree.
     *
     * @param obj A jquery object to match elements against.
     * @return The {@link ByJQuery} of the first element that matches the given {@link ByJQuery} Object.
     */
    ByJQuery closest(ByJQuery obj);

    /**
     * Get the children of each element in the set of matched elements, including text and comment nodes.
     *
     * @return The {@link ByJQuery} of all children of each matched element, including text and comment nodes.
     */
    ByJQuery contents();

    /**
     * Iterate over a jquery object, executing a function for each matched element.
     *
     * @param function A function to execute for each matched element.
     * @return The {@link ByJQuery}
     */
    ByJQuery each(Expression<BiFunction<Integer, ByJQuery, Boolean>> function);

    /**
     * End the most recent filtering operation in the current chain and return the set of matched elements to its previous state.
     *
     * @return The {@link ByJQuery}
     */
    ByJQuery end();

    /**
     * Reduce the set of matched elements to the one at the specified index.
     *
     * @param index An integer indicating the 0-based position of the element.
     * @return The {@link ByJQuery}
     */
    ByJQuery eq(int index);

    /**
     * Reduce the set of matched elements to the one at the specified index.
     *
     * @param index An integer indicating the 0-based position of the element.
     * @return The {@link ByJQuery}
     */
    ByJQuery eq(ByJQueryWithArithmeticOperatorOverload index);

    /**
     * Reduce the set of matched elements to those that match the selector or pass the function's test.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery filter(String selector);

    /**
     * Reduce the set of matched elements to those that match the selector or pass the function's test.
     *
     * @param obj An existing jquery object to match the current set of elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery filter(ByJQuery obj);

    /**
     * Reduce the set of matched elements to those that match the selector or pass the function's test.
     *
     * @param function A function used as a test for each element in the set. <code>this</code> is the current DOM element.
     * @return The {@link ByJQuery}
     */
    ByJQuery filter(Expression<Function<Integer, Boolean>> function);

    /**
     * Get the descendants of each element in the current set of matched elements, filtered by a selector, jquery object, or element.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery find(String selector);

    /**
     * Get the descendants of each element in the current set of matched elements, filtered by a selector, jquery object, or element.
     *
     * @param obj A jquery object to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery find(ByJQuery obj);

    /**
     * Reduce the set of matched elements to the first in the set.
     *
     * @return The {@link ByJQuery}
     */
    ByJQuery first();

    /**
     * Reduce the set of matched elements to those that have a descendant that matches the selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery has(String selector);

    /**
     * Get the HTML contents of the first element in the set of matched elements.
     *
     * @return The {@link ByJQuery}
     */
    ByJQuery html();

    /**
     * Search for a given element from among the matched elements.
     *
     * @return The {@link ByJQuery}
     */
    ByJQuery index();

    /**
     * Search for a given element from among the matched elements.
     *
     * @param selector A selector representing a jquery collection in which to look for an element.
     * @return The {@link ByJQuery}
     */
    ByJQuery index(String selector);

    /**
     * Search for a given element from among the matched elements.
     *
     * @param obj The first element within the jquery object to look for.
     * @return The {@link ByJQuery}
     */
    ByJQuery index(ByJQuery obj);

    /**
     * Returns the index within the calling {@link String} object of the first occurrence of the specified value, returns {@code -1} if the value is not found.
     *
     * @param searchValue A string representing the value to search for.
     * @return The {@link ByJQuery}
     */
    ByJQuery indexOf(String searchValue);

    /**
     * Returns the index within the calling {@link String} object of the first occurrence of the specified value, starting the search at {@code fromIndex}, returns {@code -1} if the value is not found.
     *
     * @param searchValue A string representing the value to search for.
     * @param fromIndex   The location within the calling string to start the search from. It can be any integer between {@code 0} and the length of the string. The default value is {@code 0}.
     * @return The {@link ByJQuery}
     */
    ByJQuery indexOf(String searchValue, int fromIndex);

    /**
     * Check the current matched set of elements against a selector, element, or jquery object and return true if at least one of these elements matches the given arguments.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery is(String selector);

    /**
     * check the current matched set of elements against a selector, element, or jquery object and return true if at least one of these elements matches the given arguments.
     *
     * @param obj An existing jquery object to match the current set of elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery is(ByJQuery obj);

    /**
     * check the current matched set of elements against a selector, element, or jquery object and return true if at least one of these elements matches the given arguments.
     *
     * @param function A function used as a test for the set of elements. It accepts one argument which is the element's index in the jquery collection. Within the function, <code>this</code> refers to the current DOM element.
     * @return The {@link ByJQuery}
     */
    ByJQuery is(Expression<Function<Integer, Boolean>> function);

    /**
     * Reduce the set of matched elements to the final one in the set.
     *
     * @return The {@link ByJQuery}
     */
    ByJQuery last();

    /**
     * Pass each element in the current matched set through a function, producing a new jquery object containing the return values.
     *
     * @param callback A function object that will be invoked for each element in the current set.
     * @return The {@link ByJQuery}
     */
    ByJQuery map(Expression<BiFunction<Integer, ByJQuery, Iterable<ByJQuery>>> callback);

    /**
     * Get the immediately following sibling of each element in the set of matched elements. If a selector is provided, it retrieves the next sibling only if it matches that selector.
     *
     * @return The {@link ByJQuery}
     */
    ByJQuery next();

    /**
     * Get the immediately following sibling of each element in the set of matched elements. If a selector is provided, it retrieves the next sibling only if it matches that selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery next(String selector);

    /**
     * Get all following siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @return The {@link ByJQuery}
     */
    ByJQuery nextAll();

    /**
     * Get all following siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery nextAll(String selector);

    /**
     * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jquery object passed.
     *
     * @param selector A string containing a selector expression to indicate where to stop matching following sibling elements.
     * @return The {@link ByJQuery}
     */
    ByJQuery nextUntil(String selector);

    /**
     * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jquery object passed.
     *
     * @param selector A string containing a selector expression to indicate where to stop matching following sibling elements.
     * @param filter   A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery nextUntil(String selector, String filter);

    /**
     * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jquery object passed.
     *
     * @param obj A jquery object indicating where to stop matching following sibling elements.
     * @return The {@link ByJQuery}
     */
    ByJQuery nextUntil(ByJQuery obj);

    /**
     * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jquery object passed.
     *
     * @param obj    A jquery object indicating where to stop matching following sibling elements.
     * @param filter A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery nextUntil(ByJQuery obj, String filter);

    /**
     * remove elements from the set of matched elements.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery not(String selector);

    /**
     * remove elements from the set of matched elements.
     *
     * @param obj An existing jquery object to match the current set of elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery not(ByJQuery obj);

    /**
     * remove elements from the set of matched elements.
     *
     * @param function A function used as a test for each element in the set. <code>this</code> is the current DOM element.
     * @return The {@link ByJQuery}
     */
    ByJQuery not(Expression<Function<Integer, Boolean>> function);

    /**
     * Get the closest ancestor element that is positioned.
     *
     * @return The {@link ByJQuery}
     */
    ByJQuery offsetParent();

    /**
     * Get the parent of each element in the current set of matched elements, optionally filtered by a selector.
     *
     * @return The {@link ByJQuery}
     */
    ByJQuery parent();

    /**
     * Get the parent of each element in the current set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery parent(String selector);

    /**
     * Get the ancestors of each element in the current set of matched elements, optionally filtered by a selector.
     *
     * @return The {@link ByJQuery}
     */
    ByJQuery parents();

    /**
     * Get the ancestors of each element in the current set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery parents(String selector);

    /**
     * Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jquery object.
     *
     * @param selector A string containing a selector expression to indicate where to stop matching following sibling elements.
     * @return The {@link ByJQuery}
     */
    ByJQuery parentsUntil(String selector);

    /**
     * Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jquery object.
     *
     * @param selector A string containing a selector expression to indicate where to stop matching following sibling elements.
     * @param filter   A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery parentsUntil(String selector, String filter);

    /**
     * Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jquery object.
     *
     * @param obj A jquery object indicating where to stop matching following sibling elements.
     * @return The {@link ByJQuery}
     */
    ByJQuery parentsUntil(ByJQuery obj);

    /**
     * Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jquery object.
     *
     * @param obj    A jquery object indicating where to stop matching following sibling elements.
     * @param filter A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery parentsUntil(ByJQuery obj, String filter);

    /**
     * Get the immediately preceding sibling of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @return The {@link ByJQuery}
     */
    ByJQuery prev();

    /**
     * Get the immediately preceding sibling of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery prev(String selector);

    /**
     * Get all preceding siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @return The {@link ByJQuery}
     */
    ByJQuery prevAll();

    /**
     * Get all preceding siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery prevAll(String selector);

    /**
     * Get all preceding siblings of each element up to but not including the element matched by the selector, DOM node, or jquery object.
     *
     * @param selector A string containing a selector expression to indicate where to stop matching preceding sibling elements.
     * @return The {@link ByJQuery}
     */
    ByJQuery prevUntil(String selector);

    /**
     * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jquery object passed.
     *
     * @param selector A string containing a selector expression to indicate where to stop matching preceding sibling elements.
     * @param filter   A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery prevUntil(String selector, String filter);

    /**
     * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jquery object passed.
     *
     * @param obj A jquery object indicating where to stop matching preceding sibling elements.
     * @return The {@link ByJQuery}
     */
    ByJQuery prevUntil(ByJQuery obj);

    /**
     * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jquery object passed.
     *
     * @param obj    A jquery object indicating where to stop matching preceding sibling elements.
     * @param filter A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery prevUntil(ByJQuery obj, String filter);

    /**
     * Get the siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @return The {@link ByJQuery}
     */
    ByJQuery siblings();

    /**
     * Get the siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return The {@link ByJQuery}
     */
    ByJQuery siblings(String selector);

    /**
     * Reduce the set of matched elements to a subset specified by a range of indices.
     *
     * @param start An integer indicating the 0-based position at which the elements begin to be selected. If negative, it indicates an offset from the end of the set.
     * @return The {@link ByJQuery}
     */
    ByJQuery slice(int start);

    /**
     * Reduce the set of matched elements to a subset specified by a range of indices.
     *
     * @param start An integer indicating the 0-based position at which the elements begin to be selected. If negative, it indicates an offset from the end of the set.
     * @return The {@link ByJQuery}
     */
    ByJQuery slice(ByJQueryWithArithmeticOperatorOverload start);

    /**
     * Get the siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param start An integer indicating the 0-based position at which the elements begin to be selected. If negative, it indicates an offset from the end of the set.
     * @param end   An integer indicating the 0-based position at which the elements stop being selected. If negative, it indicates an offset from the end of the set. If omitted, the range continues until the end of the set.
     * @return The {@link ByJQuery}
     */
    ByJQuery slice(int start, int end);

    /**
     * Get the siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param start An integer indicating the 0-based position at which the elements begin to be selected. If negative, it indicates an offset from the end of the set.
     * @param end   An integer indicating the 0-based position at which the elements stop being selected. If negative, it indicates an offset from the end of the set. If omitted, the range continues until the end of the set.
     * @return The {@link ByJQuery}
     */
    ByJQuery slice(ByJQueryWithArithmeticOperatorOverload start, int end);

    /**
     * Get the siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param start An integer indicating the 0-based position at which the elements begin to be selected. If negative, it indicates an offset from the end of the set.
     * @param end   An integer indicating the 0-based position at which the elements stop being selected. If negative, it indicates an offset from the end of the set. If omitted, the range continues until the end of the set.
     * @return The {@link ByJQuery}
     */
    ByJQuery slice(int start, ByJQueryWithArithmeticOperatorOverload end);

    /**
     * Get the siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param start An integer indicating the 0-based position at which the elements begin to be selected. If negative, it indicates an offset from the end of the set.
     * @param end   An integer indicating the 0-based position at which the elements stop being selected. If negative, it indicates an offset from the end of the set. If omitted, the range continues until the end of the set.
     * @return The {@link ByJQuery}
     */
    ByJQuery slice(ByJQueryWithArithmeticOperatorOverload start, ByJQueryWithArithmeticOperatorOverload end);

    /**
     * Returns the calling string value converted to lowercase.
     *
     * @return The {@link ByJQuery}
     */
    ByJQuery toLowerCase();

    /**
     * Get the combined text contents of each element in the set of matched elements, including their descendants.
     *
     * @return The {@link ByJQuery}
     */
    ByJQuery text();

    /**
     * Get the current value of the first element in the set of matched elements.
     *
     * @return The {@link ByJQuery}
     */
    ByJQuery val();
}

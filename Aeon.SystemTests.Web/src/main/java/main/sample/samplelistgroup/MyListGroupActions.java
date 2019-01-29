package main.sample.samplelistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

/**
 * Class for finding list group items based on values of list group item elements.
 */
public class MyListGroupActions extends ListGroupActions<MyListGroupActions, MyComponent> {

    /**
     * Constructor.
     */
    public MyListGroupActions() {
        super(MyListGroupActions.class, MyComponent.class);

        // The element that defines an item in the list group.
        this.rowSelector = "li";
    }

    /**
     * Find a list group item based on a certain value in the "Name" element.
     * @param value The value to look for in the "Name" element.
     * @return An instance of the {@link MyListGroupActions} to support chaining and filtering by multiple elements.
     */
    public MyListGroupActions name(String value) {
        return findRow(value, "span.list-group-name");
    }

    /**
     * Find a list group item based on a certain value in the "Description" element.
     * @param value The value to look for in the "Description" element.
     * @return An instance of the {@link MyListGroupActions} to support chaining and filtering by multiple elements.
     */
    public MyListGroupActions description(String value){
        return findRow(value, "span.mdl-list__item-text-body");
    }
}

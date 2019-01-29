package main.sample.samplelistgroup;

import aeon.core.testabstraction.elements.web.ComponentList;

/**
 * Class for finding list group items based on values of list group item elements.
 */
public class MyComponentList extends ComponentList<MyComponentList, MyComponent> {

    /**
     * Constructor.
     */
    public MyComponentList() {
        super(MyComponentList.class, MyComponent.class);

        // The element that defines an item in the list group.
        this.rowSelector = "li";
    }

    /**
     * Find a list group item based on a certain value in the "Name" element.
     *
     * @param value The value to look for in the "Name" element.
     * @return An instance of the {@link MyComponentList} to support chaining and filtering by multiple elements.
     */
    public MyComponentList name(String value) {
        return findRow(value, "span.list-group-name");
    }

    /**
     * Find a list group item based on a certain value in the "Description" element.
     *
     * @param value The value to look for in the "Description" element.
     * @return An instance of the {@link MyComponentList} to support chaining and filtering by multiple elements.
     */
    public MyComponentList description(String value) {
        return findRow(value, "span.mdl-list__item-text-body");
    }
}

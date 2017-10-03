package main.sample.samplelistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

public class MyListGroupActions extends ListGroupActions<MyListGroupActions, MyListGroupElements> {
    public MyListGroupActions() {
        super(MyListGroupActions.class, MyListGroupElements.class);

        this.rowSelector = "li";
    }

    public MyListGroupActions name(String value) {
        return findRow(value, "span.list-group-name:contains(%1$s)");
    }

    public MyListGroupActions description(String value){
        return findRow(value, "span.mdl-list__item-text-body:contains(%1$s)");
    }
}

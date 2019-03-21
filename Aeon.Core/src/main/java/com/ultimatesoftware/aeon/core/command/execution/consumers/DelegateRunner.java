package com.ultimatesoftware.aeon.core.command.execution.consumers;

import com.ultimatesoftware.aeon.core.command.execution.consumers.interfaces.IDelegateRunner;

/**
 * Abstract class for Delegate Runner.
 */
abstract class DelegateRunner implements IDelegateRunner {

    IDelegateRunner successor;

    /**
     * Constructor for Delegate Runner class.
     *
     * @param successor sets the successor for the Delegate Runner.
     */
    DelegateRunner(IDelegateRunner successor) {
        this.successor = successor;
    }
}

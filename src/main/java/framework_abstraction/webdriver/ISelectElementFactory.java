package framework_abstraction.webdriver;

import common.logging.ILog;

import java.util.UUID;

/**
 * Factory for select elements.
 */
public interface ISelectElementFactory {
    /**
     * Creates a select element from a web element.
     *
     * @param guid       A globally unique identifier associated with this call.
     * @param webElement A web element.
     * @param log        A log.
     * @return A select element.
     * @throws IllegalArgumentException If <paramref name="webElement"/> is <see langword="null"/>.
     */
    IWebSelectElementAdapter CreateInstance(UUID guid, IWebElementAdapter webElement, ILog log);
}

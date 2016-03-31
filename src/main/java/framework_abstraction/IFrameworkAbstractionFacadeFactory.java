package framework_abstraction;

import common.parameters.ParameterObject;

/**
 * The factory for the <see cref="IFrameworkAbstractionFacade"/>.
 */
public interface IFrameworkAbstractionFacadeFactory {
    /**
     * Creates a new <see cref="IFrameworkAbstractionFacade"/>.
     *
     * @param parameterObject Framework Parameter object.
     * @return A <see cref="IFrameworkAbstractionFacade"/>.
     */
    IFrameworkAbstractionFacade CreateInstance(ParameterObject parameterObject);
}

package echo.core.framework_abstraction;

import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.webdriver.IKeyboardMapper;
import echo.core.framework_abstraction.webdriver.ISelectElementFactory;
import echo.core.framework_interaction.IFrameworkAdapter;
import echo.core.framework_interaction.IFrameworkAdapterFactory;

/**
 * The factory for the <see cref="FrameworkAbstractionFacade"/>.
 */
public class FrameworkAbstractionFacadeFactory implements IFrameworkAbstractionFacadeFactory {
    private IKeyboardMapper keyboardMapper;
    private ISelectElementFactory selectElementFactory;
    private IFrameworkAdapterFactory frameworkAdapterFactory;
    private boolean headlessMode;

    /**
     * Initializes a new instance of the <see cref="FrameworkAbstractionFacadeFactory"/> class.
     *
     * @param keyboardMapper          The keyboard mapper.
     * @param selectElementFactory    The select element factory.
     * @param headlessMode            The headless mode.
     * @param frameworkAdapterFactory Framwork Adapter factory.
     */
    public FrameworkAbstractionFacadeFactory(IKeyboardMapper keyboardMapper,
                                             ISelectElementFactory selectElementFactory,
                                             boolean headlessMode,
                                             IFrameworkAdapterFactory frameworkAdapterFactory) {
        this.keyboardMapper = keyboardMapper;
        this.selectElementFactory = selectElementFactory;
        this.headlessMode = headlessMode;
        this.frameworkAdapterFactory = frameworkAdapterFactory;
    }

    /**
     * Creates a new <see cref="IFrameworkAbstractionFacade"/>.
     *
     * @param parameterObject Framework parameter object.
     * @return A <see cref="IFrameworkAbstractionFacade"/>.
     */
    public final IFrameworkAbstractionFacade CreateInstance(ParameterObject parameterObject) {
        IFrameworkAdapter adapter = frameworkAdapterFactory.CreateInstance(parameterObject, selectElementFactory);
        return new FrameworkAbstractionFacade(keyboardMapper, selectElementFactory, headlessMode,
                parameterObject.getGuid(), parameterObject.getAutomationInfo(), adapter);
    }
}

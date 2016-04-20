package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IWebDriver;

/**
 * <p>Clears a page's browser cache.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.ClearBrowserStorage();</p>
 */
public class ClearBrowserStorageCommand extends Command {
    /*
     * var echoJStorageLoaded=false;
     * function echoOnFinishJStorageLoading(){
     *      if(echoJStorageLoaded) return;
     *      echoJStorageLoaded = true;
     *      $.jStorage.flush();
     * }
     * if(typeof JSON == 'undefined'){
     *      var jsonScript = document.createElement('script');
     *      jsonScript.type = 'text/javascript';
     *      jsonScript.src = '//cdnjs.cloudflare.com/ajax/libs/json2/20110223/json2.js';
     *      document.getElementsByTagName('head')[0].appendChild(jsonScript);
     * }
     * if(typeof $.jStorage == 'undefined'){
     *      var jStorageScript = document.createElement('script');
     *      jStorageScript.type = 'text/javascript';
     *      jStorageScript.src = 'https://raw.github.com/andris9/jStorage/master/jstorage.js';
     *      jStorageScript.onload = echoOnFinishJStorageLoading;
     *      jStorageScript.onreadystatechange = function(){ if(this.readyState == 'loaded' || this.readyState == 'complete') { echoOnFinishJStorageLoading(); } }
     *      document.getElementsByTagName('head')[0].appendChild(jStorageScript);
     * } else {
     *       echoOnFinishJStorageLoading();
     * }
     */

    /**
     * Initializes a new instance of the <see cref="ClearBrowserStorageCommand"/> class.
     *
     * @param log The logger.
     */
    public ClearBrowserStorageCommand(ILog log) {
        this(new ParameterObject(log, Resources.getString("ClearBrowserStorageCommand_Info")), new WebCommandInitializer());
    }

    /**
     * Initializes a new instance of the <see cref="ClearBrowserStorageCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command initializer.
     */
    public ClearBrowserStorageCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        super(parameterObject, commandInitializer);
        getParameterObject().getWeb().setScript("var echoJStorageLoaded=false;function echoOnFinishJStorageLoading(){if(echoJStorageLoaded)return;echoJStorageLoaded=true;$.jStorage.flush()}var echoJStorageLoaded=false;if(typeof JSON=='undefined'){var jsonScript=document.createElement('script');jsonScript.type='text/javascript';jsonScript.src='//cdnjs.cloudflare.com/ajax/libs/json2/20110223/json2.js';document.getElementsByTagName('head')[0].appendChild(jsonScript)}if(typeof $.jStorage=='undefined'){var jStorageScript=document.createElement('script');jStorageScript.type='text/javascript';jStorageScript.src='https://raw.github.com/andris9/jStorage/master/jstorage.js';jStorageScript.onload=echoOnFinishJStorageLoading;jStorageScript.onreadystatechange=function(){if(this.readyState=='loaded'||this.readyState=='complete'){echoOnFinishJStorageLoading()}};document.getElementsByTagName('head')[0].appendChild(jStorageScript)}else{echoOnFinishJStorageLoading()}");
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void CommandDelegate(IWebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        driver.ClearBrowserStorage(getParameterObject());
    }
}

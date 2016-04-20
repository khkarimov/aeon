package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IWebDriver;

public class ScrollToEndCommand extends Command
{
	/**
	 Initializes a new instance of the <see cref="ScrollToEndCommand"/> class.

	 @param log The logger.
	*/
	public ScrollToEndCommand(ILog log)
	{
		this(new ParameterObject(log, Resources.getString("ScrollToEndCommand_Info")), new WebCommandInitializer());
	}

	/**
	 Initializes a new instance of the <see cref="ScrollToEndCommand"/> class.

	 @param parameterObject The framework param object.
	 @param commandInitializer The command initializer.
	*/
	public ScrollToEndCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer)
	{
		super(parameterObject, commandInitializer);
	}

	/** 
	 The method which provides the logic for the command.
	 
	 @param driver The framework abstraction facade.
	*/
	@Override
	protected void CommandDelegate(IWebDriver driver)
	{
		if (driver == null)
		{
			throw new IllegalArgumentException("driver");
		}

		driver.ScrollToEnd(getParameterObject());
	}
}

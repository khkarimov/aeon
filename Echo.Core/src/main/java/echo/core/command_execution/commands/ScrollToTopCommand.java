package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IDriver;

public class ScrollToTopCommand extends Command
{
	/**
	 Initializes a new instance of the <see cref="ScrollToTopCommand"/> class.
	 
	 @param log The logger.
	*/
	public ScrollToTopCommand(ILog log)
	{
		this(new ParameterObject(log, Resources.getString("ScrollToTopCommand_Info")), new WebCommandInitializer());
	}

	/** 
	 Initializes a new instance of the <see cref="ScrollToTopCommand"/> class.
	 
	 @param parameterObject The framework param object.
	 @param commandInitializer The command initializer.
	*/
	public ScrollToTopCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer)
	{
		super(parameterObject, commandInitializer);
	}

	/** 
	 The method which provides the logic for the command.
	 
	 @param driver The framework abstraction facade.
	*/
	@Override
	protected void CommandDelegate(IDriver driver)
	{
		if (driver == null)
		{
			throw new IllegalArgumentException("driver");
		}

		driver.ScrollToTop(getParameterObject());
	}
}

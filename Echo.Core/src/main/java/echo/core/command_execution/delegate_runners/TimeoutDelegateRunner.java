package echo.core.command_execution.delegate_runners;

import echo.core.command_execution.delegate_runners.interfaces.IDelegateRunner;
import echo.core.common.helpers.IClock;
import echo.core.framework_abstraction.IFrameworkAbstractionFacade;

import java.time.Duration;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public class TimeoutDelegateRunner extends DelegateRunner
{
	private IFrameworkAbstractionFacade frameworkAbstractionFacade;
		private IClock clock;
		private Duration timeout;

		public TimeoutDelegateRunner(UUID guid, IDelegateRunner successor, IFrameworkAbstractionFacade frameworkAbstractionFacade, IClock clock, Duration timeout)
		{
			super(guid, successor);
			this.frameworkAbstractionFacade = frameworkAbstractionFacade;
			this.clock = clock;
			this.timeout = timeout;
		}

		@Override
		public void Execute(Consumer<IFrameworkAbstractionFacade> commandDelegate)
		{
			ExecuteDelegate(() -> Successor.Execute(commandDelegate));
		}

		@Override
		public Object Execute(Function<IFrameworkAbstractionFacade, Object> commandDelegate)
		{
			return ExecuteDelegateWithReturn(() -> Successor.Execute(commandDelegate));
		}

		private void ExecuteDelegate(Runnable commandDelegate)
		{
			ExecuteDelegateWithReturn(() ->
			{
				commandDelegate.run();
				return null;
			});
		}

		private Object ExecuteDelegateWithReturn(Consumer<Object> commandDelegateWrapper)
		{
			RuntimeException lastCaughtException = null;
			int tries = 0;

			// We add timeout to System.DateTime.UtcNow instead of comparing against System.Diagnostics.Stopwatch.Elapsed, as System.Diagnostics.Stopwatch.Elapsed uses the Win32 API function QueryPerformanceCounter, which may be inaccurate on certain VMs.
			var end = clock.UtcNow.Add(timeout);
			while (clock.UtcNow < end)
			{
				try
				{
					tries++;
					var returnValue = commandDelegateWrapper.invoke();
					getLog().Debug(guid, String.format(CultureInfo.CurrentCulture, Resources.TimWtr_Success_Debug, tries));
					return returnValue;
				}
				catch (OutOfMemoryError e)
				{
					Log.Error(guid, Resources.TimWtr_OutOfMemoryException_Error);
					Log.Error(Resources.StackTraceMessage, e);
					throw e;
				}
				catch (ThreadAbortException e)
				{
					// The thread is being aborted.
					Log.Error(guid, Resources.TimWtr_ThreadAbortException_Error);
					Log.Error(Resources.StackTraceMessage, e);
				}
				catch (RuntimeException e)
				{
					lastCaughtException = e;
					Log.Debug(guid, String.format(CultureInfo.CurrentCulture, Resources.TimWtr_Exception_Debug, tries, lastCaughtException.getClass().getSimpleName(), lastCaughtException.getMessage()));
				}

				// Wait before retrying. Excessive attempts may cause WebDriver's client to lose connection with the server.
				Sleep.WaitInternal();
			}

			TimeoutExpiredException ex = new TimeoutExpiredException(Resources.TimWtr_TimeoutExpired_DefaultMessage, timeout, lastCaughtException);
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
			var processList = ProcessCollector.GetProcesses();

			Image screenshot = null;
			try
			{
				screenshot = frameworkAbstractionFacade.GetScreenshot();
			}
			catch (OutOfMemoryError e)
			{
				throw e;
			}
			catch (RuntimeException e2)
			{
			}

			if (screenshot == null)
			{
				Log.Error(guid, ex.getMessage(), processList);
				Log.Error(Resources.StackTraceMessage, lastCaughtException);
			}
			else
			{
				Log.Error(guid, ex.getMessage(), screenshot, processList);
				Log.Error(Resources.StackTraceMessage, lastCaughtException);
			}

			String pageSource = null;
			try
			{
				pageSource = frameworkAbstractionFacade.GetPageSource();
			}
			catch (OutOfMemoryError e3)
			{
				throw e3;
			}
			catch (RuntimeException e4)
			{
			}

			if (pageSource != null)
			{
				Log.Trace(guid, ex.getMessage(), pageSource);
			}

			throw ex;
		}
}
package de.persosim.rcp;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.swt.IEventLoopAdvisor;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.swt.widgets.Display;
import org.globaltester.logging.BasicLogger;
import org.globaltester.logging.tags.LogLevel;

/**
 * This implements logging of event loop exceptions as described
 * in https://stackoverflow.com/questions/48209573/custom-error-dialog-in-eclipse-e4-application
 * 
 * The use of the internal IEventLoopAdvisor seems to be the only valid solution at this time.
 */
public class LifeCycle {

	@SuppressWarnings("restriction")
	@PostContextCreate
	public void postContextCreate(IEclipseContext context) {
		context.set(IEventLoopAdvisor.class, new EventLoopAdvisor(context));
	}

	@SuppressWarnings("restriction")
	class EventLoopAdvisor implements IEventLoopAdvisor {
		EventLoopAdvisor(IEclipseContext appContext) {
			super();
		}

		@Override
		public void eventLoopIdle(final Display display) {
			display.sleep();
		}

		@Override
		public void eventLoopException(final Throwable exception) {
			BasicLogger.logException(getClass(), exception, LogLevel.ERROR);
		}
	}
}

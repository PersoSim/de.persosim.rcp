package de.persosim.rcp;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.swt.IEventLoopAdvisor;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.swt.widgets.Display;
import org.globaltester.logging.BasicLogger;
import org.globaltester.logging.tags.LogLevel;

public class LifeCycle {

	@PostContextCreate
	public void postContextCreate(IEclipseContext context) {
		context.set(IEventLoopAdvisor.class, new EventLoopAdvisor(context));
	}

	class EventLoopAdvisor implements IEventLoopAdvisor {
		private final IEclipseContext _appContext;

		EventLoopAdvisor(IEclipseContext appContext) {
			super();

			_appContext = appContext;
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

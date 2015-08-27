package de.persosim.rcp;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		de.persosim.simulator.Activator.getDefault().enableService();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		de.persosim.simulator.Activator.getDefault().disableService();
	}

}

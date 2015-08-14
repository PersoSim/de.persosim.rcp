package de.persosim.rcp;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import de.persosim.simulator.PersoSim;
import de.persosim.simulator.Simulator;

public class Activator implements BundleActivator {

	private ServiceRegistration<Simulator> service;

	@Override
	public void start(BundleContext context) throws Exception {
		service = context.registerService(Simulator.class, new PersoSim(), null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		service.unregister();
	}

}

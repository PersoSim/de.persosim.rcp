package de.persosim.rcp;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.persosim.simulator.PersoSim;
import de.persosim.simulator.Simulator;
import de.persosim.simulator.perso.Personalization;
import de.persosim.simulator.perso.PersonalizationFactory;
import de.persosim.simulator.ui.parts.PersoSimGuiMain;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		de.persosim.simulator.Activator.getDefault().enableService();
		startSimAndConnectToNativeDriver();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		de.persosim.simulator.Activator.getDefault().disableService();
	}
	
	/**
	 * This method handles the connection to the simulator. Its primary task is
	 * to ensure the simulator is up and running when a connection is
	 * initialized. This method uses the {@link Simulator} provided by the
	 * {@link de.persosim.simulator.Activator}.
	 */
	private void startSimAndConnectToNativeDriver() {
		    de.persosim.simulator.Activator persoSimActivator = de.persosim.simulator.Activator.getDefault();
		    PersoSim sim = persoSimActivator.getSim();
		    try {
		    	sim.startSimulator();
				sim.loadPersonalization(getDefaultPersonalization());
			} catch (IOException e) {
				e.printStackTrace();
				MessageDialog.openError(null, "Error", "Failed to automatically load default personalization");
				return;
			}
		    de.persosim.simulator.ui.Activator.connectToNativeDriver();
	}
	
	/**
	 * This method returns a personalization which can be used as default.
	 * @return a default personalization
	 * @throws IOException
	 */
	private Personalization getDefaultPersonalization() throws IOException {
		Bundle plugin = Platform.getBundle(PersoSimGuiMain.DE_PERSOSIM_SIMULATOR_BUNDLE);
		URL url = plugin.getEntry (PersoSimGuiMain.PERSO_PATH);
		URL resolvedUrl;
		
		resolvedUrl = FileLocator.resolve(url);
		
		File folder = new File(resolvedUrl.getFile());
		String pathString = folder.getAbsolutePath() + File.separator + PersoSimGuiMain.PERSO_FILE;
		
		System.out.println("Loading default personalization from: " + pathString);
		
		Personalization personalization = (Personalization) PersonalizationFactory.unmarshal(pathString);
		
		return personalization;
	}

}

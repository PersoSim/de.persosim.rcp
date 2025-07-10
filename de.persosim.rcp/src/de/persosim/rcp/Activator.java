package de.persosim.rcp;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.globaltester.logging.BasicLogger;
import org.globaltester.logging.tags.LogLevel;
import org.globaltester.logging.tags.LogTag;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.persosim.simulator.CommandParser;
import de.persosim.simulator.PersoSim;
import de.persosim.simulator.log.PersoSimLogTags;
import de.persosim.simulator.perso.Personalization;
import de.persosim.simulator.perso.export.ProfileHelper;
import de.persosim.simulator.preferences.EclipsePreferenceAccessor;
import de.persosim.simulator.preferences.PersoSimPreferenceManager;

public class Activator implements BundleActivator
{
	public static final String DEFAULT_PERSO_FILE = "Profile01.perso";

	@Override
	public void start(BundleContext context) throws Exception
	{
		BasicLogger.log("START Activator RCP", LogLevel.TRACE, new LogTag(BasicLogger.LOG_TAG_TAG_ID, PersoSimLogTags.SYSTEM_TAG_ID));
		PersoSimPreferenceManager.setPreferenceAccessorIfNotAvailable(new EclipsePreferenceAccessor());
		LogHelper.logEnvironmentInfo();
		de.persosim.simulator.Activator.getDefault().enableService();
		startSimAndConnectToNativeDriver();
		BasicLogger.log("END Activator RCP", LogLevel.TRACE, new LogTag(BasicLogger.LOG_TAG_TAG_ID, PersoSimLogTags.SYSTEM_TAG_ID));
	}

	@Override
	public void stop(BundleContext context) throws Exception
	{
		de.persosim.simulator.Activator.getDefault().disableService();
	}

	/**
	 * This method handles the connection to the simulator. Its primary task is
	 * to ensure the simulator is up and running when a connection is
	 * initialized. This method uses the {@link Simulator} provided by the
	 * {@link de.persosim.simulator.Activator}.
	 */
	private void startSimAndConnectToNativeDriver()
	{
		de.persosim.simulator.Activator persoSimActivator = de.persosim.simulator.Activator.getDefault();
		PersoSim sim = persoSimActivator.getSim();
		try {
			sim.loadPersonalization(getDefaultPersonalization());
			sim.logStartOK();
		}
		catch (IOException e) {
			e.printStackTrace();
			MessageDialog.openError(null, "Error", "Failed to automatically load default personalization");
		}
	}

	/**
	 * This method returns a personalization which can be used as default.
	 *
	 * @return a default personalization
	 * @throws IOException
	 *             in error case
	 */
	private Personalization getDefaultPersonalization() throws IOException
	{
		Bundle plugin = de.persosim.simulator.Activator.getContext().getBundle();
		URL url = plugin.getEntry("personalization/" + ProfileHelper.PERSO_FILES_PARENT_DIR);
		URL resolvedUrl = FileLocator.resolve(url);
		File folder = new File(resolvedUrl.getFile());
		String pathString = folder.getAbsolutePath();

		ProfileHelper.setRootPathPersoFiles(Path.of(pathString));

		ProfileHelper.createAllMissingOverlayProfileFiles(ProfileHelper.getRootPathPersoFiles());

		pathString = pathString + File.separator + DEFAULT_PERSO_FILE;
		BasicLogger.log("Loading default personalization from '" + pathString + "'.", LogLevel.INFO, new LogTag(BasicLogger.LOG_TAG_TAG_ID, PersoSimLogTags.PERSO_TAG_ID));

		return CommandParser.getPerso(pathString, true);
	}

}

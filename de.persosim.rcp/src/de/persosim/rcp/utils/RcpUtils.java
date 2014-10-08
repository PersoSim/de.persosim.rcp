package de.persosim.rcp.utils;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * This class provides methods generally useful within the context of RCP user infaces.
 * 
 * @author slutters
 *
 */
public class RcpUtils {
	
	public static void openWebPage(URI uri) {
		Desktop desktop = null;
		
		if(Desktop.isDesktopSupported()) {
			desktop = Desktop.getDesktop();
		}
		
		if ((desktop != null) && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void openWebPage(URL url) {
		try {
			openWebPage(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
}

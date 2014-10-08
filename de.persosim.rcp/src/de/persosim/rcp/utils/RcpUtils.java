package de.persosim.rcp.utils;

import java.awt.Desktop;
import java.io.IOException;
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
	
	/**
	 * This method opens the provided URI with the native system's default
	 * browser if supported.
	 * 
	 * @param uri
	 *            the URI to open
	 * @throws IOException 
	 */
	public static void openInBrowser(URI uri) throws IOException {
		Desktop desktop = null;
		
		if(Desktop.isDesktopSupported()) {
			desktop = Desktop.getDesktop();
		}
		
		if ((desktop != null) && desktop.isSupported(Desktop.Action.BROWSE)) {
			desktop.browse(uri);
		}
	}
	
	/**
	 * This method opens the provided URL with the native system's default
	 * browser if supported.
	 * 
	 * @param url
	 * @throws IOException 
	 */
	public static void openInBrowser(URL url) throws IOException {
		try {
			openInBrowser(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
}

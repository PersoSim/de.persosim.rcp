package de.persosim.rcp.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import de.persosim.rcp.dialogs.AboutDialog;

public class AboutHandler {
	@Execute
	public void execute(Shell shell) {
		AboutDialog aboutDialog = new AboutDialog(shell);
		aboutDialog.open();
	}
}

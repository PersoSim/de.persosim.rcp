package de.persosim.rcp.dialogs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.inject.Inject;

import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;

import de.persosim.rcp.utils.RcpUtils;

public class AboutDialog extends Dialog {
	
	public static final String PERSOSIM_URL = "http://www.persosim.de";
	
	private static final String FONT_NAME = "Helvetica";

	private Image image = null;

	@Inject
	IWorkbench workbench;

	public AboutDialog(Shell parentShell) {
		super(parentShell);
		
		try {
		    URL url = new URL("platform:/plugin/de.persosim.rcp/icons/persosim.png");
		    InputStream inputStream = url.openConnection().getInputStream();		    
		    image = new Image(Display.getDefault(), inputStream);
		    
		    ImageData imgData = image.getImageData();
			imgData = imgData.scaledTo(40, 40);
			image = new Image(Display.getDefault(), imgData);
		} catch (IOException e) {
		    e.printStackTrace();
		}

	}

	@Override
	public boolean close() {
		if (image != null)
			image.dispose();
		return super.close();
	}

	@Override
	protected Control createDialogArea(Composite parentComposite) {
		final Composite parent = parentComposite;
		
		parent.setLayout(new GridLayout(1, false));

		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(1, true));

		Composite upper = new Composite(container, SWT.NONE);

		FormLayout layout = new FormLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		upper.setLayout(layout);
		
		Label label1 = new Label(upper, SWT.NONE);
		label1.setImage(image);
		label1.setSize(label1.computeSize(10, 10));

		FormData formData = new FormData();
		formData.top = new FormAttachment(20, 0);
		label1.setLayoutData(formData);

		Label label2 = new Label(upper, SWT.NONE);
		label2.setText("PersoSim");
		label2.setFont(new Font(upper.getDisplay(), FONT_NAME, 24, SWT.BOLD));

		FormData formData2 = new FormData();
		formData2.left = new FormAttachment(label1, 10);
		formData2.bottom = new FormAttachment(label1, 0, SWT.BOTTOM);
		label2.setLayoutData(formData2);

		Label label3 = new Label(upper, SWT.NONE);
		label3.setText("v0.3.0"); //FIXME extract this number from the bundle/product version
		label3.setFont(new Font(upper.getDisplay(), FONT_NAME, 12, SWT.NONE));

		FormData formData3 = new FormData();
		formData3.left = new FormAttachment(label2, 5);
		formData3.bottom = new FormAttachment(label1, -5, SWT.BOTTOM);
		label3.setLayoutData(formData3);

		Composite lower = new Composite(container, SWT.NONE);
		GridData gridData1;
		gridData1 = new GridData();
		gridData1.horizontalAlignment = SWT.FILL;
		lower.setLayout(new GridLayout(1, false));

		Link link = new Link(lower, SWT.NONE);
		link.setText("Visit our web site at <a href=\"" + PERSOSIM_URL + "\">" + PERSOSIM_URL + "</a>");
		link.setSize(link.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					RcpUtils.openInBrowser(new URL(e.text));
				} catch (IOException ex) {
					ex.printStackTrace();
					
					MessageDialog.openError(parent.getShell(), "Error", "Unable to open URL: " + PERSOSIM_URL);
				}
			}
		});
		return container;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("About PersoSim");
	}

}

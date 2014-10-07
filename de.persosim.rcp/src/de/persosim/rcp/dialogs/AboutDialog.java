package de.persosim.rcp.dialogs;

import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.inject.Inject;

import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
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

public class AboutDialog extends Dialog {

	private static final String FONT_NAME = "Helvetica";

	private Image image;

	@Inject
	IWorkbench workbench;

	public AboutDialog(Shell parentShell) {
		super(parentShell);

		image = new Image(Display.getDefault(), "../../de.persosim.rcp/de.persosim.rcp/icons/persosim.png");
		
		ImageData imgData = image.getImageData();
		imgData = imgData.scaledTo(40, 40);
		image = new Image(Display.getDefault(), imgData);

	}
	
	public static boolean writeToFile(String fileName, byte[] data) {
		RandomAccessFile raf = null;
		
		try {
			raf = new RandomAccessFile(fileName, "rw");
			raf.write(data);
			raf.close();
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}finally{
			if(raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
	}

	@Override
	public boolean close() {
		if (image != null)
			image.dispose();
		return super.close();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
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
		label3.setText("");
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
		link.setText("Visit our web site at <a href=\"http://www.persosim.de\">http://www.persosim.de</a>");
		link.setSize(400, 100);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					openWebPage(new URL(e.text));
				} catch (MalformedURLException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});

		return container;
	}

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

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("About PersoSim");
	}

	@Override
	protected Point getInitialSize() {
		return new Point(350, 200);
	}

}

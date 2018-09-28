
package user.frame.dialog;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class MessageDialog extends Dialog {
	public static final int CLOSE = -10;
	private Composite content;
	private Shell shell;
	private Button okButton,cancelButton;
	private String title ="title",message="";
	private Label label;
	
	
	public MessageDialog(Shell parentShell,String title) {
		super(parentShell);
		this.title = title;
	}
	/**
	 * @wbp.parser.constructor
	 */
	public MessageDialog(Shell parentShell) {
		super(parentShell);
		
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		newShell.setMinimumSize(new Point(350, 160));
		super.configureShell(newShell);
		newShell.setText(title);
		shell=newShell;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		
		parent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		content.setLayout(null);
		setCenter();
		return parent;
	}

	
	public void setCenter(){
		
		Rectangle screen=Display.getDefault().getClientArea();
		Rectangle rect=shell.getBounds();
		rect.height=160;
		rect.width=350;
		rect.x=(screen.width-rect.width)/2;
		rect.y=(screen.height-rect.height)/2-100;
		shell.setBounds(rect);
	}	
	@Override
	protected Control createContents(Composite parent) {
		// create the top level composite for the dialog
		parent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		content = new Composite(parent,SWT.NONE);
		content.setLayoutData(new GridData(GridData.FILL_BOTH));
		applyDialogFont(content);
		// initialize the dialog units
		initializeDialogUnits(content);
		label = new Label(content,SWT.NONE);
		label.setAlignment(SWT.CENTER);
		label.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 12, SWT.NORMAL));
		label.setSize(303, 45);
		label.setLocation(20, 22);
		label.setText(message);
		okButton=new Button(content,SWT.NONE);
		okButton.setLocation(60, 87);
		okButton.setSize(100, 31);
		okButton.setText("OK");
		okButton.setFont(SWTResourceManager.getFont("@Arial Unicode MS", 12, SWT.NORMAL));
		okButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				okPressed();
			}
		});
		okButton.setFocus();
		cancelButton=new Button(content,SWT.NONE);
		cancelButton.setLocation(188, 87);
		cancelButton.setSize(100, 31);
		cancelButton.setText("Cancel");
		cancelButton.setFont(SWTResourceManager.getFont("@Arial Unicode MS", 12, SWT.NORMAL));
		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cancelPressed();
			}
		});
		return content;
	}
	

	@Override
	protected void cancelPressed()
	{
		super.cancelPressed();
		//setReturnCode(MessageDialog.CANCEL);
	}
	@Override
	protected void okPressed() {
		System.out.println("okPressed");
		super.okPressed();
		//setReturnCode(MessageDialog.OK);
	}
	@Override
	public boolean close() {
		System.out.println("close");
		//setReturnCode(MessageDialog.CLOSE);
		return super.close();
	}
	@Override
	protected Control createButtonBar(Composite parent) {
		
		parent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		Composite composite = new Composite(parent,SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 0; // this is incremented by createButton
		layout.makeColumnsEqualWidth = true;
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER
				| GridData.VERTICAL_ALIGN_CENTER));
		composite.setFont(parent.getFont());
		return composite;
	}
	public void setMessage(final String message){
//		Display.getCurrent().asyncExec(new Runnable(){
//
//			@Override
//			public void run()
//			{
				this.message = message;				
//			}
//			
//		});
		
	}
}

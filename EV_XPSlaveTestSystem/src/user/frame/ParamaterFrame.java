package user.frame;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import user.CheckTest.TestChekStep;
import user.Method.ResultCache;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ParamaterFrame extends TestFrameAbs
{
	private Group totalGroup;
	private Composite comp;
	private Text deviceAdrText;
	private Text VoltageCountText;
	private Text tempCountText;
	
	
	
	public ParamaterFrame(Composite parent, int style)
	{
		super(parent, style);
		
		testChekSteps = new ArrayList<TestChekStep>();
//		bsmuCheck = new BsmuCheck();
//		testChekSteps.add(bsmuCheck);
		
		
//		ok=Mainframe.ok;
//		error=Mainframe.error;
//		green=Mainframe.green;
//		red=Mainframe.red;
		comp=this;
		
		totalGroup = new Group(comp, SWT.NONE);
		totalGroup.setLocation(10, 10);
		totalGroup.setSize(522, 361);
		totalGroup.setText("\u9879\u76EE\u53C2\u6570");
		
		Label label = new Label(totalGroup, SWT.NONE);
		label.setBounds(49, 38, 51, 17);
		label.setText("\u8BBE\u5907\u5730\u5740:");
		
		Label label_1 = new Label(totalGroup, SWT.NONE);
		label_1.setBounds(25, 78, 75, 17);
		label_1.setText("\u7535\u538B\u91C7\u6837\u8282\u6570:");
		
		Label label_2 = new Label(totalGroup, SWT.NONE);
		label_2.setBounds(25, 113, 75, 17);
		label_2.setText("\u6E29\u5EA6\u91C7\u6837\u8282\u6570:");
		
		deviceAdrText = new Text(totalGroup, SWT.BORDER);
		deviceAdrText.setBounds(116, 35, 316, 23);
		
		VoltageCountText = new Text(totalGroup, SWT.BORDER);
		VoltageCountText.setBounds(116, 75, 316, 23);
		
		tempCountText = new Text(totalGroup, SWT.BORDER);
		tempCountText.setBounds(116, 110, 316, 23);
		
		initResult();
	}
	


	
	public void initResult(){

//		bsmuCheck.beforeTest();
				

	}
	public void refreshTables(){
		ResultCache cache=ResultCache.getInstance();
		
//		refreshTable(totalTable,cache.getTotalStatus(),1);
//		refreshTable(canCelltable,cache.getCAN(),0);
//		refreshTable(rs485Table,cache.getRS485(),0);
//		refreshTable(cocontactorTable,cache.getCocontactor(),0);
//		refreshTable(LedTable,cache.getLED(),0);
//		refreshTable(outputTable,cache.getOutputIO(),0);
//		refreshTable(inputTable,cache.getInputIO(),0);
//		refreshTable(sdCardTable,cache.getSdCard(),0);
//		refreshTable(ethernetTable,cache.getEthernet(),0);
	}
}

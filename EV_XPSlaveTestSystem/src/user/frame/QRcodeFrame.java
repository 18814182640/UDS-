package user.frame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import user.CanDriver.CanController;
import user.CheckTest.ReadData;
import user.CheckTest.TestChekStep;
import user.CheckTest.WriteData;
import user.Method.ResultCache;
import user.model.DataRow;
import user.service.CSVFile;
import user.service.ExportDataService;
import user.util.StringUtils;
import user.util.TiCommandCompare;

public class QRcodeFrame extends TestFrameAbs
{
	private TiCommandCompare compare =  new TiCommandCompare();
	private Group totalGroup;
	private Composite comp;
	private TableEditor paramEditor;
//	private Text Text;
	private Table paramTable;
	private Group group;
	private Label label_2;
	private Label label_3;
	private Label label_4;
	private Button button,button_1,btnNewButton,button_2;
	private Text slaveAddrText,deviceAddrText,qrModlueText,VoltCountText,tempCountText,text_0,text_2,text_5,text_6,text_7,text_8,text_9;
	private TestChekStep readData,writeData;
	private Text[] paramaterTexts = new Text[10];
	private Shell shell;
	private ExportDataService saveDataService;
	private List<DataRow> dataRowsList = new LinkedList<DataRow>();
	private DataRow dataRowSlaveAddr=new DataRow(),dataRowModlue = new DataRow(),softVersion,hardVersion;
	private int readOrWrite = -1;//1是读取，0，是写
	private List<DataRow> paramaterList;
	private Table voltageTable;
	private Table analyseTabel;
	private List<DataRow> voltDataRows;
	private List<DataRow> tempDataRows;
	public QRcodeFrame(Composite parent, int style)
	{
		super(parent, style);
		shell=parent.getShell();
		testChekSteps = new ArrayList<TestChekStep>();
//		ok=Mainframe.ok;
//		error=Mainframe.error;
//		green=Mainframe.green;
//		red=Mainframe.red;
		comp=this;
		
		totalGroup = new Group(comp, SWT.NONE);
		totalGroup.setLocation(20, 10);
		totalGroup.setSize(522, 200);
		totalGroup.setText("QR\u6761\u7801");
		
//		Label label = new Label(totalGroup, SWT.NONE);
//		label.setBounds(61, 38, 39, 17);
//		label.setText("\u4ECE\u63A7\u677F:");
		
		Label label_1 = new Label(totalGroup, SWT.NONE);
		label_1.setBounds(73, 73, 27, 17);
		label_1.setText("\u6A21\u7EC4:");
		
//		slaveAddrText = new Text(totalGroup, SWT.BORDER);
//		slaveAddrText.setBounds(116, 35, 316, 23);
		
		qrModlueText = new Text(totalGroup, SWT.BORDER);
		qrModlueText.setBounds(116, 70, 316, 23);
		
//		button = new Button(totalGroup, SWT.NONE);
//		button.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				if(!checkConnect()){
//					return;
//				}
//				CanController.getInstance().clearData("76");
//				clearParamTexts();
//				//CanController.getInstance().writeCmdQueue(CanController.getInstance().getQueryCmd(76,0,15));
//			}
//		});
//		button.setBounds(115, 115, 80, 27);
//		button.setText("\u8BFB\u53D6");
		
		button_1 = new Button(totalGroup, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox mb = null;
				if(!CanController.getInstance().isConnect()){
					mb = new MessageBox(shell, SWT.ICON_ERROR);
					mb.setText("提示");
					mb.setMessage("未连接测试设备");
					mb.open();
					return;
				}
				else if(!CanController.getInstance().isConnectBoard()){
					mb = new MessageBox(shell, SWT.ICON_ERROR);
					mb.setText("提示");
					mb.setMessage("通讯异常,未收到设备数据!");
					mb.open();
					return;
				}
				
				
				if(saveDataService!=null){
					String prompt = "";
//					if(slaveAddrText!=null){
//						if(slaveAddrText.getText()==null || "".equals(slaveAddrText.getText().trim())){
//							prompt = "从控板条码不能为空!";
//						}else{							
//							dataRowSlave.setVal(slaveAddrText.getText());					
//						}
//					}else{
//						prompt = "找不到从控板条码对象!";
//					}
					dataRowSlaveAddr.setVal(CanController.getInstance().object+"");//从控板地址
					if(qrModlueText!=null){
						if(qrModlueText.getText()==null || "".equals(qrModlueText.getText().trim())){
							prompt = "模组条码不能为空!";
						}else{
							dataRowModlue.setVal(qrModlueText.getText());													
						}
					}else{
						prompt = "找不到模组条码对象!";
					}
					if(!"".equals(prompt) &&  prompt.length()>0){
						mb = new MessageBox(shell, SWT.ICON_ERROR);
						mb.setText("提示");
						mb.setMessage(prompt);
						mb.open();
						qrModlueText.setText("");
						qrModlueText.forceFocus();//强制输入框获取焦点
						return ;
					}
					boolean ret = saveDataService.exportFile(); 
					ret=true;
					if(ret){
						mb = new MessageBox(shell, SWT.ICON_INFORMATION);
						mb.setText("提示");
						mb.setMessage("保存成功！");
					}else{
						mb = new MessageBox(shell, SWT.ICON_ERROR);
						mb.setText("提示");
						mb.setMessage("保存失败！");
					}
					
				}else{
					mb = new MessageBox(shell, SWT.ICON_ERROR);
					mb.setText("提示");
					mb.setMessage("写入失败，请重启程序!");
				}
				mb.open();
				qrModlueText.setText("");
				qrModlueText.forceFocus();//强制输入框获取焦点
			}
		});
		button_1.setBounds(352, 130, 80, 27);
		button_1.setText("\u4FDD\u5B58\u6570\u636E");
		
		group = new Group(this, SWT.NONE);
		group.setText("\u9879\u76EE\u53C2\u6570");
		group.setBounds(20, 220, 522, 462);
		
		
		paramTable = new Table(group, SWT.BORDER | SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
		paramTable.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.NORMAL));
		paramTable.setBounds(15, 32, 484, 356);
		paramTable.setHeaderVisible(true);
		paramTable.setLinesVisible(true);
		final org.eclipse.swt.graphics.Color red = SWTResourceManager.getColor(SWT.COLOR_RED);
		final org.eclipse.swt.graphics.Color yellow = SWTResourceManager.getColor(SWT.COLOR_YELLOW);

//		paramTable.addListener(SWT.MouseMove,new Listener(){
//
//			@Override
//			public void handleEvent(Event event)
//			{
//				event.detail &= ~SWT.HOT;  
//	            if ((event.detail & SWT.SELECTED) == 0) return; /* item not selected */  
//	            int clientWidth = paramTable.getClientArea().width;  
//	            GC gc = event.gc;  
//	            org.eclipse.swt.graphics.Color oldForeground = gc.getForeground();  
//	            org.eclipse.swt.graphics.Color oldBackground = gc.getBackground();  
//	            gc.setForeground(red);  
//	            gc.setBackground(yellow);  
//	            gc.fillGradientRectangle(0, event.y, clientWidth, event.height, false);  
//	            gc.setForeground(oldForeground);  
//	            gc.setBackground(oldBackground);  
//	            event.detail &= ~SWT.SELECTED;  
//				
//			}
//			
//		});
		
//		paramTable.addListener(SWT.EraseItem, new Listener() {  
//		        public void handleEvent(Event event) {  
//		            event.detail &= ~SWT.HOT;  
//		            if ((event.detail & SWT.SELECTED) == 0) return; /* item not selected */  
//		            int clientWidth = paramTable.getClientArea().width;  
//		            GC gc = event.gc;  
//		            org.eclipse.swt.graphics.Color oldForeground = gc.getForeground();  
//		            org.eclipse.swt.graphics.Color oldBackground = gc.getBackground();  
//		            gc.setForeground(red);  
//		            gc.setBackground(yellow);  
//		            gc.fillGradientRectangle(0, event.y, clientWidth, event.height, false);  
//		            gc.setForeground(oldForeground);  
//		            gc.setBackground(oldBackground);  
//		            event.detail &= ~SWT.SELECTED;  
//		        }  
//		    });  
//		Listener[] mouseHover = paramTable.getListeners(SWT.MouseEnter);
//		for(Listener m : mouseHover){
//			System.out.println(m.getClass());
//		}
//		
//		TableColumn tblclmnNewColumn = new TableColumn(paramTable, SWT.NONE);
//		tblclmnNewColumn.setWidth(74);
//		tblclmnNewColumn.setText("\u5B50\u7D22\u5F15");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(paramTable, SWT.NONE);
		tblclmnNewColumn_1.setWidth(150);
		tblclmnNewColumn_1.setText("\u53D8\u91CF\u540D");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(paramTable, SWT.NONE);
		tblclmnNewColumn_2.setWidth(125);
		tblclmnNewColumn_2.setText("\u5F53\u524D\u503C");
		
		TableColumn tblclmnNewColumn_3 = new TableColumn(paramTable, SWT.NONE);
		tblclmnNewColumn_3.setWidth(125);
		tblclmnNewColumn_3.setText("\u9ED8\u8BA4/\u914D\u7F6E\u53C2\u6570");
		
		paramEditor=new TableEditor(paramTable);
		
		paramTable.addListener(SWT.MouseDown, new Listener(){

			@Override
			public void handleEvent(Event event)
			{
				int subIndex=paramTable.getSelectionIndex();
				if(subIndex==1 || subIndex==3 || subIndex==4){
					editorTable("76",paramTable,paramEditor,event);					
				}
			}
			
		});
		
		Group group_1 = new Group(this, SWT.NONE);
		group_1.setText("\u5B9E\u65F6\u7535\u538B");
		group_1.setBounds(565, 220, 546, 462);
		
		
		
//		voltageTableViewer=new TableViewer(group_1, );
//		Table table = voltageTableViewer.getTable();
		voltageTable = new Table(group_1, SWT.BORDER | SWT.FULL_SELECTION|SWT.VIRTUAL);
		voltageTable.setBounds(15, 32, 502, 404);
		voltageTable.setHeaderVisible(true);
		voltageTable.setLinesVisible(true);
		
		
		TableColumn tableColumn = new TableColumn(voltageTable, SWT.NONE);
		tableColumn.setWidth(230);
		tableColumn.setText("\u540D\u79F0");
		
		TableColumn tblclmnv = new TableColumn(voltageTable, SWT.NONE);
		tblclmnv.setWidth(170);
		tblclmnv.setText("\u503C(mV)");
		
		initResult();
		/*table.addListener(SWT.SetData, new Listener(){

			@Override
			public void handleEvent(Event event){
				TableItem item=(TableItem) event.item;
				int index=event.index;
				for(int i=0;i<16;i++){
					item.setText(0, "单体电压"+(i+1));
					item.setText(1, (i+1)+"");
				}
			}
		});*/
		
		Group group_2 = new Group(this, SWT.NONE);
		group_2.setFont(SWTResourceManager.getFont("微软雅黑", 17, SWT.NORMAL));
		group_2.setText("\u53C2\u6570\u5206\u6790");
		group_2.setBounds(565, 10, 546, 200);
		
//		tableViewer_2=new TableViewer(group_2, SWT.BORDER | SWT.FULL_SELECTION|SWT.VIRTUAL);
//		table_2 = tableViewer_2.getTable();
		analyseTabel = new Table(group_2, SWT.BORDER | SWT.FULL_SELECTION|SWT.VIRTUAL);
		analyseTabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		analyseTabel.setFont(SWTResourceManager.getFont("微软雅黑", 16, SWT.NORMAL));
		analyseTabel.setBounds(15, 46, 502, 144);
		analyseTabel.setHeaderVisible(true);
		analyseTabel.setLinesVisible(true);
		
		TableColumn tblclmnNewColumn_10 = new TableColumn(analyseTabel, SWT.NONE);
		tblclmnNewColumn_10.setWidth(105);
		tblclmnNewColumn_10.setText("\u540D\u79F0");
		
		TableColumn tblclmnNewColumn_11 = new TableColumn(analyseTabel, SWT.NONE);
		tblclmnNewColumn_11.setWidth(135);
		tblclmnNewColumn_11.setText("\u503C");
		
		TableColumn tblclmnNewColumn_12 = new TableColumn(analyseTabel, SWT.NONE);
		tblclmnNewColumn_12.setWidth(105);
		tblclmnNewColumn_12.setText("\u540D\u79F0");
		
		TableColumn tblclmnNewColumn_13 = new TableColumn(analyseTabel, SWT.NONE);
		tblclmnNewColumn_13.setWidth(135);
		tblclmnNewColumn_13.setText("\u503C");
		
		
//		TableColumn tblclmnNewColumn_4 = new TableColumn(paramTable, SWT.NONE);
//		tblclmnNewColumn_4.setWidth(85);
//		tblclmnNewColumn_4.setText("\u7ED3\u679C");
		
//		label_2 = new Label(group, SWT.NONE);
//		label_2.setText("\u8BBE\u5907\u5730\u5740:");
//		label_2.setBounds(82, 22, 51, 17);
//		
//		label_3 = new Label(group, SWT.NONE);
//		label_3.setText("\u7535\u538B\u91C7\u6837\u8282\u6570:");
//		label_3.setBounds(58, 64, 75, 17);
//		
//		label_4 = new Label(group, SWT.NONE);
//		label_4.setText("\u6E29\u5EA6\u91C7\u6837\u8282\u6570:");
//		label_4.setBounds(58, 106, 75, 17);
//		
//		deviceAddrText = new Text(group, SWT.BORDER);
//		deviceAddrText.setBounds(139, 19, 293, 23);
//		paramaterTexts[1] = deviceAddrText;
//		
//		VoltCountText = new Text(group, SWT.BORDER);
//		VoltCountText.setBounds(139, 61, 293, 23);
//		paramaterTexts[3] = VoltCountText;
//		
//		tempCountText = new Text(group, SWT.BORDER);
//		tempCountText.setBounds(139, 103, 293, 23);
//		paramaterTexts[4] = tempCountText;
//		Label label_5 = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
//		label_5.setText("\u4EE5\u4E0B\u4FE1\u606F\u4E0D\u53EF\u4FEE\u6539");
//		label_5.setBounds(25, 145, 455, 2);
//		
//		Label label_6 = new Label(group, SWT.NONE);
//		label_6.setBounds(10, 169, 123, 17);
//		label_6.setText("\u7F51\u7EDC\u53F7\u8282\u70B9\u53F7\u914D\u7F6E\u6807\u5FD7:");
//		
//		Label lblNewLabel = new Label(group, SWT.NONE);
//		lblNewLabel.setBounds(34, 211, 99, 17);
//		lblNewLabel.setText("\u91C7\u6837\u8282\u6570\u914D\u7F6E\u6807\u5FD7:");
//		
//		Label label_7 = new Label(group, SWT.NONE);
//		label_7.setBounds(22, 253, 111, 17);
//		label_7.setText("\u91C7\u6837\u7EBF\u5224\u65AD\u914D\u7F6E\u6807\u5FD7:");
//		
//		Label label_8 = new Label(group, SWT.NONE);
//		label_8.setBounds(22, 295, 111, 17);
//		label_8.setText("\u7535\u538B\u91C7\u6837\u65AD\u7EBF\u9AD8\u538B\u70B9:");
//		
//		Label label_9 = new Label(group, SWT.NONE);
//		label_9.setBounds(22, 337, 111, 17);
//		label_9.setText("\u7535\u538B\u91C7\u6837\u65AD\u7EBF\u4F4E\u538B\u70B9:");
//		
//		Label label_10 = new Label(group, SWT.NONE);
//		label_10.setBounds(10, 379, 123, 17);
//		label_10.setText("\u6E29\u5EA6\u91C7\u6837\u7EBF\u65AD\u7EBF\u9AD8\u6E29\u70B9:");
//		
//		Label lblNewLabel_1 = new Label(group, SWT.NONE);
//		lblNewLabel_1.setBounds(10, 421, 123, 17);
//		lblNewLabel_1.setText("\u6E29\u5EA6\u91C7\u6837\u7EBF\u65AD\u7EBF\u4F4E\u6E29\u70B9:");
//		
//		text_0 = new Text(group, SWT.BORDER);
//		text_0.setEnabled(false);
//		text_0.setBounds(139, 166, 293, 23);
//		paramaterTexts[0] =text_0 ;
//		
//		text_5 = new Text(group, SWT.BORDER);
//		text_5.setEnabled(false);
//		text_5.setBounds(139, 250, 293, 23);
//		paramaterTexts[5] = text_5;
//		
//		text_7 = new Text(group, SWT.BORDER);
//		text_7.setEnabled(false);
//		text_7.setBounds(139, 334, 293, 23);
//		paramaterTexts[7] = text_7;
//		
//		text_2 = new Text(group, SWT.BORDER);
//		text_2.setEnabled(false);
//		text_2.setBounds(139, 208, 293, 23);
//		paramaterTexts[2] = text_2;
//		
//		text_8 = new Text(group, SWT.BORDER);
//		text_8.setEnabled(false);
//		text_8.setBounds(139, 376, 293, 23);
//		paramaterTexts[8] = text_8;
//		
//		text_9 = new Text(group, SWT.BORDER);
//		text_9.setEnabled(false);
//		text_9.setBounds(139, 418, 293, 23);
//		paramaterTexts[9] = text_9;
//		
//		text_6 = new Text(group, SWT.BORDER);
//		text_6.setEnabled(false);
//		text_6.setBounds(139, 292, 293, 23);
//		paramaterTexts[6] = text_6;
//		btnNewButton = new Button(group, SWT.NONE);
//		btnNewButton.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				//读取参数
//				readOrWrite=1;
//				testChekSteps.clear();
//				testChekSteps.add(readData);
//				CanController.getInstance().clearData("76");
//				clearParamTexts();
//				if(Mainframe.window!=null){
//					Mainframe.window.write();
//				}
//			}
//		});
//		btnNewButton.setBounds(139, 457, 80, 27);
//		btnNewButton.setText("\u8BFB\u53D6");
		
		button_2 = new Button(group, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//写入参数
				readOrWrite=2;
				testChekSteps.clear();
				testChekSteps.add(writeData);
				//writeToWidet();
				if(Mainframe.window!=null){
					Mainframe.window.write();
				}
			}
		});
		button_2.setBounds(392, 415, 80, 27);
		button_2.setText("\u5199\u5165");
		
		Label label = new Label(group, SWT.WRAP);
		label.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.BOLD));
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label.setBounds(15, 404, 269, 38);
		label.setText("\u8BF4\u660E\uFF1A\"\u9ED8\u8BA4/\u914D\u7F6E\u53C2\u6570\"\u5217\u7070\u8272\u80CC\u666F\u8868\u793A\u4E0D\u53EF\u4FEE\u6539\uFF0C\u767D\u8272\u80CC\u666F\u8868\u793A\u53EF\u4EE5\u4FEE\u6539\u7684\u53C2\u6570\u3002");
		
		readData = new ReadData();
		writeData = new WriteData();
		
		dataRowSlaveAddr.setVarName("从控板地址");
		dataRowModlue.setVarName("QR_Modlue");
		initResult();
		dataRowsList.addAll(CanController.getInstance().getDatas().get("85"));//电压
		List<DataRow> list=CanController.getInstance().getDatas().get("87");
//		for(int i=0;i<list.size();i++){
//			DataRow dataRow=list.get(i);
//			String string=null;
//			if(dataRow!=null){
//				if(dataRow.getVal()!=null && dataRow.getVal()!=""){
//					string=(Integer.parseInt(dataRow.getVal())-40)+"";
//				}
//			}
//			dataRow.setVal(string);
//			list.set(i,dataRow);
//		}
		dataRowsList.addAll(list);//温度
		dataRowsList.add(dataRowSlaveAddr);
		dataRowsList.add(dataRowModlue);
		softVersion = CanController.getInstance().getDatas().get("1").get(3);
		hardVersion = CanController.getInstance().getDatas().get("1").get(4);
		softVersion.setVarName("softVersion");
		hardVersion.setVarName("hardVersion");
		dataRowsList.add(softVersion);
		dataRowsList.add(hardVersion);
		saveDataService = new ExportDataService(new CSVFile(),dataRowsList.toArray(new DataRow[0]));
		new Thread(saveDataService).start();

		voltDataRows=CanController.getInstance().getDatas().get("85");
		tempDataRows=CanController.getInstance().getDatas().get("87");
//		initResult();
		initVoltageTabel();
		initAnalyseTabel();
	}
	public boolean checkConnect(){
		if(!CanController.getInstance().isConnect()){
			MessageBox box = new MessageBox(shell, SWT.NONE);
			box.setMessage("未连接设备");
			box.open();
			return false;
		}
		return true;
	}
	public void clearParamTexts(){
		for(int i = 0,len = paramaterTexts.length;i < len;i++)
		{
			paramaterTexts[i].setText("");
//			error
		}
	}

	
	public void initResult(){
		paramaterList = CanController.getInstance().getDatas().get("76");
		Collections.sort(paramaterList, compare);
		paramTable.removeAll();
		
		int rows=paramaterList.size();
		for(int i=0;i<rows;i++){
			DataRow idata=paramaterList.get(i);
			TableItem tableItem = new TableItem(paramTable, SWT.NONE);
			tableItem.setText(0, idata.getVarName()==null?"":idata.getVarName());
			tableItem.setText(1, idata.getVal()==null?"":idata.getVal());
			if(i==1||i==3||i==4){
				if(i==3||i==4){
					tableItem.setText(2, idata.getInital()==null?"":idata.getInital());
					idata.setParam(idata.getInital());
				}
				
			}else{
				tableItem.setText(2, idata.getInital()==null?"":idata.getInital());
				idata.setParam(idata.getInital());
				tableItem.setBackground(2,SWTResourceManager.getColor(SWT.COLOR_GRAY));
			}
		}
		
	}
	//刷新界面
	public void refreshTables(){
		refreshParamTabel();
		refreshAnalyseTabel();
		refreshVoltTabel();
	}
	private void refreshParamTabel(){
		Table table=paramTable;
		List<DataRow> param=CanController.getInstance().getDatas().get("76");
		for(int i = 0;i < table.getItemCount();i++)
		{
			if(i<param.size()){
				DataRow data=param.get(i);
				if(data!=null){
					TableItem item=table.getItem(i);
					item.setText(1,data.getVal()==null?"":data.getVal());
//					if((col+1)<cols&&(item.getText(col+1)==null||item.getText(col+1).trim().length()==0)){
//						item.setText(col+1,data.getVal()==null?"":data.getVal());
//					}
//					if(data.getVal()!=null&&data.getVal().equals(data.getParam())){
//						table.getItem(i).setImage(cols-1, ok);
//					}else{
//						table.getItem(i).setImage(cols-1, error);
//					}
				}
			}
		}
	}
	private String parseTempData(String str){
		String ret = "";
		Long temp = parseLong(str);
		if(temp!=null){
//			ret=(temp-40)+ "℃";
			ret=temp+ "℃";
		}
		return ret;
	}
	//刷新数据分析表格
	private void refreshAnalyseTabel(){
		String[][] analyseDatas = ResultCache.getInstance().getAnalyseDatas();
		Table table=analyseTabel;
		if(0xff==CanController.getInstance().object){			
			analyseDatas[0][1] ="";//从控板ID
		}else{
			analyseDatas[0][1] = CanController.getInstance().object+"";//从控板ID
		}
		if(tempDataRows!=null && tempDataRows.size()>=1){//温度1
			DataRow data= tempDataRows.get(0);
			if(data!=null){
				analyseDatas[1][1]=parseTempData(data.getVal());			
			}
		}
		if(tempDataRows!=null && tempDataRows.size()>=2){//温度2
			DataRow data= tempDataRows.get(1);
			if(data!=null){
				analyseDatas[2][1]=parseTempData(data.getVal());
			}
		}
		
		for(int i = 0;i < table.getItemCount();i++)
		{
			TableItem item=table.getItem(i);
			item.setText(1,analyseDatas[i][1]);
			item.setText(3,analyseDatas[i][3]);
		}
	}
	/**
	 * 刷新电压
	 */
	private void refreshVoltTabel(){
		Table table=voltageTable;
		String[][] analyseDatas = ResultCache.getInstance().getAnalyseDatas();
		Long tempLong = null;
		Long maxVolt=null,minVolt=null;

		
		for(int i = 0;i < table.getItemCount();i++)
		{
			if(i<voltDataRows.size()){
				DataRow data=voltDataRows.get(i);
				if(data!=null){
					TableItem item=table.getItem(i);
					item.setText(1,data.getVal()==null?"":data.getVal());
					
					tempLong = parseLong(data.getVal());
					if(tempLong!=null){
						if(i ==0 ){
							maxVolt = tempLong;
							minVolt = tempLong;
						}else{
							if(tempLong>maxVolt){
								maxVolt = tempLong;
							}
							if(tempLong<minVolt){
								minVolt = tempLong;
							}
						}
					}
//					else{
//						maxVolt=null;
//						minVolt=null;
//					}
					
				}
			}
		}
		if(maxVolt!=null && minVolt!=null){			
			analyseDatas[0][3]=Math.abs(maxVolt - minVolt)+"mV";
			analyseDatas[1][3] = maxVolt+"mV";
			analyseDatas[2][3] =minVolt + "mV";
		}else{
			analyseDatas[0][3]="";
			analyseDatas[1][3]="";
			analyseDatas[2][3]="";
		}
	}
	private String getVersion(){
		List<DataRow> system=CanController.getInstance().getDatas().get("1");
		int versionl=Integer.parseInt(system.get(3).getVal());
		int versionh=Integer.parseInt(system.get(4).getVal());
		return getVersion(versionh,versionl);
	}
	
	public String getVersion(int h,int l){
		int temp=0;
		temp=(h>>8)&0xff;
		String vaa=getFormatStr(temp)+".";
		temp=h&0xff;
		vaa+=getFormatStr(temp)+".";
		
		temp=(l>>8)&0xff;
		vaa+=getFormatStr(temp)+".";
		temp=l&0xff;
		vaa+=getFormatStr(temp);
		return vaa;
	}
	public String getFormatStr(int data){
		return data<10?"0"+data:data+"";
	}
	public ExportDataService getSaveDataService()
	{
		return saveDataService;
	}
	@Override
	public void dispose()
	{
		if(saveDataService!=null){
			saveDataService.setRun(false);
			saveDataService.setStop(false);
		}
		super.dispose();
	}
	public Long parseLong(String s){
		if(s==null || "".equals(s)){
			return null;
		}
		try
		{
			return Long.parseLong(s);
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public boolean writeToWidet(){
		long[] writeValues=ResultCache.getInstance().getWriteValues();
		List<DataRow> list = CanController.getInstance().getDatas().get("76");
		boolean ret = true;
		for(int i = 0 ;i<paramaterTexts.length;i++){
			if(i==1||i==3||i==4){
				if(paramaterTexts[i]!=null){
					String temp = paramaterTexts[i].getText();	
					Long l =  parseLong(temp);
					if(l!=-1 &&list!=null && list.get(i)!=null ){						
						list.get(i).setParam(temp);
						writeValues[i] = l;
					}else{
						ret &=false;
					}
				}
				continue;
			}
			if(paramaterTexts[i]!=null){
				paramaterTexts[i].setText(writeValues[i]+"");
				list.get(i).setParam(writeValues[i]+"");
			}
		}
		return ret ;
	}
//	public boolean readToWidet(){
//		long[] writeValues=ResultCache.getInstance().getWriteValues();
//		List<DataRow> list = CanController.getInstance().getDatas().get("76");
//		boolean ret = true;
//		for(int i = 0 ;i<paramaterTexts.length;i++){
//			if(i==1||i==3||i==4){
//				continue;
//			}
//			if(paramaterTexts[i]!=null){
//				paramaterTexts[i].setText(writeValues[i]+"");
//			}
//		}
//		return ret ;
//	}
	
	public void editorTable(final String index,Table table,TableEditor editor,Event event){
		
		Control old=editor.getEditor();
		if(old!=null){
			old.dispose();
		}
		Point point=new Point(event.x,event.y);
		final TableItem item=table.getItem(point);
		final int subIndex=table.getSelectionIndex();
		if(item==null){
			return;
		}
		int col=-1;
		for(int i=0;i<table.getColumnCount();i++){
			Rectangle rectang=item.getBounds(i);
			if(rectang.contains(point)){
				col=i;
				break;
			}
		}
		if(col!=2){
			return;
		}
		final Text text=new Text(table,SWT.NONE);
		text.setText(item.getText(col));
		text.setForeground(item.getForeground());
		text.selectAll();
		text.setFocus();
		editor.grabHorizontal=true;
		editor.minimumWidth=text.getBounds().width;
		editor.setEditor(text,item,col);
		final int editorcol=col;
		text.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e)
			{
				
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				DataRow row=CanController.getInstance().getDatas().get(index).get(subIndex);
				row.setParam(text.getText());
				//System.out.println(row.getVarName() + " : value = " + row.getParam());
				item.setText(editorcol, text.getText());
			}
			
		});
	}
	private void initAnalyseTabel(){
		
		String[][] analyseDatas = ResultCache.getInstance().getAnalyseDatas();
		if(analyseDatas!=null && analyseDatas.length>0){
			for(int i = 0 ;i <analyseDatas.length;i++){
				TableItem item=new TableItem(analyseTabel, SWT.NONE);
				item.setText(0,analyseDatas[i][0]);
				item.setText(2,analyseDatas[i][2]);
				item.setForeground(1, SWTResourceManager.getColor(SWT.COLOR_RED));
				item.setForeground(3, SWTResourceManager.getColor(SWT.COLOR_RED));
				item.setFont(1, SWTResourceManager.getFont("微软雅黑", 16, SWT.BOLD));
				item.setFont(3, SWTResourceManager.getFont("微软雅黑", 16, SWT.BOLD));
				
			}
		}
	}
	private void initVoltageTabel(){
		Table table = voltageTable;
		DataRow dr = null;
		String val = null;
		if(voltDataRows!=null && voltDataRows.size()>0){			
			for(int i = 0;i < voltDataRows.size();i++)
			{
				TableItem item=new TableItem(table, SWT.NONE);
				dr = voltDataRows.get(i);					
				if(voltDataRows.get(i)!=null){
					val = dr.getVarName();
					if(val!=null){
						item.setText(0,val);						
					}

				}
			}
		}
	}
}


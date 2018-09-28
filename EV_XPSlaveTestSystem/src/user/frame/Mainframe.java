package user.frame;

import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.List;

import javax.comm.CommPortIdentifier;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import user.CanDriver.CanController;
import user.CheckTask.CheckThread;
import user.CheckTest.TestChekStep;
import user.Method.ExcelUtils;
import user.Method.ResultCache;
import user.frame.dialog.MessageDialog;
import user.model.DataRow;
import user.service.ExportDataService;
import user.util.TextVerifyListener;


public class Mainframe implements Runnable {

	public static Shell userShell;
	public final static CheckThread userCheckThread = new CheckThread();
	private Combo batteryPort,canPort,canRate,sourceAddr;
	private CLabel canStatusLabel,faultStatusLabel,alarmStatusLabel,softVersionLabel;
	public static Image ok,error,green,red,gray;
	private static ProgressMonitorDialog progress;
	public static final int[] RATE={125};
	public static int workstate=0;
	private static boolean isTest=false;
//	private TestFrame testComposite;
	private TabFrame currentFrame,composite1;//,composite2,composite3;
//	private DebugFrame debugComposite;
//	private SettingFrame settingComposite;
	public static String tempuserMessage = null;
	private boolean checkUser=false;
	private Text ip1text;
	private Text ip2text;
	private Text ip3text;
	private Text ip4text;
	private Text porttext;
	private Button exportButton;
	public static Mainframe window;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) 
	{
		try 
		{
			window = new Mainframe();
			window.open();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	/**
	 * Open the window.
	 */
	@SuppressWarnings("deprecation")
	public void open() 
	{
		Display display = Display.getDefault();
		ok=new Image(display,"icons/ok_16.png");
		error=new Image(display,"icons/error_16.png");
		green=new Image(display,"icons/ok.png");
		red=new Image(display,"icons/fail.png");
		gray=new Image(display,"icons/unKnown.png");;
				
		if(ExcelUtils.readFromFile("BMU_CAN_XP.xls") != 0)
		{
			;
		}
		
//		List<ParamRow> list=CanController.getInstance().getParam();
//		String lastTestType=ReadConfigUtils.getInstance().getProperties("testType");
//		ParamRow current=list.get(0);
//		for(int i=0;i<list.size();i++){
//			String typeItems=list.get(i).getType();
//			if(lastTestType!=null&&lastTestType.equals(typeItems)){
//				current=list.get(i);
//			}
//		}
//		CanController.getInstance().setCurrentParam(current);
		
		createContents();
		userShell.open();
		userShell.layout();
		
		
		
		try
		{
			while (!userShell.isDisposed()) 
			{
				if (!display.readAndDispatch()) 
				{
					display.sleep();
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		//SimilBatteryDriver.getInstance().ReleaseComPort();
		CanController.getInstance().disConnect();
		System.exit(1);
		//ComDevice.CloseCom();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		userShell = new Shell();
		userShell.setSize(1391, 820);
		userShell.setText("BMU_XP\u6D4B\u8BD5\u4E0A\u4F4D\u673A  V1.04");
		progress=new ProgressMonitorDialog(userShell); 
        //获取屏幕高度和宽度
        int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
        
        userShell.setLocation((screenW -userShell.getSize().x)/2, (screenH-userShell.getSize().y)/2);
		userShell.setLayout(new FormLayout());
		
		//连接信息
		Group grpBms = new Group(userShell, SWT.NONE);
		FormData fd_grpBms = new FormData();
		fd_grpBms.bottom = new FormAttachment(0, 200);
		fd_grpBms.right = new FormAttachment(0, 201);
		fd_grpBms.top = new FormAttachment(0);
		fd_grpBms.left = new FormAttachment(0, 10);
		grpBms.setLayoutData(fd_grpBms);
		grpBms.setText("\u8BBE\u5907\u901A\u8BAF");
		
		Label lblBms = new Label(grpBms, SWT.NONE);
		lblBms.setText("\u8BBE\u5907\u7D22\u5F15\u53F7:");
		lblBms.setBounds(10, 25, 63, 17);
		
		Label label_1 = new Label(grpBms, SWT.NONE);
		label_1.setText("\u6CE2\u7279\u7387:");
		label_1.setBounds(34, 70, 39, 17);
		
	
		CommPortIdentifier portId = null;
		
		canPort = new Combo(grpBms, SWT.NONE);
		batteryPort = new Combo(grpBms, SWT.NONE);
		batteryPort.setBounds(85, 153, 87, 20);
		batteryPort.add("无");
		batteryPort.setVisible(false);
				
		int Count = 0;
		
		@SuppressWarnings("rawtypes")
		Enumeration  en = CommPortIdentifier.getPortIdentifiers();
		
		while(en.hasMoreElements())
		{
			portId = (CommPortIdentifier) en.nextElement();
			
			if(portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
			{
				//batteryPort.add(portId.getName());
				Count++;
			}			
		}
//		if(Count == 0)
//		{
//			batteryPort.select(0);
//		}
//		else
//		{
//			batteryPort.select(Count - 1);
//		}
		canPort.setItems(new String[]{"0","1","2","3","4","5","6","7"});
		canPort.setBounds(85, 20, 87, 20);
		canPort.select(0);
		canRate = new Combo(grpBms, SWT.NONE);
		canRate.setItems(new String[] {"500k"});
		canRate.setBounds(85, 65, 87, 20);
		canRate.select(0);
//		String[] addrs=new String[30];
//		for(int i=0;i<30;i++){
//			addrs[i]=i+"";
//		}
//		String[] addrs=new String[]{"96","110","105","80"};
//		sourceAddr = new Combo(grpBms, SWT.NONE);
//		sourceAddr.setItems(addrs);
//		sourceAddr.setBounds(85, 114, 87, 20);
//		sourceAddr.select(0);
		
		final Button button_openCom = new Button(grpBms, SWT.NONE);
		button_openCom.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				MessageBox box= new MessageBox(userShell, SWT.ICON_ERROR);
				int result = -1;
				
				if(button_openCom.getText().equalsIgnoreCase("连接") == true)
				{				
					try
					{
						int port=canPort.getSelectionIndex();
						int rate = RATE[canRate.getSelectionIndex()];
						//int object = Integer.parseInt(sourceAddr.getText());
						CanController.getInstance().comindex=port;
						//CanController.getInstance().object=object;
						if(rate==125){
							CanController.getInstance().ConfigCan.Timing0 = (byte) (0x00&0xff);
							CanController.getInstance().ConfigCan.Timing1 = (byte) (0x1c & 0xff);
						}
						result =CanController.getInstance().connect();
						//result=1;
						if(result != 1)
						{
							
							box.setMessage("连接CAN通讯盒失败!");
							box.open();
							return;
						}						
//						if(!"无".equals(batteryPort.getText())){
//							if(SimilBatteryDriver.getInstance().Start(Integer.parseInt(batteryPort.getText().substring(3))) == false)
//							{
//								box.setMessage("连接至模拟器失败");
//								box.open();
//								return;
//							}
//						}
						//sourceAddr.setEnabled(false);
						canPort.setEnabled(false);
						//batteryPort.setEnabled(false);
						canRate.setEnabled(false);
					}
					catch(NumberFormatException ek)
					{
						box.setMessage("参数设置异常");
						box.open();
						return;
					}
					
					button_openCom.setText("断开");
					
					
				}
				else
				{
					CanController.getInstance().disConnect();
					
					//SimilBatteryDriver.getInstance().ReleaseComPort();
//					sourceAddr.setEnabled(true);
					canPort.setEnabled(true);
					//batteryPort.setEnabled(true);
					canRate.setEnabled(true);
					button_openCom.setText("连接");
					clear();
				}
			}
		});
		button_openCom.setText("\u8FDE\u63A5");
		button_openCom.setBounds(53, 136, 80, 27);
		
		Label lblLink = new Label(grpBms, SWT.NONE);
		lblLink.setText("SBY\u4E32\u53E3\u53F7:");
		lblLink.setBounds(19, 151, 61, 15);
		lblLink.setVisible(false);	
				

		
		//设置
		Group group_1 = new Group(userShell, SWT.NONE);
		FormData fd_group_1 = new FormData();
		fd_group_1.bottom = new FormAttachment(grpBms, 100, SWT.BOTTOM);
		fd_group_1.right = new FormAttachment(grpBms, 0, SWT.RIGHT);
		fd_group_1.left = new FormAttachment(grpBms, 0, SWT.LEFT);
		fd_group_1.top = new FormAttachment(grpBms, 5, SWT.BOTTOM);
		group_1.setLayoutData(fd_group_1);
		group_1.setText("\u8BBE\u7F6E");
		group_1.setVisible(false);
		
		
		Group group = new Group(userShell, SWT.NONE);
		group.setText("\u68C0\u6D4B");
		FormData fd_group = new FormData();
		fd_group.bottom = new FormAttachment(group_1, 250);
		fd_group.top = new FormAttachment(group_1, 28);
		fd_group.right = new FormAttachment(grpBms, 0, SWT.RIGHT);
		fd_group.left = new FormAttachment(0, 10);
		group.setLayoutData(fd_group);
		group.setVisible(false);
		//系统信息
		Group group_6 = new Group(userShell, SWT.NONE);
		FormData fd_group_6 = new FormData();
		fd_group_6.bottom = new FormAttachment(100, -10);
		fd_group_6.left = new FormAttachment(0, 10);
		fd_group_6.top = new FormAttachment(grpBms, 20,SWT.BOTTOM);
		fd_group_6.right = new FormAttachment(grpBms, 0, SWT.RIGHT);
		
		Button button_2 = new Button(group, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				write();
			}
		});
		button_2.setText("\u5199");
		button_2.setBounds(59, 96, 80, 27);
		
		Button button = new Button(group, SWT.NONE);
		button.setBounds(59, 52, 80, 27);
		button.setText("\u8BFB");
		
		
		Label label = new Label(group_1, SWT.NONE);
		label.setBounds(6, 35, 51, 17);
		label.setText("\u4FDD\u5B58\u5468\u671F:");
		
		porttext = new Text(group_1, SWT.BORDER);
		porttext.setText("1");
		porttext.setBounds(63, 32, 45, 23);
		
		Label label_5 = new Label(group_1, SWT.NONE);
		label_5.setText("\u79D2");
		label_5.setBounds(114, 35, 19, 17);
		
		exportButton = new Button(group_1, SWT.NONE);
		exportButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExportDataService export =composite1.getSaveDataService();
				if("保存".equals(exportButton.getText())){
					if(export!=null){
						export.setRun(true);
					}
					int delayTime= parseInt(porttext.getText());
					if(delayTime!=-1){
						export.setRunTimes(delayTime*1000);
					}
					exportButton.setText("停止");
				}else{
					exportButton.setText("保存");
					if(export!=null){
						export.setRun(false);
					}
				}
			}
		});
		exportButton.setBounds(139, 30, 45, 27);
		exportButton.setText("\u4FDD\u5B58");
		porttext.addVerifyListener(new TextVerifyListener(5));
		group_6.setLayoutData(fd_group_6);
		group_6.setText("\u7CFB\u7EDF\u4FE1\u606F");
		
		Label label_2 = new Label(group_6, SWT.NONE);
		label_2.setBounds(22, 23, 60, 17);
		label_2.setText("\u901A\u4FE1\u72B6\u6001\uFF1A");
		
		Label lblNewLabel = new Label(group_6, SWT.NONE);
		lblNewLabel.setBounds(34, 63, 48, 17);
		lblNewLabel.setText("\u6545\u969C\u7801\uFF1A");
		
		Label lblNewLabel_1 = new Label(group_6, SWT.NONE);
		lblNewLabel_1.setBounds(34, 103, 48, 17);
		lblNewLabel_1.setText("\u544A\u8B66\u7801\uFF1A");
		
		Label label_4 = new Label(group_6, SWT.NONE);
		label_4.setBounds(22, 143, 60, 17);
		label_4.setText("\u8F6F\u4EF6\u7248\u672C\uFF1A");
		
		canStatusLabel = new CLabel(group_6, SWT.NONE);
		canStatusLabel.setBounds(88, 18, 67, 23);
		canStatusLabel.setText("");
		
		faultStatusLabel = new CLabel(group_6, SWT.NONE);
		faultStatusLabel.setBounds(88, 59, 67, 23);
		faultStatusLabel.setText("");
		faultStatusLabel.setImage(gray);
		
		alarmStatusLabel = new CLabel(group_6, SWT.NONE);
		alarmStatusLabel.setBounds(88, 100, 67, 23);
		alarmStatusLabel.setText("");
		alarmStatusLabel.setImage(gray);
		
		softVersionLabel = new CLabel(group_6, SWT.NONE);
		softVersionLabel.setBounds(88, 141, 93, 23);
		softVersionLabel.setText("");
		
		
		final TabFolder tabFolder = new TabFolder(userShell, SWT.NONE);
		FormData fd_tabFolder = new FormData();
		fd_tabFolder.bottom = new FormAttachment(100, -10);
		fd_tabFolder.top = new FormAttachment(0);
		fd_tabFolder.right = new FormAttachment(100, -10);
		fd_tabFolder.left = new FormAttachment(grpBms, 10,SWT.RIGHT);
		
		objectText = new Text(grpBms, SWT.BORDER);
		objectText.setBounds(85, 110, 87, 23);
		objectText.setEnabled(false);
		objectText.setVisible(false);
		Label label_3 = new Label(grpBms, SWT.NONE);
		label_3.setEnabled(false);
		label_3.setBounds(22, 113, 51, 17);
		label_3.setText("\u76EE\u6807\u5730\u5740:");
		tabFolder.setLayoutData(fd_tabFolder);
		label_3.setVisible(false);
		
		TabItem tbtmQRCode = new TabItem(tabFolder, SWT.NONE);
		tbtmQRCode.setText("\u8BBE\u5907\u4FE1\u606F");
		ScrolledComposite testScrolledComposite = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		testScrolledComposite.setLayout(new FillLayout());
		testScrolledComposite.setExpandHorizontal(true);
		testScrolledComposite.setExpandVertical(true);
		tbtmQRCode.setControl(testScrolledComposite);
		
		Composite composites = new Composite(testScrolledComposite, SWT.NONE);
		composites.setLayout(new FillLayout());
		composite1=new QRcodeFrame(composites,SWT.NONE );
		
		testScrolledComposite.setContent(composites);
		testScrolledComposite.setMinSize(composites.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		currentFrame = composite1;
		
//		TabItem tbtmPARAM = new TabItem(tabFolder, SWT.NONE);
//		tbtmPARAM.setText("写入参数");
//		
//		ScrolledComposite debugScrolledComposite = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
//		debugScrolledComposite.setLayout(new FillLayout());
//		debugScrolledComposite.setExpandHorizontal(true);
//		debugScrolledComposite.setExpandVertical(true);
//		tbtmPARAM.setControl(debugScrolledComposite);
//		
//		Composite composite1 = new Composite(debugScrolledComposite, SWT.NONE);
//		composite1.setLayout(new FillLayout());
//		debugScrolledComposite.setContent(composite1);
//		
//		composite2=new ParamaterFrame(composite1,SWT.NONE );
//		debugScrolledComposite.setMinSize(composite1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
//		composite1.layout();
		
		
//		TabItem tbtmNewItem_2 = new TabItem(tabFolder, SWT.NONE);
//		tbtmNewItem_2.setText("HVMU\u6D4B\u8BD5");
//		
//		ScrolledComposite settingScrolledComposite= new ScrolledComposite(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
//		settingScrolledComposite.setLayout(new FillLayout());
//		settingScrolledComposite.setExpandHorizontal(true);
//		settingScrolledComposite.setExpandVertical(true);
//		tbtmNewItem_2.setControl(settingScrolledComposite);
//		
//		Composite composite2 = new Composite(settingScrolledComposite, SWT.NONE);
//		composite2.setLayout(new FillLayout());
//
//		composite3=new TestHvmuFrame(composite2,SWT.NONE );
//		
//		settingScrolledComposite.setContent(composite2);
//		settingScrolledComposite.setMinSize(composite2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		tabFolder.setSelection(tbtmQRCode);


		
		tabFolder.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e)
			{
//				currentFrame = Mainframe.this.composite2;
//				tabFolder.setSelection(1);
//				if(true){
//					return;
//				}
//				int tabid=tabFolder.getSelectionIndex();
//				if(tabid==0){
//					currentFrame = Mainframe.this.composite1;
//				}else if(tabid==1){
//					currentFrame = Mainframe.this.composite2;
//				}else if(tabid==2){
//					//currentFrame = Mainframe.this.composite3;
//				}
//				if(!checkUser&&tabid==1){					
//				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e)
			{
				
			}
			
		});
		initValue();
//		Thread battery=new Thread(batteryTask);
//		battery.setDaemon(true);
//		battery.start();
		Thread flashThread=new Thread(this);
		flashThread.setDaemon(true);
		flashThread.start();
	}
	protected void clear()
	{
		CanController.getInstance().object = 0xff;
		CanController.getInstance().clearData("76");
		CanController.getInstance().clearData("85");
		CanController.getInstance().clearData("87");
		CanController.getInstance().clearData("1");
		 String[][] analyseDatas = ResultCache.getInstance().getAnalyseDatas();
		 for(int i= 0;i<analyseDatas.length;i++){
			 analyseDatas[i][1]="";
			 analyseDatas[i][3]="";
		 }
	}
	protected int parseInt(String text)
	{
		try
		{
			return Integer.parseInt(text);
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	private void flashObject(final String object){
		Display.getDefault().syncExec(new Runnable()
		{
			
			@Override
			public void run()
			{
				if(objectText!=null && !objectText.isDisposed()){				
					objectText.setText(object);
				}
			}
		});
		
		
	}
	public void run(){
		while(true){
			flashObject(CanController.getInstance().object+"");
			//System.out.println("flashFrame run isTest:" +isTest);
//			if(isTest){
				flashFrame();
//			}
//			if(CanController.getInstance().isConnect()){
			flashSystemInfo();
//			}
			if(isTest){
				delay(100);
			}else{
				delay(500);
			}
		}
	}
	public boolean checkConnect(){
		if(!CanController.getInstance().isConnect()){
			MessageBox box = new MessageBox(userShell, SWT.NONE);
			box.setMessage("未连接设备");
			box.open();
			return false;
		}
		return true;
	}
	
	public void initValue(){
		//初始化检测类型
//		List<ParamRow> list=CanController.getInstance().getParam();
//		String lastTestType=ReadConfigUtils.getInstance().getProperties("testType");
//		int lastId=0;
//		String[] typeItems=new String[list.size()];
//		ParamRow current=list.get(0);
//		for(int i=0;i<list.size();i++){
//			typeItems[i]=list.get(i).getType();
//			if(lastTestType!=null&&lastTestType.equals(typeItems[i])){
//				lastId=i;
//				current=list.get(i);
//			}
//		}
//		CanController.getInstance().setCurrentParam(current);
		initResult();
		
	}
	
	public void initResult(){
		composite1.initResult();
//		composite2.initResult();
//		composite3.initResult();
	}
	
//	public void setTestType(String typs){
//		List<ParamRow> list=CanController.getInstance().getParam();
//		for(int i=0;i<list.size();i++){
//			String type=list.get(i).getType();
//			if(typs!=null&&typs.equals(type)){
//				CanController.getInstance().setCurrentParam(list.get(i));
//				testComposite.initbalancetable();
//				testComposite.initResult();
//				debugComposite.initResult();
//				settingComposite.initResult();
//			}
//		}
//	}
	
	public static void delay(int time) {  
		try
		{
			Thread.sleep(time);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}   
	
	public static void showMessage(final String message){
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
			public void run() 
			{
				MessageBox box = new MessageBox(userShell, SWT.NONE);
				box.setMessage(message);
				box.open();
			}
		});
	}
	

	public void flashFrame(){
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
			public void run() 
			{
				currentFrame.refreshTables();

			}
		});
		
	}
	
	public void flashSystemInfo(){
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() 
			{
				
				
				List<DataRow> system=CanController.getInstance().getDatas().get("1");
				List<DataRow> fault=CanController.getInstance().getDatas().get("53");
				List<DataRow> alarm=CanController.getInstance().getDatas().get("52");
				String faultCode="";
				String alarmCode="";
				String version="";
				//String sourceId="";
				
				
				if(CanController.getInstance().isConnect()){
					try
					{
						int versionl=Integer.parseInt(system.get(3).getVal());
						int versionh=Integer.parseInt(system.get(4).getVal());
						version=getVersion(versionh,versionl);
						faultCode=fault.get(0).getVal();
						alarmCode=alarm.get(0).getVal();
						//sourceId=CanController.getInstance().getBoardSourceId()+"";
					} catch (Exception e)
					{
					}
//					settingComposite.flashActiveParam();
//					settingComposite.flashParam();
//					debugComposite.flashDebugTable();
//					debugComposite.flashDebugBatteryTable();
				}
				
				if(softVersionLabel==null || softVersionLabel.isDisposed()){
					return ;
				}
				softVersionLabel.setText(version);
			
				
				faultStatusLabel.setText(faultCode);
				alarmStatusLabel.setText(alarmCode);
//				if("0".equals(faultCode)){
//					faultStatusLabel.setImage(green);
//				}else{
//					faultStatusLabel.setImage(red);
//				}
//				
//				if("0".equals(alarmCode)){
//					alarmStatusLabel.setImage(green);
//				}else{
//					alarmStatusLabel.setImage(red);
//				}
//				canStatusLabel.setText(sourceId);
				if(CanController.getInstance().isConnectBoard()){
					canStatusLabel.setImage(green);
				}else{
					canStatusLabel.setImage(red);
				}
				
			}
		});
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
	

	
	public  void flashParam(Table table,String index,int col){
		
		List<DataRow> param=CanController.getInstance().getDatas().get(index);
		int cols=table.getColumnCount();
		for(int i = 0;i < table.getItemCount();i++)
		{
			if(i<param.size()){
				DataRow data=param.get(i);
				if(data!=null){
					TableItem item=table.getItem(i);
					item.setText(col,data.getVal()==null?"":data.getVal());
//					if((col+1)<cols&&(item.getText(col+1)==null||item.getText(col+1).trim().length()==0)){
//						item.setText(col+1,data.getVal()==null?"":data.getVal());
//					}
					if(data.getVal()!=null&&data.getVal().equals(data.getParam())){
						table.getItem(i).setImage(cols-1, ok);
					}else{
						table.getItem(i).setImage(cols-1, error);
					}
				}
			}
			
		}	
	}
	
	public static void setIsTest(boolean setisTest){
		
		isTest=setisTest;
	}
//	private static MessageDialog box = null;
	
	public static void closeMsgBox(){
		
//		if(box!=null){
//			Display.getDefault().syncExec(new Runnable(){
//
//				@Override
//				public void run()
//				{
//					try
//					{
//						System.out.println("box.close()");
//						box.close();
//					} catch (Exception e)
//					{
//						e.printStackTrace();
//					}
//				}
//				
//			});
//		}
	}
	
	public static void showMessage(final TestChekStep testChekStep,final String message,final int style){
//		Display.getDefault().asyncExec(new Runnable(){
//			@Override
//			public void run()
//			{
//				box = new MessageDialog(userShell,"提示");
//				box.setMessage(message);
//				System.out.println("box.open()");
//				int retCode = box.open();
//				System.err.println("retcode : "  + retCode);
//				
//				if(MessageDialog.OK==retCode){
//					testChekStep.setMsgBoxStatus(TestChekStep.OK);					
//				}else{
//					testChekStep.setMsgBoxStatus(TestChekStep.CANCEL);	
//				}
//				synchronized (TestChekStep.LOCK)
//				{
//					TestChekStep.LOCK.notifyAll();					
//				}
//				
//			}
//		});
	}
	public static void showMessage(final String message,final int style){
		Display.getDefault().asyncExec(new Runnable(){
			@Override
			public void run()
			{
				MessageBox box = new MessageBox(userShell, SWT.NONE|style);
				box.setMessage(message);
				box.open();
			}
		});
	}
	private static  int port = -1;
	private static String host;
	private Text objectText;
	public static int getPort(){
		return port;
	}
	public static String getHost(){
		return host;
	}
	public static void showModelMessage(final String message,final int style){
		final String lock ="lock";
		Display.getDefault().asyncExec(new Runnable(){
			@Override
			public void run()
			{
				Shell shell = progress.getShell();
				if(shell==null){
					shell=userShell;
				}
				MessageBox box = new MessageBox(shell, SWT.NONE|style);
				box.setMessage(message);
				box.open();
				synchronized (lock){
					lock.notifyAll();
				}
			}
		});
		synchronized (lock)
		{
			try
			{
				lock.wait();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}			
		}
	}
	public void write(){

		MessageBox box = new MessageBox(userShell, SWT.ICON_ERROR);
		box.setText("提示");
//		int alarmCode=-1,faultCode=-1;
//		
//		List<DataRow> fault=CanController.getInstance().getDatas().get("53");
//		List<DataRow> alarm=CanController.getInstance().getDatas().get("52");
//		try
//		{
//			alarmCode=Integer.parseInt(alarm.get(0).getVal());
//			faultCode=Integer.parseInt(fault.get(0).getVal());
//		} catch (Exception e1)
//		{
//		}
		if(!CanController.getInstance().isConnect()){
			box.setMessage("未连接测试设备");
			box.open();
			return;
		}
		else if(!CanController.getInstance().isConnectBoard()){
			box.setMessage("通讯异常,未收到设备数据!");
			box.open();
			return;
		}
		if(!isTest){
			userCheckThread.init(currentFrame.getTestCheckSteps());
			//delay(1000);
			flashFrame();
			userCheckThread.StartCheck();
			//button_2.setEnabled(false);
			try{
				progress.run(true, true, new  IRunnableWithProgress(){
					
					@Override
					public void run(IProgressMonitor monitor)
							throws InvocationTargetException, InterruptedException
					{
						
						CheckThread userCheckThread=Mainframe.userCheckThread;
						int sum=userCheckThread.getStepCount();
						monitor.beginTask("请稍等.....",sum);
						int count=0;
						while(count<sum){
							int cur=userCheckThread.getStep();
							if(count<=cur){
								int addtion=cur-count;
								monitor.setTaskName(userCheckThread.monitor());
								count=cur;
								monitor.worked(addtion);
							}
							delay(50);
							if(monitor.isCanceled()||!userCheckThread.isRun()){
								userCheckThread.stopCheck();
								break;
							}
						}  
						flashFrame();
						//if(!monitor.isCanceled()){
						while(isTest){
							delay(100);																		
						}
						//}
						//SimBatteryUtil.closeBattery();
						monitor.done();
					}
				});	
			} catch (Exception e1){
				e1.printStackTrace();
			}
			
		}else{
			box.setMessage("现在不能操作，请稍候。");
			box.open();
			return;
		}
		
	
	}
}


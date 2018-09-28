package user.frame;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import user.CanDriver.CanController;
import user.CheckTest.TestChekStep;
import user.Method.ResultCache;
import user.model.ParamRow;
import user.model.ResultModel;
import user.service.ExportDataService;

public class TestFrame extends Composite implements TabFrame
{
	private Group voltGroup,balanceGroup,tempGroup,fanGroup;
	private Composite comp;
	private Table volttable, balancetable,fantable,temptable;
	public Image ok,error,green,red;
	
	public TestFrame(Composite parent, int style)
	{
		super(parent, style);
		ok=Mainframe.ok;
		error=Mainframe.error;
		green=Mainframe.green;
		red=Mainframe.red;
		comp=this;
		balanceGroup = new Group(comp, SWT.NONE);
		balanceGroup.setLocation(438, 6);
		balanceGroup.setSize(706, 400);
		balanceGroup.setText("\u5747\u8861\u68C0\u6D4B");
		
	
		
		voltGroup = new Group(comp, SWT.NONE);
		voltGroup.setLocation(10, 6);
		voltGroup.setSize(422, 400);
		voltGroup.setText("\u7535\u538B\u68C0\u6D4B");
		
		volttable = new Table(voltGroup, SWT.BORDER | SWT.FULL_SELECTION);
		volttable.setLinesVisible(true);
		volttable.setHeaderVisible(true);
		volttable.setBounds(10, 22, 402, 370);
		
		TableColumn tableColumn = new TableColumn(volttable, SWT.CENTER);
		tableColumn.setWidth(68);
		tableColumn.setText("\u7535\u6C60\u5E8F\u53F7");
		
		TableColumn tblclmnmv = new TableColumn(volttable, SWT.CENTER);
		tblclmnmv.setWidth(75);
		tblclmnmv.setText("2000mV");
		
		TableColumn tblclmnmv_1 = new TableColumn(volttable, SWT.CENTER);
		tblclmnmv_1.setWidth(81);
		tblclmnmv_1.setText("3000mV");
		
		TableColumn tblclmnmv_2 = new TableColumn(volttable, SWT.CENTER);
		tblclmnmv_2.setWidth(93);
		tblclmnmv_2.setText("4000mV");
		
		TableColumn tableColumn_6 = new TableColumn(volttable, SWT.CENTER);
		tableColumn_6.setWidth(71);
		tableColumn_6.setText("\u68C0\u6D4B\u7ED3\u679C");
		
		
		fanGroup = new Group(comp, SWT.NONE);
		fanGroup.setBounds(10, 412, 422, 295);
		fanGroup.setText("\u98CE\u6247\u68C0\u6D4B");
		
		fantable = new Table(fanGroup, SWT.BORDER | SWT.FULL_SELECTION);
		fantable.setLinesVisible(true);
		fantable.setHeaderVisible(true);
		fantable.setBounds(10, 22, 402, 265);
		
		TableColumn tableColumn_10 = new TableColumn(fantable, SWT.CENTER);
		tableColumn_10.setWidth(127);
		tableColumn_10.setText("\u98CE\u6247\u68C0\u6D4B\u9879");
		
		TableColumn tableColumn_12 = new TableColumn(fantable, SWT.CENTER);
		tableColumn_12.setWidth(107);
		tableColumn_12.setText("\u68C0\u6D4B\u7ED3\u679C");
		
		tempGroup = new Group(comp, SWT.NONE);
		tempGroup.setBounds(438, 412, 707, 295);
		tempGroup.setText("\u6E29\u5EA6\u68C0\u6D4B");
		
		temptable = new Table(tempGroup, SWT.BORDER | SWT.FULL_SELECTION);
		temptable.setLinesVisible(true);
		temptable.setHeaderVisible(true);
		temptable.setBounds(10, 20, 687, 265);
		
		TableColumn tableColumn_13 = new TableColumn(temptable, SWT.CENTER);
		tableColumn_13.setWidth(106);
		tableColumn_13.setText("\u5E8F\u53F7");
		
		TableColumn tableColumn_14 = new TableColumn(temptable, SWT.CENTER);
		tableColumn_14.setWidth(152);
		tableColumn_14.setText("\u6E29\u5EA6\u503C");
		
		TableColumn tableColumn_15 = new TableColumn(temptable, SWT.CENTER);
		tableColumn_15.setWidth(252);
		tableColumn_15.setText("\u91C7\u6837\u503C");
		
		TableColumn tableColumn_16 = new TableColumn(temptable, SWT.NONE);
		tableColumn_16.setWidth(94);
		tableColumn_16.setText("\u68C0\u6D4B\u7ED3\u679C");
		initbalancetable();
		initResult();
	}
	
	public void flashTables(){
		ResultCache cache=ResultCache.getInstance();
		flashTable(volttable,cache.getCellVolt(),cache.getCellVoltRet());
		flashTable(balancetable,cache.getBalances(),cache.getBalancesRet());
		flashTable(fantable,cache.getFans(),cache.getFansRet());
		flashTable(temptable,cache.getTemp(),cache.getTempRet());
	}
	
	public void initbalancetable(){
		if(balancetable!=null){
			balancetable.dispose();
		}

		ParamRow current=CanController.getInstance().getCurrentParam();
		
		balancetable = new Table(balanceGroup, SWT.BORDER | SWT.FULL_SELECTION);
		balancetable.setLinesVisible(true);
		balancetable.setHeaderVisible(true);
		balancetable.setBounds(10, 22, 686, 370);
		
		TableColumn tableColumn_7 = new TableColumn(balancetable, SWT.CENTER);
		tableColumn_7.setWidth(63);
		tableColumn_7.setText("\u7535\u6C60\u5E8F\u53F7");
		
		if(current!=null&&current.getIsActiveTest()==1){
			TableColumn tableColumn_8 = new TableColumn(balancetable, SWT.CENTER);
			tableColumn_8.setWidth(86);
			tableColumn_8.setText("\u4E3B\u52A8\u5145\u7535\u72B6\u6001");
			
			TableColumn tableColumn_82 = new TableColumn(balancetable, SWT.CENTER);
			tableColumn_82.setWidth(86);
			tableColumn_82.setText("\u4E3B\u52A8\u5145\u7535\u7535\u6D41");
			
			TableColumn tableColumn_81 = new TableColumn(balancetable, SWT.CENTER);
			tableColumn_81.setWidth(84);
			tableColumn_81.setText("\u4E3B\u52A8\u653E\u7535\u72B6\u6001");
			
			TableColumn tableColumn_83 = new TableColumn(balancetable, SWT.CENTER);
			tableColumn_83.setWidth(84);
			tableColumn_83.setText("\u4E3B\u52A8\u653E\u7535\u7535\u6D41");
		}
		if(current!=null&&current.getIsPassiveTest()==1){

			TableColumn tableColumn_84 = new TableColumn(balancetable, SWT.CENTER);
			tableColumn_84.setWidth(91);
			tableColumn_84.setText("\u88AB\u52A8\u653E\u7535\u72B6\u6001");
			
			TableColumn tableColumn_85 = new TableColumn(balancetable, SWT.CENTER);
			tableColumn_85.setWidth(91);
			tableColumn_85.setText("\u88AB\u52A8\u653E\u7535\u7535\u6D41");
		}
		
		TableColumn tableColumn_9 = new TableColumn(balancetable, SWT.CENTER);
		tableColumn_9.setWidth(64);
		tableColumn_9.setText("\u68C0\u6D4B\u7ED3\u679C");
	}
	
	public void initResult(){
		ParamRow current=CanController.getInstance().getCurrentParam();
//		List<DataRow> param=CanController.getInstance().getDatas().get("76");
//		List<DataRow> activeParam=CanController.getInstance().getDatas().get("77");
		if(current==null){
			return;
		}
		ResultCache.getInstance().init(current.getCellNum(), current.getTempNum(),current.getIsFanTest(),current.getIsActiveTest(),current.getIsPassiveTest());
		
		volttable.removeAll();
		balancetable.removeAll();
		temptable.removeAll();
		fantable.removeAll();
		fantable.removeAll();
		
		String[][] volts=ResultCache.getInstance().getCellVolt();
		for(int i=0;i<volts.length;i++){
			TableItem tableItem = new TableItem(volttable, SWT.NONE);
			tableItem.setText(volts[i]);
			tableItem.setImage(volts[i].length-1, error);
		}
		
		
		String[][] balances=ResultCache.getInstance().getBalances();
		for(int i=0;i<balances.length;i++){
			TableItem tableItem = new TableItem(balancetable, SWT.NONE);
			tableItem.setText(balances[i]);
			tableItem.setImage(balances[i].length-1, error);
		}
		
		String[][] temps=ResultCache.getInstance().getTemp();
		for(int i=0;i<temps.length;i++){
			TableItem tableItem = new TableItem(temptable, SWT.NONE);
			tableItem.setText(temps[i]);
			tableItem.setImage(temps[i].length-1, error);
		}
		
		String[][] fans=ResultCache.getInstance().getFans();
		if(fans!=null){
			for(int i=0;i<fans.length;i++){
				TableItem tableItem = new TableItem(fantable, SWT.NONE);
				tableItem.setText(fans[i]);
				tableItem.setImage(fans[i].length-1, error);
			}
			
		}
	}
	
	private  void flashTable(Table table,String[][] data,boolean[][] result){
		
		for(int i = 0;i < table.getItemCount();i++)
		{
			String[] rowdata=new String[data[i].length-1];
			System.arraycopy(data[i], 0, rowdata, 0, rowdata.length);
			int len=data[i].length;
			table.getItem(i).setText(rowdata);
			if(result[i][len-1]){
				table.getItem(i).setImage(len-1, ok);
			}else{
				table.getItem(i).setImage(len-1, error);
			}
		}	
	}
	
	/**
	 * Ë¢ÐÂ±í¸ñ
	 */
	public void refreshTables(){
	}
	public List<TestChekStep> getTestCheckSteps(){
		return null;
	}

	@Override
	public boolean isCheckIP()
	{
		return false;
	}

	@Override
	public ExportDataService getSaveDataService()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}

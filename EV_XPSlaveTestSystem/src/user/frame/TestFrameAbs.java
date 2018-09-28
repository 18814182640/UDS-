package user.frame;

import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import user.CanDriver.CanController;
import user.CheckTest.TestChekStep;
import user.Method.ResultCache;
import user.model.DataRow;
import user.model.ResultModel;
import user.service.ExportDataService;

public abstract class TestFrameAbs extends Composite implements TabFrame
{
	public Image ok,error,green,red;
	protected List<TestChekStep> testChekSteps;
	public TestFrameAbs(Composite parent, int style)
	{
		super(parent, style);
		ok=Mainframe.ok;
		error=Mainframe.error;
		green=Mainframe.green;
		red=Mainframe.red;
	}
	
	protected void initTableItem(Table table,List<ResultModel> list){
		if(table==null || list==null || list.size()<=0){			
			return ;
		}		
		for(ResultModel rm : list){
			try
			{
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(rm.getRowNum()+"");
				tableItem.setText(1,rm.getName());
				//tableItem.setImage(2, error);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	protected void refreshTable(Table table,List<ResultModel> models,int startIndex){
		ResultModel model = null;
		if(table==null || table.isDisposed()){
			return ;
		}
		for(int i = 0;i < table.getItemCount();i++)
		{
			try
			{
				model = models.get(i);
				if(models==null ||model==null){
					continue;
				}
				String str  =  model.getName();
				if(str==null){
					continue;
				}
				table.getItem(i).setText(1,str);
				if(model.isResult()){
					table.getItem(i).setImage(2, ok);
				}else{
					table.getItem(i).setImage(2, error);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("models.size = " + models.size() + " , table.getItemCount() = " + table.getItemCount());
				
			}
		}	
	}
	public boolean isCheckIP(){
		return false;
	}
	public List<TestChekStep> getTestCheckSteps(){
		return testChekSteps;
	}

	@Override
	public ExportDataService getSaveDataService()
	{
		return null;
	}
}

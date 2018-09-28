package user.frame;

import java.util.List;

import user.CheckTest.TestChekStep;
import user.service.ExportDataService;

public interface TabFrame
{
	public List<TestChekStep> getTestCheckSteps();
	
	public void refreshTables();
	public void initResult();
	public boolean isCheckIP();
	public ExportDataService getSaveDataService();
}

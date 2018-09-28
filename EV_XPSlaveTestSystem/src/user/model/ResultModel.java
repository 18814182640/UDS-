package user.model;
/**
 * ¼ì²â½á¹û
 * @author 150423015
 *
 */
public class ResultModel
{
	private int rowNum=1;
	private String name="";
	private String checkStep = "";
	private boolean result=false;
	public ResultModel(){
		
	}
	public ResultModel(int id, String varName, boolean b)
	{
		this.rowNum = id ; 
		this.name = varName;
		this.result = b ; 
	}
	public int getRowNum()
	{
		return rowNum;
	}
	public void setRowNum(int rowNum)
	{
		this.rowNum = rowNum;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getCheckStep()
	{
		return checkStep;
	}
	public void setCheckStep(String checkStep)
	{
		this.checkStep = checkStep;
	}
	public boolean isResult()
	{
		return result;
	}
	public void setResult(boolean result)
	{
		this.result = result;
	}
	
}

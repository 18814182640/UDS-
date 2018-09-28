package user.model;

public class DataRow
{
	private int id;
	//信息名称
	private String name;
	//主索引
	private String indexStr;
	private int index;
	//信息变量数组名定义
	private String infoArray;
	//子索引
	private String subIndexStr;
	private int subIndex;
	//变量名称
	private String varName;
	//初始值
	private String inital;
	//数据类型
	private String type;
	//取值范围
	private String range;
	//含义说明
	private String info;
	//传输方式
	private String sendType;
	//周期(单位:ms)
	private String cycle;
	//读写属性
	private String readType;
	//CAN通道(偏移量)
	private String can;
	//EEP地址
	private String erp;
	//返回值
	private String val;
	//设置值 
	private String param;
	
	public String getVal()
	{
		return val;
	}
	public void setVal(String val)
	{
		this.val = val;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getIndex()
	{
		return index;
	}
	public void setIndex(int index)
	{
		this.index = index;
	}
	public String getInfoArray()
	{
		return infoArray;
	}
	public void setInfoArray(String infoArray)
	{
		this.infoArray = infoArray;
	}
	
	public int getSubIndex()
	{
		return subIndex;
	}
	public void setSubIndex(int subIndex)
	{
		this.subIndex = subIndex;
	}
	public String getVarName()
	{
		return varName;
	}
	public void setVarName(String varName)
	{
		this.varName = varName;
	}
	public String getInital()
	{
		return inital;
	}
	public void setInital(String inital)
	{
		this.inital = inital;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getRange()
	{
		return range;
	}
	public void setRange(String range)
	{
		this.range = range;
	}
	public String getInfo()
	{
		return info;
	}
	public void setInfo(String info)
	{
		this.info = info;
	}
	public String getSendType()
	{
		return sendType;
	}
	public void setSendType(String sendType)
	{
		this.sendType = sendType;
	}
	public String getCycle()
	{
		return cycle;
	}
	public void setCycle(String cycle)
	{
		this.cycle = cycle;
	}
	public String getReadType()
	{
		return readType;
	}
	public void setReadType(String readType)
	{
		this.readType = readType;
	}
	public String getCan()
	{
		return can;
	}
	public void setCan(String can)
	{
		this.can = can;
	}
	public String getErp()
	{
		return erp;
	}
	public void setErp(String erp)
	{
		this.erp = erp;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getIndexStr()
	{
		return indexStr;
	}
	public void setIndexStr(String indexStr)
	{
		this.indexStr = indexStr;
	}
	public String getSubIndexStr()
	{
		return subIndexStr;
	}
	public void setSubIndexStr(String subIndexStr)
	{
		this.subIndexStr = subIndexStr;
	}
	public String getParam()
	{
		return param;
	}
	public void setParam(String param)
	{
		this.param = param;
	}
	
	
}

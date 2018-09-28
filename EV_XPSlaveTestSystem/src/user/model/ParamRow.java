package user.model;

public class ParamRow
{
	private int id;
	//BMS类型
	private String type;
	//电池节数
	private int cellNum;
	//温度数目
	private int tempNum;
	//电压精度
	private int voltAcc;
	//温度精度
	private int tempAcc;
	//是否检测主动均衡
	private int isActiveTest;
	//主动均衡充电电流基准值(mA)
	private int activeChargeCurrent;
	//主动均衡充电电流精度(mA)
	private int activeChargeCurrentAcc;
	//主动均衡放电电流基准值(mA)
	private int activeDischargeCurrent;
	//主动均衡放电电流精度(mA)
	private int activeDischargeCurrentAcc;
	//是否检测被动均衡
	private int isPassiveTest;
	//被动均衡放电电流基准值(mA)
	private int passiveDischargeCurrent;
	//被动均衡放电电流精度(mA)
	private int passiveDischargeCurrentAcc;
	//是否检测风扇
	private int isFanTest;
	
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public int getCellNum()
	{
		return cellNum;
	}
	public void setCellNum(int cellNum)
	{
		this.cellNum = cellNum;
	}
	public int getTempNum()
	{
		return tempNum;
	}
	public void setTempNum(int tempNum)
	{
		this.tempNum = tempNum;
	}
	public int getIsActiveTest()
	{
		return isActiveTest;
	}
	public void setIsActiveTest(int isActiveTest)
	{
		this.isActiveTest = isActiveTest;
	}
	public int getActiveChargeCurrent()
	{
		return activeChargeCurrent;
	}
	public void setActiveChargeCurrent(int activeChargeCurrent)
	{
		this.activeChargeCurrent = activeChargeCurrent;
	}
	public int getActiveChargeCurrentAcc()
	{
		return activeChargeCurrentAcc;
	}
	public void setActiveChargeCurrentAcc(int activeChargeCurrentAcc)
	{
		this.activeChargeCurrentAcc = activeChargeCurrentAcc;
	}
	public int getActiveDischargeCurrent()
	{
		return activeDischargeCurrent;
	}
	public void setActiveDischargeCurrent(int activeDischargeCurrent)
	{
		this.activeDischargeCurrent = activeDischargeCurrent;
	}
	public int getActiveDischargeCurrentAcc()
	{
		return activeDischargeCurrentAcc;
	}
	public void setActiveDischargeCurrentAcc(int activeDischargeCurrentAcc)
	{
		this.activeDischargeCurrentAcc = activeDischargeCurrentAcc;
	}
	public int getIsPassiveTest()
	{
		return isPassiveTest;
	}
	public void setIsPassiveTest(int isPassiveTest)
	{
		this.isPassiveTest = isPassiveTest;
	}
	public int getPassiveDischargeCurrent()
	{
		return passiveDischargeCurrent;
	}
	public void setPassiveDischargeCurrent(int passiveDischargeCurrent)
	{
		this.passiveDischargeCurrent = passiveDischargeCurrent;
	}
	public int getPassiveDischargeCurrentAcc()
	{
		return passiveDischargeCurrentAcc;
	}
	public void setPassiveDischargeCurrentAcc(int passiveDischargeCurrentAcc)
	{
		this.passiveDischargeCurrentAcc = passiveDischargeCurrentAcc;
	}
	public int getIsFanTest()
	{
		return isFanTest;
	}
	public void setIsFanTest(int isFanTest)
	{
		this.isFanTest = isFanTest;
	}
	public int getVoltAcc()
	{
		return voltAcc;
	}
	public void setVoltAcc(int voltAcc)
	{
		this.voltAcc = voltAcc;
	}
	public int getTempAcc()
	{
		return tempAcc;
	}
	public void setTempAcc(int tempAcc)
	{
		this.tempAcc = tempAcc;
	}
	
}

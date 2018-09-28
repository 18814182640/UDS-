package user.model;

public class ParamRow
{
	private int id;
	//BMS����
	private String type;
	//��ؽ���
	private int cellNum;
	//�¶���Ŀ
	private int tempNum;
	//��ѹ����
	private int voltAcc;
	//�¶Ⱦ���
	private int tempAcc;
	//�Ƿ�����������
	private int isActiveTest;
	//���������������׼ֵ(mA)
	private int activeChargeCurrent;
	//�����������������(mA)
	private int activeChargeCurrentAcc;
	//��������ŵ������׼ֵ(mA)
	private int activeDischargeCurrent;
	//��������ŵ��������(mA)
	private int activeDischargeCurrentAcc;
	//�Ƿ��ⱻ������
	private int isPassiveTest;
	//��������ŵ������׼ֵ(mA)
	private int passiveDischargeCurrent;
	//��������ŵ��������(mA)
	private int passiveDischargeCurrentAcc;
	//�Ƿ������
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

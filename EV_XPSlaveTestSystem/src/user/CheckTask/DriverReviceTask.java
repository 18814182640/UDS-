package user.CheckTask;

import user.CanDriver.CanController;
import user.CanDriver.CanDriverAdapter;
import user.CanDriver.VCI_CAN_OBJ;

public class DriverReviceTask implements Runnable
{
	private CanDriverAdapter driver;
	private boolean isRun=true;
	
	public DriverReviceTask(CanDriverAdapter driver)
	{
		super();
		this.driver = driver;
	}
	
	public void SplitDate(int ID,byte length,byte[] data)
	{
		int index = 0;
		int subIndex = 0;
		int object=0;
		int source = 0;
		int func=0;
		
		index  = ID & 0xff;			//索引
		object = (ID >> 8) & 0xff;		//目标
		source = (ID >> 16) & 0xff;	//源
		CanController.getInstance().setLastReviceTime(System.currentTimeMillis());
		CanController.getInstance().setBoardSourceId(source);
		func=(ID>>25)&0xf;//功能码
		if(func==0x6){
			CanController.getInstance().flushData(index, data);
		}else if(func==0x09){
			int newId=0x0a;
			newId=newId<<25;
			newId=newId|(1<<24);
			newId=newId|(object<<16);
			newId=newId|(source<<8);
			newId=newId|(index);
			CanController.getInstance().flushData(index, data);
			CanController.getInstance().writeCmdQueue(CanController.getInstance().getCmd(newId, data));
		}
	}

	@Override
	public void run()
	{
		while(driver.isConnect()&&isRun){
			//System.out.println("driver.revice , time : " + System.currentTimeMillis());
			VCI_CAN_OBJ.ByReference rec=driver.revice();
			if(rec!=null){
				System.out.println("SplitDate , time : " + System.currentTimeMillis());
				SplitDate(rec.ID,rec.DataLen,rec.Data);
			}else{
				System.out.println("SplitDate===============null , time : " + System.currentTimeMillis());
			}
			delay(1);
			
		}
		
	}
	
	public void delay(int time){
		try
		{
			Thread.sleep(time);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stop(){
		isRun=false;
	}
	
}

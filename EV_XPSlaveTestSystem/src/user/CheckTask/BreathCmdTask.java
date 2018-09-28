package user.CheckTask;

import user.CanDriver.CanController;

public class BreathCmdTask implements Runnable
{
	private boolean isRun=true;
	@Override
	public void run()
	{
		while(isRun){
			//发送呼吸帧
			//CanController.getInstance().writeCmdQueue(CanController.getInstance().getBreathCmd());
			try
			{
				if(CanController.getInstance().isConnect()){
					//查询参数
					CanController.getInstance().writeCmdQueue(CanController.getInstance().getQueryCmd(76,0,10));				
				}
			} catch (Exception e1)
			{
				e1.printStackTrace();
			}
			
			try
			{
				if(CanController.getInstance().isConnect()){
//					发送查询系统信息帧
					CanController.getInstance().writeCmdQueue(CanController.getInstance().getQueryCmd(1,0,10));
				
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			delay(500);
		}
	}
	
	public void delay(int timeout){
		try
		{
			Thread.sleep(timeout);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void stop()
	{
		this.isRun = false;
	}
	
	

}

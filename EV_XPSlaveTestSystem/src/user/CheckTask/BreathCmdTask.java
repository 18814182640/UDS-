package user.CheckTask;

import user.CanDriver.CanController;

public class BreathCmdTask implements Runnable
{
	private boolean isRun=true;
	@Override
	public void run()
	{
		while(isRun){
			//���ͺ���֡
			//CanController.getInstance().writeCmdQueue(CanController.getInstance().getBreathCmd());
			try
			{
				if(CanController.getInstance().isConnect()){
					//��ѯ����
					CanController.getInstance().writeCmdQueue(CanController.getInstance().getQueryCmd(76,0,10));				
				}
			} catch (Exception e1)
			{
				e1.printStackTrace();
			}
			
			try
			{
				if(CanController.getInstance().isConnect()){
//					���Ͳ�ѯϵͳ��Ϣ֡
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

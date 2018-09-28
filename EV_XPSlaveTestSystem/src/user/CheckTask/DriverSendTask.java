package user.CheckTask;

import user.CanDriver.CanController;
import user.CanDriver.VCI_CAN_OBJ;
import user.util.MyQueue;

public class DriverSendTask implements Runnable
{
	private MyQueue<VCI_CAN_OBJ.ByReference> queue;
	private boolean isRun=true;
	
	public DriverSendTask(MyQueue<VCI_CAN_OBJ.ByReference> queue)
	{
		super();
		this.queue = queue;
	}
	

	@Override
	public void run()
	{
		while(CanController.getInstance().isConnect()&&isRun){
			VCI_CAN_OBJ.ByReference cmd=queue.get();
			if(cmd!=null){
				CanController.getInstance().sendCmd(cmd);
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

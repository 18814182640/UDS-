package user.CheckTest;

import java.util.List;

import org.eclipse.swt.SWT;

import user.CanDriver.CanController;
import user.Method.ResultCache;
import user.frame.Mainframe;
import user.model.DataRow;
import user.model.PromptMsg;
import user.model.ResultModel;
import user.util.NumberUtils;

public abstract class CheckAbs implements TestChekStep
{
	protected int step=0;
	protected int stepBegin=0;
	protected int stepCount;
	protected boolean ret=false;
	protected boolean isRun=true;
	protected DataRow currentRow = null;
	protected int msgStatus;
	
	@Override
	public int getStepCount()
	{
		return stepCount;
	}
	@Override
	public String monitorStep()
	{
		return currentRow==null?"":currentRow.getVarName()+ ",step:\"" + currentRow.getVal()+"\"";
	}
	public int getStep()
	{
		return step+stepBegin;
	}
	@Override
	public void setStepBegin(int begin)
	{
		stepBegin = begin;
	}
	

	public static void delay(int time){
		try{
			Thread.sleep(time);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	protected void intoCheck(int status){
//		sendAdjustControllCmd(status);
		sendControllCmd(status,0);
		delay(500);
		sendControllCmd(status,0);
		delay(1000);
	}
	public void stopCheck(){
		
		sendControllCmd(4,0);
		
		delay(500);
		
		sendControllCmd(4,0);
		
		delay(1000);
//		int sendTimes = 0 ;
//		String status ="";
//		do
//		{
//			sendTimes++;
//			sendControllCmd(4,0);
//			delay(500);
//			status= ;
//			if(NumberUtils.parseInt(status)==0){
//				break;
//			}else{
//				if(sendTimes>=3){
//					break;
//				}
//			}
//		} while (isRun);
	}
	public void sendCloseMsgCtrlCmd(int status){
		sendControllCmd(status,1);
	}
	
	public void sendEthernetControllCmd(int status){
		sendControllCmd(status,4);
		delay(1000);
	}
	public void sendControllCmd(int status , int subIndex){
		int sendId=CanController.getInstance().getSendId(2,0,CanController.getInstance().source,CanController.getInstance().object,70);
		byte[] data=new byte[4];
		data[0]=(byte) 0xc1;
		data[1]=(byte) (subIndex&0xff);
		data[2]=(byte) ((status)&0xff);
		data[3]=(byte) ((status>>8)&0xff);
		CanController.getInstance().writeCmdQueue(CanController.getInstance().getCmd(sendId, data));
		printdata("sendControllCmd",data,sendId);
	}
	public void printdata(String who ,byte[] data,int id){
		if(data==null){
			System.out.println("data = null");
		}
		System.out.println("who = " + who+"send id :" + Integer.toHexString(id));
		for(byte b : data){
			System.out.print(Integer.toHexString(b&0xff)+"  ");
		}
		System.out.println("");
	}
//	public void sendAdjustControllCmd(int status){
//		sendControllCmd(status,0);
//		delay(1000);
//	}
	protected void reflash(int lastStep, int step){
	/*	if(step>=tempData.getTotalStatus().size()){
			step=tempData.getTotalStatus().size()-1;
		}
		if(lastStep<=0){
			lastStep = 1 ;
		}
		else if(lastStep>step){
			lastStep=step;
		}
		DataRow dataRow=null;
		ResultModel reModel=null;
		for(int index = lastStep ;index<=step;index++){				
			dataRow = tempData.getTotalStatus().get(index);//
			reModel = ResultCache.getInstance().getTotalStatus().get(index-1);
			System.out.println("reflash index = " + index);
			writeTOCache(dataRow,reModel);
			writeTOCache(index);
		}	*/
	}
	protected void writeTOCache(DataRow dr,ResultModel rsModel){
		/*if(dr==null||rsModel==null){
			return ;
		}
		rsModel.setRowNum(step);
		rsModel.setName(dr.getVarName());
		rsModel.setCheckStep(dr.getVal());
		int val =NumberUtils.parseInt(dr.getVal());
		if(val==TemplateData.SUCCESS_STEP){
			System.out.println("dr.getVarName() = " + dr.getVarName() + " , val = "+val + " ,result = true");
			rsModel.setResult(true);
		}else{
			rsModel.setResult(false);
			System.out.println("dr.getVarName() = " + dr.getVarName() + " , val = "+val + " ,result = false"  );
		}*/
	}

	protected abstract void writeTOCache(int step);
	@Override
	public void stopTest()
	{
		isRun=false;
		ret = false;
	}

	@Override
	public boolean getResult()
	{
		return ret;
	}
	protected void initResulCache(List<DataRow> list,List<ResultModel> resultModels,int startIndex){
		if(list==null || list.size()<=0 )
		{
			return ;
		}
		int rowNum = 1;
		resultModels.clear();
		for(int id = startIndex ;id<list.size();id++){
			resultModels.add(new ResultModel(rowNum,list.get(id).getVarName(),false));
			rowNum++;
		}
	}
	public void setMsgBoxStatus(int msgStatus){
		this.msgStatus = msgStatus;
	}
	public int getMsgBoxStatus(){
		return this.msgStatus;
	}

	@Override
	public void beforeTest()
	{
	}
	protected void showMessage(DataRow dataRow,PromptMsg  promptMsg)
	{
		/*msgStatus = TestChekStep.OPEN;
		Mainframe.showMessage(this,promptMsg.getMessage(), SWT.ICON_QUESTION);
		while(isRun){
			
			synchronized (TestChekStep.LOCK)
			{
				try
				{
					TestChekStep.LOCK.wait(200);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			if(tempData.isCloseMsg(currentRow,promptMsg)){
				System.out.println("检测到要关闭对话框");
				//检测到要关闭对话框
				if(msgStatus==TestChekStep.OPEN){
					Mainframe.closeMsgBox();
				}
				break;
			}
			else if(msgStatus==OK){//用户关闭界面对话框							
				sendCloseMsgCtrlCmd(promptMsg.getStep());
				System.err.println("sendCloseMsgCtrlCmd");
				break;
			}else if(msgStatus==CANCEL){//用户请求停止
				Mainframe.userCheckThread.stopCheck();
				break;
			}
		}*/
	}
	public void reset(){
		int status=1;
		int subIndex=0;
		int sendId=CanController.getInstance().getSendId(2,0,CanController.getInstance().source,CanController.getInstance().object,75);
		byte[] data=new byte[4];
		data[0]=(byte) 0xc1;
		data[1]=(byte) (subIndex&0xff);
		data[2]=(byte) ((status)&0xff);
		data[3]=(byte) ((status>>8)&0xff);
		CanController.getInstance().writeCmdQueue(CanController.getInstance().getCmd(sendId, data));
		printdata("reset",data,sendId);
	}
	public Long parseLong(String s){
		if(s==null || "".equals(s)){
			return null;
		}
		try
		{
			return Long.parseLong(s);
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public String getResultPrompt(){
		if(ret){
			return "成功!";
		}else{
			return "失败!";
		}
	}
}

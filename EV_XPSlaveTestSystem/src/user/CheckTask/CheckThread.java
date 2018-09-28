package user.CheckTask;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;

import user.CheckTest.TestChekStep;
import user.frame.Mainframe;

public class CheckThread extends Thread
{
	private boolean isRun=false;
	private List<TestChekStep> taskQueue=new ArrayList<TestChekStep>();
	private TestChekStep current;
	private int count;
	public CheckThread()
	{
		;
	}
	
	public  void stopCheck()
	{
		isRun=false;
		if(current!=null){
			current.stopTest();
		}
		//Mainframe.setIsTest(false);
	}
    
    
	public  void StartCheck()
	{
		Mainframe.setIsTest(true);
		//initResult();
		
		isRun=true;
		Thread t=new Thread(this);
		t.setDaemon(true);
		t.start();
	}
	
	/**
	 * 初始化结果表
	 */
	public void init(List<TestChekStep> testChekSteps){
		if(!isRun){
			//ResultCache.getInstance().clear();
			initTask(testChekSteps);
		}
	}
	
	/**
	 * 初始化任务
	 */
	public void initTask(List<TestChekStep> testChekSteps){
		int tempcount=0;
		//ParamRow currentParam=CanController.getInstance().getCurrentParam();
		taskQueue.clear();
		
//		TestChekStep voltStep=new VoltCheck(currentParam.getVoltAcc());
//		voltStep.setStepBegin(0);
//		tempcount+=voltStep.getStepCount();
//		
//		TestChekStep tempStep=new TempCheck(currentParam.getTempAcc());
//		tempStep.setStepBegin(tempcount);
//		tempcount+=tempStep.getStepCount();
//		
//		TestChekStep balanceCheck=new BalanceCheck();
//		balanceCheck.setStepBegin(tempcount);
//		tempcount+=balanceCheck.getStepCount();
		
//		TestChekStep fanCheck=new FanCheck();
//		fanCheck.setStepBegin(tempcount);
//		tempcount+=fanCheck.getStepCount();
		
		for(TestChekStep test : testChekSteps){
			test.setStepBegin(tempcount);
			tempcount+=test.getStepCount();
			test.beforeTest();
			taskQueue.add(test);
		}
		count=tempcount;
//		taskQueue.add(voltStep);
//		taskQueue.add(tempStep);
//		taskQueue.add(balanceCheck);
//		taskQueue.add(fanCheck);
		current=taskQueue.get(0);
	}
	
	public static long startTime=0;
	public static long endtime=0;
	public void startTime(){
		startTime=System.currentTimeMillis();
	}
	public void stopTime(String name){
		endtime=System.currentTimeMillis();
		System.out.println(name+" Cost:"+(endtime-startTime)+"ms.");
	}

	
	/**
	 * 测试总步数
	 * @return
	 */
	public int getStepCount(){
		return count;
	}
	public void setStepCount(int setStepCount){
		this.count= setStepCount;
	}
	
	/**
	 * 获取当前测试进度
	 * @return
	 */
	public int getStep(){
		if(current!=null){
			if(isRun){
				//System.out.println("TASK:"+current.getStep());
				return current.getStep();
			}else{
				return count;
			}
		}
		return 0;
	}
	
	/**
	 * 获取当前测试任务名
	 * @return
	 */
	public String monitor(){
		if(current!=null){
			
			return current.monitorStep();
		}
		return "";
	}
	
	public void run()
	{
		
		boolean ret=true;
		int step=taskQueue.size();
		for(int i=0;i<step&&isRun;i++){
			current=taskQueue.remove(0);
			current.doTest();
			ret=ret&current.getResult();
			if(!ret){
				break;
			}
		}
		if(isRun){
			isRun=false;
			if(ret){
				Mainframe.showMessage(current.getResultPrompt(), SWT.ICON_QUESTION);
			}else{
				Mainframe.showMessage(current.getResultPrompt(), SWT.ICON_ERROR);
			}
		}
		//SimBatteryUtil.setBatterVolt(3500,3500,5000);
		//delay(500);
		Mainframe.setIsTest(false);
	}

	public boolean isRun()
	{
		return isRun;
	}

	public void delay(int time) {  
		try
		{
			Thread.sleep(time);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
}

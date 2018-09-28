package user.CheckTest;

public interface TestChekStep
{
	public final static String LOCK="LOCK";
	public final static int NO_OPEN=1;
	public final static int OPEN=2;
	public final static int OK=3;
	public final static int CANCEL=4;
	public final static int CLOSE=5;
	public int getStepCount();
	public String monitorStep();
	public int getStep();
	public void setStepBegin(int begin);
	public void doTest();
	public void stopTest();
	public boolean getResult();
	public void beforeTest();
	public void setMsgBoxStatus(int status);
	public int getMsgBoxStatus();
	public void reset();
	public String getResultPrompt();
}

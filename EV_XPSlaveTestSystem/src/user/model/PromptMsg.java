package user.model;

public class PromptMsg
{
	private String message;
	private int step;
	private int index;
	
	public PromptMsg()
	{
	}
	
//	public PromptMsg(String reVal, int val)
//	{
//		this.message = reVal;
//		this.step = val;
//	}
	public PromptMsg(String message, int step, int index)
	{
		super();
		this.message = message;
		this.step = step;
		this.index = index;
	}

	public int getIndex()
	{
		return index;
	}



	public void setIndex(int index)
	{
		this.index = index;
	}



	public String getMessage()
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	public int getStep()
	{
		return step;
	}
	public void setStep(int step)
	{
		this.step = step;
	}
	
}

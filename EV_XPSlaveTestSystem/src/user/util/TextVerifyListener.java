package user.util;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

public class TextVerifyListener implements VerifyListener {
	//private int max=1,min=0;

	private int len=3;
	public TextVerifyListener() {
		super();
	}	
	public TextVerifyListener(int len) {
		super();
		this.len = len;
	}
	private String getValue(VerifyEvent e){
		if(e.getSource() instanceof Text){
			Text text =  (Text)e.getSource();
			return text.getText();
		}
		return null;
	}
	private Integer getFullValue(VerifyEvent e){
			String value = null; 
			
			try{
				value = getValue(e);
				if(value!=null){
					return Integer.parseInt(value + e.text); 				
				}
				
			}catch(NumberFormatException e1 ){
				e1.printStackTrace();
			}
		return null;
	}
	@Override
	public void verifyText(VerifyEvent e) {
		
		boolean b = false;
		Text text = null;
		String str ="";
		if(e.widget instanceof Text){
			text = (Text)e.widget;
			str = text.getText()+e.text;
		}		
		System.out.println("text.getText() = "+text.getText() + " , e.text = " + e.text );

		if("0123456789".indexOf(e.text) >= 0 &&str.length()<=len){
			b = true;
		}
		
		e.doit = b;
		return; 
	}

}

package user.util;

public class StringUtils {
	public static boolean isBlank(String str){
		boolean ret=false;
		if(str==null||str.trim().length()<=0){
			ret=true;
		}
		return ret;
	}
	
	
	/**
	 * 字符串左填充
	 * @param str
	 * @param pad
	 * @param len
	 * @return
	 */
	public static String lpad(String str,String pad,int len){
		if(str==null||str.length()>=len){
			return str;
		}
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<len-str.length();i++){
			sb.append(pad);
		}
		sb.append(str);
		return sb.toString();
	}
	

	/**
	 * 字符串右填充
	 * @param str
	 * @param pad
	 * @param len
	 * @return
	 */
	public static String rpad(String str,String pad,int len){
		if(str==null||str.length()>=len){
			return str;
		}
		StringBuffer sb=new StringBuffer();
		sb.append(str);
		for(int i=0;i<(len-str.length());i++){
			sb.append(pad);
		}
		return sb.toString();
	}
	
	public static void main(String[] args){
		String name="sunwoda";
		String a=lpad(name," ",21);
		System.out.println(a.length());
	}
}

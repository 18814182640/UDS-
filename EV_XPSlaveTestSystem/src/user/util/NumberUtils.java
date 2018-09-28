package user.util;

public class NumberUtils {
	/**
	 * byte转换int
	 * @param data 原数�?
	 * @param len 几位合成�?个int
	 * @param isBig true:高位在前,false:高位在后
	 * @return
	 */
	public static int[] getIntArray(byte[] data,int len,boolean isBig){
		int[] ret=null;
		if(data!=null&&data.length>=len){
			int length=data.length;
			int newlength=length%len==0?length/len:length/len+1;
			ret=new int[newlength];
			int id=0;
			for(int i=0;i<length;i+=len){
				int rdata=0;
				for(int j=0;j<len&&i+j<length;j++){
					//高位在前
					if(isBig){
						rdata=(rdata<<8)|(data[i+j]&0xff);
					//高位在后
					}else{
						rdata=rdata|((data[i+j]&0xff)<<(8*j));
					}
				}
				ret[id++]=rdata;
			}
		}
		
		return ret;
	}
	/**
	 * 字符串转double
	 * @param s
	 * @return 如果转换失败，则返回Null
	 */
	public static Double parseDouble(String s){
		try{
			return Double.parseDouble(s);
		}catch(Exception n){
			return null;
		}
	}
	public static Integer parseInteger(String s){
		try{
			return Integer.parseInt(s);
		}catch(Exception n){
			return null;
		}
	}
	public static int parseInt(String s){
		try{
			return Integer.parseInt(s);
		}catch(Exception n){
		}
		return -1;
	}
}

package user.util;

public class DataTypeUtil
{
	public static final String U32="u32";
	public static final String I32="i32";
	public static final String U16="u16";
	public static final String I16="i16";
	public static final String I8="i8";
	public static final String U8="u8";
	
	
	public static int getTypeLen(String type){
		if(I16.equalsIgnoreCase(type) || U16.equalsIgnoreCase(type)){
			return 2;
		}else if(I32.equalsIgnoreCase(type) || U32.equalsIgnoreCase(type)){
			return 4;
		}else if(U8.equalsIgnoreCase(type) || I8.equalsIgnoreCase(type)){
			return 1;
		}
		return 1;
	}
	
	public static byte[] getByteByType(String type,int start,byte[] data,int dat){
		if(I16.equalsIgnoreCase(type) || U16.equalsIgnoreCase(type)){
			data[start]=(byte) ((dat)&0xff);
			data[start+1]=(byte) ((dat>>8)&0xff);
		}else if(I32.equalsIgnoreCase(type) || U32.equalsIgnoreCase(type)){
			data[start]=(byte) ((dat)&0xff);
			data[start+1]=(byte) ((dat>>8)&0xff);
			data[start+2]=(byte) ((dat>>16)&0xff);
			data[start+3]=(byte) ((dat>>24)&0xff);
		}else if(U8.equalsIgnoreCase(type) || I8.equalsIgnoreCase(type)){
			data[start]=(byte) ((dat)&0xff);
		}
		return data;
	}
	/**
	 * 根据类型把byte转换成字符串
	 * @param typeModel
	 * @param data
	 * @return
	 */
	public static String getTypeDatas(String type,byte[] data,int start){
		String ret="";
		//双字节
		if(I16.equalsIgnoreCase(type)){
			int dat=data[start];
			dat&=0xff;
			dat=(dat)|((data[start+1]&0xff)<<8);
			if(dat>=32768){
				dat-=65536;
			}
			ret+=dat;
		//四字节
		}else if(I32.equalsIgnoreCase(type)){
			int dat=data[start];
			dat&=0xff;
			dat=(dat)|((data[start+1]&0xff)<<8);
			dat=(dat)|((data[start+2]&0xff)<<16);
			dat=(dat)|((data[start+3]&0xff)<<24);
			ret+=dat;
		//双字节
		}else if(U16.equalsIgnoreCase(type)){
			
			int dat=data[start];
			dat&=0xff;
			dat=(dat)|((data[start+1]&0xff)<<8);
			ret+=dat;
		//四字节
		}else if(U32.equalsIgnoreCase(type)){
			
			long dat=data[start];
			dat&=0xff;
			dat=(dat)|((data[start+1]&0xff)<<8);
			dat=(dat)|((data[start+2]&0xff)<<16);
			dat=(dat)|((data[start+3]&0xff)<<24);
			dat=dat&0xffffffffl;
			ret+=dat;
		}else if(U8.equalsIgnoreCase(type)){
			int dat=data[start];
			dat&=0xff;
			ret+=dat;
		}else if(I8.equalsIgnoreCase(type)){
			int dat=data[start];
			dat&=0xff;
			if(dat>128){
				dat-=256;
			}
			ret+=dat;
		}
		return ret;
	}
}

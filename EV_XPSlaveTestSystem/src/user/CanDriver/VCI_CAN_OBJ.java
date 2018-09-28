package user.CanDriver;

import com.sun.jna.Structure;

public class VCI_CAN_OBJ extends Structure
{
    public static class ByReference extends VCI_CAN_OBJ implements Structure.ByReference { }
    public static class ByValue extends VCI_CAN_OBJ implements Structure.ByValue{}
	public int ID;
	public int TimeStamp;
	public byte TimeFlag;
	public byte SendType;
	
	public byte RemoteFlag;
	public byte ExternFlag;
	public byte DataLen;
	public byte[] Data = new byte[8];
	public byte[] Reserved = new byte[3];
}

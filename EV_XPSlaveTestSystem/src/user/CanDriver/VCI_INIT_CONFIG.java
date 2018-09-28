package user.CanDriver;

import com.sun.jna.Structure;

public class VCI_INIT_CONFIG extends Structure
{
    public static class ByReference extends VCI_INIT_CONFIG implements Structure.ByReference { }
    public static class ByValue extends VCI_INIT_CONFIG implements Structure.ByValue{}
	public int AccCode;
	public int AccMask;
	public int Reserved;
	public byte Filter;
	public byte Timing0;
	public byte Timing1;
	public byte Mode;
}

package user.CanDriver;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;


public class CanDriver 
{
	public CanDriver()
	{
		;
	}
	 //定义接口CLibrary，继承自com.sun.jna.Library
    public interface CLibrary extends StdCallLibrary
    {
	     //定义并初始化接口的静态变量
	    CLibrary Instance=(CLibrary)Native.loadLibrary(System.getProperty("user.dir")+"/ControlCAN.dll",CLibrary.class);

//	    CLibrary Instance=(CLibrary)Native.loadLibrary("F:\\PCSoftWare\\S2_BMUTestSystem\\ControlCAN.dll",CLibrary.class);
	    // CLibrary Instance=(CLibrary)Native.loadLibrary((Platform.isWindows()?"ControlCAN.dll":"c"),CLibrary.class);
	
	     /*调用.Dll中的变量*/
	     /*
	      * 用于初始化CAN设备
	      * 
	      */
	     int  VCI_InitCAN(int DevType, int DevIndex, int CANIndex,VCI_INIT_CONFIG.ByReference pInitConfig); 
	     /*
	      * 用于启动CAN设备
	      */
	     int  VCI_StartCAN(int DevType, int DevIndex, int CANIndex);
	     /*
	      * 用于打开设备驱动程序
	      */
	     int  VCI_OpenDevice(int DevType, int DevIndex, int Reserved);
	     /*
	      * 用于关闭设备驱动程序
	      */
	     int  VCI_CloseDevice(int DevType, int DevIndex);
	     /*
	      * 用于读取缓存中的CAN帧条数
	      */
	     long VCI_GetReceiveNum(int DevType, int DevIndex, int CANIndex);
	     /*
	      * 用于复位CAN设备
	      */
	     int  VCI_ResetCAN(int DevType, int DevIndex, int CANIndex);
	     /*
	      * 用于接收CAN信息
	      */
	     int VCI_Receive(int DevType, int DevIndex, int CANIndex,VCI_CAN_OBJ.ByReference  struct, int Len, int WaitTime);
	    /*
	     * 用于发送CAN信息 
	     */
	     int  VCI_Transmit(int DevType, int DevIndex, int CANIndex, VCI_CAN_OBJ.ByReference pSend, long Len);
     }
    
    public int  InitCAN(int DevType, int DevIndex, int CANIndex,VCI_INIT_CONFIG.ByReference pInitConfig)
    {
    	int Status = 0;
    	Status = CLibrary.Instance.VCI_InitCAN(DevType,DevIndex,CANIndex, pInitConfig);
    	return Status;
    }
    /*
     * 用于启动CAN设备
     */
    public int  StartCAN(int DevType, int DevIndex, int CANIndex)
    {
    	int Status = 0;
    	Status = CLibrary.Instance.VCI_StartCAN(DevType,DevIndex,CANIndex);
    	return Status;	
    }
    /*
     * 用于打开设备驱动程序
     */
    public int  OpenDevice(int DevType, int DevIndex, int Reserved)
    {
    	int Status = 0;
    	Status = CLibrary.Instance.VCI_OpenDevice(DevType,DevIndex,Reserved);
    	return Status;	
    }
    /*
     * 用于关闭设备驱动程序
     */
    public int  CloseDevice(int DevType, int DevIndex)
    {
    	int Status = 0;
    	Status = CLibrary.Instance.VCI_CloseDevice(DevType,DevIndex);
    	return Status;	
    }
    /*
     * 用于读取缓存中的CAN帧条数
     */
    public long GetReceiveNum(int DevType, int DevIndex, int CANIndex)
    {
    	long Status = 0;
    	Status = CLibrary.Instance.VCI_GetReceiveNum(DevType,DevIndex,CANIndex);
    	return Status;	
    }
    /*
     * 用于复位CAN设备
     */
    public int  ResetCAN(int DevType, int DevIndex, int CANIndex)
    {
    	int Status = 0;
    	Status = CLibrary.Instance.VCI_ResetCAN(DevType,DevIndex,CANIndex);
    	return Status;	
    }
    /*
     * 用于接收CAN信息
     */
    public int Receive(int DevType, int DevIndex, int CANIndex,VCI_CAN_OBJ.ByReference struct, int Len, int WaitTime)
    {
    	int Status = 0;
    	Status = CLibrary.Instance.VCI_Receive( DevType,  DevIndex,  CANIndex, struct,  Len,  WaitTime);
    	return Status;	   	
    }
    
   /*
    * 用于发送CAN信息 
    */
    public int  Transmit(int DevType, int DevIndex, int CANIndex, VCI_CAN_OBJ.ByReference pSend, long Len)
    {
    	int Status = 0;
    	Status =CLibrary.Instance.VCI_Transmit(DevType, DevIndex, CANIndex, pSend, Len);
    	return Status;
    }
    
}



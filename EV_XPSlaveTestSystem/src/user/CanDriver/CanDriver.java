package user.CanDriver;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;


public class CanDriver 
{
	public CanDriver()
	{
		;
	}
	 //����ӿ�CLibrary���̳���com.sun.jna.Library
    public interface CLibrary extends StdCallLibrary
    {
	     //���岢��ʼ���ӿڵľ�̬����
	    CLibrary Instance=(CLibrary)Native.loadLibrary(System.getProperty("user.dir")+"/ControlCAN.dll",CLibrary.class);

//	    CLibrary Instance=(CLibrary)Native.loadLibrary("F:\\PCSoftWare\\S2_BMUTestSystem\\ControlCAN.dll",CLibrary.class);
	    // CLibrary Instance=(CLibrary)Native.loadLibrary((Platform.isWindows()?"ControlCAN.dll":"c"),CLibrary.class);
	
	     /*����.Dll�еı���*/
	     /*
	      * ���ڳ�ʼ��CAN�豸
	      * 
	      */
	     int  VCI_InitCAN(int DevType, int DevIndex, int CANIndex,VCI_INIT_CONFIG.ByReference pInitConfig); 
	     /*
	      * ��������CAN�豸
	      */
	     int  VCI_StartCAN(int DevType, int DevIndex, int CANIndex);
	     /*
	      * ���ڴ��豸��������
	      */
	     int  VCI_OpenDevice(int DevType, int DevIndex, int Reserved);
	     /*
	      * ���ڹر��豸��������
	      */
	     int  VCI_CloseDevice(int DevType, int DevIndex);
	     /*
	      * ���ڶ�ȡ�����е�CAN֡����
	      */
	     long VCI_GetReceiveNum(int DevType, int DevIndex, int CANIndex);
	     /*
	      * ���ڸ�λCAN�豸
	      */
	     int  VCI_ResetCAN(int DevType, int DevIndex, int CANIndex);
	     /*
	      * ���ڽ���CAN��Ϣ
	      */
	     int VCI_Receive(int DevType, int DevIndex, int CANIndex,VCI_CAN_OBJ.ByReference  struct, int Len, int WaitTime);
	    /*
	     * ���ڷ���CAN��Ϣ 
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
     * ��������CAN�豸
     */
    public int  StartCAN(int DevType, int DevIndex, int CANIndex)
    {
    	int Status = 0;
    	Status = CLibrary.Instance.VCI_StartCAN(DevType,DevIndex,CANIndex);
    	return Status;	
    }
    /*
     * ���ڴ��豸��������
     */
    public int  OpenDevice(int DevType, int DevIndex, int Reserved)
    {
    	int Status = 0;
    	Status = CLibrary.Instance.VCI_OpenDevice(DevType,DevIndex,Reserved);
    	return Status;	
    }
    /*
     * ���ڹر��豸��������
     */
    public int  CloseDevice(int DevType, int DevIndex)
    {
    	int Status = 0;
    	Status = CLibrary.Instance.VCI_CloseDevice(DevType,DevIndex);
    	return Status;	
    }
    /*
     * ���ڶ�ȡ�����е�CAN֡����
     */
    public long GetReceiveNum(int DevType, int DevIndex, int CANIndex)
    {
    	long Status = 0;
    	Status = CLibrary.Instance.VCI_GetReceiveNum(DevType,DevIndex,CANIndex);
    	return Status;	
    }
    /*
     * ���ڸ�λCAN�豸
     */
    public int  ResetCAN(int DevType, int DevIndex, int CANIndex)
    {
    	int Status = 0;
    	Status = CLibrary.Instance.VCI_ResetCAN(DevType,DevIndex,CANIndex);
    	return Status;	
    }
    /*
     * ���ڽ���CAN��Ϣ
     */
    public int Receive(int DevType, int DevIndex, int CANIndex,VCI_CAN_OBJ.ByReference struct, int Len, int WaitTime)
    {
    	int Status = 0;
    	Status = CLibrary.Instance.VCI_Receive( DevType,  DevIndex,  CANIndex, struct,  Len,  WaitTime);
    	return Status;	   	
    }
    
   /*
    * ���ڷ���CAN��Ϣ 
    */
    public int  Transmit(int DevType, int DevIndex, int CANIndex, VCI_CAN_OBJ.ByReference pSend, long Len)
    {
    	int Status = 0;
    	Status =CLibrary.Instance.VCI_Transmit(DevType, DevIndex, CANIndex, pSend, Len);
    	return Status;
    }
    
}



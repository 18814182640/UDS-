package user.CanDriver;

public class CanDriverAdapter
{
	private boolean isConnect=false;
	private CanDriver canDriver=new CanDriver();
	private VCI_INIT_CONFIG.ByReference configCan;
	private int devType;
	private int devIndex;
	private int cANIndex;
	private int reserved;
	private int rxCount = 0;
	private int txCount = 0;
	
	public CanDriverAdapter()
	{
		;
	}

	public CanDriverAdapter(int devType, int devIndex, int cANIndex,int reserved,VCI_INIT_CONFIG.ByReference configCan) {
		super();
		this.devType = devType;
		this.devIndex = devIndex;
		this.cANIndex = cANIndex;
		this.reserved = reserved;
		this.configCan = configCan;
	}
	/**
	 * 关闭连接
	 */
	public void disConnect(){
		canDriver.CloseDevice(devType, devIndex);
		isConnect=false;
	}
	/**
	 * 连接
	 * @return
	 */
	public int connect(){
		disConnect();
		int ret = 0;

		ret=canDriver.OpenDevice(devType, devIndex, 0);
		if(ret == 0)
		{
			return (-1);
		}
		ret=canDriver.InitCAN(devType, devIndex, cANIndex, configCan);
		if(ret == 0)
		{
			canDriver.CloseDevice(devType, devIndex);
			return (-2);
		}
		ret=canDriver.StartCAN(devType, devIndex, cANIndex);
		if(ret == 0)
		{
			canDriver.CloseDevice(devType, devIndex);
			return (-3);
		}
		isConnect=true;
		return ret;
		
	}

	public boolean isConnect() {
		return isConnect;
	}
	
	public int send(VCI_CAN_OBJ.ByReference canMsg){
		int ret=0;
		
//		int index  = canMsg.ID & 0xff;			//设备类型
//		if(index==70){
//			System.out.print(Integer.toHexString(canMsg.ID)+" send:");
//			for(int i=0;i<canMsg.Data.length;i++){
//				System.out.print(" "+canMsg.Data[i]);
//			}
//			System.out.println();
//		}
		ret=canDriver.Transmit(devType, devIndex, cANIndex, canMsg, 1);
		if(ret==1){
			txCount++;
		}
		return ret;
	}

	public int send(int id,int length,byte[] data){
		int ret=0;
		
		byte[] reserver = new byte[]{0,0,0};

		VCI_CAN_OBJ.ByReference canMsg = new VCI_CAN_OBJ.ByReference();
		canMsg.Data = data;
		canMsg.DataLen = (byte) length;
		canMsg.ID = id;
		canMsg.ExternFlag = 1;	//是否为扩展帧
		canMsg.SendType = 0;	//正常发送
		canMsg.Reserved = reserver;
		canMsg.TimeFlag = 0;
		canMsg.TimeStamp = 0;
		canMsg.RemoteFlag = 0;	//是否为远程帧
		
		ret=canDriver.Transmit(devType, devIndex, cANIndex, canMsg, 1);
		if(ret==1){
			txCount++;
		}
		return ret;
	}
	public void delay(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public VCI_CAN_OBJ.ByReference revice(){
		long x = canDriver.GetReceiveNum(devType, devIndex, cANIndex);
		while(x<=0){
			delay(2);
			x =  canDriver.GetReceiveNum(devType, devIndex, cANIndex);
		}
		VCI_CAN_OBJ.ByReference struct=new VCI_CAN_OBJ.ByReference();
		struct.ID = 0;
		struct.TimeStamp = 0;
		struct.TimeFlag = 0;
		struct.SendType = 0;
		
		struct.RemoteFlag = 0;
		struct.ExternFlag = 0;
		struct.DataLen = 0;
		struct.Data = new byte[8];
		struct.Reserved = new byte[3];
		int len=canDriver.Receive(devType, devIndex, cANIndex, struct, 1, 1);
		if(len > 0)
		{
			
			rxCount++;
			return struct;
		}
		return null;
	}


//	public void log(VCI_CAN_OBJ.ByReference o){
//		
//		StringBuffer str=new StringBuffer();
//		str.append(Integer.toHexString(o.ID));
//		str.append(":");
//		str.append(o.DataLen);
//		str.append(":");
//		for(int i=0;i<o.DataLen;i++){
//			String ret="0"+Integer.toHexString(o.Data[i]);
//			str.append(ret.length()<3?ret:ret.substring(ret.length()-2));
//			str.append(" ");
//			
//		}
//		System.out.println(str.toString());
//	}
	
	
	public int getRxCount() {
		return rxCount;
	}

	public void setRxCount(int rxCount) {
		this.rxCount = rxCount;
	}

	public int getTxCount() {
		return txCount;
	}

	public void setTxCount(int txCount) {
		this.txCount = txCount;
	}
    
}

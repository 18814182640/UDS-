package user.CanDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import user.CheckTask.BreathCmdTask;
import user.CheckTask.DriverReviceTask;
import user.CheckTask.DriverSendTask;
import user.model.DataRow;
import user.model.ParamRow;
import user.util.DataTypeUtil;
import user.util.MyQueue;
public class CanController
{
	private static final CanController instance=new CanController();
	//key =index;
	private Map<String,List<DataRow>> datas=new HashMap<String,List<DataRow>>();
	private List<ParamRow> param=new ArrayList<ParamRow>();
	private ParamRow currentParam;
	private MyQueue<VCI_CAN_OBJ.ByReference> sendQueue=new MyQueue<VCI_CAN_OBJ.ByReference>();
	private CanDriverAdapter driver;
	public static int object=0xff;
	public static int source=100;
	public static int comindex=1;
	public static VCI_INIT_CONFIG.ByReference ConfigCan=new VCI_INIT_CONFIG.ByReference();
	private DriverReviceTask reviceTask;
	private DriverSendTask sendTask;
	private BreathCmdTask breathTask;
	private long lastReviceTime=0;
	private int boardSourceId=0;
	public int connect(){
//		VCI_INIT_CONFIG.ByReference ConfigCan = new VCI_INIT_CONFIG.ByReference();
		ConfigCan.AccCode =0x00000000;
		ConfigCan.AccMask =0xffffffff;
		ConfigCan.Timing0 = (byte) (0x00&0xff);
		ConfigCan.Timing1 = (byte) (0x1c & 0xff);
		ConfigCan.Mode = (byte) (0&0xff);
		ConfigCan.Filter = (byte) (1 & 0xff);
		ConfigCan.Reserved = 0;
		driver=new CanDriverAdapter(4,comindex,0,0,ConfigCan);
		int ret=driver.connect();
		if(ret==1){
			reviceTask=new DriverReviceTask(driver);
			sendTask=new DriverSendTask(sendQueue);
			breathTask=new BreathCmdTask();
			new Thread(reviceTask).start();
			new Thread(sendTask).start();
			new Thread(breathTask).start();
		}
		return ret;
	}
	public void disConnect(){
		if(driver!=null){
			driver.disConnect();
		}
		driver=null;
		if(sendTask!=null){
			sendTask.stop();
		}
		if(reviceTask!=null){
			reviceTask.stop();
		}
		if(breathTask!=null){
			breathTask.stop();
		}
	}
	public boolean isConnect(){
		if(driver!=null){
			return driver.isConnect();
		}
		return false;
	}
	public List<ParamRow> getParam()
	{
		return param;
	}
	public void setParam(List<ParamRow> param)
	{
		this.param = param;
	}
	public static CanController getInstance(){
		return instance;
	}
	public Map<String, List<DataRow>> getDatas()
	{
		return datas;
	}
	public void setDatas(Map<String, List<DataRow>> datas)
	{
		this.datas = datas;
	}
	
	public void flushData(int index,byte[] data){
		int subIndex=data[1];
		List<DataRow> rows=datas.get(index+"");
		if(rows==null||rows.size()<subIndex){
			return;
		}
		int num=data[0]&0x1f;
		int start=2;
		int find=0;
		for(int i=0;i<rows.size()&&start<data.length&&find<num;i++){
			DataRow row=rows.get(i);
			if(row.getSubIndex()==subIndex&&row.getIndex()==index){
				int len=DataTypeUtil.getTypeLen(row.getType());
				row.setVal(DataTypeUtil.getTypeDatas(row.getType(), data, start));
				int shift=0;
				try
				{
					shift=Integer.parseInt(row.getCan());
					int d=Integer.parseInt(row.getVal());
					d=d+shift;
					row.setVal(d+"");
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				
				start+=len;
				subIndex++;
				find++;
			}
		}
	}
	
	public void sendCmd(VCI_CAN_OBJ.ByReference cmd){
		int ret=driver.send(cmd);
	}
	
	
	public void writeCmdQueue(VCI_CAN_OBJ.ByReference cmd){
		if(driver.isConnect()){
			sendQueue.add(cmd);
		}
	}
	public void clearData(String index){
		if(index==null||index.trim().length()<=0){
			return ;
		}
		List<DataRow> list=datas.get(index);
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				list.get(i).setVal("");
			}
		}
	}
	public static VCI_CAN_OBJ.ByReference getCmd(int id,byte[] data){
		byte[] reserver = new byte[]{0,0,0};
		VCI_CAN_OBJ.ByReference canMsg = new VCI_CAN_OBJ.ByReference();
		canMsg.Data = data;
		canMsg.DataLen = (byte) data.length;
		canMsg.ID = id;
		canMsg.ExternFlag = 1;	//是否为扩展帧
		canMsg.SendType = 0;	//正常发送
		canMsg.Reserved = reserver;
		canMsg.TimeFlag = 0;
		canMsg.TimeStamp = 0;
		canMsg.RemoteFlag = 0;	//是否为远程帧
		return canMsg;
	}
	
	//获取呼吸帧
	public static VCI_CAN_OBJ.ByReference getBreathCmd(){
		
		int id=getSendId(1,0,80,0xff,1);
		byte[] reserver = new byte[]{0,0,0};
		byte[] data={90,90,90,90,90,90,90,90};
		VCI_CAN_OBJ.ByReference canMsg = new VCI_CAN_OBJ.ByReference();
		canMsg.Data = data;
		canMsg.DataLen = (byte) data.length;
		canMsg.ID = id;
		canMsg.ExternFlag = 1;	//是否为扩展帧
		canMsg.SendType = 0;	//正常发送
		canMsg.Reserved = reserver;
		canMsg.TimeFlag = 0;
		canMsg.TimeStamp = 0;
		canMsg.RemoteFlag = 0;	//是否为远程帧
		return canMsg;
	}
	
	/**
	 * 获取指令
	 * @param index 主索引
	 * @param subindex 子索引
	 * @param len 子索引数
	 * @return
	 */
	public static VCI_CAN_OBJ.ByReference getQueryCmd(int index,int subindex,int len){
		
		int id=getSendId(8,1,source,object,index);
		byte[] reserver = new byte[]{0,0,0};
		byte[] data={0x20,(byte)subindex,(byte)len};
		VCI_CAN_OBJ.ByReference canMsg = new VCI_CAN_OBJ.ByReference();
		canMsg.Data = data;
		canMsg.DataLen = (byte) data.length;
		canMsg.ID = id;
		canMsg.ExternFlag = 1;	//是否为扩展帧
		canMsg.SendType = 1;	//正常发送
		canMsg.Reserved = reserver;
		canMsg.TimeFlag = 0;
		canMsg.TimeStamp = 0;
		canMsg.RemoteFlag = 0;	//是否为远程帧
		return canMsg;
	}
	
	public static int getSendId(int func,int net,int addrs,int addro,int index){
		int newId=func&0xff;
		newId=newId<<25;
		newId=newId|((net&0x01)<<24);
		newId=newId|((addrs&0xff)<<16);
		newId=newId|((addro&0xff)<<8);
		newId=newId|((index&0xff));
		//System.err.println("send id : " + Integer.toHexString(newId));
		return newId;
	}
	
	public static void sendBalanceControllCmd(int status1,int status2,int status3){
		int sendId=CanController.getInstance().getSendId(2,1,CanController.getInstance().source,CanController.getInstance().object,70);
		byte[] data=new byte[8];
		data[0]=(byte) 0xc3;
		data[1]=0;
		data[2]=(byte) ((status1)&0xff);
		data[3]=(byte) ((status1>>8)&0xff);
		data[4]=(byte) ((status2)&0xff);
		data[5]=(byte) ((status2>>8)&0xff);
		data[6]=(byte) ((status3)&0xff);
		data[7]=(byte) ((status3>>8)&0xff);
		CanController.getInstance().writeCmdQueue(CanController.getInstance().getCmd(sendId, data));
	}
	
	public static void writeIndexDataByParam(int index,int start,int len){
		int sendId=CanController.getInstance().getSendId(2,1,CanController.getInstance().source,CanController.getInstance().object,index);
		List<DataRow> rows=CanController.getInstance().getDatas().get(""+index);
		byte[] data=new byte[8];
		int dst=2;
		int subIndex=0;
		data[1]=(byte)start;
		int ilen=DataTypeUtil.getTypeLen(rows.get(start).getType());
		for(int i=start;i<rows.size()&&i<(len+start);i++){
			if(dst+ilen>data.length){
				data[0]=(byte) (0xc0|(subIndex&0x1f));
				println(i+"",data );
				CanController.getInstance().writeCmdQueue(CanController.getInstance().getCmd(sendId, data));
				data=new byte[8];
				data[1]=(byte) i;
				dst=2;
				subIndex=0;
			}
			
			DataRow row=rows.get(i);
			int dat=0;
			try{
				dat=Integer.parseInt(row.getParam());
			}catch(Exception e){
			}
			DataTypeUtil.getByteByType(row.getType(),dst,data,dat);
			subIndex++;
			dst+=ilen;
		}
		if(dst!=2){
			data[0]=(byte) (0xc0|(subIndex&0x1f));
			byte[] datas=new byte[dst];
			System.arraycopy(data, 0, datas, 0, dst);
			println("dst=" + dst,data );
			CanController.getInstance().writeCmdQueue(CanController.getInstance().getCmd(sendId, data));
		}
		
	}
	static void println(String str ,byte[] datas){
		System.out.print(str + ":");
		for(int i = 0 ;i <datas.length;i++){
			System.out.print(datas[i]+" ");
		}
		System.out.println();
	}
	public ParamRow getCurrentParam()
	{
		return currentParam;
	}
	public void setCurrentParam(ParamRow currentParam)
	{
		this.currentParam = currentParam;
	}
	public long getLastReviceTime()
	{
		return lastReviceTime;
	}
	public void setLastReviceTime(long lastReviceTime)
	{
		this.lastReviceTime = lastReviceTime;
	}
	
	public boolean isConnectBoard(){
		return (System.currentTimeMillis()-lastReviceTime)<=500;
	}
	public int getBoardSourceId()
	{
		return boardSourceId;
	}
	public void setBoardSourceId(int boardSourceId)
	{
		this.boardSourceId = boardSourceId;
		this.object = boardSourceId;
	}
	
}

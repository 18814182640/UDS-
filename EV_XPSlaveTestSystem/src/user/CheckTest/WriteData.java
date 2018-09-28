package user.CheckTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import user.CanDriver.CanController;
import user.Method.ResultCache;
import user.frame.TabFrame;
import user.model.DataRow;
import user.model.ResultModel;
import user.util.TiCommandCompare;

/**
 * 写入配置参数
 * @author 150423015
 *
 */
public class WriteData extends CheckAbs
{
//	private TabFrame frame;
	private DataRow totalStatus=null;
	private int lastStep = 0;
	private int errorNum = 0;
	private String [] errorMessage = new String[]{"温度采样节数!","电压采样节数!","设备地址不能为空!","模版配置错误!"};
	public WriteData()
	{
		super();
		stepCount = 6;
	}

	private void check(){
		List<DataRow> dataRows = CanController.getInstance().getDatas().get("76");
		if(dataRows!=null && dataRows.size()>=5){
			
			if(dataRows.get(1).getParam()==null||"".equals(dataRows.get(1).getParam().trim())){
				errorNum|=4;
			}
			if(dataRows.get(3).getParam()==null||"".equals(dataRows.get(3).getParam().trim())){
				errorNum|=2;
			}
			if(dataRows.get(4).getParam()==null||"".equals(dataRows.get(4).getParam().trim())){
				errorNum|=1;
			}
		}else{
			errorNum|=8;
		}
	}
	
	@Override
	public void doTest()
	{
		isRun=true;
		ret = false;
		errorNum = 0;
		check();
		if(errorNum>0){
			step = 6 ;
			return ;
		}
		step = 0 ;
		CanController.getInstance().writeIndexDataByParam(76,0,3);
		step = 1 ;
		//setBoardSourceId();
		System.out.println("object: " + CanController.getInstance().object);
		delay(500);
		step = 2 ;
		System.out.println("object2: " + CanController.getInstance().object);
		CanController.getInstance().writeIndexDataByParam(76,3,7);
		CanController.getInstance().clearData("76");
		step = 3 ;
		delay(1000);
		step = 4 ;
		CanController.getInstance().writeCmdQueue(CanController.getInstance().getQueryCmd(76,0,10));
		step = 5 ;
		ret = isSuccessfully();
//		if(frame!=null){
//			frame.refreshTables();
//		}
		step = 6 ;
	}

	public void beforeTest(){
//		List<ResultModel> resultModels = null;
//		stepCount= tempData.getTotalStatus().size()-1;
//		resultModels = ResultCache.getInstance().getTotalStatus();
//		if(resultModels==null){
//			resultModels = new ArrayList<ResultModel>();
//			ResultCache.getInstance().setTotalStatus(resultModels);
//		}
//		initResulCache(tempData.getTotalStatus(),resultModels,1);
//		
//		resultModels = ResultCache.getInstance().getCAN();
//		if(resultModels==null){
//			resultModels = new ArrayList<ResultModel>();
//			ResultCache.getInstance().setCAN(resultModels);
//		}
//		initResulCache(tempData.getCAN(),resultModels,0);
//		
//		resultModels = ResultCache.getInstance().getVoltSampling();
//		if(resultModels==null){
//			resultModels = new ArrayList<ResultModel>();
//			ResultCache.getInstance().setVoltSampling(resultModels);
//		}
//		initResulCache(tempData.getVoltSampling(),resultModels,0);
//		
//		resultModels = ResultCache.getInstance().getTempSampling();
//		if(resultModels==null){
//			resultModels = new ArrayList<ResultModel>();
//			ResultCache.getInstance().setTempSampling(resultModels);
//		}
//		initResulCache(tempData.getTempSampling(),resultModels,0);
//		
//		resultModels = ResultCache.getInstance().getLeakEl();
//		if(resultModels==null){
//			resultModels = new ArrayList<ResultModel>();
//			ResultCache.getInstance().setLeakEl(resultModels);
//		}
//		initResulCache(tempData.getLeakEl(),resultModels,0);
		
	}
	protected void writeTOCache(int step){
//		List<DataRow> srcList=null;
//		List<ResultModel> rsModel=null;
//		ResultCache cache = ResultCache.getInstance();
//		switch (step)
//		{
//			case 1:
//				srcList = tempData.getCAN();
//				rsModel = cache.getCAN();
//			case 2:
//				srcList= tempData.getVoltSampling();
//				rsModel = cache.getVoltSampling();
//				break;
//			case 3:
//				srcList= tempData.getLeakEl();
//				rsModel = cache.getLeakEl();
//				break;
//			case 4:
//				srcList =tempData.getTempSampling();
//				rsModel = cache.getTempSampling();
//				break;
//			default:
//				break;
//		}
//		if(srcList==null || rsModel==null){
//			return ;
//		}
//		for(int index = 0;index<srcList.size();index++){
//			writeTOCache(srcList.get(index),rsModel.get(index));			
//		}
	}

	@Override
	public void sendCloseMsgCtrlCmd(int status)
	{
		//sendControllCmd(status,2);
	}
	private TiCommandCompare compare =  new TiCommandCompare();
	public boolean isSuccessfully(){
		//long[] writeValues=ResultCache.getInstance().getWriteValues();
		List<DataRow> paramaterList = CanController.getInstance().getDatas().get("76");
		Collections.sort(paramaterList,compare );
		boolean ret = true;
		DataRow drTemp = null;
		long now = System.currentTimeMillis();
		do{
			ret = true;
			for(int i = 0 ;i<paramaterList.size();i++){
				drTemp=paramaterList.get(i);
				if(drTemp!=null){
					//Long temp =parseLong(paramaterList.get(i).getVal());
					System.out.print("retVal: " + drTemp.getVal() + " , write data = " + drTemp.getParam() + " ,ret : " );
					if(drTemp.getParam()!=null &&drTemp.getVal()!=null && drTemp.getParam().trim().equals(drTemp.getVal().trim())){
						System.out.println("OK");
					}else{
						ret&=false;
						System.out.println("NO");
					}
				}
			}
			if(ret){
				return true;
			}
			delay(500);
		}while(System.currentTimeMillis()-now<=5000);
		return ret;
	}
	public String getResultPrompt(){
		if(ret){
			return "写入成功!";
		}else{
			if(errorNum>0){
				return getErrorMsg(errorNum);
			}else{
				return "写入失败!";				
			}
		}
	}
	private String getErrorMsg(int errNum){
		StringBuilder sb = new StringBuilder();
		if(((errNum>>3)&0x1)==1){
			sb.append(errorMessage[3]);	
		}
		if(((errNum>>2)&0x1)==1){
			sb.append(errorMessage[2]).append("\r\n");		
		}
		if(((errNum>>1)&0x1)==1){
			sb.append(errorMessage[1]).append("\r\n");	
		}
		if((errNum&0x1)==1){
			sb.append(errorMessage[0]).append("\r\n");	
		}
		if(sb.length()>0){
			return sb.toString();
		}else{
			return "";
		}
	}
}

package user.util;

import java.util.Comparator;

import user.model.DataRow;

public class TiCommandCompare implements Comparator<DataRow>{

	@Override
	public int compare(DataRow o1, DataRow o2) {
			try {
				int subIndex1 = 0 ,index1 = 0 , index2 = 0 ,subIndex2 = 0 ;
				subIndex1 =o1.getSubIndex();
				index1=o1.getIndex();
				index2 = o2.getIndex();
				subIndex2 = o2.getSubIndex();
				
				int id1=index1*10000+subIndex1;
				int id2=index2*10000+ subIndex2;
				if(id1-id2<0){
					return -1;
				}else if(id1-id2==0){
					return 0;
				}else{
					return 1;
				}
			} catch (Exception e) {
				System.out.println(" o1.getvarname  = " + o1.getVarName() + " , o1.getvarname = " + o1.getVarName());
				e.printStackTrace();
			}
		return 0;
	}
	private int parseInt(String str){
		int ret = 0 ;
		try
		{
			ret = Integer.parseInt(str);			
		}catch(Exception e ){
			ret = 0 ;
		}
		return ret;
	}
}
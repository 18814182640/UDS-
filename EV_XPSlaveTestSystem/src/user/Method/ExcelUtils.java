package user.Method;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import user.CanDriver.CanController;
import user.model.DataRow;
import user.model.ParamRow;

public class ExcelUtils {
	
	public static int readFromFile(String fileName){
		int ret=0;
		try {
			Workbook book=Workbook.getWorkbook(new File(fileName));
			Sheet[] sheets=book.getSheets();
			for(int i=0;i<sheets.length;i++){
				try {
					if( i == 0)
					{
						parseSheets(sheets[i]);
					}
					else if( i == 1)
					{
						//parseParamSheets(sheets[i]);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
			ret=-1;
		} catch (Exception e) {
			e.printStackTrace();
			ret=-2;
		}
		
		return ret;
	}
	
	/**
	 * 解析数据sheet
	 * @param sheet
	 * @return
	 */
	public static void parseSheets(Sheet sheet){
		if(sheet==null){
			return ;
		}
		int rows=sheet.getRows();
		int lastId=0;
		String lastType=null;
		String lastName=null;
		String lastInfoArray=null;
		String lastSendType=null;
		String lastCycle=null;
		String lastReadType=null;
		String lastCan=null;
		int rid=0;
		CanController cache=CanController.getInstance();
		for(int i=2;i<rows;i++){
			DataRow thisRow=parseDataRow(sheet.getRow(i));
			if(thisRow==null){
				continue;
			}
			thisRow.setId(rid++);
			
			if(!isMergeBody(sheet,sheet.getRow(i)[1])){
				lastId=Integer.parseInt(thisRow.getIndexStr());
				thisRow.setIndex(lastId);
				lastType=thisRow.getType();
				lastName=thisRow.getName();
				lastInfoArray=thisRow.getInfoArray();
				lastSendType=thisRow.getSendType();
				lastCycle=thisRow.getCycle();
				lastReadType=thisRow.getReadType();
				lastCan=thisRow.getCan();
			}else{
				thisRow.setIndex(lastId);
				thisRow.setType(lastType);
				thisRow.setName(lastName);
				thisRow.setInfoArray(lastInfoArray);
				thisRow.setSendType(lastSendType);
				thisRow.setCycle(lastCycle);
				thisRow.setReadType(lastReadType);
				thisRow.setCan(lastCan);
			}
			List<DataRow> ret=cache.getDatas().get(thisRow.getIndex()+"");
			if(ret==null){
				ret=new ArrayList<DataRow>();
				cache.getDatas().put(thisRow.getIndex()+"", ret);
			}
			ret.add(thisRow);
		}
	}
	
	
	public static void parseParamSheets(Sheet sheet){
		if(sheet==null){
			return ;
		}
		int rows=sheet.getRows();
		int rid=0;
		CanController cache=CanController.getInstance();
		for(int i=2;i<rows;i++){
			ParamRow thisRow=parseParamRow(sheet.getRow(i));
			if(thisRow==null){
				continue;
			}
			thisRow.setId(rid++);
			cache.getParam().add(thisRow);
		}
	}
	
	/**
	 * 解析数据行
	 * @param cells
	 * @return
	 */
	public static DataRow parseDataRow(Cell[] cells){
		DataRow model=null;
		try
		{
			if(cells==null||cells.length<13){
				return null;
			}
			model = new DataRow();
			model.setName(cells[0].getContents());
			model.setIndexStr(cells[1].getContents());
			//model.setIndex(Integer.parseInt(cells[1].getContents()));
			model.setInfoArray(cells[2].getContents());
			model.setSubIndexStr(cells[3].getContents());
			model.setSubIndex(Integer.parseInt(cells[3].getContents()));
			model.setVarName(cells[4].getContents());
			model.setInital(cells[5].getContents());
			model.setType(cells[6].getContents());
			model.setRange(cells[7].getContents());
			model.setInfo(cells[8].getContents());
			model.setSendType(cells[9].getContents());
			model.setCycle(cells[10].getContents());
			model.setReadType(cells[11].getContents());
			model.setCan(cells[12].getContents());
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			model=null;
		}
		return model;
	}
	
	/**
	 * 解析数据行
	 * @param cells
	 * @return
	 */
	public static ParamRow parseParamRow(Cell[] cells){
		ParamRow model=null;
		try
		{
			if(cells==null||cells.length<14){
				return null;
			}
			model = new ParamRow();
			model.setType(cells[0].getContents());
			model.setCellNum(Integer.parseInt(cells[1].getContents()));
			model.setTempNum(Integer.parseInt(cells[2].getContents()));
			model.setVoltAcc(Integer.parseInt(cells[3].getContents()));
			model.setTempAcc(Integer.parseInt(cells[4].getContents()));
			model.setIsActiveTest(Integer.parseInt(cells[5].getContents()));
			model.setActiveChargeCurrent(Integer.parseInt(cells[6].getContents()));
			model.setActiveChargeCurrentAcc(Integer.parseInt(cells[7].getContents()));
			model.setActiveDischargeCurrent(Integer.parseInt(cells[8].getContents()));
			model.setActiveDischargeCurrentAcc(Integer.parseInt(cells[9].getContents()));
			model.setIsPassiveTest(Integer.parseInt(cells[10].getContents()));
			model.setPassiveDischargeCurrent(Integer.parseInt(cells[11].getContents()));
			model.setPassiveDischargeCurrentAcc(Integer.parseInt(cells[12].getContents()));
			model.setIsFanTest(Integer.parseInt(cells[13].getContents()));
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
			model=null;
		}
		return model;
	}	
	
	public static boolean isMergeBody(Sheet sheet, Cell cell) {
		boolean ret=false;
	    Range[] ranges = sheet.getMergedCells();
	    for (Range range : ranges) {
		    int startRow = range.getTopLeft().getRow();
		    int startCol = range.getTopLeft().getColumn();
		    int endRow = range.getBottomRight().getRow();
		    int endCol = range.getBottomRight().getColumn();
		    if (range.getTopLeft().equals(cell)) {
		    	ret= false;
		    	break;
		    } 
		    if (cell.getColumn() <= endCol && cell.getColumn() >= startCol && cell.getRow() >= startRow && cell.getRow() <= endRow) {
		    	ret= true;
		    	break;
		    }
	    }
	    return ret;

	}
	
}

package com.isesol.mes.ismes.fm.activity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import com.isesol.ismes.platform.module.Bundle;
import com.isesol.ismes.platform.module.Parameters;
import com.isesol.ismes.platform.module.Sys;

public class WorkshopActivity {
	
	public String index(Parameters parameter, Bundle bundle) {		
		bundle.put("nodes", getWorkshopNodes());
		String data = this.getWorkshopData();
		bundle.put("data",data);
		
		return "workshop";
	}
	
	public String editIndex(Parameters parameter, Bundle bundle) {
		bundle.put("nodes", getWorkshopNodes());//'sc_area','wl_area',
		bundle.put("toolBtns", "['cursor','direct','|','blank','zj_area','zp_area','office',"
				+ "'jh_area','gy_area','xx_area','signboard-small','arrow','cement_floor','rubber_floor',"
				+ "'ground','channel','net','|','group']");
		String data = this.getWorkshopData();
		bundle.put("data",data);
		return "workshop-edit";
	}
	
	public void workshop_save(Parameters parameter, Bundle bundle) {
		ByteArrayInputStream bis = new ByteArrayInputStream(parameter.get("data").toString().getBytes());
		Sys.saveFile("/factory/workshop.json", bis);
	}
	
	private String getWorkshopData() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(Sys.readFile("/factory/workshop.json")));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null){
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}
	
	private String getWorkshopNodes() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("'blank':{type:'blank',label:'空白',image:'"+Sys.getResourceUrl("fm","blank")+"'},");
//		sb.append("'sc_area':{type:'sc_area',label:'生产区',image:'"+Sys.getResourceUrl("fm","sc_area")+"',"
//				+ "attributes:[{name:'name',label:'名称',type:'text',default:''}]},");
//		sb.append("'wl_area':{type:'wl_area',label:'物料区',image:'"+Sys.getResourceUrl("fm","wl_area")+"',"
//				+ "attributes:[{name:'name',label:'名称',type:'text',default:''}]},");
		sb.append("'zj_area':{type:'zj_area',label:'质检区',image:'"+Sys.getResourceUrl("fm","zj_area")+"',"
				+ "attributes:[{name:'name',label:'名称',type:'text',default:''}]},");
		sb.append("'zp_area':{type:'zp_area',label:'装配区',image:'"+Sys.getResourceUrl("fm","zp_area")+"',"
				+ "attributes:[{name:'name',label:'名称',type:'text',default:''}]},");
		sb.append("'office':{type:'office',label:'车间主任办公室',image:'"+Sys.getResourceUrl("fm","office")+"',"
				+ "attributes:[{name:'name',label:'名称',type:'text',default:''}]},");
		sb.append("'jh_area':{type:'jh_area',label:'计划科',image:'"+Sys.getResourceUrl("fm","jh_area")+"',"
				+ "attributes:[{name:'name',label:'名称',type:'text',default:''}]},");
		sb.append("'gy_area':{type:'gy_area',label:'工艺科',image:'"+Sys.getResourceUrl("fm","gy_area")+"',"
				+ "attributes:[{name:'name',label:'名称',type:'text',default:''}]},");
		sb.append("'xx_area':{type:'xx_area',label:'信息科',image:'"+Sys.getResourceUrl("fm","xx_area")+"',"
				+ "attributes:[{name:'name',label:'名称',type:'text',default:''}]},");
		
		sb.append("'signboard-small':{type:'signboard-small',label:'小警告牌',directs:["
				  + "{name:'left',label:'左前',image:'"+Sys.getResourceUrl("fm","signboard-small-left")+"'},"
				  + "{name:'right',label:'右前',image:'"+Sys.getResourceUrl("fm","signboard-small-right")+"'}"
				  + "]},");
		
		sb.append("'arrow':{type:'arrow',label:'箭头',image:'"+Sys.getResourceUrl("fm","arrow")+"',"
				+ "attributes:[{name:'name',label:'名称',type:'text',default:''}]},");
		
		//水泥地
		sb.append("'cement_floor':{type:'cement_floor',label:'生产区',image:'"+Sys.getResourceUrl("fm","cement_floor")+"',"
				+ "attributes:[{name:'name',label:'名称',type:'text',default:''}]},");
		//橡胶地
		sb.append("'rubber_floor':{type:'rubber_floor',label:'物料区',image:'"+Sys.getResourceUrl("fm","rubber_floor")+"',"
				+ "attributes:[{name:'name',label:'名称',type:'text',default:''}]},");
		
		sb.append("'ground':{type:'ground',label:'地砖',directs:["
				  + "{name:'left',label:'深色',image:'"+Sys.getResourceUrl("fm","ground-deep")+"'},"
				  + "{name:'right',label:'浅色',image:'"+Sys.getResourceUrl("fm","ground-light")+"'}"
				  + "]},");
		
//		sb.append("'line':{type:'line',label:'线',directs:["
//				  + "{name:'left',label:'上左',image:'"+Sys.getResourceUrl("fm","line-sz")+"'},"
//				  + "{name:'left',label:'上右',image:'"+Sys.getResourceUrl("fm","line-sz")+"'},"
//				  + "{name:'left',label:'左下',image:'"+Sys.getResourceUrl("fm","line-sz")+"'},"
//				  + "{name:'right',label:'右下',image:'"+Sys.getResourceUrl("fm","ground-light")+"'}"
//				  + "]},");
		
		sb.append("'channel':{type:'channel',label:'安全通道',attributes:[{name:'name',label:'名称',type:'text',default:''}],directs:[{name:'v',label:'竖',frames:['"
		          +Sys.getResourceUrl("fm","channel-v-top")+"','"
				  +Sys.getResourceUrl("fm","channel-v-center")+"','"+Sys.getResourceUrl("ui-demo","channel-v-bottom")+"']},"
				  + "{name:'h',label:'横',frames:['"+Sys.getResourceUrl("ui-demo","channel-h-top")+"','"
				  +Sys.getResourceUrl("fm","channel-h-center")+"','"
				  +Sys.getResourceUrl("fm","channel-h-bottom")+"']}"
				  + "]},");
		
		sb.append("'net':{type:'net',label:'隔离网',directs:[{name:'center-left',label:'左中',image:'"+Sys.getResourceUrl("fm","net-center-left")+"'},"
				  + "{name:'center-right',label:'右中',image:'"+Sys.getResourceUrl("fm","net-center-right")+"'},"
				  + "{name:'head-left',label:'左头',image:'"+Sys.getResourceUrl("fm","net-head-left")+"'},"
				  + "{name:'head-right',label:'右头',image:'"+Sys.getResourceUrl("fm","net-head-right")+"'}"
				  + "]}");
		sb.append("}");
		return  sb.toString();
	}
}

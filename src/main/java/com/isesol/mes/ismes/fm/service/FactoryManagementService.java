package com.isesol.mes.ismes.fm.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.isesol.ismes.platform.core.service.bean.Dataset;
import com.isesol.ismes.platform.module.Bundle;
import com.isesol.ismes.platform.module.Parameters;
import com.isesol.ismes.platform.module.Sys;
import com.isesol.mes.ismes.fm.util.DateTimeUtils;

import net.sf.json.JSONObject;

public class FactoryManagementService {

	/**
	 * 查询 工厂工时模版
	 * @param parameters
	 * @param bundle
	 */
	public void query_gcgsmbb(Parameters parameters, Bundle bundle) {
		StringBuffer conditionSb = new StringBuffer(" 1 = 1 ");
		List<Object> conditionValue = new ArrayList<Object>();
		String gsmbid = parameters.getString("gsmbid");
		if(StringUtils.isNotBlank(gsmbid)){
			conditionSb = conditionSb.append(" and gsmbid = ? ");
			conditionValue.add(gsmbid);
		}
		String sfmrmb = parameters.getString("sfmrmb");
		if(StringUtils.isNotBlank(sfmrmb)){
			conditionSb = conditionSb.append(" and sfmrmb = ? ");
			conditionValue.add(sfmrmb);
		}
		Dataset dataset = Sys.query("fm_gcgsmbb", "gsmbid,gsmbmc,gsmbms,sfmrmb,"
				+ "zhouyiks,zhouerks,zhousanks,zhousiks,"
				+ "zhouwuks,zhouliuks,zhouriks,zhouyijs,zhouerjs,zhousanjs,zhousijs,"
				+ "zhouwujs,zhouliujs,zhourijs", conditionSb.toString(), null,conditionValue.toArray());
		bundle.put("gcgsmbb", dataset.getMap());
		bundle.put("gcgsmbbList", dataset.getList());	
		
	}
	
	/**
	 * 得到工厂日历的 休息日期
	 * @param parameters
	 * @param bundle
	 */
	public void getGcCalendarDays(Parameters parameters, Bundle bundle){
		
		String start = parameters.getString("start");
		String end = parameters.getString("end");
		
		//得到所有周末 
		Map<String,Object> map = DateTimeUtils.weekdaysList(start, end);
		List<String> weekendsList = (List<String>) map.get("weekendsList");
		Map<String,Map<String,Object>> restDayMap = new HashMap<String, Map<String,Object>>();
		                         
		Dataset set = Sys.query("fm_gcrlbmb", "rlmbid"," rlmbzt = '10'",null, new Object[]{});
		if(set.getCount() == 0 || set.getCount() > 1){
			bundle.setError("启用的工厂日历模版不存在，或者存在多个");
			bundle.put("weekendsList",weekendsList);
			return;
		}
		bundle.put("gcrlmb",set.getMap());
		Dataset dataset = Sys.query( new String[]{"fm_gcrlb","fm_gcrlbmb"},"fm_gcrlb join fm_gcrlbmb on fm_gcrlb.rlmbid = fm_gcrlbmb.rlmbid",
					"fm_gcrlb.rlztdm,fm_gcrlb.rlrq,fm_gcrlb.rlms", 
					" rlmbzt = '10' and rlrq <= ? and rlrq >= ?", null, 
					new Object[]{DateTimeUtils.string2Date(start),DateTimeUtils.string2Date(end)});
		List<Map<String,Object>> specialDays = dataset.getList();
		if(CollectionUtils.isNotEmpty(specialDays)){
			for(int i = 0 ; i < specialDays.size() ; i ++){
				Map<String,Object> m = specialDays.get(i);
				String rlztdm = m.get("rlztdm").toString();
				Date rlrq =  (Date) m.get("rlrq");
				String rlrq_str = DateTimeUtils.date2String(rlrq, "yyyy-MM-dd");
				//工作日
				if("10".equals(rlztdm)){
					if(weekendsList.contains(rlrq_str)){
						weekendsList.remove(rlrq_str);
					}
				}
				//休息日
				if("20".equals(rlztdm)){
					if(!weekendsList.contains(rlrq_str)){
						weekendsList.add(rlrq_str);

						restDayMap.put("rlrq_str", m);
					}
				}
			}
		}
		//周末 + 休息日
		bundle.put("weekendsList",weekendsList);
		//特殊设置的休息日
		bundle.put("restDayMap",restDayMap);
	}
	
	public void query_sbtb(Parameters parameter, Bundle bundle) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("'blank':{type:'blank',label:'空白',image:'"+Sys.getResourceUrl("fm","blank")+"'},");
		sb.append("'basket':{type:'basket',label:'物料框',attributes:[{name:'name',label:'名称',type:'text',default:''},"
				+ "{name:'sbbh',label:'编号',type:'text',default:''}],"
				+ "directs:[{name:'left',label:'左',image:'"+Sys.getResourceUrl("fm","basket-left")+"'},"
				  + "{name:'right',label:'右',image:'"+Sys.getResourceUrl("fm","basket-right")+"'}"
				  + "]},");
		sb.append("'belt':{type:'belt',label:'传输带',attributes:[{name:'name',label:'名称',type:'text',default:''}],directs:[{name:'front-left',label:'左前',image:'"+Sys.getResourceUrl("fm","belt-front-left")+"'},"
				  + "{name:'front-right',label:'右前',image:'"+Sys.getResourceUrl("fm","belt-front-right")+"'},"
				  + "{name:'back-left',label:'左后',image:'"+Sys.getResourceUrl("fm","belt-back-left")+"'},"
				  + "{name:'back-right',label:'右后',image:'"+Sys.getResourceUrl("fm","belt-back-right")+"'}"
				  + "]},");
		sb.append("'channel':{type:'channel',label:'安全通道',attributes:[{name:'name',label:'名称',type:'text',default:''}],directs:[{name:'v',label:'竖',frames:['"+Sys.getResourceUrl("fm","channel-v-top")+"','"+Sys.getResourceUrl("fm","channel-v-center")+"','"+Sys.getResourceUrl("fm","channel-v-bottom")+"']},"
				  + "{name:'h',label:'横',frames:['"+Sys.getResourceUrl("fm","channel-h-top")+"','"+Sys.getResourceUrl("fm","channel-h-center")+"','"+Sys.getResourceUrl("fm","channel-h-bottom")+"']}"
				  + "]},");
		sb.append("       \"floor\":{\"type\":\"floor\",\"label\":\"地板\",\"description\":\"地板\",\"image\":\""+Sys.getResourceUrl("fm","floor")+"\",\"attributes\":[{\"name\":\"name\",\"label\":\"名称\",\"default\":\"\",\"type\":\"text\",\"validate\":{\"required\":true}},{\"name\":\"sbbh\",\"label\":\"编号\",\"type\":\"text\",\"validate\":{\"required\":true}}]},  ");   
		
		sb.append("'cement_floor':{type:'cement_floor',label:'水泥地',image:'"+Sys.getResourceUrl("fm","cement_floor")+"',"
				+ "attributes:[{name:'name',label:'名称',type:'text',default:''}]},");
		
		sb.append("'rubber_floor':{type:'rubber_floor',label:'橡胶地',image:'"+Sys.getResourceUrl("fm","rubber_floor")+"',"
				+ "attributes:[{name:'name',label:'名称',type:'text',default:''}]},");
		
		sb.append("'ground':{type:'ground',label:'地砖',directs:["
				  + "{name:'left',label:'深色',image:'"+Sys.getResourceUrl("fm","ground-deep")+"'},"
				  + "{name:'right',label:'浅色',image:'"+Sys.getResourceUrl("fm","ground-light")+"'}"
				  + "]},");
		
		sb.append("'i5':{type:'i5',label:'i5机床',attributes:["
				+ "{name:'name',label:'名称',type:'text',default:''},"
				+ "{name:'sbbh',label:'编号',type:'text',default:''}"
				+ "],directs:[{name:'front-left',label:'左前',frames:['"+Sys.getResourceUrl("fm","i5-front-left-white")+"','"+Sys.getResourceUrl("fm","i5-front-left-green")+"','"+Sys.getResourceUrl("fm","i5-front-left-yellow")+"','"+Sys.getResourceUrl("fm","i5-front-left-red")+"']},"
				+ "{name:'front-right',label:'右前',frames:['"+Sys.getResourceUrl("fm","i5-front-right-white")+"','"+Sys.getResourceUrl("fm","i5-front-right-green")+"','"+Sys.getResourceUrl("fm","i5-front-right-yellow")+"','"+Sys.getResourceUrl("fm","i5-front-right-red")+"']},"
				+ "{name:'back-left',label:'左后',frames:['"+Sys.getResourceUrl("fm","i5-back-left-white")+"','"+Sys.getResourceUrl("fm","i5-back-left-green")+"','"+Sys.getResourceUrl("fm","i5-back-left-yellow")+"','"+Sys.getResourceUrl("fm","i5-back-left-red")+"']},"
				+ "{name:'back-right',label:'右后',frames:['"+Sys.getResourceUrl("fm","i5-back-right-white")+"','"+Sys.getResourceUrl("fm","i5-back-right-green")+"','"+Sys.getResourceUrl("fm","i5-back-right-yellow")+"','"+Sys.getResourceUrl("fm","i5-back-right-red")+"']}"
				+ "]},");
		
		sb.append("'ma':{type:'ma',label:'机械臂',attributes:["
				  + "{name:'name',label:'名称',type:'text',default:''},"
				  + "{name:'sbbh',label:'编号',type:'text',default:''}],"
				  + "frames:['"+Sys.getResourceUrl("fm","ma-front-left")+"',"
				  + "'"+Sys.getResourceUrl("fm","ma-front-right")+"',"
				  + "'"+Sys.getResourceUrl("fm","ma-back-left")+"',"
				  + "'"+Sys.getResourceUrl("fm","ma-back-right")+"'"
				  + "]},");
		
		sb.append("'mt':{type:'mt',label:'机床',attributes:["
				  + "{name:'name',label:'名称',type:'text',default:''},"
				  + "{name:'sbbh',label:'设备编号',type:'text',default:''}],"
				  + "directs:[{name:'front-left',label:'左前',image:'"+Sys.getResourceUrl("fm","mt-front-left")+"'},"
				  + "{name:'front-right',label:'右前',image:'"+Sys.getResourceUrl("fm","mt-front-right")+"'},"
				  + "{name:'back-left',label:'左后',image:'"+Sys.getResourceUrl("fm","mt-back-left")+"'},"
				  + "{name:'back-right',label:'右后',image:'"+Sys.getResourceUrl("fm","mt-back-right")+"'}"
				  + "]},");
		
		sb.append("'net':{type:'net',label:'隔离网',attributes:[{name:'name',label:'名称',type:'text',default:''}],directs:[{name:'left',label:'左',image:'"+Sys.getResourceUrl("fm","net-left")+"'},"
				  + "{name:'right',label:'右',image:'"+Sys.getResourceUrl("fm","net-right")+"'}"
				  + "]},");
		sb.append("}");
		bundle.put("nodes", sb.toString());
		bundle.put("toolBtns", "['cursor','direct','|','blank','basket','belt','channel','floor','cement_floor',"
				+ "'rubber_floor','ground','i5','ma','mt','net','|','group']");
	
		try {
			BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(Sys.readFile("/sbxx/sbxx")));
			StringBuffer tStringBuffer = new StringBuffer();
			String sTempOneLine = new String("");
			while ((sTempOneLine = tBufferedReader.readLine()) != null){
				tStringBuffer.append(sTempOneLine);
			}
			bundle.put("data",JSONObject.fromObject(tStringBuffer.toString()));
			System.out.println(tStringBuffer.toString());
		} catch (Exception e) {
			bundle.put("data",null);
		}
	}
	
}

package com.isesol.mes.ismes.fm.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.isesol.ismes.platform.core.service.bean.Dataset;
import com.isesol.ismes.platform.module.Bundle;
import com.isesol.ismes.platform.module.Parameters;
import com.isesol.ismes.platform.module.Sys;
import com.isesol.mes.ismes.fm.util.DateTimeUtils;

public class GcrlActivity {
	
	private Logger log4j = Logger.getLogger(GcrlActivity.class);

	public String gcrlmbIndex(Parameters parameters,Bundle bundle){
		
		return "gcrlmb";
	}
	
	public String gcrlmbTable(Parameters parameters,Bundle bundle){
		Dataset dataset = Sys.query("fm_gcrlbmb", "rlmbid,rlmbmc,rlmbms,rlmbzt", null, null,new Object[]{});
		List<Map<String, Object>> list = dataset.getList();
		bundle.put("rows", list);
		bundle.put("totalRecord", dataset.getTotal());
		bundle.put("currentPage", 1);
		bundle.put("records", dataset.getCount());
		return "json:";
	}
	
	/**
	 * 得到所有的休息日
	 * @param parameters
	 * @param bundle
	 * @return
	 */
	public String getCalendarDays(Parameters parameters,Bundle bundle){
		String rlmbid = parameters.getString("rlmbid");
		
		String start = parameters.getString("start");
		String end = parameters.getString("end");
		List<Map<String,String>> events = new ArrayList<Map<String,String>>();
		
		//得到所有周末 
		Map<String,Object> map = DateTimeUtils.weekdaysList(start, end);
		List<String> weekendsList = (List<String>) map.get("weekendsList");
		
		Dataset dataset = Sys.query("fm_gcrlb", "rlztdm,rlrq", " rlmbid = ? ", null, new Object[]{rlmbid});
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
					}
				}
			}
		}
		
		
		for(String s : weekendsList){
			events.add( backgroundMap(s));
		}
			
		bundle.put("data",events);
		return "json:data";
	}
	
	private Map<String,String> backgroundMap(String s){
		Map<String,String> m = new HashMap<String,String>();
		m.put("start", s);
		m.put("backgroundColor", "blue");
		m.put("rendering", "background");
		return m;
	}
	
	/**
	 * TODO
	 * 选择某一天  加载这一天的日历信息
	 * @param parameters
	 * @param bundle
	 * @return
	 */
	public String calendarSelect(Parameters parameters,Bundle bundle){
		String rlmbid = parameters.getString("rlmbid");
		String rlrq = parameters.getString("rlrq");
		Dataset dataset = Sys.query("fm_gcrlb", "rlztdm,rlms,rllxdm", 
				" rlmbid = ? and rlrq = ? ", null, new Object[]{rlmbid,DateTimeUtils.string2Date(rlrq)});
		Map<String,Object> map = dataset.getMap();
		if(MapUtils.isEmpty(map)){
			Map<String,Object> returnMap = DateTimeUtils.weekdaysList(rlrq, rlrq);
			List<String> weekendsList = (List<String>) returnMap.get("weekendsList");
			if(weekendsList.contains(rlrq)){
				map = new HashMap<String, Object>();
				map.put("rlztdm", "20");
				map.put("rlms", "周末");
				map.put("rllxdm", "20");
			}
		}
		
		bundle.put("data", map);
		return "json:data";
		
	}
	
	/**
	 * 保存日历信息
	 * @param parameters
	 * @param bundle
	 */
	public String saveRlxx(Parameters parameters,Bundle bundle){
		
		String rlrq_s =  parameters.getString("rlsz_rl");
		List<Date> rlrqList = new ArrayList<Date>();
		if(rlrq_s.contains("-")){
			String beginStr = rlrq_s.split("-")[0];
			String endStr = rlrq_s.split("-")[1];
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateTimeUtils.string2Date(beginStr));
			while(calendar.getTime().compareTo(DateTimeUtils.string2Date(endStr)) < 1){
				rlrqList.add(calendar.getTime());
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
		}else{
			rlrqList.add(DateTimeUtils.string2Date(rlrq_s));
		}
		List<Map<String,Object>> insertList = new ArrayList<Map<String,Object>>();
		List<Object[]> deleteList = new ArrayList<Object[]>();
		List<String> returnDateStr = new ArrayList<String>();
		String rlztdm =  parameters.getString("rlsz_zt");
		String rllxdm =  parameters.getString("rlsz_lx");
		String rlmbid =  parameters.getString("hidden_rlmbid");
		String rlms =  parameters.getString("rlsz_ms");
		for(Date d :rlrqList ){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("rlrq", d);
			map.put("rlztdm", rlztdm);
			map.put("rllxdm", rllxdm);
			map.put("rlmbid", rlmbid);
			map.put("rlms", rlms);
			insertList.add(map);
			
			returnDateStr.add(DateTimeUtils.date2String(d, "yyyy/MM/dd"));
			
			deleteList.add(new Object[]{rlmbid,d});
			Sys.delete("fm_gcrlb", " rlmbid = ? and rlrq = ? ", new Object[]{rlmbid,d});
		}
		Sys.insert("fm_gcrlb", insertList);
		
		bundle.put("data", returnDateStr.toArray());
		return "json:data";
	}
}

package com.isesol.mes.ismes.fm.activity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.isesol.ismes.platform.core.service.bean.Dataset;
import com.isesol.ismes.platform.module.Bundle;
import com.isesol.ismes.platform.module.Parameters;
import com.isesol.ismes.platform.module.Sys;

public class GsActivity {
	
	private Logger log4j = Logger.getLogger(GsActivity.class);

	public String gswhIndex(Parameters parameters,Bundle bundle){
		return "gswh";
	}
	
	public String gswhTable(Parameters parameters,Bundle bundle){
		Dataset dataset = Sys.query("fm_gcgsmbb", "gsmbid,gsmbmc,gsmbms,sfmrmb,zhouyiks,zhouyijs,"
				+ "zhouerks,zhouerjs,zhousanks,zhousanjs,zhousiks,zhousijs,"
				+ "zhouwuks,zhouwujs,zhouliuks,zhouliujs,zhouriks,zhourijs", null, null,new Object[]{});
		List<Map<String, Object>> list = dataset.getList();
		if(CollectionUtils.isNotEmpty(list)){
			for(Map<String, Object> map : list){
				map.put("zhouyigs", getTimeDifference(handleDateMap(map,"zhouyiks"),handleDateMap(map,"zhouyijs") ));
				map.put("zhouergs", getTimeDifference(handleDateMap(map,"zhouerks"),handleDateMap(map,"zhouerjs") ));
				map.put("zhousangs", getTimeDifference(handleDateMap(map,"zhousanks"),handleDateMap(map,"zhousanjs") ));
				map.put("zhousigs", getTimeDifference(handleDateMap(map,"zhousiks"),handleDateMap(map,"zhousijs") ));
				map.put("zhouwugs", getTimeDifference(handleDateMap(map,"zhouwuks"),handleDateMap(map,"zhouwujs") ));
				map.put("zhouliugs", getTimeDifference(handleDateMap(map,"zhouliuks"),handleDateMap(map,"zhouliujs") ));
				map.put("zhourigs", getTimeDifference(handleDateMap(map,"zhouriks"),handleDateMap(map,"zhourijs") ));
			}
		}
		bundle.put("rows", list);
		bundle.put("totalRecord", dataset.getTotal());
		bundle.put("currentPage", 1);
		bundle.put("records", dataset.getCount());
		return "json:";
	}
	
	private Date handleDateMap(Map<String,Object> map ,String key){
		if(MapUtils.isEmpty(map)){
			return null;
		}
		if(map.get(key) == null){
			return null;
		}
		return (Date) map.get(key);
	}
	
	private Date string2Date(String timeStr){
	    if(!timeStr.contains(":")){
	    	timeStr = timeStr + " 00:00:00";
	    }
	    String format ="";
	    if(timeStr.contains("-")){
	    	format = "yyyy-MM-dd HH:mm:ss";
	    }
	    if(timeStr.contains("/")){
	    	format = "yyyy/MM/dd HH:mm:ss";
	    }
	    SimpleDateFormat formatter=new SimpleDateFormat(format);  
	    try {
			return formatter.parse(timeStr);
		} catch (ParseException e) {
			log4j.info("时间转换出现异常;;;"+timeStr);
			log4j.error(e.getMessage());
			return null;
		} 
	}
	
	private String date2String(Date time,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(time);
	}
	
	private Date timeString2Date(String timeStr){
		if(StringUtils.isBlank(timeStr)){
			return null;
		}
		String date = "2000/01/01 " + timeStr + ":00";
		return string2Date(date);
	}
	
	private BigDecimal getTimeDifference(Date date1,Date date2){
		if(date1 == null || date2 == null){
			return BigDecimal.ZERO;
		}
		return new BigDecimal(date2.getTime() - date1.getTime())
			.divide(new BigDecimal(1000*60*60),2,BigDecimal. ROUND_HALF_UP);
	}
	
	public String initModalInfo(Parameters parameters,Bundle bundle){
		String gsmbid = parameters.getString("gsmbid");
		Dataset dataset = Sys.query("fm_gcgsmbb", "gsmbid,gsmbmc,gsmbms,sfmrmb,zhouyiks,zhouyijs,"
				+ "zhouerks,zhouerjs,zhousanks,zhousanjs,zhousiks,zhousijs,"
				+ "zhouwuks,zhouwujs,zhouliuks,zhouliujs,zhouriks,zhourijs", " gsmbid = ? ", null,new Object[]{gsmbid});
		bundle.put("data", dataset.getMap());
		return "json:data";
	}
	
	public void saveGswhxx(Parameters parameters,Bundle bundle){
		Map<String,Object> hashMap = new HashMap<String, Object>();
		String gsmbmc = parameters.getString("gswh_modal_zjh");
		hashMap.put("gsmbmc", gsmbmc);
		String gsmbms = parameters.getString("gswh_modal_ms");
		hashMap.put("gsmbms", gsmbms);
		String sfmrmb = parameters.getString("gswh_modal_sfmrmb");
		hashMap.put("sfmrmb", sfmrmb);
		
		String zhouyiks_str = parameters.getString("zhouyiks");
		hashMap.put("zhouyiks", timeString2Date(zhouyiks_str));
		
		String zhouerks_str = parameters.getString( "zhouerks");
		hashMap.put("zhouerks", timeString2Date(zhouerks_str));
		
		String zhousanks_str = parameters.getString( "zhousanks");
		hashMap.put("zhousanks", timeString2Date(zhousanks_str));
		
		String zhousiks_str = parameters.getString( "zhousiks");
		hashMap.put("zhousiks", timeString2Date(zhousiks_str));
		
		String zhouwuks_str = parameters.getString( "zhouwuks");
		hashMap.put("zhouwuks", timeString2Date(zhouwuks_str));
		
		String zhouliuks_str = parameters.getString( "zhouliuks");
		hashMap.put("zhouliuks",  timeString2Date(zhouliuks_str));
		
		String zhouriks_str = parameters.getString( "zhouriks");
		hashMap.put("zhouriks", timeString2Date(zhouriks_str));
		
		
		String zhouyijs_str = parameters.getString( "zhouyijs");
		hashMap.put("zhouyijs", timeString2Date(zhouyijs_str));
		
		String zhouerjs_str = parameters.getString( "zhouerjs");
		hashMap.put("zhouerjs", timeString2Date(zhouerjs_str));
		
		String zhousanjs_str = parameters.getString( "zhousanjs");
		hashMap.put("zhousanjs", timeString2Date(zhousanjs_str));
		
		String zhousijs_str = parameters.getString( "zhousijs");
		hashMap.put("zhousijs", timeString2Date(zhousijs_str));
		
		String zhouwujs_str = parameters.getString( "zhouwujs");
		hashMap.put("zhouwujs", timeString2Date(zhouwujs_str));
		
		String zhouliujs_str = parameters.getString( "zhouliujs");
		hashMap.put("zhouliujs", timeString2Date(zhouliujs_str));
		
		String zhourijs_str = parameters.getString( "zhourijs");
		hashMap.put("zhourijs", timeString2Date(zhourijs_str));
		
		String gsmbid = parameters.getString("gswh_modal_gsmbid");
		
		//如果是默认的   将其余的修改为不是默认的
		if("10".equals(sfmrmb)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("sfmrmb", "20");
			Sys.update("fm_gcgsmbb", map, " sfmrmb = ? ", new Object[]{"10"});
		}
		
		if(StringUtils.isBlank(gsmbid)){
			Sys.insert("fm_gcgsmbb", hashMap);
		}else{
			Sys.update("fm_gcgsmbb",hashMap," gsmbid = ? ",new Object[]{gsmbid});
		}
	}
	
	public void  deleteGswhxx(Parameters parameters,Bundle bundle){
		String gsmbid = parameters.getString("gsmbid");
		Sys.delete("fm_gcgsmbb", " gsmbid = ? ", new Object[]{gsmbid});
	}
}

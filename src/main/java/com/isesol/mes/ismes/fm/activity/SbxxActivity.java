package com.isesol.mes.ismes.fm.activity;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.isesol.ismes.platform.module.Bundle;
import com.isesol.ismes.platform.module.Parameters;
import com.isesol.ismes.platform.module.Sys;

import net.sf.json.JSONObject;

public class SbxxActivity {
	
	public String department(Parameters parameter, Bundle bundle) {
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
		
//		sb.append("       \"floor\":{\"type\":\"floor\",\"label\":\"地板\",\"description\":\"地板\",\"image\":\""+Sys.getResourceUrl("fm","floor")+"\",\"attributes\":[{\"name\":\"name\",\"label\":\"名称\",\"default\":\"\",\"type\":\"text\",\"validate\":{\"required\":true}},{\"name\":\"sbbh\",\"label\":\"编号\",\"type\":\"text\",\"validate\":{\"required\":true}}]},  ");   
//		
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
		bundle.put("toolBtns", "['cursor','direct','|','blank','basket','belt','channel','cement_floor',"
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
			System.out.println("未读取到文件");
		}
		
		return "fm_sbxx";
	}
	
	/** 查询设备Tips
	 * @param parameters
	 * @param bundle
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String query_sbxx(Parameters parameters, Bundle bundle) {
		parameters.set("val_sb", "('1')");
		//处理时间参数开始
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, -5);
		date = c.getTime();
		parameters.set("querytime", date );
		Bundle b_sbxx = Sys.callModuleService("em", "emservice_sbxxBysbbh", parameters);
		List<Map<String, Object>> sbxx = (List<Map<String, Object>>) b_sbxx.get("sbxx");
		for (int i = 0; i < sbxx.size(); i++) {
			sbxx.get(i).put("state", "");
		}
		
		Bundle b_ssxx = Sys.callModuleService("interf", "ifService_ssxx", parameters);
		List<Map<String, Object>> ssxx = (List<Map<String, Object>>) b_ssxx.get("ssxx");
		if(null!=ssxx)
		{
			for (int i = 0; i < sbxx.size(); i++) {
				for (int j = 0; j < ssxx.size(); j++) {
					if (sbxx.get(i).get("sbbh").equals(ssxx.get(j).get("sbid"))) {
						sbxx.get(i).put("sbztdm", ssxx.get(j).get("sbztdm"));
					}
				}
			}
		}
		bundle.put("sbxx", sbxx);
		return "json:sbxx";
	}
	
	/**保存设备信息到本地
	 * @param parameter
	 * @param bundle
	 * @return
	 * @throws Exception
	 */
	public void saveSbxx(Parameters parameter, Bundle bundle) throws Exception {
		
		ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(parameter.get("e_data").toString().getBytes());
		System.out.println(parameter.get("e_data").toString());
//		int n=1;
//		String aa = "{\"title\":\"newFlow_1\",\"nodes\":{";
//		
//		//地板
//		for (int i = 0; i < 42; i++) {
//			aa = aa+ " \"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+(688-i%7*106+i/7*106)+",\"top\":"+(i%7*61+i/7*61+2)+",\"type\":\"floor\",\"zindex\":"+(n++)+",\"width\":220,\"height\":220,\"alt\":true},";
//		}
//		//安全通道顶部(列一)
//		for (int i = 0; i < 4; i++) {
//			aa = aa + " \"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+(720+i*154)+",\"top\":"+(i*92+52)+",\"type\":\"channel-v-top\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		}
//		
//		//安全通道顶部中间(列一)
//		for (int i = 0; i < 64; i++) {
//			aa = aa + " \"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+(703-i%16*34+i/16*154)+",\"top\":"+(i%16*20+i/16*92+71)+",\"type\":\"channel-v-center\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		}
//		//安全通道底部(列一)
//		for (int i = 0; i < 4; i++) {
//			aa = aa + " \"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+(177+i*154)+",\"top\":"+(i*92+390)+",\"type\":\"channel-v-bottom\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		}
//		//安全通道顶部(行一)
//		for (int i = 0; i < 2; i++) {
//			aa = aa + " \"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+(128+i*611)+",\"top\":"+(401-i*358)+",\"type\":\"channel-h-top\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		}
//		//安全通道顶部中间(行一)
//		for (int i = 0; i < 46; i++) {
//			aa = aa + " \"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+(156+i%23*20+i/23*611)+",\"top\":"+(i%23*12-i/23*358+407)+",\"type\":\"channel-h-center\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		}
//		//安全通道底部(行一)
//		for (int i = 0; i < 2; i++) {
//			aa = aa + " \"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+(623+i*611)+",\"top\":"+(676-i*358)+",\"type\":\"channel-h-bottom\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		}
//		
//		//围栏(一)
//		for (int i = 0; i < 14; i++) {
//			aa = aa + " \"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+(712-i%7*76+i/7*152)+",\"top\":"+(i%7*45+i/7*92+60)+",\"type\":\"net-left\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		}
//		//围栏(二)
//		for (int i = 0; i < 7; i++) {
//			aa = aa + " \"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+(1146-i%7*76+i/7*152)+",\"top\":"+(i%7*45+i/7*92+319)+",\"type\":\"net-left\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		}
//		//I5
//		for (int i = 0; i < 10; i++) {
//			aa = aa + " \"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+(755-i%5*106+i/5*152)+",\"top\":"+(i%5*61+i/5*92+115)+",\"type\":\"i5-front-left-white\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		}
//		//物料筐
//		for (int i = 0; i < 10; i++) {
//			aa = aa + " \"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+(785-i%5*106+i/5*152)+",\"top\":"+(i%5*61+i/5*92+143)+",\"type\":\"basket-right\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		}
//		for (int i = 0; i < 10; i++) {
//			aa = aa + " \"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+(755-i%5*106+i/5*152)+",\"top\":"+(i%5*61+i/5*92+163)+",\"type\":\"basket-right\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		}
//		
//		//传送带
//		for (int i = 0; i < 5; i++) {
//			aa = aa + " \"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+(943-i%5*43+i/5*152)+",\"top\":"+(i%5*24+i/5*92+316)+",\"type\":\"belt-back-left\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		}
//		
//		
//		aa = aa+"\"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+975+",\"top\":"+353+",\"type\":\"i5-front-right-white\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		aa = aa+"\"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+846+",\"top\":"+441+",\"type\":\"i5-back-left-white\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		aa = aa+"\"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+941+",\"top\":"+419+",\"type\":\"i5-back-right-white\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		aa = aa+"\"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+895+",\"top\":"+375+",\"type\":\"ma-back-left\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		
//		//传送带
//		for (int i = 0; i < 4; i++) {
//			aa = aa + " \"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+(943-i%5*43+i/5*152)+",\"top\":"+(i%5*24+i/5*92+316)+",\"type\":\"belt-back-left\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		}
//		//传送带
//		for (int i = 0; i < 3; i++) {
//			aa = aa + " \"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+(514+i%3*42+i/3*152)+",\"top\":"+(i%3*23+i/3*92+580)+",\"type\":\"belt-back-right\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		}
//		
//		aa = aa+"\"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+586+",\"top\":"+543+",\"type\":\"i5-front-left-white\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		aa = aa+"\"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+617+",\"top\":"+558+",\"type\":\"ma-front-left\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		aa = aa+"\"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+657+",\"top\":"+585+",\"type\":\"i5-back-right-white\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		aa = aa+"\"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+567+",\"top\":"+595+",\"type\":\"basket-left\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//		aa = aa+"\"sbxxTb_node_"+n+"\":{\"attributes\":{\"name\":\"\"},\"left\":"+674+",\"top\":"+520+",\"type\":\"i5-front-right-white\",\"zindex\":"+(n++)+",\"width\":100,\"height\":100,\"alt\":true},";
//
//		
//		aa = aa+ "},\"lines\":{},\"areas\":{},\"initNum\":"+n+"}";
//		
//		System.out.println(aa);
//		ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(aa.getBytes());
		Sys.saveFile("/sbxx/sbxx", tInputStringStream);
	}
	
}

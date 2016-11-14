package com.isesol.mes.ismes.fm.activity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.isesol.ismes.platform.module.Bundle;
import com.isesol.ismes.platform.module.Parameters;
import com.isesol.ismes.platform.module.Sys;

public class FactoryActivity {
	
	public String index(Parameters parameter, Bundle bundle) {		
		bundle.put("nodes", getFactoryNodes());
		String data = this.getFactoryData();
		bundle.put("data",data);
		
		List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
		Map<String,Object> map1_1 = new HashMap<String, Object>();
		map1_1.put("title", "订单D20160617001完成30%...");
		map1_1.put("state", "normal");
		map1_1.put("bizdata", "xxxx");
		map1_1.put("url", "http://www.baidu.com/");
		list1.add(map1_1);
		
		Map<String,Object> map1_2 = new HashMap<String, Object>();
		map1_2.put("title", "机床I500011011主轴损坏！");
		map1_2.put("state", "error");
		map1_2.put("bizdata", "xxxx");
		map1_2.put("url", "http://www.baidu.com/");
		list1.add(map1_2);
		
//		bundle.put("data_cj1", JSONArray.fromObject(list1).toString());
		
//		bundle.put("data_cj2", value);
		
		return "factory";
	}
	
	public String editIndex(Parameters parameter, Bundle bundle) {
		bundle.put("nodes", getFactoryNodes());
		bundle.put("toolBtns", "['cursor','direct','|','blank','department','building','singlebuilding','road','scraper','bench','net','billboard','container','tree-small','tree-big','trash','signboard-big','signboard-small','grass','ground','car','bus','stone','stones','|','group']");
		String data = this.getFactoryData();
		bundle.put("data",data);
		return "factory-edit";
	}
	
	public void factory_save(Parameters parameter, Bundle bundle) {
		ByteArrayInputStream bis = new ByteArrayInputStream(parameter.get("data").toString().getBytes());
		Sys.saveFile("/factory/factory.json", bis);
	}
	
	private String getFactoryData() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(Sys.readFile("/factory/factory.json")));
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
	
	private String getFactoryNodes() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("'blank':{type:'blank',label:'空白',image:'"+Sys.getResourceUrl("fm","blank")+"'},");
		sb.append("'light':{type:'light',label:'路灯',image:'"+Sys.getResourceUrl("fm","ground-light")+"'},");
		
		sb.append("'car':{type:'car',label:'小汽车',image:'"+Sys.getResourceUrl("fm","car")+"'},");
		sb.append("'grass':{type:'grass',label:'草地',image:'"+Sys.getResourceUrl("fm","grass")+"'},");
		sb.append("'stone':{type:'stone',label:'石头',image:'"+Sys.getResourceUrl("fm","stone")+"'},");
		sb.append("'stones':{type:'stones',label:'小石头',image:'"+Sys.getResourceUrl("fm","stones")+"'},");
		sb.append("'road':{type:'road',label:'马路',directs:["
				  + "{name:'left',label:'左前',image:'"+Sys.getResourceUrl("fm","road-left")+"'},"
				  + "{name:'right',label:'右前',image:'"+Sys.getResourceUrl("fm","road-right")+"'}"
				  + "]},");
		
		sb.append("'scraper':{type:'scraper',label:'叉车',directs:["
				  + "{name:'left',label:'左前',image:'"+Sys.getResourceUrl("fm","scraper-left")+"'},"
				  + "{name:'right',label:'右前',image:'"+Sys.getResourceUrl("fm","scraper-right")+"'}"
				  + "]},");
		
		sb.append("'department':{type:'department',label:'车间',groups:[{name:'popover',label:'信息提示'},{name:'gopage',label:'页面跳转'}],attributes:["
				+ "{name:'name',label:'名称',type:'text',default:''},"
				+ "{name:'bizid',label:'业务唯一标识',type:'text',default:''},"
				+ "{name:'placement',group:'popover',label:'显示位置',type:'enum',default:'auto',opts:{'source':[{value:'auto',text:'自动'},{value:'top',text:'顶部'},{value:'bottom',text:'底部'},{value:'left',text:'左侧'},{value:'right',text:'右侧'}]}},"
				+ "{name:'popover-width',group:'popover',label:'宽度',type:'digit'},"
				+ "{name:'popover-height',group:'popover',label:'高度',type:'digit'},"
				+ "{name:'popover-max',group:'popover',label:'最大显示条数',type:'digit'},"
				+ "{name:'title',group:'gopage',label:'页面标题',type:'text'},"
				+ "{name:'url',group:'gopage',label:'页面地址',type:'text'},"
				+ "{name:'mode',group:'gopage',label:'跳转方式',type:'enum',default:'modal',opts:{'source':[{value:'modal',text:'模态窗口'},{value:'blank',text:'新窗口'},{value:'self',text:'当前窗口'}]}},"
				+ "{name:'window-width',group:'gopage',label:'宽度',type:'digit'},"
				+ "{name:'window-height',group:'gopage',label:'高度',type:'digit'}"
				+ "],directs:["
				  + "{name:'left',label:'左前',image:'"+Sys.getResourceUrl("fm","department-left")+"'},"
				  + "{name:'right',label:'右前',image:'"+Sys.getResourceUrl("fm","department-right")+"'}"
				  + "]},");
		
		sb.append("'building':{type:'building',label:'办公楼',directs:["
				  + "{name:'left',label:'左前',image:'"+Sys.getResourceUrl("fm","building-left")+"'},"
				  + "{name:'right',label:'右前',image:'"+Sys.getResourceUrl("fm","building-right")+"'}"
				  + "]},");
		
		sb.append("'bench':{type:'bench',label:'长椅',directs:["
				  + "{name:'left',label:'左前',image:'"+Sys.getResourceUrl("fm","bench-left")+"'},"
				  + "{name:'right',label:'右前',image:'"+Sys.getResourceUrl("fm","bench-right")+"'}"
				  + "]},");
		
		sb.append("'billboard':{type:'billboard',label:'广告牌',directs:["
				  + "{name:'left',label:'左前',image:'"+Sys.getResourceUrl("fm","billboard-left")+"'},"
				  + "{name:'right',label:'右前',image:'"+Sys.getResourceUrl("fm","billboard-right")+"'}"
				  + "]},");
		
		sb.append("'trash':{type:'trash',label:'垃圾箱',directs:["
				  + "{name:'left',label:'左前',image:'"+Sys.getResourceUrl("fm","trash-left")+"'},"
				  + "{name:'right',label:'右前',image:'"+Sys.getResourceUrl("fm","trash-right")+"'}"
				  + "]},");
		
		sb.append("'tree-small':{type:'tree-small',label:'小树',directs:["
				  + "{name:'left',label:'左前',image:'"+Sys.getResourceUrl("fm","tree-small-left")+"'},"
				  + "{name:'right',label:'右前',image:'"+Sys.getResourceUrl("fm","tree-small-right")+"'}"
				  + "]},");
		
		sb.append("'tree-big':{type:'tree-big',label:'大树',directs:["
				  + "{name:'left',label:'左前',image:'"+Sys.getResourceUrl("fm","tree-big-left")+"'},"
				  + "{name:'right',label:'右前',image:'"+Sys.getResourceUrl("fm","tree-big-right")+"'}"
				  + "]},");
		
		sb.append("'signboard-small':{type:'signboard-small',label:'小警告牌',directs:["
				  + "{name:'left',label:'左前',image:'"+Sys.getResourceUrl("fm","signboard-small-left")+"'},"
				  + "{name:'right',label:'右前',image:'"+Sys.getResourceUrl("fm","signboard-small-right")+"'}"
				  + "]},");
		
		sb.append("'singlebuilding':{type:'singlebuilding',label:'大楼',directs:["
				  + "{name:'left',label:'左前',image:'"+Sys.getResourceUrl("fm","singlebuilding-left")+"'},"
				  + "{name:'right',label:'右前',image:'"+Sys.getResourceUrl("fm","singlebuilding-right")+"'}"
				  + "]},");
		
		sb.append("'ground':{type:'ground',label:'地砖',directs:["
				  + "{name:'left',label:'深色',image:'"+Sys.getResourceUrl("fm","ground-deep")+"'},"
				  + "{name:'right',label:'浅色',image:'"+Sys.getResourceUrl("fm","ground-light")+"'}"
				  + "]},");
		
		sb.append("'signboard-big':{type:'signboard-big',label:'大警告牌',directs:[{name:'lefta',label:'左1',image:'"+Sys.getResourceUrl("fm","signboard-big-lefta")+"'},"
				  + "{name:'leftb',label:'左2',image:'"+Sys.getResourceUrl("fm","signboard-big-leftb")+"'},"
				  + "{name:'right1',label:'右1',image:'"+Sys.getResourceUrl("fm","signboard-big-righta")+"'},"
				  + "{name:'right2',label:'右2',image:'"+Sys.getResourceUrl("fm","signboard-big-rightb")+"'}"
				  + "]},");
		
		sb.append("'bus':{type:'bus',label:'客车',directs:[{name:'bottom-left',label:'左下',image:'"+Sys.getResourceUrl("fm","bus-bottom-left")+"'},"
				  + "{name:'top-left',label:'左上',image:'"+Sys.getResourceUrl("fm","bus-top-left")+"'},"
				  + "{name:'bottom-right',label:'右下',image:'"+Sys.getResourceUrl("fm","bus-bottom-right")+"'},"
				  + "{name:'top-right',label:'右上',image:'"+Sys.getResourceUrl("fm","bus-top-right")+"'}"
				  + "]},");
		
		sb.append("'container':{type:'container',label:'集装箱',directs:[{name:'red-left',label:'红左',image:'"+Sys.getResourceUrl("fm","container-red-left")+"'},"
				  + "{name:'top-left',label:'绿左',image:'"+Sys.getResourceUrl("fm","container-green-left")+"'},"
				  + "{name:'red-right',label:'红右',image:'"+Sys.getResourceUrl("fm","container-red-right")+"'},"
				  + "{name:'top-right',label:'绿右',image:'"+Sys.getResourceUrl("fm","container-green-right")+"'}"
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

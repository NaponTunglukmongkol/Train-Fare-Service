package com.trainfee.service;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Calculator {
	private String stationName;
	private String startLine;
	private String stopLine;
	private Reader rd = new Reader();
	
	public String checkLine(String station) throws IOException, ParseException {
		String line = null;
		JSONObject airlink = rd.readFileObject("Airlink");
		JSONObject bts = rd.readFileObject("BTS");
		JSONObject mrt1 = rd.readFileObject("MRT1");
		JSONObject mrt2 = rd.readFileObject("MRT2");
		if(airlink.get(station) != null) {
			return line = "Airlink";
		}else if(bts.get(station) != null) {
			return line = "BTS";
		}else if(mrt1.get(station) != null) {
			return line = "MRT1";
		}else if(mrt2.get(station) != null) {
			return line = "MRT2";
		}
		return line;
	}
	
	public JSONObject calculateFare(String start, String startLine, String stop, String stopLine) throws IOException, ParseException {
		JSONObject jo = new JSONObject();
		int count = 0;
		JSONArray ja = new JSONArray();
		if(startLine == stopLine) { //same line
			long fare = -1;
			JSONObject keep = rd.readFileObject(startLine);
			JSONObject station = (JSONObject) keep.get(start);
			fare = (Long) station.get(stop);
			count = 1;
			JSONObject joo = new JSONObject();
			joo.put("fare", fare);
			ja.add(joo);
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);
		}else if(startLine == "BTS" && stopLine == "MRT1") { //bts to mrt1
			count = 4;
			JSONObject bts = rd.readFileObject(startLine);//get bts json
			JSONObject mrt1 = rd.readFileObject(stopLine);//get mrt1 json
			JSONObject airlink = rd.readFileObject("Airlink");//get airlink json
			JSONObject btsStation = (JSONObject) bts.get(start);//get start staion json
			JSONObject airlinkStation = (JSONObject) airlink.get("พญาไท");
			//go through Asok
			long btsfare1 = (Long) btsStation.get("อโศก");
			JSONObject mrt1Station1 = (JSONObject) mrt1.get("สุขุมวิท");
			long mrtfare1 = (Long) mrt1Station1.get(stop);
			long fare1 = btsfare1 + mrtfare1;
			String route1 = "ไปลงที่สถานี อโศก จากนั้นเปลี่ยนไป mrt";
			JSONObject jo1 = new JSONObject();
			jo1.put("fare", fare1);
			jo1.put("route", route1);
			ja.add(jo1);
			//go through Sala deang
			long btsfare2 = (Long) btsStation.get("ศาลาแดง");
			JSONObject mrt1Station2 = (JSONObject) mrt1.get("สีลม");
			long mrtfare2 = (Long) mrt1Station2.get(stop);
			long fare2 = btsfare2 + mrtfare2;
			String route2 = "ไปลงที่สถานี ศาลาแดง จากนั้นเปลี่ยนไป mrt";
			JSONObject jo2 = new JSONObject();
			jo2.put("fare", fare2);
			jo2.put("route", route2);
			ja.add(jo2);
			//go through Mo chit
			long btsfare3 = (Long) btsStation.get("หมอชิต");
			JSONObject mrt1Station3 = (JSONObject) mrt1.get("สวนจตุจักร");
			long mrtfare3 = (Long) mrt1Station3.get(stop);
			long fare3 = btsfare3 + mrtfare3;
			String route3 = "ไปลงที่สถานี หมอชิต จากนั้นเปลี่ยนไป mrt";
			JSONObject jo3 = new JSONObject();
			jo3.put("fare", fare3);
			jo3.put("route", route3);
			ja.add(jo3);
			//go through Airlink
			long btsfare4 = (Long) btsStation.get("พญาไท");
			JSONObject mrt1Station4 = (JSONObject) mrt1.get("เพชรบุรี");
			long mrtfare4 = (Long) mrt1Station3.get(stop);
			long airlinkfare = (Long) airlinkStation.get("มักกะสัน");
			long fare4 = btsfare3 + mrtfare3 + airlinkfare;
			String route4 = "ไปลงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink นั่งต่อไปจนถึงสถานี มักกะสัน จากนั้นเปลี่ยนไป mrt";
			JSONObject jo4 = new JSONObject();
			jo4.put("fare", fare4);
			jo4.put("route", route4);
			ja.add(jo4);
			//finish return
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);
		}
		return jo;
	}
	
}

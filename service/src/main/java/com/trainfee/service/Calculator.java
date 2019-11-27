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
		JSONObject mrt = rd.readFileObject("MRT");
		if (airlink.get(station) != null) {
			return line = "Airlink";
		} else if (bts.get(station) != null) {
			return line = "BTS";
		} else if (mrt.get(station) != null) {
			return line = "MRT";
		}
		return line;
	}

	public JSONObject calculateFare(String start, String startLine, String stop, String stopLine)
			throws IOException, ParseException {
		JSONObject jo = new JSONObject();
		int count = 0;
		JSONArray ja = new JSONArray();
		if (startLine == stopLine) { // same line
			long fare = -1;
			long time = -1;
			JSONObject keep = rd.readFileObject(startLine);
			JSONObject station = (JSONObject) keep.get(start);
			JSONObject keeptime = rd.readFileObject(startLine + "_time");
			JSONObject stationtime = (JSONObject) keeptime.get(start);
			time = (Long) stationtime.get(stop);
			fare = (Long) station.get(stop);
			count = 1;
			JSONObject joo = new JSONObject();
			joo.put("fare", fare);
			joo.put("time", time);
			ja.add(joo);
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);
		} else if (startLine == "BTS" && stopLine == "MRT") { // bts to mrt
			count = 6;
			JSONObject bts = rd.readFileObject(startLine);// get bts json
			JSONObject mrt = rd.readFileObject(stopLine);// get mrt json
			JSONObject airlink = rd.readFileObject("Airlink");// get airlink json
			JSONObject btsStation = (JSONObject) bts.get(start);// get start station json
			JSONObject airlinkStation = (JSONObject) airlink.get("พญาไท");
			// go through Asok
			long btsfare1 = (Long) btsStation.get("อโศก");
			JSONObject mrt1Station1 = (JSONObject) mrt.get("สุขุมวิท");
			long mrtfare1 = (Long) mrt1Station1.get(stop);
			long fare1 = btsfare1 + mrtfare1;
			String route1 = "ไปลงที่สถานี อโศก จากนั้นเปลี่ยนไป mrt";
			JSONObject jo1 = new JSONObject();
			jo1.put("fare", fare1);
			jo1.put("route", route1);
			// go through Sala deang
			long btsfare2 = (Long) btsStation.get("ศาลาแดง");
			JSONObject mrt1Station2 = (JSONObject) mrt.get("สีลม");
			long mrtfare2 = (Long) mrt1Station2.get(stop);
			long fare2 = btsfare2 + mrtfare2;
			String route2 = "ไปลงที่สถานี ศาลาแดง จากนั้นเปลี่ยนไป mrt";
			JSONObject jo2 = new JSONObject();
			jo2.put("fare", fare2);
			jo2.put("route", route2);
			// go through Mo chit
			long btsfare3 = (Long) btsStation.get("หมอชิต");
			JSONObject mrt1Station3 = (JSONObject) mrt.get("สวนจตุจักร");
			long mrtfare3 = (Long) mrt1Station3.get(stop);
			long fare3 = btsfare3 + mrtfare3;
			String route3 = "ไปลงที่สถานี หมอชิต จากนั้นเปลี่ยนไป mrt";
			JSONObject jo3 = new JSONObject();
			jo3.put("fare", fare3);
			jo3.put("route", route3);
			// go through Airlink
			long btsfare4 = (Long) btsStation.get("พญาไท");
			JSONObject mrt1Station4 = (JSONObject) mrt.get("เพชรบุรี");
			long mrtfare4 = (Long) mrt1Station4.get(stop);
			long airlinkfare = (Long) airlinkStation.get("มักกะสัน");
			long fare4 = btsfare4 + mrtfare4 + airlinkfare;
			String route4 = "ไปลงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink นั่งต่อไปจนถึงสถานี มักกะสัน จากนั้นเปลี่ยนไป mrt";
			JSONObject jo4 = new JSONObject();
			jo4.put("fare", fare4);
			jo4.put("route", route4);
			// go through Bangwa
			long btsfare5 = (Long) btsStation.get("บางหว้า");
			JSONObject mrt1Station5 = (JSONObject) mrt.get("บางหว้า");
			long mrtfare5 = (Long) mrt1Station5.get(stop);
			long fare5 = btsfare5 + mrtfare5;
			String route5 = "ไปลงสถานี บางหว้า จากนั้นเปลี่ยนไป mrt";
			JSONObject jo5 = new JSONObject();
			jo5.put("fare", fare5);
			jo5.put("route", route5);
			// go through ha yeak ladproud
			long btsfare6 = (Long) btsStation.get("ห้าแยกลาดพร้าว");
			JSONObject mrt1Station6 = (JSONObject) mrt.get("พหลโยธิน");
			long mrtfare6 = (Long) mrt1Station6.get(stop);
			long fare6 = btsfare6 + mrtfare6;
			String route6 = "ไปลงสถานี ห้าแยกลาดพร้าว จากนั้นเปลี่ยนไป mrt";
			JSONObject jo6 = new JSONObject();
			jo6.put("fare", fare6);
			jo6.put("route", route6);
			// time
			JSONObject btsTime = rd.readFileObject(startLine + "_time");// get bts json
			JSONObject mrt1Time = rd.readFileObject(stopLine + "_time");// get mrt1 json
			JSONObject airlinkTime = rd.readFileObject("Airlink_time");// get airlink json
			JSONObject btsStationTime = (JSONObject) btsTime.get(start);// get start staion json
			JSONObject airlinkStationTime = (JSONObject) airlinkTime.get("พญาไท");
			// go through Asok
			long btsTime1 = (Long) btsStationTime.get("อโศก");
			JSONObject mrt1StationTime1 = (JSONObject) mrt1Time.get("สุขุมวิท");
			long mrtTime1 = (Long) mrt1StationTime1.get(stop);
			long time1 = btsTime1 + mrtTime1;
			jo1.put("time", time1);
			ja.add(jo1);
			// go through Sala deang
			long btsTime2 = (Long) btsStationTime.get("ศาลาแดง");
			JSONObject mrt1StationTime2 = (JSONObject) mrt1Time.get("สีลม");
			long mrtTime2 = (Long) mrt1StationTime2.get(stop);
			long time2 = btsTime2 + mrtTime2;
			jo2.put("time", time2);
			ja.add(jo2);
			// go through Mo chit
			long btsTime3 = (Long) btsStationTime.get("หมอชิต");
			JSONObject mrt1StationTime3 = (JSONObject) mrt1Time.get("สวนจตุจักร");
			long mrtTime3 = (Long) mrt1StationTime3.get(stop);
			long time3 = btsTime3 + mrtTime3;
			jo3.put("time", time3);
			ja.add(jo3);
			// go through Airlink
			long btsTime4 = (Long) btsStationTime.get("พญาไท");
			JSONObject mrt1StationTime4 = (JSONObject) mrt1Time.get("เพชรบุรี");
			long mrtTime4 = (Long) mrt1StationTime4.get(stop);
			long airlinkTime1 = (Long) airlinkStationTime.get("มักกะสัน");
			long time4 = btsTime4 + mrtTime4 + airlinkTime1;
			jo4.put("time", time4);
			ja.add(jo4);
			// go through Bangwa
			long btstime5 = (Long) btsStationTime.get("บางหว้า");
			JSONObject mrt1StationTime5 = (JSONObject) mrt1Time.get("บางหว้า");
			long mrttime5 = (Long) mrt1StationTime5.get(stop);
			long time5 = btstime5 + mrttime5;
			jo5.put("time", time5);
			ja.add(jo5);
			// go through ha yeak ladproud
			long btstime6 = (Long) btsStationTime.get("ห้าแยกลาดพร้าว");
			JSONObject mrt1StationTime6 = (JSONObject) mrt1Time.get("พหลโยธิน");
			long mrttime6 = (Long) mrt1StationTime6.get(stop);
			long time6 = btstime6 + mrttime6;
			jo6.put("time", time6);
			ja.add(jo6);
			// finish return
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);
		} else if (startLine == "MRT" && stopLine == "BTS") { // mrt to bts
			count = 6;
			JSONObject mrt1 = rd.readFileObject(startLine);// get mrt json
			JSONObject bts = rd.readFileObject(stopLine);// get bts json
			JSONObject airlink = rd.readFileObject("Airlink");// get airlink json
			JSONObject mrt1Station = (JSONObject) mrt1.get(start);// get start staะion json
			JSONObject airlinkStation = (JSONObject) airlink.get("พญาไท");// get start staะion json
			JSONObject btsStation = (JSONObject) bts.get("พญาไท");
			// go through Sukumwit
			///////
			long mrtfare1 = (Long) mrt1Station.get("สุขุมวิท");
			JSONObject btsStation1 = (JSONObject) bts.get("อโศก");
			long btsfare1 = (Long) btsStation1.get(stop);
			long fare1 = btsfare1 + mrtfare1;
			String route1 = "ไปลงที่สถานี สุขุมวิท จากนั้นเปลี่ยนไป bts";
			JSONObject jo1 = new JSONObject();
			jo1.put("fare", fare1);
			jo1.put("route", route1);
			// go through Sala deang
			long mrtfare2 = (Long) mrt1Station.get("สีลม");
			JSONObject btsStation2 = (JSONObject) bts.get("ศาลาแดง");
			long btsfare2 = (Long) btsStation2.get(stop);
			long fare2 = btsfare2 + mrtfare2;
			String route2 = "ไปลงที่สถานี สีลม จากนั้นเปลี่ยนไป bts";
			JSONObject jo2 = new JSONObject();
			jo2.put("fare", fare2);
			jo2.put("route", route2);
			// go through Jatujak
			long mrtfare3 = (Long) mrt1Station.get("สวนจตุจักร");
			JSONObject btsStation3 = (JSONObject) bts.get("หมอชิต");
			long btsfare3 = (Long) btsStation3.get(stop);
			long fare3 = btsfare3 + mrtfare3;
			String route3 = "ไปลงที่สถานี สวนจตุจักร จากนั้นเปลี่ยนไป bts";
			JSONObject jo3 = new JSONObject();
			jo3.put("fare", fare3);
			jo3.put("route", route3);
			// go through Airlink
			long mrtfare4 = (Long) mrt1Station.get("เพชรบุรี");
			JSONObject btsStation4 = (JSONObject) bts.get("พญาไท");
			long btsfare4 = (Long) btsStation4.get(stop);
			long airlinkfare = (Long) airlinkStation.get("มักกะสัน");
			long fare4 = btsfare4 + mrtfare4 + airlinkfare;
			String route4 = "ไปลงสถานี เพชรบุรี จากนั้นเปลี่ยนไป airportlink นั่งต่อไปจนถึงสถานี พญาไท จากนั้นเปลี่ยนไป bts";
			JSONObject jo4 = new JSONObject();
			jo4.put("fare", fare4);
			jo4.put("route", route4);
			// go through Bangwa
			long mrtfare5 = (Long) mrt1Station.get("บางหว้า");
			JSONObject btsStation5 = (JSONObject) bts.get("บางหว้า");
			long btsfare5 = (Long) btsStation5.get(stop);
			long fare5 = btsfare5 + mrtfare5;
			String route5 = "ไปลงที่สถานี บางหว้า จากนั้นเปลี่ยนไป bts";
			JSONObject jo5 = new JSONObject();
			jo5.put("fare", fare5);
			jo5.put("route", route5);
			// go through Pahonyotin
			long mrtfare6 = (Long) mrt1Station.get("พหลโยธิน");
			JSONObject btsStation6 = (JSONObject) bts.get("ห้าแยกลาดพร้าว");
			long btsfare6 = (Long) btsStation6.get(stop);
			long fare6 = btsfare6 + mrtfare6;
			String route6 = "ไปลงที่สถานี พหลโยธิน จากนั้นเปลี่ยนไป bts";
			JSONObject jo6 = new JSONObject();
			jo6.put("fare", fare6);
			jo6.put("route", route6);
			// time
			JSONObject mrt1Time = rd.readFileObject(startLine + "_time");// get mrt1 json
			JSONObject btsTime = rd.readFileObject(stopLine + "_time");// get bts json
			JSONObject airlinkTime = rd.readFileObject("Airlink_time");// get airlink json
			JSONObject mrt1StationTime = (JSONObject) mrt1Time.get(start);// get start staion json
			JSONObject airlinkStationTime = (JSONObject) airlinkTime.get("พญาไท");// get start staion json
			JSONObject btsStationTime = (JSONObject) btsTime.get("พญาไท");
			// go through Sukumwit
			///////
			long mrtTime1 = (Long) mrt1StationTime.get("สุขุมวิท");
			JSONObject btsStationTime1 = (JSONObject) btsTime.get("อโศก");
			long btsTime1 = (Long) btsStationTime1.get(stop);
			long time1 = btsTime1 + mrtTime1;
			jo1.put("time", time1);
			ja.add(jo1);
			// go through Sala deang
			long mrtTime2 = (Long) mrt1StationTime.get("สีลม");
			JSONObject btsStationTime2 = (JSONObject) btsTime.get("ศาลาแดง");
			long btsTime2 = (Long) btsStationTime2.get(stop);
			long time2 = btsTime2 + mrtTime2;
			jo2.put("time", time2);
			ja.add(jo2);
			// go through Jatujak
			long mrtTime3 = (Long) mrt1StationTime.get("สวนจตุจักร");
			JSONObject btsStationTime3 = (JSONObject) btsTime.get("หมอชิต");
			long btsTime3 = (Long) btsStationTime3.get(stop);
			long time3 = btsTime3 + mrtTime3;
			jo3.put("time", time3);
			ja.add(jo3);
			// go through Airlink
			long mrtTime4 = (Long) mrt1StationTime.get("เพชรบุรี");
			JSONObject btsStationTime4 = (JSONObject) btsTime.get("พญาไท");
			long btsTime4 = (Long) btsStationTime4.get(stop);
			long airlinkTime1 = (Long) airlinkStationTime.get("มักกะสัน");
			long time4 = btsTime4 + mrtTime4 + airlinkTime1;
			jo4.put("time", time4);
			ja.add(jo4);
			// go through Bangwa
			long mrtTime5 = (Long) mrt1StationTime.get("บางหว้า");
			JSONObject btsStationTime5 = (JSONObject) btsTime.get("บางหว้า");
			long btsTime5 = (Long) btsStationTime5.get(stop);
			long time5 = btsTime5 + mrtTime5;
			jo5.put("time", time5);
			ja.add(jo5);
			// go through Pahonyotin
			long mrtTime6 = (Long) mrt1StationTime.get("พหลโยธิน");
			JSONObject btsStationTime6 = (JSONObject) btsTime.get("ห้าแยกลาดพร้าว");
			long btsTime6 = (Long) btsStationTime6.get(stop);
			long time6 = btsTime6 + mrtTime6;
			jo6.put("time", time6);
			ja.add(jo6);
			// finish return
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);

		} else if (startLine == "BTS" && stopLine == "Airlink") { // bts to airlink
			count = 6;
			JSONObject bts = rd.readFileObject(startLine);// get bts json
			JSONObject mrt1 = rd.readFileObject("MRT");// get mrt1 json
			JSONObject airlink = rd.readFileObject(stopLine);// get airlink json
			JSONObject btsStation = (JSONObject) bts.get(start);// get start station json
			JSONObject airlinkStation = (JSONObject) airlink.get("พญาไท");
			// go through Morchit
			long btsfare1 = (Long) btsStation.get("หมอชิต");
			JSONObject mrt1Station1 = (JSONObject) mrt1.get("สวนจตุจักร");
			JSONObject airlinkStation1 = (JSONObject) airlink.get("มักกะสัน");
			long mrtfare1 = (Long) mrt1Station1.get("เพชรบุรี");
			long airlinkfare1 = (Long) airlinkStation1.get(stop);
			long fare1 = btsfare1 + mrtfare1 + airlinkfare1;
			String route1 = "ไปลงที่สถานี หมอชิต จากนั้นเปลี่ยนไป mrt นั่งต่อไปจนถึงสถานี เพชรบุรี จากนั้นเปลี่ยนไป airportlink";
			JSONObject jo1 = new JSONObject();
			jo1.put("fare", fare1);
			jo1.put("route", route1);
			// go through Asok
			long btsfare2 = (Long) btsStation.get("อโศก");
			JSONObject mrt1Station2 = (JSONObject) mrt1.get("สุขุมวิท");
			JSONObject airlinkStation2 = (JSONObject) airlink.get("มักกะสัน");
			long mrtfare2 = (Long) mrt1Station2.get("เพชรบุรี");
			long airlinkfare2 = (Long) airlinkStation2.get(stop);
			long fare2 = btsfare2 + mrtfare2 + airlinkfare2;
			String route2 = "ไปลงที่สถานี อโศก จากนั้นเปลี่ยนไป mrt นั่งต่อไปจนถึงสถานี เพชรบุรี จากนั้นเปลี่ยนไป airportlink";
			JSONObject jo2 = new JSONObject();
			jo2.put("fare", fare2);
			jo2.put("route", route2);
			// go through Saladang
			long btsfare3 = (Long) btsStation.get("ศาลาแดง");
			JSONObject mrt1Station3 = (JSONObject) mrt1.get("สีลม");
			JSONObject airlinkStation3 = (JSONObject) airlink.get("มักกะสัน");
			long mrtfare3 = (Long) mrt1Station3.get("เพชรบุรี");
			long airlinkfare3 = (Long) airlinkStation3.get(stop);
			long fare3 = btsfare3 + mrtfare3 + airlinkfare3;
			String route3 = "ไปลงที่สถานี ศาลาแดง จากนั้นเปลี่ยนไป mrt นั่งต่อไปจนถึงสถานี เพชรบุรี จากนั้นเปลี่ยนไป airportlink";
			JSONObject jo3 = new JSONObject();
			jo3.put("fare", fare3);
			jo3.put("route", route3);
			// go through Phayathai
			long btsfare4 = (Long) btsStation.get("พญาไท");
			long airlinkfare = (Long) airlinkStation.get(stop);
			long fare4 = btsfare4 + airlinkfare;
			String route4 = "ไปลงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink";
			JSONObject jo4 = new JSONObject();
			jo4.put("fare", fare4);
			jo4.put("route", route4);
			// go through ha yaek ladproud
			long btsfare5 = (Long) btsStation.get("ห้าแยกลาดพร้าว");
			JSONObject mrt1Station5 = (JSONObject) mrt1.get("พหลโยธิน");
			JSONObject airlinkStation5 = (JSONObject) airlink.get("มักกะสัน");
			long mrtfare5 = (Long) mrt1Station5.get("เพชรบุรี");
			long airlinkfare5 = (Long) airlinkStation5.get(stop);
			long fare5 = btsfare5 + mrtfare5 + airlinkfare5;
			String route5 = "ไปลงที่สถานี ห้าแยกลาดพร้าว จากนั้นเปลี่ยนไป mrt นั่งต่อไปจนถึงสถานี เพชรบุรี จากนั้นเปลี่ยนไป airportlink";
			JSONObject jo5 = new JSONObject();
			jo5.put("fare", fare5);
			jo5.put("route", route5);
			// go through Bang wa
			long btsfare6 = (Long) btsStation.get("บางหว้า");
			JSONObject mrt1Station6 = (JSONObject) mrt1.get("บางหว้า");
			JSONObject airlinkStation6 = (JSONObject) airlink.get("มักกะสัน");
			long mrtfare6 = (Long) mrt1Station6.get("เพชรบุรี");
			long airlinkfare6 = (Long) airlinkStation6.get(stop);
			long fare6 = btsfare6 + mrtfare6 + airlinkfare6;
			String route6 = "ไปลงที่สถานี บางหว้า จากนั้นเปลี่ยนไป mrt นั่งต่อไปจนถึงสถานี เพชรบุรี จากนั้นเปลี่ยนไป airportlink";
			JSONObject jo6 = new JSONObject();
			jo6.put("fare", fare6);
			jo6.put("route", route6);
			// time
			JSONObject btsTime = rd.readFileObject(startLine + "_time");// get bts json
			JSONObject mrt1Time = rd.readFileObject("MRT_time");// get mrt1 json
			JSONObject airlinkTime = rd.readFileObject(stopLine + "_time");// get airlink json
			JSONObject btsStationTime = (JSONObject) btsTime.get(start);// get start station json
			JSONObject airlinkStationTime = (JSONObject) airlinkTime.get("พญาไท");
			// go through Morchit
			long btsTime1 = (Long) btsStationTime.get("หมอชิต");
			JSONObject mrt1StationTime1 = (JSONObject) mrt1Time.get("สวนจตุจักร");
			JSONObject airlinkStationTime1 = (JSONObject) airlinkTime.get("มักกะสัน");
			long mrtTime1 = (Long) mrt1StationTime1.get("เพชรบุรี");
			long airlinkTime1 = (Long) airlinkStationTime1.get(stop);
			long time1 = btsTime1 + mrtTime1 + airlinkTime1;
			jo1.put("time", time1);
			ja.add(jo1);
			// go through Asok
			long btsTime2 = (Long) btsStationTime.get("อโศก");
			JSONObject mrt1StationTime2 = (JSONObject) mrt1Time.get("สุขุมวิท");
			JSONObject airlinkStationTime2 = (JSONObject) airlinkTime.get("มักกะสัน");
			long mrtTime2 = (Long) mrt1StationTime2.get("เพชรบุรี");
			long airlinkTime2 = (Long) airlinkStationTime2.get(stop);
			long time2 = btsTime2 + mrtTime2 + airlinkTime2;
			jo2.put("time", time2);
			ja.add(jo2);
			// go through Saladang
			long btsTime3 = (Long) btsStationTime.get("ศาลาแดง");
			JSONObject mrt1StationTime3 = (JSONObject) mrt1Time.get("สีลม");
			JSONObject airlinkStationTime3 = (JSONObject) airlinkTime.get("มักกะสัน");
			long mrtTime3 = (Long) mrt1StationTime3.get("เพชรบุรี");
			long airlinkTime3 = (Long) airlinkStationTime3.get(stop);
			long time3 = btsTime3 + mrtTime3 + airlinkTime3;
			jo3.put("time", time3);
			ja.add(jo3);
			// go through Phayathai
			long btsTime4 = (Long) btsStationTime.get("พญาไท");
			long airlinkTime4 = (Long) airlinkStationTime.get(stop);
			long time4 = btsTime4 + airlinkTime4;
			jo4.put("time", time4);
			ja.add(jo4);
			// go through ha yeak ladproud
			long btsTime5 = (Long) btsStationTime.get("ห้าแยกลาดพร้าว");
			JSONObject mrt1StationTime5 = (JSONObject) mrt1Time.get("พหลโยธิน");
			JSONObject airlinkStationTime5 = (JSONObject) airlinkTime.get("มักกะสัน");
			long mrtTime5 = (Long) mrt1StationTime5.get("เพชรบุรี");
			long airlinkTime5 = (Long) airlinkStationTime5.get(stop);
			long time5 = btsTime5 + mrtTime5 + airlinkTime5;
			jo5.put("time", time5);
			ja.add(jo5);
			// go through ha yeak Bang wa
			long btsTime6 = (Long) btsStationTime.get("บางหว้า");
			JSONObject mrt1StationTime6 = (JSONObject) mrt1Time.get("บางหว้า");
			JSONObject airlinkStationTime6 = (JSONObject) airlinkTime.get("มักกะสัน");
			long mrtTime6 = (Long) mrt1StationTime6.get("เพชรบุรี");
			long airlinkTime6 = (Long) airlinkStationTime6.get(stop);
			long time6 = btsTime6 + mrtTime6 + airlinkTime6;
			jo6.put("time", time6);
			ja.add(jo6);
			// finish return
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);
		} else if (startLine == "Airlink" && stopLine == "BTS") { // airlink to bts
			count = 6;
			JSONObject airlink = rd.readFileObject(startLine);// get airlink json
			JSONObject mrt1 = rd.readFileObject("MRT");// get mrt1 json
			JSONObject bts = rd.readFileObject(stopLine);// get bts json
			JSONObject airlinkStation = (JSONObject) airlink.get(start);// get start station json
			JSONObject btsStation = (JSONObject) bts.get("พญาไท");
			// go through Phayathai
			long btsfare1 = (Long) btsStation.get(stop);
			long airlinkfare1 = (Long) airlinkStation.get("พญาไท");
			long fare1 = btsfare1 + airlinkfare1;
			String route1 = "ไปลงที่สถานี พญาไท จากนั้นเปลี่ยนไป bts";
			JSONObject jo1 = new JSONObject();
			jo1.put("fare", fare1);
			jo1.put("route", route1);
			// go through Makkasan1
			JSONObject mrt1Station2 = (JSONObject) mrt1.get("เพชรบุรี");
			long airlinkfare2 = (Long) airlinkStation.get("มักกะสัน");
			JSONObject btsStation2 = (JSONObject) bts.get("ศาลาแดง");
			long btsfare2 = (Long) btsStation2.get(stop);
			long mrt1fare2 = (Long) mrt1Station2.get("สีลม");
			long fare2 = btsfare2 + airlinkfare2 + mrt1fare2;
			String route2 = "ไปลงที่สถานี มักกะสัน จากนั้นเปลี่ยนไป mrt นั่งต่อไปจนถึงสถานี สีลม จากนั้นเปลี่ยนไป bts";
			JSONObject jo2 = new JSONObject();
			jo2.put("fare", fare2);
			jo2.put("route", route2);
			// go through Makkasan2
			JSONObject mrt1Station3 = (JSONObject) mrt1.get("เพชรบุรี");
			long airlinkfare3 = (Long) airlinkStation.get("มักกะสัน");
			JSONObject btsStation3 = (JSONObject) bts.get("อโศก");
			long btsfare3 = (Long) btsStation3.get(stop);
			long mrt1fare3 = (Long) mrt1Station3.get("สุขุมวิท");
			long fare3 = btsfare3 + airlinkfare3 + mrt1fare3;
			String route3 = "ไปลงที่สถานี มักกะสัน จากนั้นเปลี่ยนไป mrt นั่งต่อไปจนถึงสถานี สุขุมวิท จากนั้นเปลี่ยนไป bts";
			JSONObject jo3 = new JSONObject();
			jo3.put("fare", fare3);
			jo3.put("route", route3);
			// go through Makkasan3
			JSONObject mrt1Station4 = (JSONObject) mrt1.get("เพชรบุรี");
			long airlinkfare4 = (Long) airlinkStation.get("มักกะสัน");
			JSONObject btsStation4 = (JSONObject) bts.get("หมอชิต");
			long btsfare4 = (Long) btsStation4.get(stop);
			long mrt1fare4 = (Long) mrt1Station4.get("สวนจตุจักร");
			long fare4 = btsfare4 + airlinkfare4 + mrt1fare4;
			String route4 = "ไปลงที่สถานี มักกะสัน จากนั้นเปลี่ยนไป mrt นั่งต่อไปจนถึงสถานี สวนจตุจักร จากนั้นเปลี่ยนไป bts";
			JSONObject jo4 = new JSONObject();
			jo4.put("fare", fare4);
			jo4.put("route", route4);
			// go through Makkasan4
			JSONObject mrt1Station5 = (JSONObject) mrt1.get("เพชรบุรี");
			long airlinkfare5 = (Long) airlinkStation.get("มักกะสัน");
			JSONObject btsStation5 = (JSONObject) bts.get("ห้าแยกลาดพร้าว");
			long btsfare5 = (Long) btsStation5.get(stop);
			long mrt1fare5 = (Long) mrt1Station5.get("พหลโยธิน");
			long fare5 = btsfare5 + airlinkfare5 + mrt1fare5;
			String route5 = "ไปลงที่สถานี มักกะสัน จากนั้นเปลี่ยนไป mrt นั่งต่อไปจนถึงสถานี พหลโยธิน จากนั้นเปลี่ยนไป bts";
			JSONObject jo5 = new JSONObject();
			jo5.put("fare", fare5);
			jo5.put("route", route5);
			// go through Makkasan5
			JSONObject mrt1Station6 = (JSONObject) mrt1.get("เพชรบุรี");
			long airlinkfare6 = (Long) airlinkStation.get("มักกะสัน");
			JSONObject btsStation6 = (JSONObject) bts.get("บางหว้า");
			long btsfare6 = (Long) btsStation6.get(stop);
			long mrt1fare6 = (Long) mrt1Station6.get("บางหว้า");
			long fare6 = btsfare6 + airlinkfare6 + mrt1fare6;
			String route6 = "ไปลงที่สถานี มักกะสัน จากนั้นเปลี่ยนไป mrt นั่งต่อไปจนถึงสถานี บางหว้า จากนั้นเปลี่ยนไป bts";
			JSONObject jo6 = new JSONObject();
			jo6.put("fare", fare6);
			jo6.put("route", route6);
			// time
			JSONObject airlinkTime = rd.readFileObject(startLine + "_time");// get airlink json
			JSONObject mrt1Time = rd.readFileObject("MRT_time");// get mrt1 json
			JSONObject btsTime = rd.readFileObject(stopLine + "_time");// get bts json
			JSONObject airlinkStationTime = (JSONObject) airlinkTime.get(start);// get start station json
			JSONObject btsStationTime = (JSONObject) btsTime.get("พญาไท");
			// go through Phayathai
			long btsTime1 = (Long) btsStationTime.get(stop);
			long airlinkTime1 = (Long) airlinkStationTime.get("พญาไท");
			long time1 = btsTime1 + airlinkTime1;
			jo1.put("time", time1);
			ja.add(jo1);
			// go through Makkasan1
			JSONObject mrt1StationTime2 = (JSONObject) mrt1.get("เพชรบุรี");
			long airlinkTime2 = (Long) airlinkStationTime.get("มักกะสัน");
			JSONObject btsStationTime2 = (JSONObject) btsTime.get("ศาลาแดง");
			long btsTime2 = (Long) btsStationTime2.get(stop);
			long mrt1Time2 = (Long) mrt1StationTime2.get("สีลม");
			long time2 = btsTime2 + airlinkTime2 + mrt1Time2;
			jo2.put("time", time2);
			ja.add(jo2);
			// go through Makkasan2
			JSONObject mrt1StationTime3 = (JSONObject) mrt1.get("เพชรบุรี");
			long airlinkTime3 = (Long) airlinkStationTime.get("มักกะสัน");
			JSONObject btsStationTime3 = (JSONObject) btsTime.get("อโศก");
			long btsTime3 = (Long) btsStationTime3.get(stop);
			long mrt1Time3 = (Long) mrt1StationTime3.get("สุขุมวิท");
			long time3 = btsTime3 + airlinkTime3 + mrt1Time3;
			jo3.put("time", time3);
			ja.add(jo3);
			// go through Makkasan3
			JSONObject mrt1StationTime4 = (JSONObject) mrt1.get("เพชรบุรี");
			long airlinkTime4 = (Long) airlinkStationTime.get("มักกะสัน");
			JSONObject btsStationTime4 = (JSONObject) btsTime.get("หมอชิต");
			long btsTime4 = (Long) btsStationTime4.get(stop);
			long mrt1Time4 = (Long) mrt1StationTime4.get("สวนจตุจักร");
			long time4 = btsTime4 + airlinkTime4 + mrt1Time4;
			jo4.put("time", time4);
			ja.add(jo4);
			// go through Makkasan4
			JSONObject mrt1StationTime5 = (JSONObject) mrt1.get("เพชรบุรี");
			long airlinkTime5 = (Long) airlinkStationTime.get("มักกะสัน");
			JSONObject btsStationTime5 = (JSONObject) btsTime.get("ห้าแยกลาดพร้าว");
			long btsTime5 = (Long) btsStationTime5.get(stop);
			long mrt1Time5 = (Long) mrt1StationTime5.get("พหลโยธิน");
			long time5 = btsTime5 + airlinkTime5 + mrt1Time5;
			jo5.put("time", time5);
			ja.add(jo5);
			// go through Makkasan5
			JSONObject mrt1StationTime6 = (JSONObject) mrt1.get("เพชรบุรี");
			long airlinkTime6 = (Long) airlinkStationTime.get("มักกะสัน");
			JSONObject btsStationTime6 = (JSONObject) btsTime.get("บางหว้า");
			long btsTime6 = (Long) btsStationTime6.get(stop);
			long mrt1Time6 = (Long) mrt1StationTime6.get("บางหว้า");
			long time6 = btsTime6 + airlinkTime6 + mrt1Time6;
			jo6.put("time", time6);
			ja.add(jo6);
			// finish return
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);
		} else if (startLine == "Airlink" && stopLine == "MRT") { // airlink to mrt1
			count = 6;
			JSONObject airlink = rd.readFileObject(startLine);// get airlink json
			JSONObject bts = rd.readFileObject("BTS");// get bts json
			JSONObject mrt1 = rd.readFileObject(stopLine);// get mrt1 json
			JSONObject airlinkStation = (JSONObject) airlink.get(start);// get start station json
			JSONObject mrt1Station = (JSONObject) mrt1.get(stop);// get stop station json
			JSONObject btsStation = (JSONObject) bts.get("พญาไท");
			// go through Makkasan/PetchBuri
			long airlinkfare1 = (Long) airlinkStation.get("มักกะสัน");
			long mrt1fare1 = (Long) mrt1Station.get("เพชรบุรี");
			long fare1 = mrt1fare1 + airlinkfare1;
			String route1 = "ไปลงที่สถานี มักกะสัน จากนั้นเปลี่ยนไป mrt";
			JSONObject jo1 = new JSONObject();
			jo1.put("fare", fare1);
			jo1.put("route", route1);
			// go through Morchit/Jatujak
			long airlinkfare2 = (Long) airlinkStation.get("พญาไท");
			long btsfare2 = (Long) btsStation.get("หมอชิต");
			long mrt1fare2 = (Long) mrt1Station.get("สวนจตุจักร");
			long fare2 = btsfare2 + airlinkfare2 + mrt1fare2;
			String route2 = "ไปลงที่สถานี พญาไท จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี หมอชิต จากนั้นเปลี่ยนไป mrt";
			JSONObject jo2 = new JSONObject();
			jo2.put("fare", fare2);
			jo2.put("route", route2);
			// go through Makkasan2
			JSONObject mrt1Station3 = (JSONObject) mrt1.get("สุขุมวิท");
			long airlinkfare3 = (Long) airlinkStation.get("พญาไท");
			long btsfare3 = (Long) btsStation.get("อโศก");
			long mrt1fare3 = (Long) mrt1Station3.get(stop);
			long fare3 = btsfare3 + airlinkfare3 + mrt1fare3;
			String route3 = "ไปลงที่สถานี พญาไท จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี อโศก จากนั้นเปลี่ยนไป mrt";
			JSONObject jo3 = new JSONObject();
			jo3.put("fare", fare3);
			jo3.put("route", route3);
			// go through Makkasan3
			JSONObject mrt1Station4 = (JSONObject) mrt1.get("สีลม");
			long btsfare4 = (Long) btsStation.get("ศาลาแดง");
			long mrt1fare4 = (Long) mrt1Station4.get(stop);
			long airlinkfare4 = (Long) airlinkStation.get("พญาไท");
			long fare4 = btsfare4 + mrt1fare4 + airlinkfare4;
			String route4 = "ไปลงที่สถานี พญาไท จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี ศาลาแดง จากนั้นเปลี่ยนไป mrt";
			JSONObject jo4 = new JSONObject();
			jo4.put("fare", fare4);
			jo4.put("route", route4);
			// go through Makkasan4
			JSONObject mrt1Station5 = (JSONObject) mrt1.get("พหลโยธิน");
			long airlinkfare5 = (Long) airlinkStation.get("พญาไท");
			long btsfare5 = (Long) btsStation.get("ห้าแยกลาดพร้าว");
			long mrt1fare5 = (Long) mrt1Station5.get(stop);
			long fare5 = btsfare5 + airlinkfare5 + mrt1fare5;
			String route5 = "ไปลงที่สถานี พญาไท จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี ห้าแยกลาดพร้าว จากนั้นเปลี่ยนไป mrt";
			JSONObject jo5 = new JSONObject();
			jo5.put("fare", fare5);
			jo5.put("route", route5);
			// go through Makkasan5
			JSONObject mrt1Station6 = (JSONObject) mrt1.get("บางหว้า");
			long airlinkfare6 = (Long) airlinkStation.get("พญาไท");
			long btsfare6 = (Long) btsStation.get("บางหว้า");
			long mrt1fare6 = (Long) mrt1Station6.get(stop);
			long fare6 = btsfare6 + airlinkfare6 + mrt1fare6;
			String route6 = "ไปลงที่สถานี พญาไท จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี บางหว้า จากนั้นเปลี่ยนไป mrt";
			JSONObject jo6 = new JSONObject();
			jo6.put("fare", fare6);
			jo6.put("route", route6);
			// time
			JSONObject airlinkTime = rd.readFileObject(startLine + "_time");// get airlink json
			JSONObject btsTime = rd.readFileObject("BTS_time");// get bts json
			JSONObject mrt1Time = rd.readFileObject(stopLine + "_time");// get mrt1 json
			JSONObject airlinkStationTime = (JSONObject) airlinkTime.get(start);// get start station json
			JSONObject mrt1StationTime = (JSONObject) mrt1Time.get(stop);// get stop station json
			JSONObject btsStationTime = (JSONObject) btsTime.get("พญาไท");
			// go through Makkasan/PetchBuri
			long airlinkTime1 = (Long) airlinkStationTime.get("มักกะสัน");
			long mrt1Time1 = (Long) mrt1StationTime.get("เพชรบุรี");
			long time1 = mrt1Time1 + airlinkTime1;
			jo1.put("time", time1);
			ja.add(jo1);
			// go through Morchit/Jatujak
			long airlinkTime2 = (Long) airlinkStationTime.get("พญาไท");
			long btsTime2 = (Long) btsStationTime.get("หมอชิต");
			long mrt1Time2 = (Long) mrt1StationTime.get("สวนจตุจักร");
			long time2 = btsTime2 + airlinkTime2 + mrt1Time2;
			jo2.put("time", time2);
			ja.add(jo2);
			// go through Makkasan2
			JSONObject mrt1StationTime3 = (JSONObject) mrt1Time.get("สุขุมวิท");
			long airlinkTime3 = (Long) airlinkStationTime.get("พญาไท");
			long btsTime3 = (Long) btsStationTime.get("อโศก");
			long mrt1Time3 = (Long) mrt1StationTime3.get(stop);
			long time3 = btsTime3 + airlinkTime3 + mrt1Time3;
			jo3.put("time", time3);
			ja.add(jo3);
			// go through Makkasan3
			JSONObject mrt1StationTime4 = (JSONObject) mrt1Time.get("สีลม");
			long btsTime4 = (Long) btsStationTime.get("ศาลาแดง");
			long mrt1Time4 = (Long) mrt1StationTime4.get(stop);
			long airlinkTime4 = (Long) airlinkStationTime.get("พญาไท");
			long time4 = btsTime4 + mrt1Time4 + airlinkTime4;
			jo4.put("time", time4);
			ja.add(jo4);
			// go through Makkasan4
			JSONObject mrt1StationTime5 = (JSONObject) mrt1Time.get("พหลโยธิน");
			long btsTime5 = (Long) btsStationTime.get("ห้าแยกลาดพร้าว");
			long mrt1Time5 = (Long) mrt1StationTime5.get(stop);
			long airlinkTime5 = (Long) airlinkStationTime.get("พญาไท");
			long time5 = btsTime5 + mrt1Time5 + airlinkTime5;
			jo5.put("time", time5);
			ja.add(jo5);
			// go through Makkasan5
			JSONObject mrt1StationTime6 = (JSONObject) mrt1Time.get("บางหว้า");
			long btsTime6 = (Long) btsStationTime.get("บางหว้า");
			long mrt1Time6 = (Long) mrt1StationTime6.get(stop);
			long airlinkTime6 = (Long) airlinkStationTime.get("พญาไท");
			long time6 = btsTime6 + mrt1Time6 + airlinkTime6;
			jo6.put("time", time6);
			ja.add(jo6);
			// finish return
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);
		} else if (startLine == "MRT" && stopLine == "Airlink") { // mrt1 to airlink
			count = 4;
			JSONObject mrt1 = rd.readFileObject(startLine);// get mrt1 json
			JSONObject bts = rd.readFileObject("BTS");// get bts json
			JSONObject airlink = rd.readFileObject(stopLine);// get airlink json
			JSONObject mrt1Station = (JSONObject) mrt1.get(start);// get start station json
			JSONObject airlinkStation = (JSONObject) airlink.get(stop);// get stop station json
			JSONObject btsStation = (JSONObject) bts.get("พญาไท");
			// go through Sukumwit
			long mrt1fare1 = (Long) mrt1Station.get("สุขุมวิท");
			long btsfare1 = (Long) btsStation.get("อโศก");
			long airlinkfare1 = (Long) airlinkStation.get("พญาไท");
			long fare1 = mrt1fare1 + btsfare1 + airlinkfare1;
			String route1 = "ไปลงที่สถานี สุขุมวิท จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink";
			JSONObject jo1 = new JSONObject();
			jo1.put("fare", fare1);
			jo1.put("route", route1);
			// go through Sealom
			long mrt1fare2 = (Long) mrt1Station.get("สีลม");
			long btsfare2 = (Long) btsStation.get("ศาลาแดง");
			long airlinkfare2 = (Long) airlinkStation.get("พญาไท");
			long fare2 = mrt1fare2 + btsfare2 + airlinkfare2;
			String route2 = "ไปลงที่สถานี สีลม จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink";
			JSONObject jo2 = new JSONObject();
			jo2.put("fare", fare2);
			jo2.put("route", route2);
			// go through jatujak
			long mrt1fare3 = (Long) mrt1Station.get("สวนจตุจักร");
			long btsfare3 = (Long) btsStation.get("หมอชิต");
			long airlinkfare3 = (Long) airlinkStation.get("พญาไท");
			long fare3 = mrt1fare3 + btsfare3 + airlinkfare3;
			String route3 = "ไปลงที่สถานี สวนจตุจักร จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink";
			JSONObject jo3 = new JSONObject();
			jo3.put("fare", fare3);
			jo3.put("route", route3);
			// go through Makkasan3
			long airlinkfare4 = (Long) airlinkStation.get("มักกะสัน");
			long mrt1fare4 = (Long) mrt1Station.get("เพชรบุรี");
			long fare4 = airlinkfare4 + mrt1fare4;
			String route4 = "ไปลงสถานี เพชรบุรี จากนั้นเปลี่ยนไป airportlink";
			JSONObject jo4 = new JSONObject();
			jo4.put("fare", fare4);
			jo4.put("route", route4);
			// go through pahonyotin
			long mrt1fare5 = (Long) mrt1Station.get("พหลโยธิน");
			long btsfare5 = (Long) btsStation.get("ห้าแยกลาดพร้าว");
			long airlinkfare5 = (Long) airlinkStation.get("พญาไท");
			long fare5 = mrt1fare5 + btsfare5 + airlinkfare5;
			String route5 = "ไปลงที่สถานี พหลโยธิน จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink";
			JSONObject jo5 = new JSONObject();
			jo5.put("fare", fare5);
			jo5.put("route", route5);
			// go through pahonyotin
			long mrt1fare6 = (Long) mrt1Station.get("บางหว้า");
			long btsfare6 = (Long) btsStation.get("บางหว้า");
			long airlinkfare6 = (Long) airlinkStation.get("พญาไท");
			long fare6 = mrt1fare6 + btsfare6 + airlinkfare6;
			String route6 = "ไปลงที่สถานี บางหว้า จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink";
			JSONObject jo6 = new JSONObject();
			jo6.put("fare", fare6);
			jo6.put("route", route6);
			// time
			JSONObject mrt1Time = rd.readFileObject(startLine + "_time");// get mrt1 json
			JSONObject btsTime = rd.readFileObject("BTS_time");// get bts json
			JSONObject airlinkTime = rd.readFileObject(stopLine + "_time");// get airlink json
			JSONObject mrt1StationTime = (JSONObject) mrt1Time.get(start);// get start station json
			JSONObject airlinkStationTime = (JSONObject) airlinkTime.get(stop);// get stop station json
			JSONObject btsStationTime = (JSONObject) btsTime.get("พญาไท");
			// go through Sukumwit
			long mrt1Time1 = (Long) mrt1StationTime.get("สุขุมวิท");
			long btsTime1 = (Long) btsStationTime.get("อโศก");
			long airlinkTime1 = (Long) airlinkStationTime.get("พญาไท");
			long time1 = mrt1Time1 + btsTime1 + airlinkTime1;
			jo1.put("time", time1);
			ja.add(jo1);
			// go through Sealom
			long mrt1Time2 = (Long) mrt1StationTime.get("สีลม");
			long btsTime2 = (Long) btsStationTime.get("ศาลาแดง");
			long airlinkTime2 = (Long) airlinkStationTime.get("พญาไท");
			long time2 = mrt1Time2 + btsTime2 + airlinkTime2;
			jo2.put("time", time2);
			ja.add(jo2);
			// go through jatujak
			long mrt1Time3 = (Long) mrt1StationTime.get("สวนจตุจักร");
			long btsTime3 = (Long) btsStationTime.get("หมอชิต");
			long airlinkTime3 = (Long) airlinkStationTime.get("พญาไท");
			long time3 = mrt1Time3 + btsTime3 + airlinkTime3;
			jo3.put("time", time3);
			ja.add(jo3);
			// go through Makkasan3
			long airlinkTime4 = (Long) airlinkStationTime.get("มักกะสัน");
			long mrt1Time4 = (Long) mrt1StationTime.get("เพชรบุรี");
			long time4 = airlinkTime4 + mrt1Time4;
			jo4.put("time", time4);
			ja.add(jo4);
			// go through pahonyotin
			long mrt1Time5 = (Long) mrt1StationTime.get("พหลโยธิน");
			long btsTime5 = (Long) btsStationTime.get("ห้าแยกลาดพร้าว");
			long airlinkTime5 = (Long) airlinkStationTime.get("พญาไท");
			long time5 = mrt1Time5 + btsTime5 + airlinkTime5;
			jo5.put("time", time5);
			ja.add(jo5);
			// go through Bang wa
			long mrt1Time6 = (Long) mrt1StationTime.get("บางหว้า");
			long btsTime6 = (Long) btsStationTime.get("บางหว้า");
			long airlinkTime6 = (Long) airlinkStationTime.get("พญาไท");
			long time6 = mrt1Time6 + btsTime6 + airlinkTime6;
			jo6.put("time", time6);
			ja.add(jo6);
			// finish return
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);
		}

		return jo;
	}

	public JSONObject lowestFare(JSONObject routes) {
		JSONArray keep = (JSONArray) routes.get("routes");
		JSONObject check = new JSONObject();
		long fare = 1000000;
		int possibleRoute = (Integer) routes.get("possibleRoutes");
		int i = 0;
		while (i < possibleRoute) {
			JSONObject check2 = (JSONObject) keep.get(i);
			System.out.println(check2);
			if (fare > (Long) check2.get("fare")) {
				check = check2;
				fare = (Long) check2.get("fare");
			}
			i += 1;
		}
		return check;
	}

	public JSONObject lowestTime(JSONObject routes) {
		JSONArray keep = (JSONArray) routes.get("routes");
		JSONObject check = new JSONObject();
		long time = 1000000;
		int possibleRoute = (Integer) routes.get("possibleRoutes");
		int i = 0;
		while (i < possibleRoute) {
			JSONObject check2 = (JSONObject) keep.get(i);
			System.out.println(check2);
			if (time > (Long) check2.get("time")) {
				check = check2;
				time = (Long) check2.get("time");
			}
			i += 1;
		}
		return check;
	}

}

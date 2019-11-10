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
		if (airlink.get(station) != null) {
			return line = "Airlink";
		} else if (bts.get(station) != null) {
			return line = "BTS";
		} else if (mrt1.get(station) != null) {
			return line = "MRT1";
		} else if (mrt2.get(station) != null) {
			return line = "MRT2";
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
		} else if (startLine == "MRT1" && stopLine == "MRT2") { // mrt1 to mrt2
			JSONObject mrt1 = rd.readFileObject(startLine);
			JSONObject mrt1Time = rd.readFileObject(startLine + "_time");
			JSONObject mrt2 = rd.readFileObject(stopLine);
			JSONObject mrt2Time = rd.readFileObject(stopLine + "_time");
			JSONObject mrt1Station = (JSONObject) mrt1.get(start);
			JSONObject mrt1StationTime = (JSONObject) mrt1Time.get(start);
			JSONObject mrt2Station = (JSONObject) mrt2.get("เตาปูน");
			JSONObject mrt2StationTime = (JSONObject) mrt2Time.get("เตาปูน");
			long mrt1fare = (Long) mrt1Station.get("เตาปูน");
			long mrt2fare = (Long) mrt2Station.get(stop);
			long mrt1timing = (Long) mrt1StationTime.get("เตาปูน");
			long mrt2timing = (Long) mrt2StationTime.get(stop);
			long fare1 = mrt1fare + mrt2fare;
			long time1 = mrt2timing + mrt1timing;
			String route1 = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีม่วง";
			count = 1;
			JSONObject jo1 = new JSONObject();
			jo1.put("fare", fare1);
			jo1.put("route", route1);
			jo1.put("time", time1);
			ja.add(jo1);
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);
		} else if (startLine == "MRT2" && stopLine == "MRT1") { // mrt2 to mrt1
			JSONObject mrt1 = rd.readFileObject(stopLine);
			JSONObject mrt1Time = rd.readFileObject(stopLine + "_time");
			JSONObject mrt2 = rd.readFileObject(startLine);
			JSONObject mrt2Time = rd.readFileObject(startLine + "_time");
			JSONObject mrt1Station = (JSONObject) mrt1.get("เตาปูน");
			JSONObject mrt1StationTime = (JSONObject) mrt1Time.get("เตาปูน");
			JSONObject mrt2Station = (JSONObject) mrt2.get(start);
			JSONObject mrt2StationTime = (JSONObject) mrt2Time.get(start);
			long mrt1fare = (Long) mrt1Station.get(stop);
			long mrt1timing = (Long) mrt1StationTime.get(stop);
			long mrt2fare = (Long) mrt2Station.get("เตาปูน");
			long mrt2timing = (Long) mrt2StationTime.get("เตาปูน");
			long fare1 = mrt1fare + mrt2fare;
			long time1 = mrt1timing + mrt2timing;
			String route1 = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีฟ้า";
			count = 1;
			JSONObject jo1 = new JSONObject();
			jo1.put("fare", fare1);
			jo1.put("route", route1);
			jo1.put("time", time1);
			ja.add(jo1);
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);
		} else if (startLine == "BTS" && stopLine == "MRT1") { // bts to mrt1
			count = 4;
			JSONObject bts = rd.readFileObject(startLine);// get bts json
			JSONObject mrt1 = rd.readFileObject(stopLine);// get mrt1 json
			JSONObject airlink = rd.readFileObject("Airlink");// get airlink json
			JSONObject btsStation = (JSONObject) bts.get(start);// get start staion json
			JSONObject airlinkStation = (JSONObject) airlink.get("พญาไท");
			// go through Asok
			long btsfare1 = (Long) btsStation.get("อโศก");
			JSONObject mrt1Station1 = (JSONObject) mrt1.get("สุขุมวิท");
			long mrtfare1 = (Long) mrt1Station1.get(stop);
			long fare1 = btsfare1 + mrtfare1;
			String route1 = "ไปลงที่สถานี อโศก จากนั้นเปลี่ยนไป mrt";
			JSONObject jo1 = new JSONObject();
			jo1.put("fare", fare1);
			jo1.put("route", route1);
			// go through Sala deang
			long btsfare2 = (Long) btsStation.get("ศาลาแดง");
			JSONObject mrt1Station2 = (JSONObject) mrt1.get("สีลม");
			long mrtfare2 = (Long) mrt1Station2.get(stop);
			long fare2 = btsfare2 + mrtfare2;
			String route2 = "ไปลงที่สถานี ศาลาแดง จากนั้นเปลี่ยนไป mrt";
			JSONObject jo2 = new JSONObject();
			jo2.put("fare", fare2);
			jo2.put("route", route2);
			// go through Mo chit
			long btsfare3 = (Long) btsStation.get("หมอชิต");
			JSONObject mrt1Station3 = (JSONObject) mrt1.get("สวนจตุจักร");
			long mrtfare3 = (Long) mrt1Station3.get(stop);
			long fare3 = btsfare3 + mrtfare3;
			String route3 = "ไปลงที่สถานี หมอชิต จากนั้นเปลี่ยนไป mrt";
			JSONObject jo3 = new JSONObject();
			jo3.put("fare", fare3);
			jo3.put("route", route3);
			// go through Airlink
			long btsfare4 = (Long) btsStation.get("พญาไท");
			JSONObject mrt1Station4 = (JSONObject) mrt1.get("เพชรบุรี");
			long mrtfare4 = (Long) mrt1Station3.get(stop);
			long airlinkfare = (Long) airlinkStation.get("มักกะสัน");
			long fare4 = btsfare4 + mrtfare4 + airlinkfare;
			String route4 = "ไปลงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink นั่งต่อไปจนถึงสถานี มักกะสัน จากนั้นเปลี่ยนไป mrt";
			JSONObject jo4 = new JSONObject();
			jo4.put("fare", fare4);
			jo4.put("route", route4);
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
			long mrtTime4 = (Long) mrt1StationTime3.get(stop);
			long airlinkTime1 = (Long) airlinkStationTime.get("มักกะสัน");
			long time4 = btsTime4 + mrtTime4 + airlinkTime1;
			jo4.put("time", time4);
			ja.add(jo4);
			// finish return
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);
		} else if (startLine == "MRT1" && stopLine == "BTS") { // mrt1 to bts
			count = 4;
			JSONObject mrt1 = rd.readFileObject(startLine);// get mrt1 json
			JSONObject bts = rd.readFileObject(stopLine);// get bts json
			JSONObject airlink = rd.readFileObject("Airlink");// get airlink json
			JSONObject mrt1Station = (JSONObject) mrt1.get(start);// get start staion json
			JSONObject airlinkStation = (JSONObject) airlink.get("พญาไท");// get start staion json
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
			// finish return
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);

		} else if (startLine == "BTS" && stopLine == "Airlink") { // bts to airlink
			count = 4;
			JSONObject bts = rd.readFileObject(startLine);// get bts json
			JSONObject mrt1 = rd.readFileObject("MRT1");// get mrt1 json
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
			// time
			JSONObject btsTime = rd.readFileObject(startLine + "_time");// get bts json
			JSONObject mrt1Time = rd.readFileObject("MRT1_time");// get mrt1 json
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
			// finish return
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);
		} else if (startLine == "Airlink" && stopLine == "BTS") { // airlink to bts
			count = 4;
			JSONObject airlink = rd.readFileObject(startLine);// get airlink json
			JSONObject mrt1 = rd.readFileObject("MRT1");// get mrt1 json
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
			// time
			JSONObject airlinkTime = rd.readFileObject(startLine + "_time");// get airlink json
			JSONObject mrt1Time = rd.readFileObject("MRT1_time");// get mrt1 json
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
			// finish return
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);
		} else if (startLine == "Airlink" && stopLine == "MRT1") { // airlink to mrt1
			count = 4;
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
			// finish return
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);
		} else if (startLine == "MRT1" && stopLine == "Airlink") { // mrt1 to airlink
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
			// finish return
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);
		} else if (startLine == "BTS" && stopLine == "MRT2") { // bts to mrt2
			count = 4;
			JSONObject bts = rd.readFileObject(startLine);// get bts json
			JSONObject mrt1 = rd.readFileObject("MRT1");// get mrt1 json
			JSONObject airlink = rd.readFileObject("Airlink");// get airlink json
			JSONObject btsStation = (JSONObject) bts.get(start);// get start station json
			JSONObject airlinkStation = (JSONObject) airlink.get("พญาไท");
			JSONObject mrt2 = rd.readFileObject(stopLine);// get mrt2 json
			JSONObject mrt2Station = (JSONObject) mrt2.get("เตาปูน");
			long mrt2fare = (Long) mrt2Station.get(stop);
			// go through Asok
			long btsfare1 = (Long) btsStation.get("อโศก");
			JSONObject mrt1Station1 = (JSONObject) mrt1.get("สุขุมวิท");
			long mrtfare1 = (Long) mrt1Station1.get("เตาปูน");
			long mrt2fare1 = (Long) mrt2Station.get(stop);
			long fare1 = btsfare1 + mrtfare1 + mrt2fare1;
			String route1 = "ไปลงที่สถานี อโศก จากนั้นเปลี่ยนไป mrt เมื่อถึง เตาปูน ให้เปลี่ยนไปสายสีม่วง";
			JSONObject jo1 = new JSONObject();
			jo1.put("fare", fare1);
			jo1.put("route", route1);
			// go through Sala deang
			long btsfare2 = (Long) btsStation.get("ศาลาแดง");
			JSONObject mrt1Station2 = (JSONObject) mrt1.get("สีลม");
			long mrtfare2 = (Long) mrt1Station2.get("เตาปูน");
			long mrt2fare2 = (Long) mrt2Station.get(stop);
			long fare2 = btsfare2 + mrtfare2 + mrt2fare2;
			String route2 = "ไปลงที่สถานี ศาลาแดง จากนั้นเปลี่ยนไป mrt เมื่อถึง เตาปูน ให้เปลี่ยนไปสายสีม่วง";
			JSONObject jo2 = new JSONObject();
			jo2.put("fare", fare2);
			jo2.put("route", route2);
			// go through Mo chit
			long btsfare3 = (Long) btsStation.get("หมอชิต");
			JSONObject mrt1Station3 = (JSONObject) mrt1.get("สวนจตุจักร");
			long mrtfare3 = (Long) mrt1Station3.get("เตาปูน");
			long mrt2fare3 = (Long) mrt2Station.get(stop);
			long fare3 = btsfare3 + mrtfare3 + mrt2fare3;
			String route3 = "ไปลงที่สถานี หมอชิต จากนั้นเปลี่ยนไป mrt เมื่อถึง เตาปูน ให้เปลี่ยนไปสายสีม่วง";
			JSONObject jo3 = new JSONObject();
			jo3.put("fare", fare3);
			jo3.put("route", route3);
			// go through Airlink
			long btsfare4 = (Long) btsStation.get("พญาไท");
			JSONObject mrt1Station4 = (JSONObject) mrt1.get("เพชรบุรี");
			long mrtfare4 = (Long) mrt1Station4.get("เตาปูน");
			long mrt2fare4 = (Long) mrt2Station.get(stop);
			long airlinkfare = (Long) airlinkStation.get("มักกะสัน");
			long fare4 = btsfare4 + mrtfare4 + airlinkfare + mrt2fare4;
			String route4 = "ไปลงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink นั่งต่อไปจนถึงสถานี มักกะสัน จากนั้นเปลี่ยนไป mrt เมื่อถึง เตาปูน ให้เปลี่ยนไปสายสีม่วง";
			JSONObject jo4 = new JSONObject();
			jo4.put("fare", fare4);
			jo4.put("route", route4);
			// time
			JSONObject btsTime = rd.readFileObject(startLine + "_time");// get bts json
			JSONObject mrt1Time = rd.readFileObject("MRT1_time");// get mrt1 json
			JSONObject airlinkTime = rd.readFileObject("Airlink_time");// get airlink json
			JSONObject btsStationTime = (JSONObject) btsTime.get(start);// get start station json
			JSONObject airlinkStationTime = (JSONObject) airlinkTime.get("พญาไท");
			JSONObject mrt2Timing = rd.readFileObject(stopLine + "_time");// get mrt2 json
			JSONObject mrt2StationTime = (JSONObject) mrt2Timing.get("เตาปูน");
			long mrt2Time = (Long) mrt2StationTime.get(stop);
			// go through Asok
			long btsTime1 = (Long) btsStationTime.get("อโศก");
			JSONObject mrt1StationTime1 = (JSONObject) mrt1Time.get("สุขุมวิท");
			long mrtTime1 = (Long) mrt1StationTime1.get("เตาปูน");
			long time1 = btsTime1 + mrtTime1 + mrt2Time;
			jo1.put("time", time1);
			ja.add(jo1);
			// go through Sala deang
			long btsTime2 = (Long) btsStationTime.get("ศาลาแดง");
			JSONObject mrt1StationTime2 = (JSONObject) mrt1Time.get("สีลม");
			long mrtTime2 = (Long) mrt1StationTime2.get("เตาปูน");
			long time2 = btsTime2 + mrtTime2 + mrt2Time;
			jo2.put("time", time2);
			ja.add(jo2);
			// go through Mo chit
			long btsTime3 = (Long) btsStationTime.get("หมอชิต");
			JSONObject mrt1StationTime3 = (JSONObject) mrt1Time.get("สวนจตุจักร");
			long mrtTime3 = (Long) mrt1StationTime3.get("เตาปูน");
			long time3 = btsTime3 + mrtTime3 + mrt2Time;
			jo3.put("time", time3);
			ja.add(jo3);
			// go through Airlink
			long btsTime4 = (Long) btsStationTime.get("พญาไท");
			JSONObject mrt1StationTime4 = (JSONObject) mrt1Time.get("เพชรบุรี");
			long mrtTime4 = (Long) mrt1StationTime4.get("เตาปูน");
			long airlinkTime1 = (Long) airlinkStationTime.get("มักกะสัน");
			long time4 = btsTime4 + mrtTime4 + airlinkTime1 + mrt2Time;
			jo4.put("time", time4);
			ja.add(jo4);
			// finish return
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);
		} else if (startLine == "MRT2" && stopLine == "BTS") { // mrt2 to bts
			count = 4;
			JSONObject mrt2 = rd.readFileObject(startLine);// get mrt2 json
			JSONObject mrt2Station = (JSONObject) mrt2.get(start);// get start station
			long mrt2fare = (Long) mrt2Station.get("เตาปูน");
			JSONObject mrt1 = rd.readFileObject("MRT1");// get mrt1 json
			JSONObject bts = rd.readFileObject(stopLine);// get bts json
			JSONObject airlink = rd.readFileObject("Airlink");// get airlink json
			JSONObject mrt1Station = (JSONObject) mrt1.get("เตาปูน");// get start station json
			JSONObject airlinkStation = (JSONObject) airlink.get("พญาไท");// get start station json
			JSONObject btsStation = (JSONObject) bts.get("พญาไท");
			// go through Sukumwit
			///////
			long mrtfare1 = (Long) mrt1Station.get("สุขุมวิท");
			JSONObject btsStation1 = (JSONObject) bts.get("อโศก");
			long btsfare1 = (Long) btsStation1.get(stop);
			long fare1 = btsfare1 + mrtfare1 + mrt2fare;
			String route1 = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีฟ้า นั่งต่อไปจนถึงสถานี สุขุมวิท จากนั้นเปลี่ยนไป bts";
			JSONObject jo1 = new JSONObject();
			jo1.put("fare", fare1);
			jo1.put("route", route1);
			// go through Sala deang
			long mrtfare2 = (Long) mrt1Station.get("สีลม");
			JSONObject btsStation2 = (JSONObject) bts.get("ศาลาแดง");
			long btsfare2 = (Long) btsStation2.get(stop);
			long fare2 = btsfare2 + mrtfare2 + mrt2fare;
			String route2 = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีฟ้า นั่งต่อไปจนถึงสถานี สีลม จากนั้นเปลี่ยนไป bts";
			JSONObject jo2 = new JSONObject();
			jo2.put("fare", fare2);
			jo2.put("route", route2);
			// go through Jatujak
			long mrtfare3 = (Long) mrt1Station.get("สวนจตุจักร");
			JSONObject btsStation3 = (JSONObject) bts.get("หมอชิต");
			long btsfare3 = (Long) btsStation3.get(stop);
			long fare3 = btsfare3 + mrtfare3 + mrt2fare;
			String route3 = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีฟ้า นั่งต่อไปจนถึงสถานี สวนจตุจักร จากนั้นเปลี่ยนไป bts";
			JSONObject jo3 = new JSONObject();
			jo3.put("fare", fare3);
			jo3.put("route", route3);
			// go through Airlink
			long mrtfare4 = (Long) mrt1Station.get("เพชรบุรี");
			JSONObject btsStation4 = (JSONObject) bts.get("พญาไท");
			long btsfare4 = (Long) btsStation4.get(stop);
			long airlinkfare = (Long) airlinkStation.get("มักกะสัน");
			long fare4 = btsfare4 + mrtfare4 + airlinkfare + mrt2fare;
			String route4 = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีฟ้า นั่งต่อไปจนถึงสถานี เพชรบุรี จากนั้นเปลี่ยนไป airportlink นั่งต่อไปจนถึงสถานี พญาไท จากนั้นเปลี่ยนไป bts";
			JSONObject jo4 = new JSONObject();
			jo4.put("fare", fare4);
			jo4.put("route", route4);
			// time
			JSONObject mrt2Time = rd.readFileObject(startLine + "_time");// get mrt2 json
			JSONObject mrt2StationTime = (JSONObject) mrt2Time.get(start);// get start station
			long mrt2Time1 = (Long) mrt2StationTime.get("เตาปูน");
			JSONObject mrt1Time = rd.readFileObject("MRT1_time");// get mrt1 json
			JSONObject btsTime = rd.readFileObject(stopLine + "_time");// get bts json
			JSONObject airlinkTime = rd.readFileObject("Airlink_time");// get airlink json
			JSONObject mrt1StationTime = (JSONObject) mrt1Time.get("เตาปูน");// get start staion json
			JSONObject airlinkStationTime = (JSONObject) airlinkTime.get("พญาไท");// get start staion json
			JSONObject btsStationTime = (JSONObject) btsTime.get("พญาไท");
			// go through Sukumwit
			///////
			long mrtTime1 = (Long) mrt1StationTime.get("สุขุมวิท");
			JSONObject btsStationTime1 = (JSONObject) btsTime.get("อโศก");
			long btsTime1 = (Long) btsStationTime1.get(stop);
			long time1 = btsTime1 + mrtTime1 + mrt2Time1;
			jo1.put("time", time1);
			ja.add(jo1);
			// go through Sala deang
			long mrtTime2 = (Long) mrt1StationTime.get("สีลม");
			JSONObject btsStationTime2 = (JSONObject) btsTime.get("ศาลาแดง");
			long btsTime2 = (Long) btsStationTime2.get(stop);
			long time2 = btsTime2 + mrtTime2 + mrt2Time1;
			jo2.put("time", time2);
			ja.add(jo2);
			// go through Jatujak
			long mrtTime3 = (Long) mrt1StationTime.get("สวนจตุจักร");
			JSONObject btsStationTime3 = (JSONObject) btsTime.get("หมอชิต");
			long btsTime3 = (Long) btsStationTime3.get(stop);
			long time3 = btsTime3 + mrtTime3 + mrt2Time1;
			jo3.put("time", time3);
			ja.add(jo3);
			// go through Airlink
			long mrtTime4 = (Long) mrt1StationTime.get("เพชรบุรี");
			JSONObject btsStationTime4 = (JSONObject) btsTime.get("พญาไท");
			long btsTime4 = (Long) btsStationTime4.get(stop);
			long airlinkTime1 = (Long) airlinkStationTime.get("มักกะสัน");
			long time4 = btsTime4 + mrtTime4 + airlinkTime1 + mrt2Time1;
			jo4.put("time", time4);
			ja.add(jo4);
			// finish return
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);

		} else if (startLine == "Airlink" && stopLine == "MRT2") { // airlink to mrt2
			count = 4;
			JSONObject mrt2 = rd.readFileObject(stopLine);// get mrt2 json
			JSONObject airlink = rd.readFileObject(startLine);// get airlink json
			JSONObject bts = rd.readFileObject("BTS");// get bts json
			JSONObject mrt1 = rd.readFileObject("MRT1");// get mrt1 json
			JSONObject airlinkStation = (JSONObject) airlink.get(start);// get start station json
			JSONObject mrt2Station = (JSONObject) mrt2.get("เตาปูน");
			long mrt2fare = (Long) mrt2Station.get(stop);// get stop station fee
			JSONObject mrt1Station = (JSONObject) mrt1.get("เตาปูน");
			JSONObject btsStation = (JSONObject) bts.get("พญาไท");
			// go through Makkasan/PetchBuri
			long airlinkfare1 = (Long) airlinkStation.get("มักกะสัน");
			long mrt1fare1 = (Long) mrt1Station.get("เพชรบุรี");
			long fare1 = mrt1fare1 + airlinkfare1 + mrt2fare;
			String route1 = "ไปลงที่สถานี มักกะสัน จากนั้นเปลี่ยนไป mrt นั่งไปต่อจนถึงสถานี เตาปูน จากนั้นเปลี่ยนไป สายสีม่วง";
			JSONObject jo1 = new JSONObject();
			jo1.put("fare", fare1);
			jo1.put("route", route1);
			// go through Morchit/Jatujak
			long airlinkfare2 = (Long) airlinkStation.get("พญาไท");
			long btsfare2 = (Long) btsStation.get("หมอชิต");
			long mrt1fare2 = (Long) mrt1Station.get("สวนจตุจักร");
			long fare2 = btsfare2 + airlinkfare2 + mrt1fare2 + mrt2fare;
			String route2 = "ไปลงที่สถานี พญาไท จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี หมอชิต จากนั้นเปลี่ยนไป mrt นั่งไปต่อจนถึงสถานี เตาปูน จากนั้นเปลี่ยนไป สายสีม่วง";
			JSONObject jo2 = new JSONObject();
			jo2.put("fare", fare2);
			jo2.put("route", route2);
			// go through Makkasan2
			JSONObject mrt1Station3 = (JSONObject) mrt1.get("สุขุมวิท");
			long airlinkfare3 = (Long) airlinkStation.get("พญาไท");
			long btsfare3 = (Long) btsStation.get("อโศก");
			long mrt1fare3 = (Long) mrt1Station3.get("เตาปูน");
			long fare3 = btsfare3 + airlinkfare3 + mrt1fare3 + mrt2fare;
			String route3 = "ไปลงที่สถานี พญาไท จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี อโศก จากนั้นเปลี่ยนไป mrt นั่งไปต่อจนถึงสถานี เตาปูน จากนั้นเปลี่ยนไป สายสีม่วง";
			JSONObject jo3 = new JSONObject();
			jo3.put("fare", fare3);
			jo3.put("route", route3);
			// go through Makkasan3
			long airlinkfare4 = (Long) airlinkStation.get("พญาไท");
			JSONObject mrt1Station4 = (JSONObject) mrt1.get("สีลม");
			long btsfare4 = (Long) btsStation.get("ศาลาแดง");
			long mrt1fare4 = (Long) mrt1Station4.get("เตาปูน");
			long fare4 = btsfare4 + mrt1fare4 + mrt2fare + airlinkfare4;
			String route4 = "ไปลงที่สถานี พญาไท จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี ศาลาแดง จากนั้นเปลี่ยนไป mrt นั่งไปต่อจนถึงสถานี เตาปูน จากนั้นเปลี่ยนไป สายสีม่วง";
			JSONObject jo4 = new JSONObject();
			jo4.put("fare", fare4);
			jo4.put("route", route4);
			// time
			JSONObject mrt2Time = rd.readFileObject(stopLine + "_time");// get mrt2 json
			JSONObject airlinkTime = rd.readFileObject(startLine + "_time");// get airlink json
			JSONObject btsTime = rd.readFileObject("BTS_time");// get bts json
			JSONObject mrt1Time = rd.readFileObject("MRT1_time");// get mrt1 json
			JSONObject airlinkStationTime = (JSONObject) airlinkTime.get(start);// get start station json
			JSONObject mrt2StationTime = (JSONObject) mrt2Time.get("เตาปูน");
			long mrt2Time1 = (Long) mrt2StationTime.get(stop);// get stop station fee
			JSONObject mrt1StationTime = (JSONObject) mrt1Time.get("เตาปูน");
			JSONObject btsStationTime = (JSONObject) btsTime.get("พญาไท");
			// go through Makkasan/PetchBuri
			long airlinkTime1 = (Long) airlinkStationTime.get("มักกะสัน");
			long mrt1Time1 = (Long) mrt1StationTime.get("เพชรบุรี");
			long time1 = mrt1Time1 + airlinkTime1 + mrt2Time1;
			jo1.put("time", time1);
			ja.add(jo1);
			// go through Morchit/Jatujak
			long airlinkTime2 = (Long) airlinkStationTime.get("พญาไท");
			long btsTime2 = (Long) btsStationTime.get("หมอชิต");
			long mrt1Time2 = (Long) mrt1StationTime.get("สวนจตุจักร");
			long time2 = btsTime2 + airlinkTime2 + mrt1Time2 + mrt2Time1;
			jo2.put("time", time2);
			ja.add(jo2);
			// go through Makkasan2
			JSONObject mrt1StationTime3 = (JSONObject) mrt1Time.get("สุขุมวิท");
			long airlinkTime3 = (Long) airlinkStationTime.get("พญาไท");
			long btsTime3 = (Long) btsStationTime.get("อโศก");
			long mrt1Time3 = (Long) mrt1StationTime3.get("เตาปูน");
			long time3 = btsTime3 + airlinkTime3 + mrt1Time3 + mrt2Time1;
			jo3.put("time", time3);
			ja.add(jo3);
			// go through Makkasan3
			long airlinkTime4 = (Long) airlinkStationTime.get("พญาไท");
			JSONObject mrt1StationTime4 = (JSONObject) mrt1Time.get("สีลม");
			long btsTime4 = (Long) btsStationTime.get("ศาลาแดง");
			long mrt1Time4 = (Long) mrt1StationTime4.get("เตาปูน");
			long time4 = btsTime4 + mrt1Time4 + mrt2Time1 + airlinkTime4;
			jo4.put("time", time4);
			ja.add(jo4);
			// finish return
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);
		} else if (startLine == "MRT2" && stopLine == "Airlink") { // mrt2 to airlink
			count = 4;
			JSONObject mrt1 = rd.readFileObject("MRT1");// get mrt1 json
			JSONObject bts = rd.readFileObject("BTS");// get bts json
			JSONObject airlink = rd.readFileObject(stopLine);// get airlink json
			JSONObject mrt1Station = (JSONObject) mrt1.get("เตาปูน");// get start station json
			JSONObject airlinkStation = (JSONObject) airlink.get(stop);// get stop station json
			JSONObject btsStation = (JSONObject) bts.get("พญาไท");
			JSONObject mrt2 = rd.readFileObject(startLine);
			JSONObject mrt2Station = (JSONObject) mrt2.get(start);
			long mrt2fare = (Long) mrt2Station.get("เตาปูน");
			// go through Sukumwit
			long mrt1fare1 = (Long) mrt1Station.get("สุขุมวิท");
			long btsfare1 = (Long) btsStation.get("อโศก");
			long airlinkfare1 = (Long) airlinkStation.get("พญาไท");
			long fare1 = mrt1fare1 + btsfare1 + airlinkfare1 + mrt2fare;
			String route1 = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีฟ้า นั่งต่อไปจนถึงสถานี สุขุมวิท จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink";
			JSONObject jo1 = new JSONObject();
			jo1.put("fare", fare1);
			jo1.put("route", route1);
			// go through Sealom
			long mrt1fare2 = (Long) mrt1Station.get("สีลม");
			long btsfare2 = (Long) btsStation.get("ศาลาแดง");
			long airlinkfare2 = (Long) airlinkStation.get("พญาไท");
			long fare2 = mrt1fare2 + btsfare2 + airlinkfare2 + mrt2fare;
			String route2 = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีฟ้า นั่งต่อไปจนถึงสถานี สีลม จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink";
			JSONObject jo2 = new JSONObject();
			jo2.put("fare", fare2);
			jo2.put("route", route2);
			// go through jatujak
			long mrt1fare3 = (Long) mrt1Station.get("สวนจตุจักร");
			long btsfare3 = (Long) btsStation.get("หมอชิต");
			long airlinkfare3 = (Long) airlinkStation.get("พญาไท");
			long fare3 = mrt1fare3 + btsfare3 + airlinkfare3 + mrt2fare;
			String route3 = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีฟ้า นั่งต่อไปจนถึงสถานี สวนจตุจักร จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink";
			JSONObject jo3 = new JSONObject();
			jo3.put("fare", fare3);
			jo3.put("route", route3);
			// go through Makkasan3
			long airlinkfare4 = (Long) airlinkStation.get("มักกะสัน");
			long mrt1fare4 = (Long) mrt1Station.get("เพชรบุรี");
			long fare4 = airlinkfare4 + mrt1fare4 + mrt2fare;
			String route4 = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีฟ้า นั่งต่อไปจนถึงสถานี เพชรบุรี จากนั้นเปลี่ยนไป airportlink";
			JSONObject jo4 = new JSONObject();
			jo4.put("fare", fare4);
			jo4.put("route", route4);
			// time
			JSONObject mrt1Time = rd.readFileObject("MRT1_time");// get mrt1 json
			JSONObject btsTime = rd.readFileObject("BTS_time");// get bts json
			JSONObject airlinkTime = rd.readFileObject(stopLine + "_time");// get airlink json
			JSONObject mrt1StationTime = (JSONObject) mrt1Time.get("เตาปูน");// get start station json
			JSONObject airlinkStationTime = (JSONObject) airlinkTime.get(stop);// get stop station json
			JSONObject btsStationTime = (JSONObject) btsTime.get("พญาไท");
			JSONObject mrt2Time = rd.readFileObject(startLine + "_time");
			JSONObject mrt2StationTime = (JSONObject) mrt2Time.get(start);
			long mrt2Time1 = (Long) mrt2StationTime.get("เตาปูน");
			// go through Sukumwit
			long mrt1Time1 = (Long) mrt1StationTime.get("สุขุมวิท");
			long btsTime1 = (Long) btsStationTime.get("อโศก");
			long airlinkTime1 = (Long) airlinkStationTime.get("พญาไท");
			long time1 = mrt1Time1 + btsTime1 + airlinkTime1 + mrt2Time1;
			jo1.put("time", time1);
			ja.add(jo1);
			// go through Sealom
			long mrt1Time2 = (Long) mrt1StationTime.get("สีลม");
			long btsTime2 = (Long) btsStationTime.get("ศาลาแดง");
			long airlinkTime2 = (Long) airlinkStationTime.get("พญาไท");
			long time2 = mrt1Time2 + btsTime2 + airlinkTime2 + mrt2Time1;
			jo2.put("time", time2);
			ja.add(jo2);
			// go through jatujak
			long mrt1Time3 = (Long) mrt1StationTime.get("สวนจตุจักร");
			long btsTime3 = (Long) btsStationTime.get("หมอชิต");
			long airlinkTime3 = (Long) airlinkStationTime.get("พญาไท");
			long time3 = mrt1Time3 + btsTime3 + airlinkTime3 + mrt2Time1;
			jo3.put("time", time3);
			ja.add(jo3);
			// go through Makkasan3
			long airlinkTime4 = (Long) airlinkStationTime.get("มักกะสัน");
			long mrt1Time4 = (Long) mrt1StationTime.get("เพชรบุรี");
			long time4 = airlinkTime4 + mrt1Time4 + mrt2Time1;
			jo4.put("time", time4);
			ja.add(jo4);
			// finish return
			jo.put("routes", ja);
			jo.put("possibleRoutes", count);
		}

		return jo;
	}

}

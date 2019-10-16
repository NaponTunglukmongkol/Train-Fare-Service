package com.trainfee.service;

import java.io.IOException;

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
		if(airlink.get(station) != null) {
			return line = "Airlink";
		}
		return line;
	}
	
	public JSONObject calculateFare(String start, String startLine, String stop, String stopLine) throws IOException, ParseException {
		long fare = -1;
		JSONObject jo = new JSONObject();
		if(startLine == stopLine) {
			JSONObject keep = rd.readFileObject(startLine);
			JSONObject station = (JSONObject) keep.get(start);
			fare = (Long) station.get(stop);
		}
		jo.put("possibleRoutes", 1);
		JSONArray ja = new JSONArray();
		JSONObject joo = new JSONObject();
		joo.put("fare", fare);
		ja.add(joo);
		jo.put("routes", ja);
		return jo;
	}
	
}

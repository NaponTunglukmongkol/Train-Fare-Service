package com.trainfee.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Reader {
	
	public Reader() {
	}
	
	public void writeToFile(JSONArray hand) throws IOException {
		try {
			FileWriter fw = new FileWriter("database.json");
			fw.write(hand.toJSONString());
			fw.flush();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JSONArray readFile(String location) throws IOException, ParseException{
		try {
			JSONParser jp = new JSONParser();
			FileReader fr = new FileReader("resource/" + location + ".json");
			Object obj = jp.parse(fr);
			JSONArray jsa = (JSONArray) obj;
			return jsa;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public JSONObject readFileObject(String location) throws IOException, ParseException{
		try {
			JSONParser jp = new JSONParser();
			FileReader fr = new FileReader("resource/fare/" + location + ".json");
			Object obj = jp.parse(fr);
			JSONObject jsa = (JSONObject) obj;
			return jsa;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}

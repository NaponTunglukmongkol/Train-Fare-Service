package com.trainfee.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class SpringBoot {
	private Reader rd = new Reader();
	private Calculator cl = new Calculator();

	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		SpringApplication.run(SpringBoot.class, args);
	}
	
	@RequestMapping("/")
	String home() {
		return "Welcome to Train Fare Service";
	}
	
	@RequestMapping("/viewstation")
	JSONArray showStation() throws IOException, ParseException {
		JSONObject airlinkJson = rd.readFileObject("Airlink");
		JSONObject btsJson = rd.readFileObject("BTS");
		JSONObject mrt1Json = rd.readFileObject("MRT1");
		JSONObject mrt2Json = rd.readFileObject("MRT2");
		JSONArray result = new JSONArray();
		JSONObject airlink = new JSONObject();
		JSONObject bts = new JSONObject();
		JSONObject mrt1 = new JSONObject();
		JSONObject mrt2 = new JSONObject();
		airlink.put("Airlink", airlinkJson.keySet());
		bts.put("BTS", btsJson.keySet());
		mrt1.put("MRT1", mrt1Json.keySet());
		mrt2.put("MRT2", mrt2Json.keySet());
		result.add(airlink);
		result.add(bts);
		result.add(mrt1);
		result.add(mrt2);
		return result;
	}
	
	@RequestMapping("/viewstation/{line}")
	JSONArray showLineStation(@PathVariable String line) throws IOException, ParseException {
		JSONObject jsa = rd.readFileObject(line);
		ArrayList<String> keeper = new ArrayList<String>();
		JSONArray result = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put(line, jsa.keySet());
		result.add(jo);
		return result;
	}
	
	@RequestMapping("{start}/to/{stop}")
	JSONObject showFare(@PathVariable String start, @PathVariable String stop) throws IOException, ParseException {
		JSONObject result = new JSONObject();
		String startLine = cl.checkLine(start);
		String stopLine = cl.checkLine(stop);
		result = cl.calculateFare(start, startLine, stop, stopLine);
		return result;
	}

}

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

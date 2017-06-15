package com.accenture.bars.rest.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.bars.rest.domain.Request;
import com.accenture.bars.rest.exception.BarsException;
import com.accenture.bars.rest.factory.InputFileFactory;
import com.accenture.bars.rest.file.IInputFile;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Controller
public class WebController {

	@RequestMapping("/index")
	public String getIndex() {
		return "home";
	}

	@PostMapping("/parseJson")
	public ModelAndView parseJson(@RequestParam("path") File file) {
		ModelAndView model = new ModelAndView();
		// System.out.println("JSON: "+parseFileToJson(file));
		try {
			//String json = getParsedJson(parseFileToJson(file).toString());
			String json = getParsedJson(parseFileToJson(file).toString());
			model.addObject("json", json);
			model.setViewName("success");
		} catch (BarsException e) {
			model.addObject("json", e.getMessage());
			model.setViewName("success");
		} catch (Exception e) {
			model.addObject("json", e.getMessage());
			model.setViewName("success");
		}
		return model;
	}

	private JSONArray parseFileToJson(File file) throws Exception {
		JSONArray json = null;

		IInputFile inputFile;
		InputFileFactory factory = InputFileFactory.getInstance();
		inputFile = factory.getInputFile(file);
		if (inputFile == null) {
			try {
				throw new BarsException(BarsException.FILE_NOT_SUPPORTED);
			} catch (BarsException e) {
				throw new BarsException(e.getMessage());
			}
		}
		try {
			inputFile.setFile(file);
			List<Request> requests = inputFile.readFile();
			if (requests.isEmpty()) {
				throw new BarsException(BarsException.NO_REQUESTS_TO_READ);
			} else {
				json = new JSONArray();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for(Request r : requests){
					JSONObject obj = new JSONObject();
					obj.put("billingCycle",r.getBillingCycle());
					obj.put("startDate",sdf.format(r.getStartDate()));
					obj.put("endDate", sdf.format(r.getEndDate()));

					json.put(obj);
				}
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		System.out.println("Parsed Jason: "+json);
		return json;
	}

	private String getParsedJson(String json) {

		Client client = new Client();
		WebResource web = client
				.resource("http://10.223.218.205:8089/barswebservice/bars2/getrecord");

		 ClientResponse response = web.type(MediaType.APPLICATION_JSON).post(
		 ClientResponse.class, json);
		 String resStr = response.getEntity(String.class);
		 return resStr;
	}

}

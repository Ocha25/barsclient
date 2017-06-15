package com.accenture.bars.rest;

import java.io.File;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.accenture.bars.rest.dao.IRequestDAO;
import com.accenture.bars.rest.domain.Request;
import com.accenture.bars.rest.exception.BarsException;
import com.accenture.bars.rest.factory.InputFileFactory;
import com.accenture.bars.rest.file.IInputFile;

public class BarsClient {

	public static void main(String[] args) {

		IInputFile inputFile;
		IRequestDAO requestDAO;

		File file = new File(
				"C:/Users/ruby.jynn.r.delantar/Documents/valid_txt_file.txt");
		InputFileFactory factory = InputFileFactory.getInstance();
		inputFile = factory.getInputFile(file);

		if (inputFile == null) {
			try {
				throw new BarsException(BarsException.FILE_NOT_SUPPORTED);
			} catch (BarsException e) {
				e.printStackTrace();
			}
		}
		try {
			inputFile.setFile(file);
			List<Request> requests = inputFile.readFile();
			if (requests.isEmpty()) {
				throw new BarsException(BarsException.NO_REQUESTS_TO_READ);
			} else {
				JSONArray json = new JSONArray(requests);
				System.out.println("JSON OBJECT: " + json);

				System.out.println("Extracting...");
				for (int x = 0; x < json.length(); x++) {
					JSONObject obj = json.getJSONObject(x);
					System.out.println("JSON Object: " + obj);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

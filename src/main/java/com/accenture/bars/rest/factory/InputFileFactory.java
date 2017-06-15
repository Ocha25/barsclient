package com.accenture.bars.rest.factory;

import java.io.File;

import com.accenture.bars.rest.file.CSVInputFileImpl;
import com.accenture.bars.rest.file.IInputFile;
import com.accenture.bars.rest.file.TextInputFileImpl;

public class InputFileFactory {

	private static InputFileFactory instance = null;

	public static synchronized InputFileFactory getInstance() {
		if (instance == null) {
			instance = new InputFileFactory();
		}
		return instance;
	}

	public IInputFile getInputFile(File file) {

		int begin = (file.getAbsolutePath().length()) - 3;
		int last = file.getAbsolutePath().length();
		String temp = file.getAbsolutePath().substring(begin, last);

		if ("CSV".equalsIgnoreCase(temp)) {
			return new CSVInputFileImpl();
		} else if ("TXT".equalsIgnoreCase(temp)) {
			return new TextInputFileImpl();
		} else {
			return null;
		}
	}
}

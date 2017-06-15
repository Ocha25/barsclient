package com.accenture.bars.rest.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public abstract class AbstractInputFile implements IInputFile {

    public static final int MIN_BILLING_CYCLE = 1;
    public static final int MAX_BILLING_CYCLE = 12;

    private File file;
    private static Logger logger;

    @Override
    public void setFile(final File fle) {
        this.file = fle;
    }

    @Override
    public File getFile() {
        return file;
    }

    public static synchronized Logger getLogger() {
        return logger;
    }

    public static synchronized void setLogger(final Logger lgr) {
        logger = lgr;
    }

    protected String[] readAllLinesOfFile() throws IOException {
    	//FileReader fileReader = new FileReader(file);
    	InputStream inputStream = new FileInputStream(file);
    	Reader fileReader = new InputStreamReader(inputStream,"UTF-8");

        BufferedReader reader = new BufferedReader(fileReader);
        String line = reader.readLine();



        List<String> lines = new ArrayList<>();
        while (line != null) {
            lines.add(line);
            line = reader.readLine();
        }
        fileReader.close();
        reader.close();
        return (String[]) lines.toArray(new String[0]);
    }

}

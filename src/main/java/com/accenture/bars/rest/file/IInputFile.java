package com.accenture.bars.rest.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.accenture.bars.rest.domain.Request;
import com.accenture.bars.rest.exception.BarsException;

public interface IInputFile {

    List<Request> readFile() throws IOException, BarsException;
    void setFile(File file);
    File getFile();

}

package com.accenture.bars.rest.file;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.accenture.bars.rest.domain.Request;
import com.accenture.bars.rest.exception.BarsException;


public class TextInputFileImpl extends AbstractInputFile {

    public TextInputFileImpl() {
    	BasicConfigurator.configure();
        setLogger(Logger.getLogger(TextInputFileImpl.class));
    }

//    private static final int BILLING_CYCLE_STRING_START = 0;
//    private static final int BILLING_CYCLE_STRING_END = 2;
//    private static final int START_DATE_STRING_END = 10;
//    private static final int END_DATE_STRING_END = 18;

    @Override
    public List<Request> readFile() throws IOException, BarsException {
        getLogger().info(
                "Processing " + getFile().getName() + " request file...");

        String[] lines = readAllLinesOfFile();

        List<Request> requests = new ArrayList<>();

        for (int i = 0; i < lines.length; i++) {
            Request request = new Request();

            String billingCycle = lines[i].substring(
                    0, 2);
            try {
                request.setBillingCycle(Integer.parseInt(billingCycle));
            } catch (NumberFormatException e) {
                throw new BarsException(BarsException.INVALID_BILLING_CYCLE
                        + (i + 1), e);
            }
            if (request.getBillingCycle() < MIN_BILLING_CYCLE
                    || request.getBillingCycle() > MAX_BILLING_CYCLE) {
                throw new BarsException(
                        BarsException.BILLING_CYCLE_NOT_ON_RANGE + (i + 1));
            }

            SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
            sdf.setLenient(false);

            try {
                String startDate = lines[i].substring(2,
                        10);
                int str = Integer.parseInt(startDate);
                request.setStartDate(sdf.parse(startDate));
            } catch (NumberFormatException | IndexOutOfBoundsException
                    | ParseException e) {
                throw new BarsException(BarsException.INVALID_START_DATE_FORMAT
                        + (i + 1), e);
            }

            try {
                String endDate = lines[i].substring(10,
                        18);
                int str = Integer.parseInt(endDate);
                request.setEndDate(sdf.parse(endDate));
            } catch (NumberFormatException | IndexOutOfBoundsException
                    | ParseException e) {
                throw new BarsException(BarsException.INVALID_END_DATE_FORMAT
                        + (i + 1), e);
            }

            requests.add(request);
        }

        return requests;
    }
}

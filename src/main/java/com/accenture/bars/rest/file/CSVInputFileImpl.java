package com.accenture.bars.rest.file;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.accenture.bars.rest.domain.Request;
import com.accenture.bars.rest.exception.BarsException;


public class CSVInputFileImpl extends AbstractInputFile {

	@Override
	public List<Request> readFile() throws IOException, BarsException {
		// TODO Auto-generated method stub
		List<Request> requests = new ArrayList<>();
		String[] lines = readAllLinesOfFile();
		String[] temp;

		int x = 1;
		for (String r : lines) {

			if(r.equals("")){
				throw new BarsException(BarsException.NO_REQUESTS_TO_READ);
			}

			Request request = new Request();
			temp = r.split(",");

			int billingCycle = Integer.parseInt(temp[0]);
			try {
				request.setBillingCycle(billingCycle);

			} catch (NumberFormatException e) {
				throw new BarsException(BarsException.INVALID_BILLING_CYCLE
						+ (x), e);
			}

			if (request.getBillingCycle() < MIN_BILLING_CYCLE
					|| request.getBillingCycle() > MAX_BILLING_CYCLE) {
				throw new BarsException(
						BarsException.BILLING_CYCLE_NOT_ON_RANGE + (x));
			}

			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			sdf.setLenient(false);

			try {
				request.setStartDate(sdf.parse(temp[1]));
			} catch (NumberFormatException | IndexOutOfBoundsException
					| ParseException e) {
				throw new BarsException(BarsException.INVALID_START_DATE_FORMAT
						+ (x), e);
			}
			//
			try {
				request.setEndDate(sdf.parse(temp[2]));
			} catch (NumberFormatException | IndexOutOfBoundsException
					| ParseException e) {
				throw new BarsException(BarsException.INVALID_END_DATE_FORMAT
						+ (x), e);
			}

			requests.add(request);

			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			x++;
		}

		return requests;
	}
}

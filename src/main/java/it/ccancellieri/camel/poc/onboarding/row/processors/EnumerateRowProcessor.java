package it.ccancellieri.camel.poc.onboarding.row.processors;

import it.ccancellieri.camel.poc.onboarding.row.RowChecker.RowProcessor;

import java.util.Map;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

/**
 * Process the row returning the same as the input
 * 
 * @author carlo cancellieri - PayBay
 */
@Component
public class EnumerateRowProcessor implements RowProcessor {

	@Override
	public Map<Object, Object> processRow(Map<Object, Object> row,
			final Map<String, Object> properties) throws Exception {
		int rowNum=getChunkNumber(properties);
		// this should be also multiplied for the tokenizer (\n) group size
		row.put(ROW_NUMBER_KEY,++rowNum);
		return row;
	}

	@Override
	public boolean isStopOnException() {
		return false;
	}
	
	public static final String ROW_NUMBER_KEY = "ROW_NUMBER";
	
	private static int getChunkNumber(Map<String, Object> properties){
//		final Object s = properties.get(Exchange.SPLIT_SIZE);
		final Object i = properties.get(Exchange.SPLIT_INDEX);
		Integer chunkNum = 0;
		if (i != null && Integer.class.isAssignableFrom(i.getClass())) {
			chunkNum = (Integer) i;
		}
		return chunkNum;
	}
}

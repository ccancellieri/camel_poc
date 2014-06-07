package it.ccancellieri.camel.poc.onboarding.row.processors;

import it.ccancellieri.camel.poc.onboarding.row.RowChecker.RowProcessor;

import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * Process the row creating an AggrId which will be useful to aggregate the rows
 * 
 * @author carlo cancellieri - PayBay
 */

@Component
public class AggregationIdRowProcessor implements RowProcessor {

        public final static String AGGREGATION_ID_KEY="aggrId";
//        public final static String RECORD_TYPE_KEY="RECORD_TYPE";
        public final static String SITE_ID_KEY="SITE_ID";
        public final static String CHAIN_ID_KEY="CHAIN_ID";
        
	@Override
	public Map<Object, Object> processRow(Map<Object, Object> row,
			final Map<String, Object> properties) throws Exception {
		if (row == null)
			return null;
		final Object site_id=row.get(SITE_ID_KEY);
		if (site_id==null)
		    throw new Exception("Unable to use a null site_id value"); //TODO create specific exception
		final Object chain_id=row.get(CHAIN_ID_KEY);
                if (chain_id==null)
                    throw new Exception("Unable to use a null chain_id value"); //TODO create specific exception
                final StringBuilder sb=new StringBuilder(site_id.toString());
                properties.put(AGGREGATION_ID_KEY, sb.append(chain_id.toString()).toString());
//		for (Object k : row.keySet()) {
//			if (!k.equals(EnumerateRowProcessor.ROW_NUMBER_KEY))
//				row.put(k, 0);
//		}
		return row;
	}

	@Override
	public boolean isStopOnException() {
		return false;
	}

}

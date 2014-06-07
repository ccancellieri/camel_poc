package it.ccancellieri.camel.poc.onboarding.row;

import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Apply RowProcessor(s) to a passed row returning a modified/validated one
 * 
 * @author carlo cancellieri - PayBay
 */
public class RowChecker implements Processor {
	final private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired(required = false)
	List<RowProcessor> procs;

	/**
	 * Process the row returning a modified/validated one
	 * 
	 * @author carlo cancellieri - PayBay
	 */
	public interface RowProcessor {
		public Map<Object, Object> processRow(Map<Object, Object> row, final Map<String, Object> map)
				throws Exception;

		public boolean isStopOnException();
	}

	@Override
	public void process(Exchange exchange) throws Exception {
	    if (procs == null || procs.isEmpty()) {
	            return;
	    }
	    final List<Map<Object, Object>> rows=RowExchangeUtils.getBodyAsListOfMaps(exchange);
		if (rows == null || rows.isEmpty()) {
			return;
		}
		for (Map<Object, Object> row : rows) {
			if (row == null) {
				if (LOGGER.isWarnEnabled()) {
					LOGGER.warn("Skipping: This row is null!");
				}
				continue;
			}
			for (RowProcessor p : procs) {
				try {
					p.processRow(row, exchange.getProperties());
				} catch (Exception e) {
					if (p.isStopOnException()) {
						throw e;
					} else {
						if (LOGGER.isWarnEnabled()) {
							LOGGER.warn(e.getLocalizedMessage(), e);
						}
					}
				}
			}
		}
	}

}

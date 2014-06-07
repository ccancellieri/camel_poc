package it.ccancellieri.camel.poc.onboarding.file.exchange;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FileExchangeChecker {
	final private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired(required = false)
	private List<FileExchangeEnricher> enrichers;

	public interface FileExchangeEnricher {
		public void enrichUsingPath(Map<String, Object> headers)
				throws Exception;

		public boolean isStopOnException();
	}

	public void enrichUsingPath(Exchange ex) throws Exception {
		if (enrichers == null)
			return;
		final File f = ex.getIn().getBody(File.class);
		if (f == null)
			return;
		for (FileExchangeEnricher enricher : enrichers) {
			try {
				enricher.enrichUsingPath(ex.getIn().getHeaders());
			} catch (Exception e) {
				if (enricher.isStopOnException()) {
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

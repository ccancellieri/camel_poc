package it.ccancellieri.camel.poc.onboarding.file;

import java.util.List;

import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author carlo cancellieri - PayBay
 */
public class FileFilterChecker<T> implements GenericFileFilter<T> {
	final private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired(required = false)
	List<FileFilter> filters;

	/**
	 * @author carlo cancellieri - PayBay
	 */
	public interface FileFilter {
		public boolean accept(GenericFile<?> file);

		public boolean isStopOnException();
	}

	@Override
	public boolean accept(GenericFile<T> file) {
		boolean accept = true;
		if (filters != null) {
			for (FileFilter filter : filters) {
				accept &= filter.accept(file);
			}
		}
		if (LOGGER.isWarnEnabled() && !accept) {
			LOGGER.warn("Filtering out the file: " + file.getAbsoluteFilePath());
		} else if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Filtering over the file: "
					+ file.getAbsoluteFilePath() + "\nAcceptance result is: "
					+ accept);
		}
		return accept;
	}

}

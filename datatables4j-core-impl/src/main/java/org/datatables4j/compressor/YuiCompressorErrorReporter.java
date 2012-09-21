package org.datatables4j.compressor;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 * @author tduchate
 *
 */
public class YuiCompressorErrorReporter implements ErrorReporter {
	
	// Logger
	private static Logger logger = LoggerFactory.getLogger(YuiCompressorErrorReporter.class);
		
	public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
		if (line < 0) {
			logger.warn(message);
		} else {
			logger.warn("{}:{}:{}", new Object[]{ line, lineOffset, message});
		}
	}

	public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
		if (line < 0) {
			logger.error(message);
		} else {
			logger.error("{}:{}:{}", new Object[]{ line, lineOffset, message});
		}
	}

	public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset) {
		error(message, sourceName, line, lineSource, lineOffset);
		return new EvaluatorException(message);
	}
}

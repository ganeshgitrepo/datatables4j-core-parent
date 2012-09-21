package org.datatables4j.compressor;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.datatables4j.exception.CompressionException;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

/**
 * Default compression class which uses YUICompressor.
 * 
 * @author Thibault Duchateau
 */
public class YuiResourceCompressor implements ResourceCompressor {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(YuiResourceCompressor.class);
		
	// Default options
	private static Options options = new Options();

	public String getCompressedJavascript(String input) throws CompressionException {
		
		Writer output = new StringWriter();
		JavaScriptCompressor compressor = null;
		
		try {
			compressor = new JavaScriptCompressor( new StringReader(input),
					new YuiCompressorErrorReporter());
			compressor.compress(output, options.lineBreakPos, options.munge, options.verbose, options.preserveAllSemiColons, options.disableOptimizations);
		} catch (EvaluatorException e) {
			logger.error("Unable to compress Javascript resource");
			throw new CompressionException(e);
		} catch (IOException e) {
			logger.error("Unable to compress Javascript resource");
			throw new CompressionException(e);
		}
		
		return output.toString();
	}
	
	public String getCompressedCss(String input) throws CompressionException{
		Writer output = new StringWriter();
		
		CssCompressor compressor;
		try {
			compressor = new CssCompressor(new StringReader(input));
			compressor.compress(output, options.lineBreakPos);
		} catch (IOException e) {
			logger.error("Unable to compress Css resource");
			throw new CompressionException(e);
		}
		
		return output.toString();
	}
}

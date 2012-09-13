package org.datatables4j.compressor;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.mozilla.javascript.EvaluatorException;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

public class YuiCompressor {

	// Default options
	private static Options options = new Options();

	public static String getCompressedJavascript(String input) throws EvaluatorException, IOException{
		Writer output = new StringWriter();

		JavaScriptCompressor compressor = new JavaScriptCompressor( new StringReader(input),
				new YuiCompressorErrorReporter());
		
		compressor.compress(output, options.lineBreakPos, options.munge, options.verbose, options.preserveAllSemiColons, options.disableOptimizations);
		
		return output.toString();
	}
	
	public static String getCompressedCss(String input) throws IOException{
		Writer output = new StringWriter();
		
		CssCompressor compressor = new CssCompressor(new StringReader(input));
		
		compressor.compress(output, options.lineBreakPos);
		
		return output.toString();
	}
}

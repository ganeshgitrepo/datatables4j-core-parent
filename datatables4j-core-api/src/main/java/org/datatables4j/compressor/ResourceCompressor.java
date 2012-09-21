package org.datatables4j.compressor;

import org.datatables4j.exception.CompressionException;

public interface ResourceCompressor {

	public String getCompressedJavascript(String input) throws CompressionException;
	
	public String getCompressedCss(String input) throws CompressionException;
}

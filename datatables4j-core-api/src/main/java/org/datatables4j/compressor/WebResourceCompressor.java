package org.datatables4j.compressor;

import org.datatables4j.exception.CompressionException;

/**
 * Compressor for web resources (Javascript, Stylesheets).
 * 
 * @author Thibault Duchateau
 */
public interface WebResourceCompressor {

	/**
	 * Return as String a compressed version of the given Javascript code.
	 * 
	 * @param input
	 *            The Javascript code to compress.
	 * @return The Javascript code compressed.
	 * @throws CompressionException
	 *             if the String containing Javascript code is malformed or
	 *             cannot be evaluated.
	 */
	public String getCompressedJavascript(String input) throws CompressionException;

	/**
	 * Return as String a compressed version of the given CSS code.
	 * 
	 * @param input
	 *            The CSS code to compress.
	 * @return The CSS code compressed.
	 * @throws CompressionException
	 *             if the String containing CSS is malformed.
	 */
	public String getCompressedCss(String input) throws CompressionException;
}
package org.datatables4j.compressor;

import org.datatables4j.exception.BadConfigurationException;
import org.datatables4j.model.HtmlTable;
import org.datatables4j.util.ReflecUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 * 
 * @author Thibault Duchateau
 */
public class ResourceCompressorHelper {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(ResourceCompressorHelper.class);
	
//	private DataTablesContext context = DataTablesContext.getInstance();
	private Class<?> compressorClass;
	
	public ResourceCompressorHelper(HtmlTable table) throws BadConfigurationException{
		this.compressorClass = ReflecUtils.getClass(table.getProperties().getCompressorClassName());
		logger.debug("Compress using {} implementation", this.compressorClass.getSimpleName());
	}
	
	/**
	 * TODO
	 * @param input
	 * @return
	 * @throws BadConfigurationException
	 */
	public String getCompressedJavascript(String input) throws BadConfigurationException {
		
		Object obj = ReflecUtils.getNewInstance(this.compressorClass);

		
		return (String) ReflecUtils.invokeMethod(obj, "getCompressedJavascript", new Object[]{input});
	}
	
	public String getCompressedCss(String input) throws BadConfigurationException {

		Object obj = ReflecUtils.getNewInstance(this.compressorClass);
		
		return (String) ReflecUtils.invokeMethod(obj, "getCompressedCss", new Object[]{input});
	}
}

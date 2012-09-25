package org.datatables4j.module.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.datatables4j.constants.DTConstants;
import org.datatables4j.model.HtmlTable;
import org.datatables4j.model.JsResource;
import org.datatables4j.model.Module;
import org.datatables4j.util.JsonUtils;

/**
 * Java implementation of the DataTables FixedHeader plugin.
 * 
 * @see <a href="http://datatables.net/extras/fixedheader/">Reference</a>
 * @author Thibault Duchateau
 */
public class FixedHeaderModule extends Module {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getModuleName() {
		return "FixedHeader";
	}

	/**	
	 * {@inheritDoc}
	 */
	@Override
	public String getModuleVersion() {
		return "2.0.6";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setup(HtmlTable table) {
		
		Map<String, Object> specificConfObj = getSpecificCongiguration(table);
		String specificConfStr = null;
		if(!specificConfObj.isEmpty()){
			specificConfStr = JsonUtils.convertObjectToJsonString(specificConfObj);
			beforeEndDocumentReady = "new FixedHeader(oTable_" + table.getId() + "," + specificConfStr + ");";
		}
		else{
			beforeEndDocumentReady = "new FixedHeader(oTable_" + table.getId() + ");";
		}
		
		addJsResource(new JsResource("datatables.fixedheader.min.js"));
	}
	
	/**
	 * Depending on the attributes, the FixedHeader object may need a JSON
	 * object as configuration.
	 * 
	 * @param table
	 *            The HTML table.
	 * @return Map<String, Object> Map of property used by the FixedHeader
	 *         plugin.
	 */
	private Map<String, Object> getSpecificCongiguration(HtmlTable table){
		Map<String, Object> conf = new HashMap<String, Object>();
		
		// fixedPosition attribute (default "top")
		if(StringUtils.isNotBlank(table.getFixedPosition())){
			if(table.getFixedPosition().equals("bottom")){
				conf.put("bottom", true);
			}
			else if(table.getFixedPosition().equals("right")){
				conf.put("right", true);
			}
			else if(table.getFixedPosition().equals("left")){
				conf.put("left", true);
			}
			else{
				beforeEndDocumentReady = "new FixedHeader(oTable_" + table.getId() + ");";
			}
		}
		else{
			conf.put("top", true);
		}
		
		// offsetTop attribute
		if(table.getFixedOffsetTop() != null){
			conf.put(DTConstants.DT_OFFSETTOP, table.getFixedOffsetTop());
		}
		
		return conf;
	}
}

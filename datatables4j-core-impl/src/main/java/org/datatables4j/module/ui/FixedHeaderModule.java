package org.datatables4j.module.ui;

import org.apache.commons.lang.StringUtils;
import org.datatables4j.model.HtmlTable;
import org.datatables4j.model.JsResource;
import org.datatables4j.model.Module;

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
		if(StringUtils.isBlank(table.getFixedPosition())){
			beforeEndDocumentReady = "new FixedHeader(oTable_" + table.getId() + ");";			
		}
		else{
			if(table.getFixedPosition().equals("bottom")){
				beforeEndDocumentReady = "new FixedHeader(oTable_" + table.getId() + ", {\"bottom\": true});";					
			}
			else if(table.getFixedPosition().equals("right")){
				beforeEndDocumentReady = "new FixedHeader(oTable_" + table.getId() + ", {\"right\": true});";
			}
			else if(table.getFixedPosition().equals("left")){
				beforeEndDocumentReady = "new FixedHeader(oTable_" + table.getId() + ", {\"left\": true});";
			}
			else{
				beforeEndDocumentReady = "new FixedHeader(oTable_" + table.getId() + ");";
			}
		}
		addJsResource(new JsResource("datatables.fixedheader.min.js"));
	}
}

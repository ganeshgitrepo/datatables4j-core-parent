package org.datatables4j.module.ui;

import org.datatables4j.constants.DTConstants;
import org.datatables4j.model.HtmlTable;
import org.datatables4j.model.JsResource;
import org.datatables4j.model.Module;
import org.datatables4j.model.ModuleConf;

/**
 * Java implementation of the DataTables TableTools plugin.
 * 
 * @see <a href="http://datatables.net/extras/tabletools/">Reference</a>
 * @author Thibault Duchateau
 */
public class TableToolsModule extends Module {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getModuleName() {
		return "ColReorder";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getModuleVersion() {
		return "1.0.6";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setup(HtmlTable table) {
		addModuleConf(new ModuleConf(DTConstants.DT_DOM, "R", ModuleConf.Mode.PREPEND));
	}	
}

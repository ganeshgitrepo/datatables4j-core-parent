package org.datatables4j.module.ui;

import org.datatables4j.constants.DTConstants;
import org.datatables4j.model.HtmlTable;
import org.datatables4j.model.JsResource;
import org.datatables4j.model.Module;
import org.datatables4j.model.ModuleConf;

/**
 * Java implementation of the DataTables ColReorder plugin.
 * 
 * @see <a href="http://datatables.net/extras/colreorder/">Reference</a>
 * @author Thibault Duchateau
 */
public class ColReorderModule extends Module {

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
		addJsResource(new JsResource("colreorder.min.js"));
		addModuleConf(new ModuleConf(DTConstants.DT_DOM, "R", ModuleConf.Mode.PREPEND));
	}	
}

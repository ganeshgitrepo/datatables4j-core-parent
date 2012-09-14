package org.datatables4j.module.ui;

import org.datatables4j.constants.DTConstants;
import org.datatables4j.model.HtmlTable;
import org.datatables4j.model.JsResource;
import org.datatables4j.model.Module;
import org.datatables4j.model.ModuleConf;

/**
 * Java implementation of the DataTables Scroller plugin.
 * 
 * @see <a href="http://datatables.net/extras/scroller/">Reference</a>
 * @author Thibault Duchateau
 */
public class ScrollerModule extends Module {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getModuleName() {
		return "Scroller";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getModuleVersion() {
		return "1.1.0";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setup(HtmlTable table) {
		addJsResource(new JsResource("datatables.scroller.min.js"));
		addModuleConf(new ModuleConf(DTConstants.DT_DOM, "S", ModuleConf.Mode.APPEND));
		addModuleConf(new ModuleConf(DTConstants.DT_SCROLLY, table.getScrollY()));
	}
}

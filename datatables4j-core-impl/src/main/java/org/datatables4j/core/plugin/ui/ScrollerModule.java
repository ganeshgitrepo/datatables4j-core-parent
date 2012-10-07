/*
 * DataTables4j, a JSP taglib to display table with jQuery and DataTables
 * Copyright (c) 2012, DataTables4j <datatables4j@gmail.com>
 *
 * This Program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License, version 2, as
 * published by the Free Software Foundation.
 * 
 * The Program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.datatables4j.core.plugin.ui;

import org.datatables4j.core.api.constants.DTConstants;
import org.datatables4j.core.api.model.HtmlTable;
import org.datatables4j.core.api.model.JsResource;
import org.datatables4j.core.api.model.Plugin;
import org.datatables4j.core.api.model.PluginConf;

/**
 * Java implementation of the DataTables Scroller plugin.
 * 
 * @see <a href="http://datatables.net/extras/scroller/">Reference</a>
 * @author Thibault Duchateau
 */
public class ScrollerModule extends Plugin {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPluginName() {
		return "Scroller";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPluginVersion() {
		return "1.1.0";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setup(HtmlTable table) {
		addJsResource(new JsResource("datatables.scroller.min.js"));
		addPluginConf(new PluginConf(DTConstants.DT_DOM, "S", PluginConf.Mode.APPEND));
		addPluginConf(new PluginConf(DTConstants.DT_SCROLLY, table.getScrollY()));
	}
}

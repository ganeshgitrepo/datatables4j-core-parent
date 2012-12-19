package com.github.datatables4j.core.api.generator;

import java.util.Map;

import com.github.datatables4j.core.api.model.HtmlTable;

public abstract class AbstractConfigurationGenerator {

	public abstract Map<String, Object> generate(HtmlTable table);
}

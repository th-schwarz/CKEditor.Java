/*
 * CKEditor.java - Java backend of CKEditor (http://ckeditor.com). 
 * It provides a simple object for creating an editor instance.
 * Copyright (C) Thilo Schwarz
 * 
 * == BEGIN LICENSE ==
 * 
 * Licensed under the terms of any of the following licenses at your
 * choice:
 * 
 *  - GNU General Public License Version 2 or later (the "GPL")
 *    http://www.gnu.org/licenses/gpl.html
 * 
 *  - GNU Lesser General Public License Version 2.1 or later (the "LGPL")
 *    http://www.gnu.org/licenses/lgpl.html
 * 
 *  - Mozilla Public License Version 1.1 or later (the "MPL")
 *    http://www.mozilla.org/MPL/MPL-1.1.html
 * 
 * == END LICENSE ==
 */
package de.thischwa.ckeditor;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import de.thischwa.ckeditor.util.StringUtils;


/**
 * Simple object to handle the properties of the CKEditor.
 */
class CKEditorConfig {
	private Map<String, Object> configEntries;
	
	CKEditorConfig() {
		configEntries = new LinkedHashMap<String, Object>();
	}
	
	void put(final String key, final String value) {
		configEntries.put(key, value);
	}
	
	void putAll(Map<String, String> props) {
		configEntries.putAll(props);
	}
	
	String get(final String key) {
		return configEntries.get(key).toString();
	}

	 Set<String> keySet() {
		return configEntries.keySet();
	}

	void remove(final String key) {
		configEntries.remove(key);
	}
	
	Map<String, Object> getUnmodifiableProperties() {
		return Collections.unmodifiableMap(configEntries);
	}
	
	String buildJSON() {
		/* we can't use an object wrapper like jackson here because a value could be json too */
		
		// cleaning of blank or empty values
		for(String key: configEntries.keySet()) {
			String val = configEntries.get(key).toString();
			if (StringUtils.isNullOrEmptyOrBlank(val)) {
				configEntries.remove(key);
				continue;
			}			
			// quote json values, if not quoted yet
			if (!val.startsWith("'") && !val.endsWith("'") && !val.startsWith("[") && !val.startsWith("{"))
				configEntries.put(key, String.format("'%s'", val));
		}
		
		// build json
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		boolean isFirst = true;
		for(String key : configEntries.keySet()) {
			if(!isFirst)
				sb.append(",\n");
			sb.append(String.format(" %s : %s ", key, configEntries.get(key)));
			isFirst = false;
		}
		sb.append("}");
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return buildJSON();
	}
}

/*
 * CKEditor.java - Java backend of CKEditor (http://ckeditor.com). 
 * It provides a simple object for creating an editor instance.
 * Copyright (C) Thilo Schwarz
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package codes.thischwa.ckeditor;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import codes.thischwa.ckeditor.util.StringUtils;


/**
 * Simple object to handle the properties of the CKEditor.
 */
class CKEditorConfig {
	private Map<String, Object> configEntries;
	
	CKEditorConfig() {
		configEntries = new LinkedHashMap<>();
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

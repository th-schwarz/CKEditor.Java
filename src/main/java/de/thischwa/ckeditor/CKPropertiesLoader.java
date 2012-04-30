/*
 * ckeditor.java - Java backend of CKEditor (http://ckeditor.com). 
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

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Static object for managing the properties files. <br/>
 * The files are loaded in the following order:
 * <ol>
 * <li>The default properties.</li>
 * <li>The user-defined properties <code>ckeditor.properties</code> if present.</li>
 * </ol> 
 * Please note: The user-defined properties <em>override</em> the default one.<br/> 
 * Moreover, you can set properties programmatically too ({@link #setProperty(String, String)}) 
 * but make sure to override them <em>before</em> the first call of that specific property.
 */
public class CKPropertiesLoader {
	private static final Logger logger = LoggerFactory.getLogger(CKPropertiesLoader.class);
	private static final String DEFAULT_FILENAME = "default.properties";
	private static final String LOCAL_PROPERTIES = "/ckeditor.properties";
	private static Properties properties = new Properties();

	static {
		// 1. load library defaults
		InputStream inDefault = CKPropertiesLoader.class.getResourceAsStream(DEFAULT_FILENAME);

		if (inDefault == null) {
			logger.error("{} not found", DEFAULT_FILENAME);
			throw new RuntimeException(DEFAULT_FILENAME + " not found");
		} else {
			if (!(inDefault instanceof BufferedInputStream))
				inDefault = new BufferedInputStream(inDefault);

			try {
				properties.load(inDefault);
				inDefault.close();
				logger.info("{} successful loaded", DEFAULT_FILENAME);
			} catch (Exception e) {
				String msg = "Error while processing " + DEFAULT_FILENAME;
				logger.error(msg);
				throw new RuntimeException(msg, e);
			}
		}

		// 2. load user defaults if present
		InputStream inUser = CKPropertiesLoader.class.getResourceAsStream(LOCAL_PROPERTIES);
		if (inUser == null) {
			logger.info("{} not found", LOCAL_PROPERTIES);
		} else {
			if (!(inUser instanceof BufferedInputStream))
				inUser = new BufferedInputStream(inUser);
			try {
				properties.load(inUser);
				inUser.close();
				logger.info("{} successful loaded", LOCAL_PROPERTIES);
			} catch (Exception e) {
				String msg = "Error while processing " + LOCAL_PROPERTIES;
				logger.error(msg);
				throw new RuntimeException(msg, e);
			}
		}
	}

	/**
	 * Searches for the property with the specified key in this property list.
	 * 
	 * @see Properties#getProperty(String)
	 */
	public static String getProperty(final String key) {
		return properties.getProperty(key);
	}

	/**
	 * Sets the property with the specified key in this property list.
	 * 
	 * @see Properties#setProperty(String, String)
	 */
	public static void setProperty(final String key, final String value) {
		properties.setProperty(key, value);
	}

	/**
	 * Returns <code>default.encoding</code> property
	 */
	public static String getDefaultEncoding() {
		return properties.getProperty("default.encoding");
	}
	
	/**
	 * Returns <code>ckeditor.toolbar</code> property
	 */
	public static String getEditorToolbar() {
		return properties.getProperty("ckeditor.toolbar");
	}

	/**
	 * Returns <code>ckeditor.width</code> property
	 */
	public static String getEditorWidth() {
		return properties.getProperty("ckeditor.width");
	}

	/**
	 * Returns <code>ckeditor.height</code> property
	 */
	public static String getEditorHeight() {
		return properties.getProperty("ckeditor.height");
	}
	
	/**
	 * Returns <code>ckeditor.textarea.cols</code> property
	 */
	public static String getEditorTextareaCols() {
		return properties.getProperty("ckeditor.textarea.cols");
	}
	/**
	 * Returns <code>ckeditor.textarea.rows</code> property
	 */
	public static String getEditorTextareaRows() {
		return properties.getProperty("ckeditor.textarea.rows");
	}

	/**
	 * Returns <code>ckeditor.basePath</code> property and ensures that it ends with '/'.
	 */
	public static String getEditorBasePath() {
		String basePath = properties.getProperty("ckeditor.basePath");
		if(!basePath.endsWith(basePath))
			basePath += "/";
		return basePath;
	}
}
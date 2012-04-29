/*
 * ckeditor.java - A Java backend for the CKEditor (http://ckeditor.com).
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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import de.thischwa.ckeditor.util.BrowserCompatibility;
import de.thischwa.ckeditor.util.StringUtils;
import de.thischwa.ckeditor.util.XHtmlTagTool;

/**
 * The object-oriented representation of the <a href="http://ckeditor.com">CKEditor</a>. 
 * It can be configured as any other JavaBean type class. The final output of this class is HTML code.<br />
 */
public class CKEditor {
	private HttpServletRequest request;
	private String instanceName;
	private String fieldID = null;

	// editor defaults
	private String value = StringUtils.EMPTY_STRING;
	private String toolbar = CKPropertiesLoader.getEditorToolbar();
	private String width = CKPropertiesLoader.getEditorWidth();
	private String height = CKPropertiesLoader.getEditorHeight();
	private String basePath = CKPropertiesLoader.getEditorBasePath();
	private String textAreaCols = CKPropertiesLoader.getEditorTextareaCols();
	private String textAreaRows = CKPropertiesLoader.getEditorTextareaRows();

	private boolean isCompatible;
	private CKEditorConfig config;

	/**
	 * Basic class constructor.
	 * 
	 * @param request The current {@link HttpServletRequest}.
	 * @param instanceName The unique name of this editor.
	 * @throws IllegalArgumentException If 'request' is null or if 'instanceName' is empty or not a valid XHTML id.
	 */
	public CKEditor(final HttpServletRequest request, final String instanceName) throws IllegalArgumentException {
		if (request == null)
			throw new IllegalArgumentException("The request parameter cannot be null!");
		if (StringUtils.isNullOrEmpty(instanceName))
			throw new IllegalArgumentException("instanceName cannot be empty");
		this.request = request;
		
		config = new CKEditorConfig();
		config.put("toolbar", toolbar); // set the default toolbar

		isCompatible = BrowserCompatibility.isCompatibleBrowser(request);
		if(!isCompatible)
			return;

		if (!instanceName.matches("\\p{Alpha}[\\p{Alnum}:_.-]*"))
			throw new IllegalArgumentException("instanceName must be a valid XHTML id containing only \"\\p{Alpha}[\\p{Alnum}:_.-]*\"");
		this.instanceName = instanceName;
	}

	/**
	 * Class constructor. 
	 * 
	 * @param request The current {@link HttpServletRequest}.
	 * @param instanceName The unique name of this editor.
	 * @param value The initial value to be edit as HTML markup.
	 * @throws IllegalArgumentException If 'request' is null or if 'instanceName' is empty or not a valid XHTML id.
	 */
	public CKEditor(final HttpServletRequest request, final String instanceName, final String value) throws IllegalArgumentException {
		this(request, instanceName);
		this.value = value;
	}

	/**
	 * Class constructor with all parameters.
	 * 
	 * @param request The current {@link HttpServletRequest}.
	 * @param instanceName The unique name of this editor.
	 * @param height The desired editor height (CSS-style value).
	 * @param width The desired editor width (CSS-style value).
	 * @param value The initial value to be edit as HTML markup.
	 * @throws IllegalArgumentException If 'request' is null or if 'instanceName' is empty or not a valid XHTML id.
	 */
	public CKEditor(final HttpServletRequest request, final String instanceName, final int height, final int width, final String value) throws IllegalArgumentException {
		this(request, instanceName, value);
		setSize(width, height);
	}

	/**
	 * Setter for the initial value to be edit as HTML markup.
	 * 
	 * @param value The value to be edit.
	 */ 
	public void setValue(final String value) {
		this.value = value;
	}

	/**
	 * Register the name to use for the id-tag of the underlying textarea element.
	 */
	public void setFieldID(final String fieldID) {
		if (StringUtils.isNullOrEmptyOrBlank(fieldID))
			this.fieldID = instanceName;
		else
			this.fieldID = fieldID;
	}
	
	/**
	 * Generate information about the compatibility of the editor in HTML.
	 * 
	 * @return Compatibility information in HTML.
	 */
	public String getCompatibilityInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append(new XHtmlTagTool("p", "CKEditor is compatible with the following browsers:"));
		sb.append("\n<ul>\n");
		for(String engine : BrowserCompatibility.getCompatibleBrowsers().keySet()) {
			sb.append(String.format("<li><strong>%s: </strong>%s or higher</li>\n", engine, BrowserCompatibility.getCompatibleBrowsers().get(engine)));
		}
		sb.append("\n</ul>");
		return sb.toString();
	}
	
	/**
	 * Return whether the current browser is compatible with the editor (true) or not (false).
	 */
	public boolean isCompatible() {
		return isCompatible;
	}
	
	/**
	 * Register the size of the underlying textarea element.
	 * 
	 * @param width The width of the textarea element.
	 * @param height The height of the textarea element.
	 */
	public void setSize(final int width, final int height) {
		this.width = String.valueOf(width);
		this.height = String.valueOf(height);
	}
	
	/**
	 * Obtain the value for the desired property key.
	 * 
	 * @param name The name of the parameter (case-sensitive).
	 * @return The value represented by the desired key, or null.
	 */
	public String getProperty(String name) {
		return config.get(name);
	}
	
	/**
	 * Register the desired property.
	 * 
	 * @param key The key of the property.
	 * @param value The value of the property.
	 */
	public void setProperty(final String key, final String value) {
		config.put(key, value);
	}

	/**
	 * @param name The name of the toolbar.
	 */
	public void setToolbarName(final String name) {
		if(StringUtils.isNullOrEmptyOrBlank(name))
			config.remove("toolbar");
		else
			config.put("toolbar", name);
	}

	public void setToolbarDefinition(final String name, final String definition) {
		String toolbarName = (name.startsWith("toolbar_")) ? name : "toolbar_".concat(name);
		config.put(toolbarName, definition);
	}
	
	public Map<String, String> getProperties() {
		return config.getUnmodifiableProperties();
	}

	public String createHtml() {
		return toString();
	}

	@Override
	public String toString() {
		if(!isCompatible) 
			return null;
			
		setFieldID(fieldID);
		StringBuilder sb = new StringBuilder();

		// build js to load the editor
		sb.append(String.format("<script type=\"text/javascript\" src=\"%s%sckeditor.js\"></script>\n", 
				request.getContextPath(), basePath)); 

		// build the textarea
		sb.append(buildTextArea());
		sb.append("\n\n");

		// build the in-page configuration
		String inPageConfig = buildConfig();
		sb.append(suroundScript(inPageConfig));
		return sb.toString();
	}

	private String buildConfig() {
		String configStr = config.buildJSON();
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("CKEDITOR.replace( '%s', %s)", instanceName, configStr));
		return sb.toString();
	}

	private String buildTextArea() {
		XHtmlTagTool textAreaTag = new XHtmlTagTool("textarea", value);
		textAreaTag.addAttribute("rows", textAreaRows);
		textAreaTag.addAttribute("cols", textAreaCols);
		textAreaTag.addAttribute("id", fieldID);
		textAreaTag.addAttribute("name", instanceName);
		textAreaTag.addAttribute("wrap", "virtual");
		textAreaTag.addAttribute("style", String.format("width: %s; height: %s", width, height));
		return textAreaTag.toString();
	}

	private String suroundScript(String js) {
		String script = String.format("<script type=\"text/javascript\">//<![CDATA[\n%s\n//]]></script>\n", js);
		return script;
	}

}

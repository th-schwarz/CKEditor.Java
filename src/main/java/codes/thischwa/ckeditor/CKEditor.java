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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import codes.thischwa.ckeditor.util.BrowserCompatibility;
import codes.thischwa.ckeditor.util.StringUtils;
import codes.thischwa.ckeditor.util.XHtmlTagTool;

/**
 * The object-oriented representation of the <a href="http://ckeditor.com">CKEditor</a>. 
 * It can be configured as any other JavaBean type class. The final output of this class is HTML code.
 */
public class CKEditor {
	
	private HttpServletRequest request;
	
	/** 'className' of the element */
	private String instanceName;
	private String fieldID = null;

	// editor defaults
	private String value = StringUtils.EMPTY_STRING;
	private String width = CKPropertiesLoader.getProperty("width");
	private String height = CKPropertiesLoader.getProperty("height");
	private String basePath = CKPropertiesLoader.getEditorBasePath();
	
	private boolean useDiv;

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
		
		// setting the defaults
		config = new CKEditorConfig();
		config.putAll(CKPropertiesLoader.getAllEditorProperties());

		isCompatible = BrowserCompatibility.isCompatibleBrowser(request);
		if(!isCompatible)
			return;

		if (!instanceName.matches("\\p{Alpha}[\\p{Alnum}:_.-]*"))
			throw new IllegalArgumentException("instanceName must be a valid XHTML id containing only \"\\p{Alpha}[\\p{Alnum}:_.-]*\"");
		this.instanceName = instanceName;
	}

	/**
	 * Minimal constructor. 
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
	public CKEditor(final HttpServletRequest request, final String instanceName, final String height, final String width, final String value) throws IllegalArgumentException {
		this(request, instanceName, value);
		setSize(width, height);
	}

	/**
	 * Set the initial value to be edit as HTML markup.
	 * 
	 * @param value The value to be edit.
	 */ 
	public void setValue(final String value) {
		this.value = StringUtils.isNullOrEmptyOrBlank(value) ? XHtmlTagTool.SPACE : value;
	}

	/**
	 * Register the name to use for the id-tag of the underlying textarea element.
	 * 
	 * @param fieldID The name for the id-tag. If it is <code>null</code>, the <code>instanceName</code> of the editor is used.
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
	 * Defines which html-element will be used to construct the editor.
	 *  
	 * @param enabled if <code>true</code> a 'div'-element, otherwise a 'textarea'-element will be built.
	 */
	public void useDiv(boolean enabled) {
		useDiv = enabled;
	}
	
	/**
	 * Calling this method ensures a 'div' is building instead of a 'textarea'. <p>
	 * Just a wrapper for {@link #useDiv(boolean)}.
	 */
	public void useDiv() {
		useDiv(true);
	}
	
	/**
	 * Register the size of the underlying textarea element.
	 * 
	 * @param width The width of the textarea element, (CSS-style value).
	 * @param height The height of the textarea element, (CSS-style value).
	 */
	public void setSize(final String width, final String height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Obtain the value for the property key.
	 * 
	 * @param name The name of the property (case-sensitive).
	 * @return The value represented by the desired key, or null.
	 */
	public String getProperty(String name) {
		return config.get(name);
	}
	
	/**
	 * Register the desired frontend property.
	 * 
	 * @param key The key of the property (case-sensitive).
	 * @param value The value of the property.
	 */
	public void setProperty(final String key, final String value) {
		config.put(key, value);
	}

	/**
	 * Remove the property represented by the key.
	 * 
	 * @param key the key of the property to delete.
	 */
	public void removeProperty(final String key) {
		config.remove(key);
	}
	
	/**
	 * Construct an unmodifiable map of all properties.
	 * 
	 * @return Unmodifiable map of all properties.
	 */
	public Map<String, Object> getProperties() {
		return config.getUnmodifiableProperties();
	}

	/**
	 * Build the html/javascript code for using the editor in a web environment. 
	 * 
	 * @return The code of the editor or <code>null</code> if the current browser isn't compatible.
	 */
	@Override
	public String toString() {
		if(!isCompatible) 
			return null;
			
		setFieldID(fieldID);
		StringBuilder sb = new StringBuilder();

		// build the textarea
		sb.append(buildElement());

		// build js to load the editor
		sb.append(String.format("<script type=\"text/javascript\" src=\"%s%sckeditor.js\"></script>\n", 
				request.getContextPath(), basePath)); 
		
		sb.append("\n\n");

		// build the in-page configuration
		String inPageConfig = buildConfig();
		sb.append(suroundScriptTag(inPageConfig));
		return sb.toString();
	}

	private String buildConfig() {
		String configStr = config.buildJSON();
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("CKEDITOR.replace( '%s', %s );", instanceName, configStr));
		return sb.toString();
	}

	private String buildElement() {
		String elementName = useDiv ? "div" : "textarea";
		XHtmlTagTool elementTag = new XHtmlTagTool(elementName, value);
		elementTag.addAttribute("id", fieldID);
		elementTag.addAttribute("name", instanceName);
		if(!StringUtils.isNullOrEmptyOrBlank(height) && !StringUtils.isNullOrEmptyOrBlank(width))
			elementTag.addAttribute("style", String.format("width: %s; height: %s", width, height));
		return elementTag.toString();
	}

	private String suroundScriptTag(String js) {
		String script = String.format("<script type=\"text/javascript\">//<![CDATA[\n%s\n//]]></script>\n", js);
		return script;
	}
}

/*
 * CKEditor.java - Java backend of CKEditor (http://ckeditor.com). 
 * It provides a simple object for creating an editor instance.
 * Copyright (C) Thilo Schwarz
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package codes.thischwa.ckeditor.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Java representation of an XHTML tag.
 * <p>
 * Usage:
 * <pre>
 * XHtmlTagTool tag = new XHtmlTagTool("a", "link to google");
 * tag.addAttribute("href", "http://google.com");
 * tag.toString(); // builds: &lt;a href="http://google.com"&gt;link to google&lt;/a&gt;
 * </pre>
 * </p>
 * 
 * <em>Note</em>:
 * <ul>
 * <li>Attributes are not ordered.</li>
 * <li>If you want to avoid self-closing tags without supplying a value, set {@link #SPACE} as the tag's value.</li>
 * </ul>
 */
public class XHtmlTagTool {

	/** Indicator to uses non self-closing tag. */
	public static final String SPACE = " ";

	/** Name of the tag. */
	private String name;

	/** Container for the attributes. */
	private Map<String, String> attributes;

	/** Value of the tag. */
	private String value;

	/**
	 * Class constructor with tag name.
	 * 
	 * @param name
	 *            the tag name of the new XHtmlTagTool
	 * @throws IllegalArgumentException
	 *             if <code>name</code> is empty
	 */
	public XHtmlTagTool(final String name) {
		if (StringUtils.isNullOrEmptyOrBlank(name))
			throw new IllegalArgumentException("Parameter 'name' shouldn't be empty!");
		this.name = name;
		this.attributes = new HashMap<>();
	}

	/**
	 * Class constructor with name and value.
	 * 
	 * @param name
	 *            the tag name of the new XHtmlTagTool
	 * @param value
	 *            the tag value of the new XHtmlTagTool which is the tag body
	 * @throws IllegalArgumentException
	 *             if <code>name</code> is empty
	 */
	public XHtmlTagTool(final String name, final String value) {
		this(name);
		this.value = value;
	}

	/**
	 * Sets the tag value.
	 * 
	 * @param value
	 *            the tag value which is the tag body
	 */
	public void setValue(final String value) {
		this.value = value;
	}

	/**
	 * Adds an attribute to this tag.
	 * 
	 * @param name
	 *            attribute name
	 * @param value
	 *            attribute value
	 * @throws IllegalArgumentException
	 *             if <code>name</code> is empty
	 */
	public void addAttribute(final String name, final String value) {
		if (StringUtils.isNullOrEmptyOrBlank(name))
			throw new IllegalArgumentException("Name is null or empty");
		attributes.put(name, value);
	}

	/**
	 * Creates the HTML representation of this tag. It follows the XHTML standard.
	 * 
	 * @return HTML representation of this tag
	 */
	@Override
	public String toString() {
		StringBuilder tag = new StringBuilder();

		// open tag
		tag.append("<").append(name);

		// add attributes
		for (String key : attributes.keySet()) {
			String val = attributes.get(key);
			tag.append(' ').append(key).append('=').append('\"').append(val).append('\"');
		}

		// close the tag
		if (!StringUtils.isNullOrEmpty(value)) {
			tag.append(">").append(value).append("</").append(name).append('>');
		} else
			tag.append(" />");

		return tag.toString();
	}

}
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
package de.thischwa.ckeditor.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Java representation of an XHTML tag.
 * <p>
 * Usage:
 * <pre>
 * XHtmlTagTool tag = new XHtmlTagTool(&quot;a&quot;, &quot;link&quot;);
 * tag.addAttribute(&quot;href&quot;, &quot;http://google.com&quot;);
 * tag.toString(); // builds: &lt;a href=&quot;http://google.com&quot;&gt;link&lt;/a&gt;
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
		this.attributes = new HashMap<String, String>();
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
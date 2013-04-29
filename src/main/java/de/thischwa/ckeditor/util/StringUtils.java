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
 *  - GNU Lesser General Public License Version 3.0 or later (the "LGPL")
 *    http://www.gnu.org/licenses/lgpl-3.0.html
 * 
 *  - Mozilla Public License Version 2.0 or later (the "MPL")
 *    http://www.mozilla.org/MPL/2.0/
 * 
 * == END LICENSE ==
 */
package de.thischwa.ckeditor.util;


/**
 * Some static helper methods.
 */
public class StringUtils {
	
	/** The empty string {@code ""}. */
	public static final String EMPTY_STRING = "";

	public static final boolean isNullOrEmpty(final String str) {
		return (str == null || str.length() == 0);
	}

	public static final boolean isNullOrEmptyOrBlank(final String str) {
		if (isNullOrEmpty(str))
			return true;
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isWhitespace(str.charAt(i)))
				return false;
		}
		return true;
	}
}

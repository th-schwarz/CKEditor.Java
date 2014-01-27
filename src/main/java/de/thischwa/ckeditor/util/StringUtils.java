/*
 * CKEditor.java - Java backend of CKEditor (http://ckeditor.com). 
 * It provides a simple object for creating an editor instance.
 * Copyright (C) Thilo Schwarz
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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

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
package de.thischwa.ckeditor.util;

import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class BrowserCompatibility {

	private static Map<String, String> compatibleBrowsers;

	static {
		SortedMap<String, String> browsers = new TreeMap<String, String>();
		browsers.put("gecko", "Firefox 2.0");
		browsers.put("ie", "Internet Explorer 6.0");
		browsers.put("opera", "Opera 9.5");
		browsers.put("webkit", "Safari 3.0");
		compatibleBrowsers = Collections.unmodifiableSortedMap(browsers);
	}

	/**
	 * Checks if a browser's user agent string is compatible with the FCKeditor.
	 * 
	 * @param userAgentString
	 *            the user agent string of a browser
	 * @return <code>true</code> if compatible, else <code>false</code>
	 */
	protected static boolean check(final String userAgentString) {
		if (StringUtils.isNullOrEmptyOrBlank(userAgentString))
			return false;

		String userAgentStr = userAgentString.toLowerCase();

		// IE 6+, check special keys like 'Opera' and 'mac', because there are
		// some other browsers, containing 'MSIE' in there agent string!
		if (userAgentStr.indexOf("opera") < 0 && userAgentStr.indexOf("mac") < 0
				&& getBrowserVersion(userAgentStr, ".*msie ([\\d]+.[\\d]+).*") >= 6f)
			return true;

		// for all gecko based browsers
		if (getBrowserVersion(userAgentStr, ".*rv:([\\d]+.[\\d]+).*") > 1.7f)
			return true;

		// Opera 9.5+
		if (getBrowserVersion(userAgentStr, "opera/([\\d]+.[\\d]+).*") >= 9.5f
				|| getBrowserVersion(userAgentStr, ".*opera ([\\d]+.[\\d]+)") >= 9.5f)
			return true;

		// Safari 3+
		if (getBrowserVersion(userAgentStr, ".*applewebkit/([\\d]+).*") >= 522f)
			return true;

		return false;
	}

	/**
	 * Returns <code>true</code> if a browser is compatible by its request user-agent header.
	 * 
	 * @see #check(String)
	 * @param request
	 *            current user request instance
	 * @return <code>true</code> if a browser is compatible, else <code>false</code>
	 */
	public static boolean isCompatibleBrowser(final HttpServletRequest request) {
		return (request == null) ? false : check(request.getHeader("user-agent"));
	}

	/**
	 * Helper method to get the browser version from 'userAgent' with regex. The first matching group has to be the version number!
	 * 
	 * @param userAgentString
	 *            the user agent string of a browser
	 * @param regex
	 *            the pattern to retrieve the browser version
	 * @return the browser version, or -1f if version can't be determined
	 */
	private static float getBrowserVersion(final String userAgentString, final String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(userAgentString);
		if (matcher.matches()) {
			try {
				return Float.parseFloat(matcher.group(1));
			} catch (NumberFormatException e) {
				return -1f;
			}
		}
		return -1f;
	}

	/**
	 * Returns a {@link Map} of minimum browser requirements. <code>Key</code> is the name of the underlying engine.
	 * 
	 * @return A {@link Map} of minimum browser requirements.
	 */
	public static Map<String, String> getCompatibleBrowsers() {
		return compatibleBrowsers;
	}
}
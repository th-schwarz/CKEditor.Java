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
package de.thischwa.ckeditor.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for {@link BrowserCompatibility}.<br />
 * User-Agent-Strings are taken from: http://www.useragentstring.com/pages/useragentstring.php
 */
public class BrowserCompatibilityTest {

	@Test
	public void testIE4Win() throws Exception {
	    assertFalse(BrowserCompatibility.check("Mozilla/4.0 (compatible; MSIE 4.01; Windows NT 5.0)"));
    }
	
	@Test
	public void testIE522Mac() throws Exception {
	    assertFalse(BrowserCompatibility.check("Mozilla/4.0 (compatible; MSIE 5.22; Mac_PowerPC)"));
    }
	
	@Test
	public void testIE55Win98() throws Exception {
	    assertFalse(BrowserCompatibility.check("Mozilla/4.0 (compatible; MSIE 5.5; Windows 98)"));
    }

	@Test
	public void testIE60WinNT() throws Exception {
	    assertFalse(BrowserCompatibility.check("Mozilla/4.0 (compatible; MSIE 6.0; MSIE 5.5; Windows NT 5.1)"));
    }
	
	@Test
	public void testIE60b() {
		assertTrue(BrowserCompatibility.check("Mozilla/4.0 (compatible; MSIE 6.0b; Windows NT 5.1)"));
	}
	
	@Test
	public void testIE70WinVista() throws Exception {
	    assertTrue(BrowserCompatibility.check("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0;)"));
    }

	@Test
	public void testIE70bWinVista() throws Exception {
	    assertTrue(BrowserCompatibility.check("Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 6.0)"));
    }

	@Test
	public void testIE80WinVista() throws Exception {
	    assertTrue(BrowserCompatibility.check("Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 1.0.3705; .NET CLR 1.1.4322)"));
    }
	
	@Test
	public void testSafari204OSX() throws Exception {
	    assertFalse(BrowserCompatibility.check("Mozilla/5.0 (Macintosh; U; PPC Mac OS X; sv-se) AppleWebKit/419 (KHTML, like Gecko) Safari/419.3"));
    }

	@Test
	public void testSafari30WinXP() throws Exception {
	    assertTrue(BrowserCompatibility.check("Mozilla/5.0 (Windows; U; Windows NT 5.2; pt) AppleWebKit/522.11.3 (KHTML, like Gecko) Version/3.0 Safari/522.11.3"));
    }

	@Test
	public void testSafari302OSX() throws Exception {
	    assertTrue(BrowserCompatibility.check("Mozilla/5.0 (Macintosh; U; Intel Mac OS X; en) AppleWebKit/522+ (KHTML, like Gecko) Version/3.0.2 Safari/522.12"));
    }

	@Test
	public void testSafari31OSX() throws Exception {
	    assertTrue(BrowserCompatibility.check("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_2; en-au) AppleWebKit/525.8+ (KHTML, like Gecko) Version/3.1 Safari/525.6"));
    }

	@Test
	public void testFirefox200112OSX() throws Exception {
	    assertTrue(BrowserCompatibility.check("Mozilla/5.0 (Macintosh; U; Intel Mac OS X; en-US; rv:1.8.1.12) Gecko/20080201 Firefox/2.0.0.12"));
    }
	
	@Test
	public void testFirefox30Linux() throws Exception {
		assertTrue(BrowserCompatibility.check("Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9pre) Gecko/2008040318 Firefox/3.0pre (Swiftfox)"));
	}

	@Test
	public void testFirefox1509LinuxDebian() throws Exception {
	    assertTrue(BrowserCompatibility.check("Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.9) Gecko/20071025 Firefox/1.5.0.9 (Debian-2.0.0.9-2)"));
    }
	
	@Test
	public void testOper925Win2k() throws Exception {
	    assertFalse(BrowserCompatibility.check("Opera/9.25 (Windows NT 5.0; U; en)"));
    }
	
	@Test
	public void testOpera854WinNT() throws Exception {
	    assertFalse(BrowserCompatibility.check("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; de) Opera 8.54"));
    }
	
	@Test
	public void testOpera95OSX() throws Exception {
		assertTrue(BrowserCompatibility.check("Opera/9.50 (Macintosh; Intel Mac OS X; U; en)"));   
    }
	
	@Test
	public void testOpera95WinVista() throws Exception {
		assertTrue(BrowserCompatibility.check("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 6.0; en) Opera 9.50"));   
    }
	
	@Test
	public void testEpaphany220Linux() throws Exception {
		assertTrue(BrowserCompatibility.check("Mozilla/5.0 (X11; U; Linux i686; en; rv:1.9b3) Gecko Epiphany/2.20"));
	}
}

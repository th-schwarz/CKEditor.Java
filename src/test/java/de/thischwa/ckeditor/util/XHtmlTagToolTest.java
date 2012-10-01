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

import de.thischwa.ckeditor.util.XHtmlTagTool;

/**
 * Tests for {@link XHtmlTagTool}.
 *
 * @version $Id$
 */
public class XHtmlTagToolTest {

	@Test
	public void testClosingTag01() throws Exception {
	    XHtmlTagTool tag = new XHtmlTagTool("test");
	    assertEquals("<test />", tag.toString());
    }
	
	@Test
	public void testClosingTag02() throws Exception {
	    XHtmlTagTool tag = new XHtmlTagTool("test", "");
	    assertEquals("<test />", tag.toString());
    }

	@Test
	public void testClosingTag03() throws Exception {
	    XHtmlTagTool tag = new XHtmlTagTool("test", "val");
	    assertEquals("<test>val</test>", tag.toString());
    }
	
	@Test
	public void testClosingTag04() throws Exception {
	    XHtmlTagTool tag = new XHtmlTagTool("test", XHtmlTagTool.SPACE);
	    assertEquals("<test> </test>", tag.toString());
    }
	
	@Test
	public void testFull() throws Exception {
		XHtmlTagTool tag = new XHtmlTagTool("a", "link");
		 tag.addAttribute("href", "http://google.com");
		 assertEquals("<a href=\"http://google.com\">link</a>", tag.toString());
	}
}

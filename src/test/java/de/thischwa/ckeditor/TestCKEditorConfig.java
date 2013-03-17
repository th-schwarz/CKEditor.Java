package de.thischwa.ckeditor;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCKEditorConfig {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBuildJSON() {
		CKEditorConfig conf = new CKEditorConfig();
		conf.put("key", "value");
		assertEquals("{ key : 'value' }", conf.buildJSON());
	}

}

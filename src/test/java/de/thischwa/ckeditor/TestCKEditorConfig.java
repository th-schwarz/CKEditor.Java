package de.thischwa.ckeditor;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCKEditorConfig {

	@Test
	public void testBuildJSON01() {
		CKEditorConfig conf = new CKEditorConfig();
		assertEquals("{}", conf.buildJSON());
	}
	
	@Test
	public void testBuildJSON02() {
		CKEditorConfig conf = new CKEditorConfig();
		conf.put("key", "value");
		assertEquals("{ key : 'value' }", conf.buildJSON());
	}

}

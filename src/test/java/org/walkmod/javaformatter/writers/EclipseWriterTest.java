package org.walkmod.javaformatter.writers;

import java.io.File;

import org.apache.ivy.util.FileUtil;
import org.junit.Assert;
import org.junit.Test;
import org.walkmod.walkers.AbstractWalker;
import org.walkmod.walkers.VisitorContext;

public class EclipseWriterTest {

	@Test
	public void testSpacesInsteadOfTabs() throws Exception {
		EclipseWriter ew = new EclipseWriter();
		VisitorContext ctx = new VisitorContext();
		File outputDir = new File("src/test/output");
		outputDir.mkdirs();
		File output = new File(outputDir, "Foo.java");

		ctx.put(AbstractWalker.ORIGINAL_FILE_KEY, output);

		ew.write("\t public class Foo {\n\tint a = 0;\n int b = 0;}", ctx);

		ew.close();

		String code = FileUtil.readEntirely(output);

		output.delete();
		outputDir.delete();
		Assert.assertFalse(code.contains("\t"));

	}

	@Test
	public void testFormatterConfig() throws Exception {
		EclipseWriter ew = new EclipseWriter();
		ew.setConfigFile("src/test/resources/eclipse-style.xml");

		VisitorContext ctx = new VisitorContext();
		File outputDir = new File("src/test/output");
		outputDir.mkdirs();
		File output = new File(outputDir, "Foo.java");

		ctx.put(AbstractWalker.ORIGINAL_FILE_KEY, output);

		ew.write("public class Foo {\n\tint a = 0;\n int b = 0;}", ctx);

		ew.close();

		String code = FileUtil.readEntirely(output);

		output.delete();
		outputDir.delete();
		Assert.assertTrue(code.contains("\t"));
	}
	
	@Test
	public void testLambdaFormatConfig() throws Exception{
		EclipseWriter ew = new EclipseWriter();
		ew.setConfigFile("src/test/resources/eclipse-style.xml");

		VisitorContext ctx = new VisitorContext();
		File outputDir = new File("src/test/output");
		outputDir.mkdirs();
		File output = new File(outputDir, "Foo.java");

		ctx.put(AbstractWalker.ORIGINAL_FILE_KEY, output);

		ew.write("public class Foo {\n\tObject o = s -> String::length;\n int b = 0;}", ctx);

		ew.close();

		String code = FileUtil.readEntirely(output);

		output.delete();
		outputDir.delete();
		Assert.assertTrue(code.contains("o = s-> String"));
	}

}

package org.datatables4j.compressor;

import org.datatables4j.exception.CompressionException;

import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.JSSourceFile;

public class GoogleClosureCompressor implements ResourceCompressor {

	@Override
	public String getCompressedJavascript(String input) throws CompressionException {
		Compiler compiler = new Compiler();

	    CompilerOptions options = new CompilerOptions();
	    // Advanced mode is used here, but additional options could be set, too.
	    CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(
	        options);

	    // To get the complete set of externs, the logic in
	    // CompilerRunner.getDefaultExterns() should be used here.
	    JSSourceFile extern = JSSourceFile.fromCode("externs.js",
	        "function alert(x) {}");

	    // The dummy input name "input.js" is used here so that any warnings or
	    // errors will cite line numbers in terms of input.js.
	    JSSourceFile input2 = JSSourceFile.fromCode("input.js", input);

	    // compile() returns a Result, but it is not needed here.
	    compiler.compile(extern, input2, options);

	    // The compiler is responsible for generating the compiled code; it is not
	    // accessible via the Result.
	    return compiler.toSource();
	}

	@Override
	public String getCompressedCss(String input) throws CompressionException {
		// TODO Auto-generated method stub
		return null;
	}

}

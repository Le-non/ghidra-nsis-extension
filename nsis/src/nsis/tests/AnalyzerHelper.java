package nsis.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import ghidra.app.util.bin.ByteArrayProvider;
import ghidra.app.util.headless.HeadlessAnalyzer;

/**
 * Class to launch the analyzer using Ghidra headless mode. Useful to debug the
 * SLEIGH or anything used in the NsisAnalyzer without having to load the UI.
 *
 */
public class AnalyzerHelper {

	public static String filePath = "/usr/local/google/home/ldutil/Documents/ghidra-nsis-extension/nsis/src/testData";
	public static String fileName = "/nsis_without_compression.dat";
	public static String temporaryDirectoryName = "ghidra_test";

	public static void main(String[] args) throws IOException {
		
		InputStream test = new FileInputStream(new File(filePath + fileName));
		ByteArrayProvider deobfuscated = NsisExecutableTest.deobfuscate(test);
		InputStream testFile = deobfuscated.getInputStream(0);
		List<File> filesToImport = new ArrayList<File>();
		filesToImport.add(new File(testFile));
		Path temporaryDirectory = Files.createTempDirectory(temporaryDirectoryName);
		HeadlessAnalyzer analyzer = HeadlessAnalyzer.getInstance();
		analyzer.processLocal(temporaryDirectory.toString(), "headless_test", filePath,
				filesToImport);
	}
}

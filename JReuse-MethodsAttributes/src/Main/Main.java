package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import Model.FieldDeclarationVisitor;
import Model.FindSimilarity;
import Model.InsertAttributes;
import Model.InsertClass;
import Model.InsertMethod;
import Model.MethodDeclarationVisitor;
import Model.Parameters;
import Model.TypeDeclarationVisitor;
import Util.Log;
import Util.RemoveCaractere;

public class Main {

	private static int cont = 1;
	private static String nameClass;

	public static String parse(String str, File source) throws IOException {

		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		String[] classpath = java.lang.System.getProperty("java.class.path").split(";");
		String[] sources = { source.getParentFile().getAbsolutePath() };

		Hashtable<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);
		options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
		parser.setUnitName(source.getAbsolutePath());

		parser.setCompilerOptions(options);
		parser.setEnvironment(classpath, sources, new String[] { "UTF-8" }, true);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);

		final CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

		Parameters parameters = new Parameters();
		
		MethodDeclarationVisitor visitorMethod = new MethodDeclarationVisitor(compilationUnit);
		FieldDeclarationVisitor visitorField = new FieldDeclarationVisitor();
		
		visitorMethod.parametersMethod= parameters;
		visitorField.parametersField= parameters;


		compilationUnit.accept(visitorMethod);
		compilationUnit.accept(visitorField);
		
		try {
			
			String caminhoA = source.getPath().toString().toLowerCase().replace("\\", "/");
			String[] caminhoPedacoA = caminhoA.split("/");
			
			RemoveCaractere remov = new RemoveCaractere();
			nameClass=remov.removeCaracteres(source.getName().toLowerCase(), ".java", "");
			
			parameters.setNameClass(nameClass);
			parameters.setNameProject(caminhoPedacoA[3]);
			parameters.setAbsolutePath(source.getAbsolutePath());

			InsertClass includeClass = new InsertClass();
			InsertMethod includeMethod = new InsertMethod();
			InsertAttributes includeAttribute = new InsertAttributes();
						
			includeClass.insertClass(parameters);
			includeMethod.insertMethod(parameters);
			includeAttribute.insertAttribute(parameters);
			
		} catch (Exception e) {
			// Caso tenha uma exceção printa na tela
			e.printStackTrace();
			Log.log(source.getParentFile() + " erro SQL" + e + " " + cont);
		}

		return  source.getAbsolutePath() 		+ "," + // endereco absoluto da classe
				source.getName() 				+ "," + // nome da classe
				parameters.getNameAttribute() 	+ "," + 
				parameters.getTypeAttribute();
	}

	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}

		reader.close();
		return fileData.toString();
	}

	public static void parseFilesInDir(File file, PrintWriter writeCSVFieldProject) throws IOException {

		if (file.isFile()) {
			if (file.getName().endsWith(".java")) {
				String line = parse(readFileToString(file.getAbsolutePath()), file);
				if (line != null) {
					writeCSVFieldProject.println(line);
				}
			}
		} else {
			for (File f : file.listFiles()) {
				parseFilesInDir(f, writeCSVFieldProject);
			}
		}
	}

	public static void main(String[] args) throws IOException {

		FileReader fileProjects = new FileReader("Projects2.txt");
		BufferedReader readFile = new BufferedReader(fileProjects);
		try {
			String project = readFile.readLine();
			while (project != null) {

				FileWriter csvFieldProject = new FileWriter("../.." + File.separator + "projects" + File.separatorChar + project + "_external.csv");
				PrintWriter writeCSVFieldProject = new PrintWriter(csvFieldProject);
				String endereco = "../.." + File.separator + "projects" + File.separatorChar + project;
				parseFilesInDir(new File(endereco), writeCSVFieldProject);

				csvFieldProject.close();

				project = readFile.readLine();
			}
			FindSimilarity findSim = new FindSimilarity();// calcula a
															// similaridade
			findSim.similarity();

		} catch (IOException e) {
			System.err.println(e);
		}
	}

}

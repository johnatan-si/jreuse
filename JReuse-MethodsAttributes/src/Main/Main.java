package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import Model.FieldDeclarationVisitor;
import Model.MethodDeclarationVisitor;
import Model.TypeDeclarationVisitor;


//import com.opencsv.CSVWriter;


public class Main {

	public static String parse(String str, File source) throws IOException {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		String[] classpath = java.lang.System.getProperty("java.class.path").split(";");
		String[] sources = { source.getParentFile().getAbsolutePath() };

		Hashtable<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM,JavaCore.VERSION_1_8);
		 options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
		parser.setUnitName(source.getAbsolutePath());
		
		parser.setCompilerOptions(options);
		parser.setEnvironment(classpath, sources, new String[] { "UTF-8" },
				true);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);

		final CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		

		FieldDeclarationVisitor visitorField = new FieldDeclarationVisitor();
		TypeDeclarationVisitor visitorType = new TypeDeclarationVisitor();
		MethodDeclarationVisitor visitorMethod = new MethodDeclarationVisitor();
		compilationUnit.accept(visitorField);
		compilationUnit.accept(visitorType);
		compilationUnit.accept(visitorMethod);
			
		return //source.getAbsolutePath() + "," +  // endereco da classe analisada
		
			visitorField.nameAtr+","+
			visitorField.tipo+","+
			
			visitorMethod.nameMethod.toString()+","+
			visitorMethod.getNumberMethods(); //+ "," + // numero de metodos
			
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
		//System.out.print(file.toString());
		//System.out.print(file.listFiles());
		
		if (file.isFile()) {
			if (file.getName().endsWith(".java")) {
				
				String line = parse(readFileToString(file.getAbsolutePath()), file);
				if (line != null) {
					writeCSVFieldProject.println(line);
			//		System.out.println(line);
				}
			}
		} else {
			for (File f : file.listFiles()) {
				///System.out.println(f.toString());
				parseFilesInDir(f, writeCSVFieldProject);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		//receive the projects list (args[0]) 
		//			Map<String, MethodParameters> map = calcMetrics("../.." + File.separator + "projects" + File.separator + project);

		FileReader fileProjects = new FileReader("Projects.txt");
		BufferedReader readFile = new BufferedReader(fileProjects);
		
		String project = readFile.readLine();
		while (project != null) {
			
			FileWriter csvFieldProject = new FileWriter("../.." + File.separator + "projects"+ File.separatorChar + project + "_external.csv");
			PrintWriter writeCSVFieldProject = new PrintWriter(csvFieldProject);
			String endereco= "../.." + File.separator + "projects"+ File.separatorChar + project;
			parseFilesInDir(new File(endereco), writeCSVFieldProject);
			
			csvFieldProject.close();
			
			project = readFile.readLine();
		}

		
	}

}

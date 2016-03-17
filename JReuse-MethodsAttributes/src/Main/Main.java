package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import Model.FieldDeclarationVisitor;
import Model.MethodDeclarationVisitor;
import Model.TypeDeclarationVisitor;
import Util.Conexao;


public class Main {

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

		FieldDeclarationVisitor visitorField = new FieldDeclarationVisitor();
		TypeDeclarationVisitor visitorType = new TypeDeclarationVisitor();
		MethodDeclarationVisitor visitorMethod = new MethodDeclarationVisitor(compilationUnit);

		compilationUnit.accept(visitorField);
		compilationUnit.accept(visitorType);
		compilationUnit.accept(visitorMethod);

		Connection conn = null;
		String sqlInsert = "INSERT INTO entities(method, class,visibility,type,loc,numberMethods,AbsolutePath) VALUES (?,?,?,?,?,?,?)";
		java.sql.PreparedStatement stm = null;
		Conexao bd = new Conexao();
		
		try {
			conn = bd.obtemConexao();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		try {
			conn.setAutoCommit(false);
			stm = conn.prepareStatement(sqlInsert);
			
			System.out.println("NOME: "+visitorMethod.getNameMethod());
			System.out.println("TAMANHO: "+visitorMethod.getNameMethod().size());
			
			
			//System.out.println(" classe "+source.getName());
			System.out.println("Visi "+visitorMethod.getVisib().size()); 
			System.out.println(" typ "+visitorMethod.getTyp().size() );
			System.out.println("loc "+visitorMethod.getLoc().size());
 		    //System.out.println(" qtdMetod "+visitorMethod.getNumberMethods());


			
			for (int i = 0; i < visitorMethod.getNameMethod().size(); i++) {
			
				stm.setString(1, visitorMethod.getNameMethod().get(i));// nome do method
				stm.setString(2, source.getName());// nome da classe
				stm.setString(3, visitorMethod.getVisib().get(i));// visibilidade do metodo 
				stm.setString(4, visitorMethod.getTyp().get(i));// tipo do método 
				stm.setInt	 (5, visitorMethod.getLoc().get(i));
				stm.setInt	 (6, visitorMethod.getNumberMethods());
				stm.setString(7, source.getAbsolutePath());// caminho do arquivo 

				stm.execute();
				conn.commit();// efetiva inclusoes
				System.out.println("Inclusão concluída "+i+"\n\r");
			}
			// stm.execute();
			// conn.commit();// efetiva inclusoes
			/*if (stm.execute()) {
				System.out.println("Inclusão concluída");
			}*/

		} catch (Exception e) {
			// Caso tenha uma exceção printa na tela
			e.printStackTrace();
			try {
				// Aqui ele 'tenta' retroceder, na ação que deu errado.
				// quase um Ctrl+Z da vida.
				conn.rollback();
			} catch (SQLException e1) {
				System.out.print(e1.getStackTrace());
			}
		} finally {
			if (stm != null) {
				try {
					// Encerra as operações.
					stm.close();
				} catch (SQLException e1) {
					System.out.print(e1.getStackTrace());
				}
			}
		}

		return source.getAbsolutePath() + "," + // endereco absoluto da classe
				source.getName() + "," + // nome da classe

		visitorMethod.getNameMethod() + "," + // nome do método
				visitorMethod.getVisib() + "," + visitorMethod.getTyp() + "," + visitorMethod.getLoc() + ","
				+ visitorMethod.getNumberMethods(); // qtd de métodos

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

		FileReader fileProjects = new FileReader("Projects.txt");
		BufferedReader readFile = new BufferedReader(fileProjects);
		try {
			String project = readFile.readLine();
			while (project != null) {

				FileWriter csvFieldProject = new FileWriter(
						"../.." + File.separator + "projects" + File.separatorChar + project + "_external.csv");
				PrintWriter writeCSVFieldProject = new PrintWriter(csvFieldProject);
				String endereco = "../.." + File.separator + "projects" + File.separatorChar + project;
				parseFilesInDir(new File(endereco), writeCSVFieldProject);

				csvFieldProject.close();

				project = readFile.readLine();
			}

		} catch (IOException e) {
			System.err.println(e);
		}
	}
}

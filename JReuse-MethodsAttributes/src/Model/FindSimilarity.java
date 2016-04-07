package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Util.Conexao;
import Util.Similarity;

public class FindSimilarity {

	private static float similarityMethod;
	private static float similarityClass;
	private static float similarityAttribute;
	private static int cont;

	public static void similarity() {

		String query = "SELECT  identities,nameMethod, nameattribute, nameClass, nameProject,AbsolutePath FROM  entities  ORDER BY nameclass ASC";

		PreparedStatement stm = null;
		Conexao bd = new Conexao();

		Connection conn = null;
		try {
			conn = bd.obtemConexao();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		try {

			//System.out.println("SEARCH FILES.... " + "\n\r");

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);

			if (!rs.next()) {
				System.out.println("TABLE entities IS EMPTY");
			}
			// iterate through the java resultset
			conn.setAutoCommit(false);

			ArrayList<String> FKentities = new ArrayList<String>();

			ArrayList<String> classes = new ArrayList<String>();
			ArrayList<String> metodo = new ArrayList<String>();
			ArrayList<String> attribute = new ArrayList<String>();
			ArrayList<String> nameProject = new ArrayList<String>();
			ArrayList<String> pathProjetc = new ArrayList<String>();
			
			int cont=1;
			
			while (rs.next()) {

				FKentities.add(rs.getString("identities"));
				classes.add(rs.getString("nameClass"));
				metodo.add(rs.getString("nameMethod"));
				attribute.add(rs.getString("nameattribute"));

				nameProject.add(rs.getString("nameProject"));
				pathProjetc.add(rs.getString("AbsolutePath"));
				cont++;
			}
			buscaresultado(classes, metodo, attribute, FKentities, nameProject,pathProjetc,cont);

		} catch (Exception e) {
			// Caso tenha uma exceção printa na tela
			e.printStackTrace();
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
	}

	private static void buscaresultado(ArrayList<String> classes, ArrayList<String> method, ArrayList<String> attribute,
			ArrayList<String> fKentities, ArrayList<String> nameProject, ArrayList<String> pathProjetc, int cont2) throws Exception {

		// float similaridadeMet, similaridadeClas;

		Connection conn = null;
		String sqlInsert = "INSERT INTO Similarity(FKentities,nameClassA,nameClassB,methodA, methodB,attributeA,attributeB,similarityClass,similarityMethods,similarityAttributes,nameProjectA,nameProjectB,pathA,pathB) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		java.sql.PreparedStatement stm = null;
		Conexao bd = new Conexao();

		try {
			conn = bd.obtemConexao();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		try {
			for (int i = 0; i < method.size(); i++) {
				cont++;
				for (int j = i + 1; j < method.size(); j++) {

					similarityClass = Similarity.checkSimilarity(classes.get(i), classes.get(j));
					similarityMethod = Similarity.checkSimilarity(method.get(i), method.get(j));
					similarityAttribute = Similarity.checkSimilarity(attribute.get(i), attribute.get(j));

					if (similarityClass >= 0.7 && !(nameProject.get(i).contains(nameProject.get(j)))) {

						conn.setAutoCommit(false);
						stm = conn.prepareStatement(sqlInsert);

						stm.setString(1, fKentities.get(i));
						stm.setString(2, classes.get(i));
						stm.setString(3, classes.get(j));
						stm.setFloat(8, similarityClass);

						if (similarityMethod >= 0.7) {
							stm.setString(4, method.get(i));
							stm.setString(5, method.get(j));
							stm.setFloat(9, similarityMethod);
						} else {
							stm.setString(4, "");
							stm.setString(5, "");
							stm.setFloat(9, 0);
						}
						if (similarityAttribute >= 0.7) {
							stm.setString(6, attribute.get(i));
							stm.setString(7, attribute.get(j));
							stm.setFloat(10, similarityAttribute);
						} else {
							stm.setString(6, "");
							stm.setString(7, "");
							stm.setFloat(10, 0);
						}
						
						stm.setString(11, nameProject.get(i));
						stm.setString(12, nameProject.get(j));
						stm.setString(13, pathProjetc.get(i));
						stm.setString(14, pathProjetc.get(j));

						stm.execute();
						conn.commit();// efetiva inclusoes
					}
					System.out.println("SEARCH FILES.... "+cont+" of "+cont2);
					//System.out.println("Inclusão concluída " + i + "\n");
				}
			}
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

	}

}

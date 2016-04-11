package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.eclipse.jdt.core.dom.BooleanLiteral;

import Util.Conexao;
import Util.Similarity;

public class FindSimilarity {

	private static float similarityMethod;
	private static float similarityClass;
	private static float similarityAttribute;
	private static int cont;

	public static void similarity() {

		String query = "SELECT nameClass, nameMethod, nameAttribute,projectClass, idClasses, idMethod, idAttribute FROM classes clas INNER JOIN method met ON clas.idClasses=met.Classes_idClasses INNER JOIN attribute on clas.idClasses= attribute.Classes_idClasses ORDER BY nameClass ASC";

		PreparedStatement stm = null;
		Conexao bd = new Conexao();

		Connection conn = null;
		try {
			conn = bd.obtemConexao();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		try {

			// System.out.println("SEARCH FILES.... " + "\n\r");

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);

			if (!rs.next()) {
				System.out.println("TABLE entities IS EMPTY");
			}
			// iterate through the java resultset
			conn.setAutoCommit(false);

			ArrayList<Integer> fkmethod = new ArrayList<Integer>();
			ArrayList<Integer> fkattributes = new ArrayList<Integer>();
			ArrayList<Integer> fkclasses = new ArrayList<Integer>();

			ArrayList<String> classes = new ArrayList<String>();
			ArrayList<String> metodo = new ArrayList<String>();
			ArrayList<String> attribute = new ArrayList<String>();
			ArrayList<String> nameProject = new ArrayList<String>();
			ArrayList<String> pathProjetc = new ArrayList<String>();

			int cont = 1;

			while (rs.next()) {

				fkclasses.add(rs.getInt("idClasses"));
				fkmethod.add(rs.getInt("idMethod"));
				fkattributes.add(rs.getInt("idAttribute"));

				classes.add(rs.getString("nameClass"));
				metodo.add(rs.getString("nameMethod"));
				attribute.add(rs.getString("nameattribute"));

				nameProject.add(rs.getString("nameProject"));
				pathProjetc.add(rs.getString("AbsolutePath"));
				cont++;
			}
			buscaresultado(classes, metodo, attribute, fkclasses, fkmethod, fkattributes, nameProject, pathProjetc,	cont);

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

	private static void buscaresultado(ArrayList<String> classes, ArrayList<String> metodo, ArrayList<String> attribute,
			ArrayList<Integer> fkclasses, ArrayList<Integer> fkmethod, ArrayList<Integer> fkattributes,
			ArrayList<String> nameProject, ArrayList<String> pathProjetc, int cont2) throws Exception {

		Connection conn = null;
		conn.setAutoCommit(false);
		String 	sqlInsert = "INSERT INTO similarityclassa(similarityClassA, classes_idClasses) VALUES (?,?)";
		java.sql.PreparedStatement stm = null;
		Conexao bd = new Conexao();

		try {
			conn = bd.obtemConexao();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		try {

			for (int i = 0; i < classes.size(); i++) {
				cont++;
				for (int j = i + 1; j < classes.size(); j++) {

					similarityClass = Similarity.checkSimilarity(classes.get(i), classes.get(j));

					if (similarityClass >= 0.7 && !(nameProject.get(i).contains(nameProject.get(j)))) {

						
						stm = conn.prepareStatement(sqlInsert);

						stm.setFloat(1, similarityClass);
						stm.setInt(2, fkclasses.get(i));
						stm.executeUpdate();
						
				

					}
					/*
					 * similarityMethod =
					 * Similarity.checkSimilarity(metodo.get(i), metodo.get(j));
					 * similarityAttribute =
					 * Similarity.checkSimilarity(attribute.get(i),
					 * attribute.get(j));
					 */
					System.out.println("SEARCH FILES.... " + cont + " of " + cont2);
					// System.out.println("Inclusão concluída " + i + "\n");
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

	private static boolean calcMethod(ArrayList<String> metodo) throws Exception {
		boolean result = false;

		for (int i = 0; i < metodo.size(); i++) {
			for (int j = i + 1; j < metodo.size(); j++) {
				similarityMethod = Similarity.checkSimilarity(metodo.get(i), metodo.get(j));
				if (similarityMethod >= 0.7) {
					result = true;
					break;
				} else {
					result = false;
					// break;
				}
			}
		}
		return result;
	}

	private static boolean calcAttribute(ArrayList<String> attribute) throws Exception {
		boolean result = false;

		for (int i = 0; i < attribute.size(); i++) {
			for (int j = i + 1; j < attribute.size(); j++) {
				similarityAttribute = Similarity.checkSimilarity(attribute.get(i), attribute.get(j));
				if (similarityAttribute >= 0.7) {
					result = true;
					break;
				} else {
					result = false;
				}
			}
		}
		return result;
	}

}

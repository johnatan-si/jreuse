package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Util.Conexao;
import Util.Log;

public class InsertClass {

	public void insertClass(Parameters parameters) {

		Connection connection = null;
		String sqlInsert = "INSERT INTO Classes (nameClass,qtdMethod,qtdAttribute, projectClass,absolutPath) VALUES(?,?,?,?,?) ";
		java.sql.PreparedStatement stm = null;
		Conexao bd = new Conexao();

		try {
			connection = bd.obtemConexao();
		} catch (SQLException e2) {
			e2.printStackTrace();
			Log.log(parameters.getNameProject() + "/" + parameters.getNameClass() + " erro de conexão com o banco de dados" + e2);
		}
		try {
			// connection.setAutoCommit(false);
			stm = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);

			stm.setString(1, parameters.getNameClass());
			stm.setInt(2, parameters.getNumberMethods());
			stm.setInt(3, parameters.getNumberAttribute());
			stm.setString(4, parameters.getNameProject());
			stm.setString(5, parameters.getAbsolutePath());

			stm.executeUpdate();// efetiva as inclusões

			ResultSet rs = stm.getGeneratedKeys();
			rs.next();
			int idClass = rs.getInt(1);
			parameters.setIdClass(idClass);

			System.out.println("Inclusão da classe/projeto: " + parameters.getNameProject() + "/" + parameters.getNameClass() + "\n\r");

		} catch (Exception e) {
			// Caso tenha uma exceção printa na tela
			e.printStackTrace();
			Log.log(parameters.getNameProject() + "/" + parameters.getNameClass() + " erro SQL" + e);
			try {
				// Aqui ele 'tenta' retroceder, na ação que deu errado.
				// quase um Ctrl+Z da vida.
				connection.rollback();
			} catch (SQLException e1) {
				System.out.print(e1.getStackTrace());
				Log.log(parameters.getNameProject() + "/" + parameters.getNameClass() + " erro SQL" + e);
			}
		} finally {
			if (stm != null) {
				try {
					// Encerra as operações.
					stm.close();
				} catch (SQLException e1) {
					System.out.print(e1.getStackTrace());
					Log.log(parameters.getNameProject() + "/" + parameters.getNameClass() + " erro SQL" + e1);
				}
			}
		}
	}
}

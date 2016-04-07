package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Util.Conexao;
import Util.Log;

public class InsertAttributes {

	public void insertAttribute(Parameters parameters) {

		Connection connection = null;
		String sqlInsert = "INSERT INTO attribute (nameAttribute,visibilityAttribute, typeAttribute,Classes_idClasses) VALUES(?,?,?,?)";
		java.sql.PreparedStatement stm = null;
		Conexao bd = new Conexao();

		try {
			connection = bd.obtemConexao();
		} catch (SQLException e2) {
			e2.printStackTrace();
			Log.log(parameters.getNameProject() + "/" + parameters.getNameClass()+ " erro de conex�o com o banco de dados" + e2);
		}
		try {
			// connection.setAutoCommit(false);
			stm = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);

			stm.setString(1, parameters.getNameAttribute());
			stm.setString(2, parameters.getVisibilityAttribute());
			stm.setString(3, parameters.getTypeAttribute());
			stm.setInt(4, parameters.getIdClass());
			

			stm.executeUpdate();// efetiva as inclus�es

			ResultSet rs = stm.getGeneratedKeys();
			rs.next();
			int idClass = rs.getInt(1);
			
			parameters.setIdClass(idClass);

			System.out.println("Inclus�o da classe/projeto: " + parameters.getNameProject() + "/"+ parameters.getNameClass() + "\n\r");

		} catch (Exception e) {
			// Caso tenha uma exce��o printa na tela
			e.printStackTrace();
			Log.log(parameters.getNameProject() + "/" + parameters.getNameClass() + " erro SQL" + e);
			try {
				// Aqui ele 'tenta' retroceder, na a��o que deu errado.
				// quase um Ctrl+Z da vida.
				connection.rollback();
			} catch (SQLException e1) {
				System.out.print(e1.getStackTrace());
				Log.log(parameters.getNameProject() + "/" + parameters.getNameClass() + " erro SQL" + e);
			}
		} finally {
			if (stm != null) {
				try {
					// Encerra as opera��es.
					stm.close();
				} catch (SQLException e1) {
					System.out.print(e1.getStackTrace());
					Log.log(parameters.getNameProject() + "/" + parameters.getNameClass() + " erro SQL" + e1);
				}
			}
		}
	}

}
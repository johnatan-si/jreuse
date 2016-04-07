package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Util.Conexao;
import Util.Log;

public class InsertMethod {

	public void insertMethod(Parameters parameters)throws SQLException {
		

		Connection connection = null;
		String sqlInsert = "INSERT INTO Method (nameMethod,locMethod,methodVisibility, returnMethod,classes_idClasses) VALUES(?,?,?,?,?) ";
		java.sql.PreparedStatement stm = null;
		Conexao bd = new Conexao();

		try {
			connection = bd.obtemConexao();
		} catch (SQLException e2) {
			e2.printStackTrace();
			Log.log(parameters.getNameProject() + "/" + parameters.getNameClass() + " erro SQL" + e2);
		}
		try {
			//connection.setAutoCommit(false);
			stm = connection.prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS);
			
			stm.setString(1, parameters.getNameMethod());
			stm.setInt(2, parameters.getLocMethod());
			stm.setString(3, parameters.getVisibilityMethod());
			stm.setString(4, parameters.getReturnTypeMethod());
			stm.setInt(5, parameters.getIdClass());
			
	        stm.executeUpdate();// efetiva as inclus�es 

			
			ResultSet rs = stm.getGeneratedKeys();
			rs.next();
			int idMethod = rs.getInt(1);
			parameters.setIdMethod(idMethod);
			System.out.println("Inclus�o of "+ idMethod+" do m�todo/classe/projeto " +parameters.getNameMethod()+"/"+parameters.getNameClass()+"/"+parameters.getNameProject()+ "\n\r");	
		} catch (Exception e) {
			// Caso tenha uma exce��o printa na tela
			e.printStackTrace();
			Log.log(parameters.getNameProject() + "/" + parameters.getNameClass() + " erro SQL" + e);
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.print(e1.getStackTrace());
				Log.log(parameters.getNameProject() + "/" + parameters.getNameClass() + " erro SQL" + e1);
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

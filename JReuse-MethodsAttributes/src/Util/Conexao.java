package Util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class Conexao {
 
    // Carrega driver JDBC
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
 
    //Obtem conexao com banco de dados
    public Connection obtemConexao() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/jreuse-method?useSSL=false","root","admin");
        //jdbc:mysql://localhost:3306/Peoples?autoReconnect=true&useSSL=false;
        //Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/INTtech?useSSL=false","root","root");


    }
}

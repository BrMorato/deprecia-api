package br.com.deprecia.api.Dao;


import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://localhost:5432/db_deprecia";
	private static final String USER = "postgres";
	private static final String PASSWORD = "brasil8";
	
	public static Connection getConnection() {
		try {
			Class.forName(DRIVER);
		    return DriverManager.getConnection(URL,USER, PASSWORD);
		}catch (Exception e) {
			System.out.print("Erro ao conectar ao Bando de dados! "+e.getMessage() );
			return null;
		}
	}

}


package br.com.deprecia.api.Dao;


import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://tuffi.db.elephantsql.com:5432/zlegamcr";
	
//	postgres://zlegamcr:1E4jSW3TWzdXnEQrmd4sr1QbLQjvRa-h@tuffi.db.elephantsql.com:5432/zlegamcr
	
	private static final String USER = "zlegamcr";
	private static final String PASSWORD = "1E4jSW3TWzdXnEQrmd4sr1QbLQjvRa-h";
	
	public static Connection getConnection() {
		try {
			Class.forName(DRIVER);
		    return DriverManager.getConnection(URL,USER, PASSWORD);
		}catch (Exception e) {
			System.out.print("Erro ao conectar ao Bando de dados! "+e.getMessage() );
			return null;
		}
	}
	
	public static void main(String[] args) {
		
		System.out.print(ConnectionFactory.getConnection());
	}

}


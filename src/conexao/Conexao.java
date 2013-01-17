package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {
	
	private static Connection sqlConn = null, sqlConnAux = null, sqlConnConsulta = null;
	private static Connection fbConn = null, fbConnAux = null;
	
	public static String SQL_SERVIDOR = "";
	public static String SQL_BANCO = "";
	public static String FB_BANCO = "";
	public static String SQL_SERVIDOR_CONSULTA = "";
	public static String SQL_BANCO_CONSULTA = "";
	
	public static Connection getSqlConnection() {
		try {
			if (sqlConn == null || sqlConn.isClosed()) {			
				String url = "jdbc:jtds:sqlserver://" + SQL_SERVIDOR + "/" + SQL_BANCO;
				String usuario = "sa";
				String senha = "vls021130";
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
				sqlConn = DriverManager.getConnection(url, usuario, senha);
				System.out.println("conectou " + SQL_BANCO);
			}
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Erro de drive: " + e.getMessage());
		}
		return sqlConn;
	}

	public static Connection getSqlConnectionAux() {
		try {
			if (sqlConnAux == null || sqlConnAux.isClosed()) {			
				String url = "jdbc:jtds:sqlserver://" + SQL_SERVIDOR + "/" + SQL_BANCO;
				String usuario = "sa";
				String senha = "vls021130";
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
				sqlConnAux = DriverManager.getConnection(url, usuario, senha);
				System.out.println("conectou " + SQL_BANCO);
			}
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Erro de drive: " + e.getMessage());
		}
		return sqlConnAux;
	}

	public static Connection getFbConnection() {
		try {
			if (fbConn == null || fbConn.isClosed()) {
				String url = "jdbc:firebirdsql://localhost/" + FB_BANCO+"?cl_ctype=ISO8859_1";
				String usuario = "SYSDBA";
				String senha = "masterkey";
				Class.forName("org.firebirdsql.jdbc.FBDriver");
				fbConn = DriverManager.getConnection(url, usuario, senha);
				fbConn.setAutoCommit(true);
				System.out.println("conectou Fire Bird");
				
//				Statement st = fbConn.createStatement();   
//	            String s = "SELECT * FROM TBFOR";   
//	            ResultSet rs = st.executeQuery(s);   
//	              while (rs.next()) {   
//	               System.out.println(rs.getString(19));   
//	               System.out.println(rs.getString(21)); 
//	               System.out.println(rs.getString(7)); 
//	   
//	               
//	              }  
	           // conexao.close();  
				 
			}
		} catch (Exception e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
		}
		return fbConn;
	}
	
	public static Connection getFbConnectionAux() {
		try {
			if (fbConnAux == null || fbConnAux.isClosed()) {
				String url = "jdbc:firebirdsql://localhost/" + FB_BANCO+"?cl_ctype=ISO8859_1";
				String usuario = "SYSDBA";
				String senha = "masterkey";
				Class.forName("org.firebirdsql.jdbc.FBDriver");
				fbConnAux = DriverManager.getConnection(url, usuario, senha);
				fbConnAux.setAutoCommit(true);
				System.out.println("conectou Fire Bird");
			}
		} catch (Exception e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
		}
		return fbConnAux;
	}
	
	public static Connection getSqlConnectionConsulta() {
		try {
			if (sqlConnConsulta == null || sqlConnConsulta.isClosed()) {			
				String url = "jdbc:jtds:sqlserver://" + SQL_SERVIDOR_CONSULTA + "/" + SQL_BANCO_CONSULTA;
				String usuario = "sa";
				String senha = "vls021130";
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
				sqlConnConsulta = DriverManager.getConnection(url, usuario, senha);
				System.out.println("conectou " + SQL_BANCO_CONSULTA);
			}
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Erro de drive: " + e.getMessage());
		}
		return sqlConnConsulta;
	}

}
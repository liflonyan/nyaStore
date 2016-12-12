package py.com.nyaStore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import py.com.nyaStore.model.Serie;

public class SerieDao {
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/NyaStore";
	private static final String DB_USER = "postgres";
	private static final String DB_PASSWORD = "lalala961";
	
	public boolean insertarSerie(Serie serie) throws SQLException {
 
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
 
		String insertTableSQL = "INSERT INTO serie"
				+ "(nombre) VALUES"
				+ "(?)";
 
		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			
			preparedStatement.setString(1, serie.getNombre());
 
			preparedStatement.executeUpdate();
		
			System.out.println("Record is inserted into SERIE table!");
			return true;
 
		} catch (SQLException e) {
 
			System.out.println("No se ha podido establecer conexion con la base de datos");
			return false;
			
 
		} finally {
 
			if (preparedStatement != null) {
				preparedStatement.close();
			}
 
			if (dbConnection != null) {
				dbConnection.close();
			}
 
		}
 
	}
	
	private static Connection getDBConnection() {
		 
		Connection dbConnection = null;
 
		try {
 
			Class.forName(DB_DRIVER);
 
		} catch (ClassNotFoundException e) {
 
			System.out.println("No se ha encontrado descripcion en DB_DRIVER");
 
		}
 
		try {
 
			dbConnection = DriverManager.getConnection(
                            DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;
 
		} catch (SQLException e) {
 
			System.out.println("No se ha podido establecer conexion con la base de datos");
 
		}
 
		return dbConnection;
 
	}
	
	public Serie recuperarSerie(){
		Connection dbConnection = null;
		Statement statement = null;
		
		String selectSQL = "SELECT * FROM serie";
		Serie serie = null;
		
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			ResultSet resp = statement.executeQuery(selectSQL);
			
			while (resp.next()) {
				
				serie = new Serie();
				serie.setNombre(resp.getString(1));

			}
			System.out.println(serie.toString());
			return serie;
			
 
		} catch (SQLException e) {
 
			System.out.println("No se ha podido establecer conexion con la base de datos");
 
		} finally {
			try {
				statement.close();
				dbConnection.close();
				} catch (SQLException e) {
				e.printStackTrace();
				}
		}
		return serie;
	}
	public List<Serie> recuperarSeries() {
		Connection dbConnection = null;
		Statement statement = null;

		String query = "SELECT * FROM serie";
		List<Serie> series = new ArrayList<Serie>();
		
		try {
			dbConnection = getDBConnection();
			ResultSet rs = dbConnection.createStatement().executeQuery(query);
			while (rs.next()) {
				Serie serie = new Serie();
				
				serie.setNombre(rs.getString(1));
				
				series.add(serie);
			}			
		}

		catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
	 
				if (dbConnection != null) {
					dbConnection.close();
				}
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		
		return series;

	}

}


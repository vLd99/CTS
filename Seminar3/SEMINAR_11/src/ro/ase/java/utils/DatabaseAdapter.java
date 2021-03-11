package ro.ase.java.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseAdapter {

	private Connection dbConnection;
	private static final String DB_CONNECTION_URL="jdbc:sqlite:products.db";
	private static final String CREATE_TABLE_PRODUCTS_STRING="CREATE TABLE PRODUCTS"+
	"(ID INTEGER PRIMARY KEY AUTOINCREMENT,"+"PRODUCT_NAME TEXT NOT NULL,"+"PRODUCT_PRICE REAL NOT NULL)";
	private static final String DROP_TABLE_PRODUCTS_STRING="DROP TABLE IF EXISTS PRODUCTS";
	
	public void connect() throws SQLException {
		this.dbConnection=DriverManager.getConnection(DB_CONNECTION_URL);
		
	}
	
	public void disconnect() throws SQLException {
		this.dbConnection.close();
	}
	
	public void createTable() throws SQLException {
		Statement st1=this.dbConnection.createStatement();
		st1.execute(DROP_TABLE_PRODUCTS_STRING);
		st1.execute(CREATE_TABLE_PRODUCTS_STRING);
		st1.close();
		
	}
	
	public void insertProduct(String productName,float productPrice) throws SQLException {
		Statement st1=this.dbConnection.createStatement();
		String insertProductQuery="INSERT INTO PRODUCTS (PRODUCT_NAME, PRODUCT_PRICE) VALUES ('" + productName
				+ "'," + productPrice + ")";
		st1.executeUpdate(insertProductQuery);
		st1.close();
		
		String psquery="INSERT INTO PRODUCTS(PRODUCT_NAME,PRODUCT_PRICE) VALUES (?,?)";
		PreparedStatement ps1=this.dbConnection.prepareStatement(psquery);
		ps1.setString(1,productName);
		ps1.setFloat(2,productPrice);
		ps1.executeUpdate();
		ps1.close();
		
	}
	
	public void SelectProducts(float PriceCriteria) throws SQLException {
		String psquery="SELECT * FROM PRODUCTS WHERE PRODUCT_PRICE > ?";
		PreparedStatement ps1=this.dbConnection.prepareStatement(psquery);
		ps1.setFloat(1, PriceCriteria);
		ResultSet rs=ps1.executeQuery();
		while(rs.next()) {
			System.out.println(String.format("PRODUCT ID: %d PRODUCT NAME: %s PRODUCT PRICE %f ",rs.getInt("ID"),rs.getString("PRODUCT_NAME"),rs.getFloat(3)));
			rs.close();
		}
		ps1.close();
	}
	
}

package ro.ase.java;

import java.sql.SQLException;

import ro.ase.java.utils.DatabaseAdapter;

public class Application {

	public static void main(String[] args) {
		DatabaseAdapter db1=new DatabaseAdapter();
		try {
			db1.connect();
			db1.createTable();
			db1.insertProduct("IPHONE",2000);
			db1.insertProduct("IPHONE",3000);
			db1.SelectProducts(2000);
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		
	}

}

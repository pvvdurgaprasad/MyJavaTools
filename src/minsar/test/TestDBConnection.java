package minsar.test;

import java.sql.Connection;

import minsar.db.DAO;

public class TestDBConnection {
	
	public static void main(String args[]) throws Exception {
		DAO MyDAO= new DAO();
		Connection conn = MyDAO.getConnection();
        MyDAO.closeConnection();
	}	

}
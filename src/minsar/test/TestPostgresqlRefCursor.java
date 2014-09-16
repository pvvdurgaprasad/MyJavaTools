package minsar.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;


import minsar.db.DAO;

public class TestPostgresqlRefCursor {
	
	public static void main(String args[]) throws Exception {
		DAO MyDAO= new DAO();
		Connection conn = MyDAO.getConnection();
		conn.setAutoCommit(false);
		CallableStatement proc=conn.prepareCall("{ ? = call ipm_getcoff() }");
		proc.registerOutParameter(1, Types.OTHER);
		proc.execute();
		ResultSet results = (ResultSet) proc.getObject(1);
		while (results.next()) {
		    System.out.println("Inside resultset");
		    System.out.println(results.getString(1)+" "+results.getString(2));
		}
		results.close();
		proc.close();		
        MyDAO.closeConnection();
	}	
	

}

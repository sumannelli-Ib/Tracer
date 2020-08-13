package com.monitoring.aspects;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class Tracer_DB {
	
	//Constants con = new Constants();
	

	public static void main(String[] args) {
	
		 
		//Write Content
		
	}
	
	public  Connection connection() {
		 try {
	            // This will load the MySQL driver, each DB has its own driver
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/tracer","root","admin");  
	            
	            return con;
		
	}catch(Exception e) {
		System.out.println("error");
		System.out.println(e);
		
	}
		 return null;
	}
		 public String tableexist(Connection con,String tablename ) {
			 boolean test=false;
			 DatabaseMetaData meta;
			String result = "";
			try {
				int cnt = 0;
				meta = con.getMetaData();
				ResultSet rs = meta.getTables(null, null, tablename, new String[] {"TABLE"});
				 while(rs.next()) {
					 
					 test=true;
					 
				 }
				 if(test) {
					 
					 result = "Table exist";
					  
				 }else {
					 
					 String table_creation_sql = "CREATE TABLE "+tablename
					 		+ "(Ip_address VARCHAR(255),"
					 		+ "Request_time VARCHAR(255),"
					 		+ "Package VARCHAR(255),"
					 		+ "Class VARCHAR(255),"
					 		+ "Method VARCHAR(255),"
					 		+ "URI VARCHAR(255),"
					 		+ "Requested_Body VARCHAR(2550),"
					 		+ "Response_time VARCHAR(255),"
					 		+ "Response VARCHAR(255))";
					 Statement stmt = con.createStatement();
					 stmt.executeUpdate(table_creation_sql);
					 result= "Table not exist";
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
			 
		 }
		
	
	
	public List<String> insert(Connection con,String tracer_ib,List<String> data) {
		Statement stmt;
		try {
			
			System.out.println(data);
			
			  String query = " insert into "
			  +tracer_ib+" (Ip_address, Request_time, Package, Class, Method, URI, Requested_Body, Response_time,Response)"
			  + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
			  PreparedStatement pr =
			  con.prepareStatement(query);
			 for(int i=1;i<=data.size();i++) {
			  pr.setString(i,data.get(i-1));
			 }
			 
			 
			/*
			 * pr.setString(0, data.get(0)); pr.setString(1, data.get(1)); pr.setString(2,
			 * data.get(2)); pr.setString(3, data.get(3)); pr.setString(4, data.get(4));
			 * pr.setString(5, data.get(5)); pr.setString(6, data.get(6)); pr.setString(7,
			 * data.get(7));
			 */
			  
			pr.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return data;
		//return "table created successfully";
	}
	
	
	
}

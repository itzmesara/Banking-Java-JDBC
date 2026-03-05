package com.bankapp;


import java.sql.*;
public class JDBConnection {
	
	Connection conn=null;
	PreparedStatement st=null;
	ResultSet rs=null;
	String Host="jdbc:mysql://localhost:3306/bank";
	String username="root";
	String pass="your_password";
	public JDBConnection() throws Exception
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn=DriverManager.getConnection(Host, username, pass);
		System.out.println("Database connected Successfully");
	}
	
	public int UpdateQuery(String query) throws Exception
	{
		st=conn.prepareStatement(query);
		return st.executeUpdate();
	}
	public ResultSet ExecuteQuery(String query) throws Exception
	{
		st=conn.prepareStatement(query);
		rs=st.executeQuery();
		return rs;
	}
	
}

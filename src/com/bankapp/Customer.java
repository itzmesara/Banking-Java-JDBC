package com.bankapp;

import java.util.*;
import java.sql.*;
public class Customer {
	private JDBConnection jd=null;
	private String query;
	private PreparedStatement ps=null;
	private ResultSet rs=null;
	public Customer(JDBConnection jd)
	{
		this.jd=jd;
	}
	
	public void CreateAccount() throws Exception
	{
	    Scanner sc = new Scanner(System.in);
	    int MPIN;
	    long phonenumber;
	    String username, address;
	    
	    System.out.println("Enter Your Name:");
	    username = sc.nextLine();
	    
	    System.out.println("Enter Your Phone number:");
	    phonenumber = sc.nextLong();
	    sc.nextLine();
	    
	    System.out.println("Enter Your Address:");
	    address = sc.nextLine(); 
	    
	    System.out.println("Enter Your Branch\n0.Pollachi\n1.Coimbatore\n2.Tirupur");
	    int branch = sc.nextInt();
	    
	    System.out.println("Enter Your Preferred MPIN:");
	    MPIN = sc.nextInt();
	    
	    if(isPhoneExists(jd, phonenumber))
	    {
	        System.out.println("❌ Account Already Exists");
	        return;
	    }
	    
	    int acc = genAcc(jd);
	    String ifsc = genIFSC(branch);
	    
	    query = "INSERT INTO Customer(account_no, username, phonenumber, address, IFSC, MPIN) VALUES (?, ?, ?, ?, ?, ?)";
	    ps = jd.conn.prepareStatement(query);
	    ps.setInt(1, acc);
	    ps.setString(2, username);
	    ps.setLong(3, phonenumber);
	    ps.setString(4, address);  
	    ps.setString(5, ifsc);
	    ps.setInt(6, MPIN);
	    ps.executeUpdate();
	    query="INSERT into account(account_no,balance) values(?,?)";
	    ps=jd.conn.prepareStatement(query);
	    ps.setInt(1, acc);
	    ps.setInt(2, 0);
	    ps.executeUpdate();
	    
	    System.out.println("✅ Account has been Created Successfully.");
	    System.out.println("Account No : " + acc);
	    System.out.println("IFSC Code : " + ifsc);
	}
	public boolean isPhoneExists(JDBConnection jd,long phonenumber) throws Exception
	{
		query="select count(*) from customer where phonenumber=?";
		ps=jd.conn.prepareStatement(query);
		ps.setLong(1,phonenumber);
		rs=ps.executeQuery();
		if(rs.next())
		{
			int c=rs.getInt(1);
			return c>0;
		}
		return false;
	}
	public int genAcc(JDBConnection jd) throws Exception
	{
		query = "select max(account_no) from Customer";
		ps=jd.conn.prepareStatement(query);
		rs=ps.executeQuery(query);
		if(rs.next())
		{
			int m=rs.getInt(1);
			if(m>1)
			{				
				return m+1;
			}
		}
		return 1000000;
	}
	public String genIFSC(int branch)
	{
		String[] branches= {"PLY1234","CBE1234","TPR1234"};
		return branches[branch];
	}
}

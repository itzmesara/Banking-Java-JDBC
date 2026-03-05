package com.bankapp;

import java.sql.*;
import java.util.Scanner;
public class Validation {
	private int acc;
	private int mpin;
	public boolean valid(JDBConnection jd,Scanner sc) throws Exception
	{
		boolean valid = false;
		System.out.println("Enter Your Account No:");
		this.acc=sc.nextInt();
		System.out.println("Enter Your MPIN:****");
		this.mpin=sc.nextInt();
		String query="select * from customer where account_no="+acc+" and MPIN="+mpin;
		ResultSet rs=jd.ExecuteQuery(query);
		if(rs.next())
		{
			int c=rs.getInt(1);
			valid=c>0;
		}
		return valid;
	}
	
	public int getAcc()
	{
		return acc;
	}
}

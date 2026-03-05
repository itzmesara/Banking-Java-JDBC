package com.bankapp;


import java.sql.*;
import java.util.Scanner;
public class Account extends Validation {
	private JDBConnection jd=null;
	private String query;
	private PreparedStatement ps=null;
	private ResultSet rs=null;
	private Scanner sc=null;
	
	public Account(JDBConnection jd,Scanner sc)
	{
		this.jd=jd;
		this.sc=sc;
	}
	public void checkBal() throws Exception
	{
		if(valid(jd, sc))
		{
			query="Select balance from account where account_no="+getAcc();
			rs=jd.ExecuteQuery(query);
			if(rs.next())
			{
				System.out.println("Your Account Balance : "+rs.getInt(1));
			}
		}
	}
	
	public void Withdraw() throws Exception
	{
		if(valid(jd, sc))
		{
			query="Select balance from account where account_no="+getAcc();
			rs=jd.ExecuteQuery(query);
			if(rs.next())
			{
				int bal=rs.getInt(1);
				System.out.println("Enter Your Withdrawal Amount:");
				int amnt=sc.nextInt();
				if(bal<amnt)
				{
					System.out.println("Insufficient Balance");
					System.out.println("Enter 0 for Back or 1 for Withdrawal");
					int ch=sc.nextInt();
					if(ch==0)
					{
						return;
					}
					else if(ch==1)
					{
						Withdraw();
					}
				}
				else
				{
					bal=bal-amnt;
					query="Update account set balance="+bal+" where account_no="+getAcc();
					int res=jd.UpdateQuery(query);
				}
			}
		}
	}
	
	public void Deposit() throws Exception
	{
		if(valid(jd, sc))
		{
			query="Select balance from account where account_no="+getAcc();
			rs=jd.ExecuteQuery(query);
			if(rs.next())
			{
				int bal=rs.getInt(1);
				System.out.println("Enter Your Deposit Amount:");
				int amnt=sc.nextInt();
				bal=bal+amnt;
				query="Update account set balance="+bal+" where account_no="+getAcc();
				int res=jd.UpdateQuery(query);
			}
		}
	}
	
	public void AccInfo() throws Exception
	{
		if(valid(jd, sc))
		{
			query="select * from customer where account_no="+getAcc();
			rs=jd.ExecuteQuery(query);
			ResultSetMetaData rsmd=rs.getMetaData();
			int n=rsmd.getColumnCount();
			while(rs.next())
			{
				for(int i=1;i<=n;i++)
				{
					System.out.println(rs.getString(i));
				}
			}
		}
	}
}

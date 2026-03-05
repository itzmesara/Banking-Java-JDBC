package com.bankapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Transaction extends Validation {

	private JDBConnection jd=null;
	private String query;
	private PreparedStatement ps=null;
	private ResultSet rs=null;
	private Scanner sc=null;
	
	public Transaction(JDBConnection jd,Scanner sc)
	{
		this.jd=jd;
		this.sc=sc;
	}
	public void transfer() throws Exception
	{
		if(valid(jd, sc))
		{
			System.out.println("Enter Receiver's Account:");
			int racc=sc.nextInt();
			query="select balance from account where account_no=?";
			ps=jd.conn.prepareStatement(query);
			ps.setInt(1, getAcc());
			rs=ps.executeQuery();
			int sbal = 0;
			if(rs.next())
			{
				sbal=rs.getInt(1);
				
			}
			ps.setInt(1,racc);
			rs=ps.executeQuery();
			int rbal=0;
			if(rs.next())
			{				
				rbal=rs.getInt(1);
			}
			System.out.println("Enter Transfer Amount:");
			int amnt=sc.nextInt();
			if(sbal<amnt)
			{
				System.out.println("Insufficient Balance");
				System.out.println("Enter 0 for back or 1 for Continue");
				int ch=sc.nextInt();
				if(ch==0)
				{
					return;
				}
				else if(ch==1)
				{
					transfer();
				}
			}
			sbal=sbal-amnt;
			rbal=rbal+amnt;
			query="update account set balance=? where account_no=?";
			ps=jd.conn.prepareStatement(query);
			ps.setInt(1, sbal);
			ps.setInt(2, getAcc());
			ps.executeUpdate();
			ps.setInt(1, rbal);
			ps.setInt(2,racc);
			ps.executeUpdate();
			query="insert into passbook(send_no,rec_no,trans,Date) values(?,?,?,?)";
			LocalDateTime date=LocalDateTime.now();
			Timestamp sqltime=Timestamp.valueOf(date);
			ps=jd.conn.prepareStatement(query);
			ps.setInt(1,getAcc());
			ps.setInt(2, racc);
			ps.setInt(3, amnt);
			ps.setTimestamp(4, sqltime);
			ps.executeUpdate();
			System.out.println("Your Current Balance : "+sbal);
		}
	}
	public void mpassBook() throws Exception
	{
		if(valid(jd, sc))
		{
			query="select send_no as `From`,rec_no as `To`,trans as Cash,Date from passbook where send_no=? or rec_no=? order by Date desc";
			ps=jd.conn.prepareStatement(query);
			ps.setInt(1,getAcc());
			ps.setInt(2,getAcc());
			rs=ps.executeQuery();
			ResultSetMetaData rsmd=rs.getMetaData();
			int n=rsmd.getColumnCount();
			String[] data= {"FROM","TO","CASH","DATE"};
			System.out.println("#####################################################################################");
			for(int i=0;i<n;i++)
			{
				System.out.printf("%-15s",data[i]);
			}
			System.out.println();
			while(rs.next())
			{
				System.out.println();
				for(int i=1;i<=n;i++)
				{
					System.out.printf("%-15s",rs.getString(i));
				}
			}
			System.out.println();
			System.out.println("#####################################################################################");
		}
	}
}

package com.bankapp;

import java.util.*;
import com.bankapp.JDBConnection;

public class Banking {

	public static void main(String[] args) throws Exception {
		Scanner sc=new Scanner(System.in);
		JDBConnection jd=new JDBConnection();
		Customer cust=new Customer(jd);
		Account acc=new Account(jd,sc);
		Transaction trans=new Transaction(jd,sc);
		
	while(true)
	{
		Phase1();
		System.out.print("Enter Your Choice:");
		int n = sc.nextInt();
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		switch(n)
		{
		    case 1:
			    cust.CreateAccount();
				break;
		    case 2:
		    	System.out.println("\t\t\t\t\t\tCheck Your Balance\n");
		    	acc.checkBal();
		    	break;
		    case 3:
		    	System.out.println("\t\t\t\t\t\tWithdraw Cash(Rs.)\n");
		    	acc.Withdraw();
		    	System.out.println("Amount Withdrawed Successfully!!!");
		    	break;
		    case 4:
		    	System.out.println("\t\t\t\t\t\tDeposit Cash(Rs.)\n");
		    	acc.Deposit();
		    	System.out.println("Amount Deposited Successfully!!!!");
		    	break;
		    case 5:
		    	System.out.println("\t\t\t\t\t\tTransfer Your Cash(Rs.)\n");
		    	trans.transfer();
		    	System.out.println("Amount Transferred Successfully!!!");
		    	break;
		    case 6:
		    	System.out.println("\t\t\t\t\t\tMini Statement\n");
		    	trans.mpassBook();
		    	break;
		    case 7:
		    	System.out.println("\t\t\t\t\t\tAccount Info\n");
		    	acc.AccInfo();
		    	break;
			case 8:
				System.out.println("Thank You for Using us.");
				return;
			default:
				System.out.println("\t\t\t\t\t\tOOPS ~ |-_-|\n");
				System.out.println("Invalid Input Choice");
				break;
			}
		System.out.println("--------------------------------------------------------------------------------------------------------------------");
		}
	}
	
	public static void Phase1()
	{
		System.out.println("\t\t\t\t\t\t\tWelcome to SuperBank\n");
		System.out.println("Enter the Service code(eg: 1.xxxxxx code:1)");
		System.out.println("1.Create Account");
		System.out.println("2.Check Balance");
		System.out.println("3.Withdraw");
		System.out.println("4.Deposit");
		System.out.println("5.Transfer");
		System.out.println("6.mPassbook");
		System.out.println("7.Account Info");
		System.out.println("8.Exit");
	}
}

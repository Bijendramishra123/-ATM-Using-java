import java.util.Scanner;
class A
{
	Scanner sc=new Scanner(System.in);
	String name;
	int account_no;
	String account_type;
	String IFSC;
	String Bank_name;
	int balance;
	
    void getdata()
	{
		System.out.println("plese Enter Account holder name");
		name=sc.nextLine();
		System.out.println("plese Enter Account number");
		account_no=sc.nextInt();
		sc.nextLine();
		System.out.println("plese Enter Account_Type");
		account_type=sc.nextLine();
		System.out.println("plese Enter bank name");
		Bank_name=sc.nextLine();
		System.out.println("abe IFSC code kon batayega tera baap");
		IFSC=sc.nextLine();
	}
	void Deposit()
	{
		int Depositamount;
		System.out.println("Enter Deposit amount");
		Depositamount=sc.nextInt();
		balance=balance+Depositamount;
		System.out.println("Amount Depodited successfully");
	}
	void withdraw()
	{
		int withdraw_amount;
		System.out.println("Enter Amount you want to withdraw");
		withdraw_amount=sc.nextInt();
		if(withdraw_amount>balance)
		{
			System.out.println("sorry you entered insufficient balance");
		}	    
				
        else
		{
			balance=balance-withdraw_amount;
			System.out.println("transaction completed");
		}
	}
		void display()
		{
			System.out.println("Account holder name is:"+name);
			System.out.println("remaning balance is:"+balance);
		}
	
}
	
	class Bank5
	{
        public static void main(String args[])
		{
			A B=new A();
			B.getdata();
			B.Deposit();
			B.withdraw();
			B.display();
		}
	}
	
			
				
		
		
	
	
	
	
	
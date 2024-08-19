package javabankingsystemproject;

import java.util.ArrayList;
import java.util.Scanner;

class StartUpProcess
{
    public void bankFeatures() 
    {
        System.out.println("List of features including in the bank are as follows ");
       System.out.println("1. Adding Customer\n" +
                          "2. Removing Customer\n" +
                          "3. Creating Account\n" +
                          "4. Removing Account\n" +
                          "5. Account Details\n" +
                          "6. Loan Application Process\n" +
                          "7. Loan Repaiment Processs\n" +
                          "8. Displaying All Customers\n" +
                          "9. Exit" );
    }
}

interface BankAccountFunctions
{
    void withdraw(double withDrawAmount);
    void deposit(double depositAmount);
    double getBalance();
}

abstract class BankAccount implements BankAccountFunctions
{
    private String accountNo;
    protected double balance;
    private double depositAmount;
    private double withDrawAmount;
    
    public BankAccount(String accountNo,double balance,double depositAmount,double withDrawAmount)
    {
        this.accountNo = accountNo;
        this.balance = balance;
        this.depositAmount = depositAmount;
        this.withDrawAmount = withDrawAmount;
    }
   
     @Override
    public void deposit(double depositAmount)
    {
         balance= balance + depositAmount;
        System.out.println("Deposited Amount = "+balance);
    }
    
    @Override
    public void withdraw(double withDrawAmount)
    {
    if(withDrawAmount<=balance)
        {
           balance = balance - withDrawAmount;
           System.out.println("Withdraw Amount  = "+balance);
        }
        else
        {
            System.out.println("Insufficient Funds for withdraw");
        }
    }
    
    @Override
    public double getBalance()
    {
        return balance;
    }
  
    public String getAccountNo()
    {
        return accountNo;
    }
    
    public abstract void interestCalculation();
    
    public void AccountInformation()
    {
        System.out.println("Account No. = "+getAccountNo());
        System.out.println("Account Balance = "+getBalance());
        deposit(depositAmount);
        withdraw(withDrawAmount);
        System.out.println("Account Balance After Transactions = "+getBalance());
        interestCalculation();
    }
}

enum AccountType
{
    SAVING,
    CURRENT
}

class SavingAccount extends BankAccount
{
    private double interestRate;
    
    public SavingAccount(String accountNo,double balance,double interestRate,
            double depositAmount,double withDrawAmount)
    {
        super(accountNo, balance,depositAmount,withDrawAmount);
        this.interestRate = interestRate;
    }
    
    @Override
    public void interestCalculation()
    {
        double inrst;
        inrst = balance*(interestRate/100);
        System.out.println("Interest in case of Saving Account is = "+inrst);
        balance = balance +inrst;
    }
}

class CurrentAccount extends BankAccount
{
    private double overdraftLimit;
    
    public CurrentAccount(String accountNo,double balance,double overdraftLimit,
              double depositAmount,double withDrawAmount)
    {
        super(accountNo, balance, depositAmount, withDrawAmount);
        this.overdraftLimit = overdraftLimit;
    }
    
     @Override
    public void deposit(double amount)
    {
         balance= balance + amount;
        System.out.println("Deposited Amount in the Current Account= "+balance);
    }
    
    @Override
    public void withdraw(double amount)
    {
    if(balance+overdraftLimit>=amount)
        {
           balance = balance - amount;
           System.out.println("Withdraw Amount in the Current Account = "+balance);
        }
        else
        {
            System.out.println("Overdraft limit exceeds as Transaction is failed");
        }
    }
    public double getbalance()
    {
        return balance+overdraftLimit;
    }
    
    @Override
    public void interestCalculation()
    {
        System.out.println("Interest calculation is not valid in Current account");
    }
    
}

class Loan
{
    private double interestRate;
    private double loanamount;
    private String loanNo;
    
    public Loan(double interestRate,double loanamount, String loanNo)
    {
        this.interestRate = interestRate;
        this.loanamount = loanamount;
        this.loanNo = loanNo;
    }
    
    public void calculateInterest()
    {
        double inrst;
        inrst = loanamount*(interestRate/100);
        System.out.println("Interest in case of Loan = "+inrst);
        loanamount = loanamount +inrst;
    }
    
    public double getLoanAmount()
    {
        return loanamount;
    }
    
    public String getLoanNo()
    {
        return loanNo;
    }
    
    public void loanRepaition(double repayamount)
    {
        System.out.println(+repayamount +" loanamount has been repaid");
        loanamount = loanamount - repayamount;
        System.out.println(+loanamount +" loanamount has not been repaid yet");
    }
    
    public void loanInformation()
    {
       System.out.println("Loan No: "+getLoanNo());
       System.out.println("Loan Amount: "+getLoanAmount());
       calculateInterest();
       System.out.println("Loan Amount After Interest: "+getLoanAmount());
    }
}

class Customer
{
    private String Customername;
    ArrayList<Loan>loans;
    ArrayList<BankAccount>accounts;
    
    public Customer(String Customername)
    {
       this.Customername = Customername;
       loans = new ArrayList<>();
       accounts = new ArrayList<>();
    }
    
    public String getcustomerName()
    {
        return Customername;
    }
    
    public void addAccount(BankAccount account)
    {
        System.out.println("Account has been created by the customer");
        accounts.add(account);
    }
    
    public void deleteAccount(String accountNo)
    {
        boolean  state = true;
        for(BankAccount account: accounts)
        {
            if(account.getAccountNo().equals(accountNo))
            {
                accounts.remove(account);
                state = false;
                break;
            }
        }
        if(state ==true)
        {
            System.out.println(accountNo + " has not found");
        }
    }
    
    String accountType(BankAccount account)
    {
        if(account instanceof SavingAccount)
        {
            return "SavingAccount";
        }
        
        else if(account instanceof CurrentAccount)
        {
            return "CurrentAccount";
        }
                
        else
        {
             return "Invalid AccountType";
        }
        
    }
    
    public void allAccountsInfo()
    {
        System.out.println("Following is the list of customer accounts:");
        System.out.println("Name of Customer: "+Customername);
        for(BankAccount account: accounts)
        {
            System.out.println("Account Type: "+accountType(account));
            account.AccountInformation();
            System.out.println();
        }
        allLoanInfo();
    }
    
    public void allLoanInfo()
    {
        System.out.println("Following is the list of customer loans:");
        for(Loan loan: loans)
        {
            loan.loanInformation();
            System.out.println();
        }
    }
    
    public void requiringloans(Loan loan)
    {
        System.out.println("Customer borrows a loan");
        loans.add(loan);
    }
    
    public void loanRepaition(double repayamount,String loanNo)
    {
        boolean state = true;
        
        for(Loan loan: loans)
        {
            if(loan.getLoanNo().equals(loanNo));
           {
              loan.loanRepaition(repayamount);
              state = false;
              break;
           }
        }
        
        if(state == true)
        {
            System.out.println(loanNo + "has not found");
        }
    }
}

interface BankOperations
{
    void addCustomer(Customer customer);
    void deleteCustomer(String customername);
    void showAllCustomers();
}

class Bank implements BankOperations
{
    ArrayList<Customer>customers;
    private int count;
    public Bank()
    {
        customers = new ArrayList<>();
        this.count = 0;
    }
    
    @Override
    public void addCustomer(Customer customer)
    {
        customers.add(customer);
        count ++;
        
        if(count == 1)
        {
            System.out.println("Customer has been added in  the bank");
        }
        
        else
        {
            System.out.println("Another Customer has been added in  the bank");
        }
    }
    
    @Override
    public void deleteCustomer(String customername)
    {
        boolean  state = true;
        
        for(Customer customer: customers)
        {
            if(customer.getcustomerName().equals(customername))
            {
                customers.remove(customer);
                count --;
                System.out.println("Customer with name "+ customername +" has been removed from the bank");
                state = false;
                break;
            }
        }
        
        if(state ==true)
        {
            System.out.println("Customer with name " +customername +" not found");
        }
    }
    
    @Override
    public void showAllCustomers()
    {
        System.out.println("Total No. of customers registered in the bank = "+count);
        System.out.println("Here is the complete list of customers registered in the bank.");
        int number = 1;
        for(Customer customer: customers)
        {
            System.out.println();
            System.out.println("Customer No. "+ number++ +" :");
            customer.allAccountsInfo();
        }
    }
}

public class JavaBankingSystemProject
{
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
       System.out.println();
       System.out.println("............Welcome to Bank Management system............");
       System.out.println();
       
       boolean st= false;
       System.out.println("Press 'E' Key to access Bank Features!");
       while(st == false)
       {
           char start= sc.next().charAt(0);

           if(start == 'E' || start == 'e')
           {
               StartUpProcess SUP = new StartUpProcess();
               SUP.bankFeatures();
               st = true;
           }
             
             else
             {
                 System.out.println("Please! Enter valid Key to startUp process.");
             }
       }
            
       Bank bank = new Bank();
       boolean state = true;
       while(state)
       {
           System.out.println();
           int choice ;
           System.out.print("Enter the choice: ");
           choice = sc.nextInt();
           sc.nextLine();
           switch(choice)
           {
             case 1:
                 System.out.println("Enter the customer name to add: ");
                 String Customername = sc.nextLine();
                 Customer customer = new Customer(Customername);
                 bank.addCustomer(customer);
                 break;
               
             case 2:
                 System.out.println("Enter the name of customer to remove: ");
                 String Customernameremove = sc.nextLine();
                 bank.deleteCustomer(Customernameremove);
                 break;
                
             case 3:
                 System.out.println("Enter the customer name whose account can be added");
                 String  cstnameaccadd = sc.nextLine();
                 
                 Customer customeraddAccount = null;
                 for(Customer c : bank.customers)
                 {
                     if(c.getcustomerName().equals(cstnameaccadd))
                     {
                         customeraddAccount = c;
                         break;
                     }
                 }
                 
                 System.out.println("Enter the type of Account:");
                 System.out.println("* Saving Account\n"+ 
                                    "* Current Account");
                 char type  = sc.next().charAt(0);
                 sc.nextLine();
                 System.out.println("Enter the account number: ");
                 String accountno = sc.nextLine();
                 System.out.println("Enter the initial balance: ");
                 double initialbalance = sc.nextDouble();
                 sc.nextLine();
                 System.out.print("Enter the amount of deposit: ");
                 double  depositAmount = sc.nextDouble();
                 sc.nextLine();
                 System.out.print("Enter the amount of withdraw: ");
                 double withDrawAmount = sc.nextDouble();
                 sc.nextLine();
                 
                 if(type == 'S' || type == 's')
                 {
                     System.out.println("Enter the interest rate:  ");
                     double interestrate = sc.nextDouble();
                     sc.nextLine();
                     SavingAccount savacc = new SavingAccount(accountno,initialbalance,interestrate,
                            depositAmount,withDrawAmount);
                     customeraddAccount.addAccount(savacc);
                 }
                 
                 else if(type == 'C' || type == 'c')
                 {
                     System.out.println("Enter the overdraftlimiT: ");
                     double overdraftlimit = sc.nextDouble();
                     sc.nextLine();
                     CurrentAccount curacc = new CurrentAccount(accountno, initialbalance, overdraftlimit,
                             depositAmount,withDrawAmount);
                     customeraddAccount.addAccount(curacc);
                 }
                 
                 else
                 {
                     System.out.println("Sorry! \n"+
                                         "You enter invalid Account type");
                 }
                 
                 break;
               
             case 4:
                 System.out.println("Enter the  customer name whose account you can remove!");
                 String cstnameacctremove = sc.nextLine();
                 
                 Customer customerremoveAccount= null;
                  for(Customer c : bank.customers)
                 {
                     if(c.getcustomerName().equals(cstnameacctremove))
                     {
                         customerremoveAccount = c;
                         break;
                     }
                 }
                  
                 System.out.println("Enter the account number :");
                 String accountnoremove = sc.nextLine();
                 customerremoveAccount.deleteAccount(accountnoremove);
                 break;
                 
             case 5:
                 System.out.println("Enter the customer name to show account details: ");
                 String cstnameacctdetails = sc.nextLine();
                 Customer customerAccountdetails = null;
                 for(Customer c : bank.customers)
                 {
                     if(c.getcustomerName().equals(cstnameacctdetails))
                     {
                         customerAccountdetails = c;
                         break;
                     }
                 }
                 customerAccountdetails.allAccountsInfo();
                 break;
                 
             case 6:
                 System.out.println("Enter the customer name who has applied for loan");
                 String cstnameapploan = sc.nextLine();
                 
                 Customer customerapplyLoan = null;
                 for(Customer c : bank.customers)
                 {
                     if(c.getcustomerName().equals(cstnameapploan))
                     {
                         customerapplyLoan = c;
                         break;
                     }
                 }
                 
                 System.out.println("Enter the amount of Loan: ");
                 double loanAmount = sc.nextDouble();
                 sc.nextLine();
                 System.out.println("Enter the loan No: ");
                 String loanNo = sc.nextLine();
                 System.out.println("Enter the interest rate for Loan: ");
                 double interestRate = sc.nextDouble();
                 sc.nextLine();
                 Loan loan  = new Loan(loanAmount,interestRate,loanNo);
                 customerapplyLoan.requiringloans(loan);
                 break;
               
             case 7:
                 System.out.println("Enter the customer name who wants to repay loan: ");
                 String cstnamerepayloan = sc.nextLine();
                 System.out.println("Enter the repay amount for loan: ");
                 double repayAmount = sc.nextDouble();
                 sc.nextLine();
                 System.out.println("Enter the loan Number: ");
                 String loanNumber = sc.nextLine();
                 
                 Customer customerclearanceLoan = null;
                 for(Customer c : bank.customers)
                 {
                     if(c.getcustomerName().equals(cstnamerepayloan))
                     {
                         customerclearanceLoan = c;
                         break;
                     }
                 }
                 
                 customerclearanceLoan.loanRepaition(repayAmount,loanNumber);
                 break;
                 
             case 8: 
                 bank.showAllCustomers();
                 break;
                 
             case 9:
                state = false;
                System.out.println("Process Finished!");
                System.out.println("Exit.....");
                break;
            
             default:
               System.out.println("Please enter valid choice");
               System.out.println("Your requirement does not exist in our bank");
           }
       }
    }
}
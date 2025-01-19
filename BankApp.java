import java.util.Scanner;

// Interface to manage account details
interface IAccount {
    void setAccountDetails(String name, int accountNo, String accountType, String bankName, String ifsc);

    String getAccountDetails();
}

// Interface to manage transactions
interface ITransactionHandler {
    void deposit(int amount);

    boolean withdraw(int amount);

    int getBalance();
}

// Class to represent an account and store its details
class Account implements IAccount {
    private String name;
    private int accountNo;
    private String accountType;
    private String bankName;
    private String ifsc;
    private int balance;

    @Override
    public void setAccountDetails(String name, int accountNo, String accountType, String bankName, String ifsc) {
        this.name = name;
        this.accountNo = accountNo;
        this.accountType = accountType;
        this.bankName = bankName;
        this.ifsc = ifsc;
    }

    @Override
    public String getAccountDetails() {
        return String.format(
                "Name: %s\nAccount Number: %d\nType: %s\nBank: %s\nIFSC: %s\nBalance: %d",
                name, accountNo, accountType, bankName, ifsc, balance
        );
    }

    public void deposit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
        balance += amount;
    }

    public boolean withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    public int getBalance() {
        return balance;
    }
}

// Class to handle transactions such as deposit and withdrawal
class TransactionHandler implements ITransactionHandler {
    private Account account;

    // Constructor to inject dependency
    public TransactionHandler(Account account) {
        this.account = account;
    }

    @Override
    public void deposit(int amount) {
        try {
            account.deposit(amount);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public boolean withdraw(int amount) {
        try {
            return account.withdraw(amount);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public int getBalance() {
        return account.getBalance();
    }
}

// Interface for user input handling
interface InputHandler {
    String readString(String prompt);

    int readInt(String prompt);
}

// Class to handle console input
class ConsoleInputHandler implements InputHandler {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}

// Class to display and manage the bank menu
class BankMenu {
    private InputHandler inputHandler;
    private IAccount account;
    private ITransactionHandler transactionHandler;

    // Constructor for dependency injection
    public BankMenu(InputHandler inputHandler, IAccount account, ITransactionHandler transactionHandler) {
        this.inputHandler = inputHandler;
        this.account = account;
        this.transactionHandler = transactionHandler;
    }

    // Method to display the menu and handle user input
    public void showMenu() {
        while (true) {
            try {
                System.out.println("\n--- Bank Menu ---");
                System.out.println("1. Enter Account Details");
                System.out.println("2. Deposit Money");
                System.out.println("3. Withdraw Money");
                System.out.println("4. Display Account Details");
                System.out.println("5. Exit");

                int choice = inputHandler.readInt("Choose an option:");

                switch (choice) {
                    case 1:
                        handleAccountDetails();
                        break;
                    case 2:
                        handleDeposit();
                        break;
                    case 3:
                        handleWithdrawal();
                        break;
                    case 4:
                        handleDisplayDetails();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    // Method to handle account details input
    private void handleAccountDetails() {
        String name = inputHandler.readString("Enter Name:");
        int accNo = inputHandler.readInt("Enter Account Number:");
        String accType = inputHandler.readString("Enter Account Type:");
        String bankName = inputHandler.readString("Enter Bank Name:");
        String ifsc = inputHandler.readString("Enter IFSC:");
        account.setAccountDetails(name, accNo, accType, bankName, ifsc);
        System.out.println("Account details saved successfully.");
    }

    // Method to handle deposit
    private void handleDeposit() {
        int depositAmount = inputHandler.readInt("Enter Deposit Amount:");
        transactionHandler.deposit(depositAmount);
        System.out.println("Deposit successful. New Balance: " + transactionHandler.getBalance());
    }

    // Method to handle withdrawal
    private void handleWithdrawal() {
        int withdrawAmount = inputHandler.readInt("Enter Withdrawal Amount:");
        if (transactionHandler.withdraw(withdrawAmount)) {
            System.out.println("Withdrawal successful. Remaining Balance: " + transactionHandler.getBalance());
        } else {
            System.out.println("Insufficient balance. Transaction failed.");
        }
    }

    // Method to display account details
    private void handleDisplayDetails() {
        System.out.println(account.getAccountDetails());
    }
}

// Main class to run the application
public class BankApp {
    public static void main(String[] args) {
        try {
            // Initialize dependencies
            InputHandler inputHandler = new ConsoleInputHandler();
            Account account = new Account();
            TransactionHandler transactionHandler = new TransactionHandler(account);
            BankMenu bankMenu = new BankMenu(inputHandler, account, transactionHandler);

            // Start the bank menu
            bankMenu.showMenu();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred during application execution: " + e.getMessage());
        }
    }
}

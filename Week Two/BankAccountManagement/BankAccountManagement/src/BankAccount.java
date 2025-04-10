import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public abstract class BankAccount implements Transactional{
    // declare instance variables
    protected String customerName;
    private double accountBalance;
    protected String accountNumber;
    protected LinkedList<Transaction> transactions =  new LinkedList<>();
    
    protected abstract String displayInterest();

    //getter for account balance
    protected double getAccountBalance(){
        return accountBalance;
    }

    // setter for account balance
    protected void setAccountBalance(double amount){
        accountBalance = amount;
    }

    // constructor to initiate the instance variables
    public BankAccount(String customerName, double initialDeposit, String accountNumber){
        this.customerName = customerName;
        this.accountBalance = initialDeposit;
        this.accountNumber = accountNumber;
    }

    protected void addTransaction(TransactionType transactionType, double amount){
        Transaction newTransaction = new Transaction(amount, transactionType);
        transactions.addFirst(newTransaction);
    }

    
    public List<Transaction> getTransactionList(int n){
        List<Transaction> resultList = new ArrayList<>();
        for(int i = 0; i < n && i < transactions.size(); i++){
            resultList.add(transactions.get(i));
        }
        return resultList;
    }

    public List<Transaction> getTransactionList(){
        return new ArrayList<>(transactions);
    }

}
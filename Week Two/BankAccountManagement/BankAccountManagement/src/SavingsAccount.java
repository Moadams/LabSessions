
public class SavingsAccount extends BankAccount{
    private static final double MIN_BALANCE = 100; // set the minimum balance for the account to be 100
    private static final double INTEREST_RATE = 0.05;

    // constructor
    public SavingsAccount(String customerName, double initialDeposit, String accountNumber){
        super(customerName, initialDeposit, accountNumber);
    }

    @Override
    public String deposit(double amount){
        if(amount <= 0){
            return "Deposit amount must be greater than zero.";
        }
        setAccountBalance(getAccountBalance() + amount);
        addTransaction(TransactionType.DEPOSIT, amount);
        return "Deposit successful";
        
    }

    @Override
    public String withdraw(double amount){
        if (amount <= 0) {
            return "Withdrawal amount must be greater than zero.";
        }
    
        if(getAccountBalance() - amount < MIN_BALANCE){
            return "Your account balance should not be less than " + MIN_BALANCE;
        }else{
            setAccountBalance(getAccountBalance() - amount);
            addTransaction(TransactionType.WITHDRAW, amount);
            return "Withdrawal successful";
        }
        
    }

    public double calculateInterest(){
        return getAccountBalance() * INTEREST_RATE;
    }

    @Override
    public String displayInterest(){
        return "Your interest is Ghs"+calculateInterest();
    }

}
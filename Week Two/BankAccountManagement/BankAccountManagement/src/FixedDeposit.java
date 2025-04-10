import java.time.LocalDate;

public class FixedDeposit extends BankAccount {
    LocalDate maturityDate;
    private static final double INTEREST_RATE = 0.10; // setting the interest rate a 10%
    
    public FixedDeposit(String customerName, String accountNumber, double initialDeposit, LocalDate maturityDate){
        super(customerName, initialDeposit, accountNumber);
        this.maturityDate = maturityDate;
    }

    @Override
    public String deposit(double amount){
        
        if (amount <= 0) {
            return "Deposit amount should be greater than 0.";
        }
        if(getAccountBalance() > 0){
            return "Sorry, you cannot make extra deposit to a fixed account";
        }else{
            setAccountBalance(getAccountBalance() + amount);
            addTransaction(TransactionType.DEPOSIT, amount);
            return "Deposit successful";
        }
        
    }

    @Override
    public String withdraw(double amount){
        if (amount <= 0) {
            return "Withdrawal amount should be greater than 0.";
        }
        
        // Check if the account is matured
        if(LocalDate.now().isBefore(maturityDate)){
            return "Your fixed deposit is not yet matured. Withdrawal is not allowed.";
        }

        if(amount > getAccountBalance()){
            return "Sorry! You do not have enough funds";
        }

        setAccountBalance(getAccountBalance() - amount);
        addTransaction(TransactionType.WITHDRAW, amount);
        return "Withdrawal successful";
        
    }

    public double calculateInterest(){
        return getAccountBalance() * INTEREST_RATE;
    }

    @Override
    public String displayInterest(){
        return "Your interest is Ghs"+calculateInterest();
    }    

}

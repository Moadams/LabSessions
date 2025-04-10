public class CurrentAccount extends BankAccount {
    private double overdraftLimit = 50;


    public CurrentAccount(String customerName, String accountNumber, double initialDeposit){
        super(customerName, initialDeposit, accountNumber);
    }

    @Override
    public String deposit(double amount){
        if (amount <= 0) {
            return "Deposit amount should be greater than 0.";
        }
       
        setAccountBalance(getAccountBalance() + amount);
        addTransaction(TransactionType.DEPOSIT, amount);
        return "Deposit successful. New balance: Ghs " + getAccountBalance();
       
    }
    

    @Override
    public String withdraw(double amount){
        if (amount <= 0) {
            return "Withdrawal amount should be greater than 0.";
        }

        if(getAccountBalance() - amount < -overdraftLimit){
            return "Withdrawal failed! You cannot go below the overdraft limit of Ghs"+overdraftLimit;
        }
        
        // process overdraft withdrawal
        if(getAccountBalance() - amount < 0){
            setAccountBalance(getAccountBalance() - amount);
            addTransaction(TransactionType.OVERDRAFT, amount);    
            return "Overdraft withdrawal successful. New balance: Ghs " + getAccountBalance();

        }

        //process normal withdrawal
        setAccountBalance(getAccountBalance() - amount);
        addTransaction(TransactionType.WITHDRAW, amount);
        return "Withdrawal successful. New balance: Ghs " + getAccountBalance();
        
    }

    @Override
    public String displayInterest(){
        return "Current accounts do not earn interest.";
    }

}

public enum TransactionType {
    DEPOSIT("Deposit"),
    WITHDRAW("Withdrawal"),
    OVERDRAFT("Overdraft");

    private final String description;

    TransactionType(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
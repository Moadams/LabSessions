import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction{
    protected double amount;
    protected TransactionType transactionType;
    protected LocalDateTime transactionDate;
    

    public Transaction(double amount, TransactionType transactionType){
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = LocalDateTime.now();
    }

    public String transactionToString(){
        return transactionDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) + " - " + transactionType.getDescription() + " Ghs"+amount ;
    }
}
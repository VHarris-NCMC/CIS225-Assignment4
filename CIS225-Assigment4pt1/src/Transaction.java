import java.util.ArrayList;

public class Transaction {


    private final TransactionType transactionType;
    private final User user;
    private final Bank bank;
    public static ArrayList<Transaction> TransactionLog = new ArrayList<Transaction>();
    public enum TransactionType { WITHDRAWAL, DEPOSIT, BALANCE}

    public Transaction(Bank bank, User user, Account account, Transaction.TransactionType transactionType, float amount)
    {
        this.bank = bank;
        this.user = user;
        this.transactionType = transactionType;




    }
}

public class Account {


    private final AccountType accountType;
    private String accountNumber;
    public Account(String accountNumber, Account.AccountType type) {
        this.accountType = type;
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public enum AccountType { Saving, Checking}

}

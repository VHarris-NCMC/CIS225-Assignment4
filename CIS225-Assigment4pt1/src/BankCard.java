public class BankCard {
    // BANK -- PUBLIC GET
    private Bank bank; public Bank getBank() { return bank; }
    // ACCOUNT NUMBER PUBLIC GET
    private String accountNumber; public String getAccountNumber() { return accountNumber;}
    private String name;
    public BankCard(Bank bank, String accountNumber, String name)
    {

        this.bank = bank;
        this.accountNumber = accountNumber;
        this.name = name;
    }


    public String getName() {
        return name;
    }
}

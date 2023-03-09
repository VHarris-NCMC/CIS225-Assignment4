
import java.util.HashMap;



public class Bank {

    // NAME -- GET PRIVATE SET
    private String name;

    public static void NewCustomer(User user, Bank bank ) {

    }
    public static void NewCustomerTest(User user, Bank bank){
        for (Bank b : banks) {
            if (b == bank)
            {
                bank.AddCustomer(user, new PIN("0000"));
                bank.issueBankCard(user);
            }
        }
    }

    private void issueBankCard(User user) {

        BankCard card = new BankCard(this, Customers.get(user)[0].getAccountNumber(), user.getName());
        user.assignBankCard(card);
    }


    public String getName(){ return name;}
    private void setName(String s) {name = s;}
    // FRB -- GET
    private final String FRB; String getFRB() { return FRB;}
    // BANKS GET
    private static final Bank[] banks = new Bank[] {
            new Bank("BankOfAmerica", "072000805"),
            new Bank("StFrancis", "071000301"),
            new Bank("FFront", "272485385") };

    public static Bank[] getBanks() { return banks;}
    // USERS / Bank Customers
    private HashMap<User, Account[]> Customers = new HashMap<User, Account[]>();


    private HashMap<User, PIN> PINS = new HashMap<User, PIN>();



    public Bank(String name, String frb) {

        this.FRB = frb;
        this.name = name;
    }



    private void AddCustomer(User user, PIN pin) {
        int randomRef = (int) Math.floor(Math.random() * 100);
        String accountNumber = String.valueOf(randomRef + 7000000);
        String sAccountNum = String.valueOf(randomRef + 2000000);
        Account ChkAccount = new Account(accountNumber, Account.AccountType.Saving);
        Account SavAccount = new Account(sAccountNum, Account.AccountType.Checking);

        Customers.put(user, new Account[]{ChkAccount, SavAccount});

        PINS.put(user, pin);
        System.out.println("Customer added: " + user.getName() + " Account Number: " + accountNumber + " PIN: " + String.valueOf(pin));

    }

    public static class Connection {

        private static ATM atm; public static ATM getATM(){ return atm;}
        private static Bank bank; public static Bank getBank(){ return bank;}

        public Connection(ATM atm, Bank bank) {
            Connection.bank = bank;
            Connection.atm = atm;
        }
    }
}

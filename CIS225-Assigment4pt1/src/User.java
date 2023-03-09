public class User {

    private int accountNumber;
    private final String[] name;
    private BankCard card = null;

    public User(String first, String last)
    {
        name = new String[]{first, last};


    }

    public void assignBankCard(BankCard card)
    {
        this.card = card;
    }
    public String getName() {
        return name[0] + " " + name[1];
    }

    public BankCard getCard() {
        return card;
    }
}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardReader implements Adapter, UIComponent
{

    private static boolean hasCardInserted; public static boolean hasCardInserted() { return hasCardInserted; }

    private Display display;
    private JPanel reader_UI;
    private JButton button;
    public CardReader(Display display) {
        this.display = display;
        reader_UI = BuildReaderPanel();
        ConfigureUI();
    }
    private static BankCard insertedCard; public static BankCard getInsertedCard() { return insertedCard; } public static void setInsertedCard(BankCard card) { insertedCard = card; hasCardInserted = true; } private void removeCard(){ insertedCard = null; hasCardInserted = false; }

    private JPanel BuildReaderPanel() {


        JPanel panel = new JPanel();
        panel.setSize(200,200);
        button = new JButton();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardReader.insertCard(ATM.getCurrentUser().getCard());
            }
        });
        panel.add(button);
        panel.setBorder(UI_Prefabs.Border1());
        return panel;
    }


    private static void insertCard(BankCard card) {
        try {
            setInsertedCard(card);
            NIC.EstablishConnection(card.getBank());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public Bank getBankFromCard() { return insertedCard.getBank();
    }
    public String GetAccountFromCard()
    {
        return insertedCard.getAccountNumber();
    }
    public String getNameFromCard() { return (hasCardInserted()) ? insertedCard.getName() : "Card not Found"; }
    @Override
    public void OnBoot() {
        Adapter.super.OnBoot();
        button.setText("Card Inserted");
        button.setEnabled(false);
    }

    @Override
    public void OnWaiting() {
        Adapter.super.OnWaiting();
        button.setText("Insert Card");
        button.setEnabled(true);
    }

    @Override
    public Display GetDisplay() {
        return display;
    }


    @Override
    public JPanel GetPanel() {
        return reader_UI;
    }


}

import javax.swing.*;
import java.awt.*;

public class DepositSlot implements Adapter, UIComponent{

    @Override
    public Display GetDisplay() {
        return display;
    }
    private final Display display;

    @Override
    public JPanel GetPanel() { return deposit_UI;}
    private final JPanel deposit_UI;

    public DepositSlot(Display display) {

        this.display = display;
        deposit_UI = BuildDepositPanel();


        ConfigureUI();


    }

    private JPanel BuildDepositPanel()
    {
        JPanel panel = new JPanel();
        panel.setVisible(true);
        panel.setSize(new Dimension(400,400));
        panel.setBackground(Color.lightGray);
        JButton depositButton = new JButton();
        depositButton.setText("Make Deposit");
        panel.add(depositButton);
        depositButton.setVisible(true);
        panel.setBorder(UI_Prefabs.Border1());
        return panel;
    }




    @Override
    public void OnWaiting() {

        ConfigureUI();
    }






}

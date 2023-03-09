import javax.swing.*;
import java.awt.*;

public class Dispenser implements Adapter, UIComponent {

    //PANEL
    private final JPanel dispenser_UI;
    @Override public JPanel GetPanel() {
        return dispenser_UI;
    }

    //DISPLAY
    private Display display;
    @Override public Display GetDisplay() {
        return display;
    }

    public Dispenser(Display display) {

        this.display = display;

        dispenser_UI = BuildDispenserPanel();
        ConfigureUI();
    }

    private JPanel BuildDispenserPanel() {

        JPanel panel = new JPanel();
        panel.setVisible(true);
        panel.setSize(new Dimension(200,200));

        panel.setBackground(Color.lightGray);
        panel.setBorder(UI_Prefabs.Border1());

        return panel;


    }




    @Override
    public void OnWaiting() {
        ConfigureUI();
    }






}

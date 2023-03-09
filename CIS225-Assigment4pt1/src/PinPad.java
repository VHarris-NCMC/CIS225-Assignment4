import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.util.Scanner;

public class PinPad extends KeyAdapter implements Adapter, UIComponent, Runnable {


    private JButton[] buttons;

    private final JPanel pinPad_UI;



    public static char[] pinEntry;
    public static String getPinInput() {

        String str = new String();
        for( char c : pinEntry)
        {
            str = str.concat(Character.toString('*'));
        }

        return str;

    }

    public static Account getAccount() {



    }

    @Override public void OnConnected() { resetInput();}

    private void resetInput() {
        pinEntry = new char[] { '_', '_', '_', '_' };
    }

    @Override public JPanel GetPanel() { return pinPad_UI;}
    private Display display;
    @Override public Display GetDisplay() {return display;}

    private boolean keyPressed()
    {

        return false;
    }

    public PinPad(Display display)
    {

        this.display = display;
        pinPad_UI = BuildPinPanel();
        buttons = GenerateButtons(pinPad_UI);
        ConfigureUI();





    }

    public static void keyIn(char c)
    {
        if (Character.isDigit(c) && Character.getNumericValue(c) < 10 && Character.getNumericValue(c) >= 0)
        {

            for ( int i = 0; i < pinEntry.length; i ++)
            {
                if (pinEntry[i] == '_')
                {
                    pinEntry[i] = c;
                    return;
                }
            }
        }else
        {
          throw new RuntimeException();
        }
        if (pinEntry[3] != '_')
        {
            Transaction.AddTransaction(ATM.DoTransaction());
        }
    }



    void GetInput()
    {
        Scanner scanner = new Scanner(System.in);
        String input = new String();
        while (scanner.hasNextInt())
        {
            input.concat(scanner.next());
            System.out.println(input);
        }

    }


    @Override
    public void OnWaiting()
    {

        for (JButton b: buttons)
        {
            b.setEnabled(false);
        }
    }



    public JPanel BuildPinPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,3));
        panel.setVisible(true);
        panel.setBorder(UI_Prefabs.Border1());
        return panel;

    }

    private JButton[] GenerateButtons(JPanel panel) {

        JButton[] buttons = new JButton[12];
        char[] chars = new char[]{ '1', '2', '3', '4', '5', '6', '7', '8', '9', '*', '0', '#'};
        int i = 0;
        for (char c : chars)
        {
            JButton button = new JButton();
            button.setVisible(true);
            button.setText(String.valueOf(c));
            panel.add(button);
            buttons[i] = button;
            i++;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    char c = button.getText().charAt(0);
                    keyIn(c);
                }
            });
        }
        return buttons;


    }


    @Override
    public void run() {
        UpdateState(ATM.GetState());
    }
    @Override
    public void UpdateState(ATM.State state)
    {
        switch (state)
        {
            case CONNECTING -> {
                for (JButton b : buttons)
                {
                    if(!b.isEnabled())
                    {
                        continue;
                    }
                    else
                    {
                        b.setEnabled(false);
                    }
                    b.repaint();
                }
            }
            case CONNECTED -> {
                for (JButton b : buttons)
                {
                 if(b.isEnabled())
                 {
                     continue;
                 }
                 else
                 {
                     b.setEnabled(true);
                 }
                 b.repaint();
                }
            }
        }
    }
}

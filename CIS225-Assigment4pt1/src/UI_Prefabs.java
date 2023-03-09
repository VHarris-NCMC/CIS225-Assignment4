import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI_Prefabs {


    public static Border Border1()
    {

        return BorderFactory.createLineBorder(Color.darkGray, 1);
    }
    public static JFrame NewCustomerForm()
    {
        Dimension frameDimension = new Dimension(Math.round((Toolkit.getDefaultToolkit().getScreenSize().width * 0.3f)),
                Math.round((Toolkit.getDefaultToolkit().getScreenSize().height * 0.3f)));
        JFrame readmeFrame = new JFrame();
        readmeFrame.setMinimumSize(frameDimension);
        readmeFrame.pack();
        readmeFrame.setTitle("Bank Application");

        JTextPane text = new JTextPane();
        text.setVisible(true);
        readmeFrame.add(text);

        JButton button = new JButton();
        button.setVisible(true);
        button.setText("Create Account");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User("Victor", "Harris");
                Bank.NewCustomerTest(user, Bank.getBanks()[0]);
                ATM.setCurrentUser(user);
                readmeFrame.setVisible(false);
            }
        });
        readmeFrame.add(button);

        readmeFrame.setAlwaysOnTop(true);


        readmeFrame.setVisible(true);
        return readmeFrame;

    }
}

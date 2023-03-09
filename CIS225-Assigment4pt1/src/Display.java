import java.awt.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.swing.*;



public class Display extends JFrame implements Adapter, UIComponent{


    public static String getStatusText1(ATM.State state)
    {
        switch (state)
        {

            case CONNECTING -> { return "Status: " + NIC.getConnectionStatus(); }
            case CONNECTED -> { return NIC.getConnectionStatus() + " : " + Bank.Connection.getBank().getName() + " | " + Bank.Connection.getBank().getFRB() +"\n" + ATM._ATM_.getCardReader().getNameFromCard();}

            default -> { return ""; }
        }



    }
    public static String getStatusText2(ATM.State state)
    {
        return "Machine Status: " + ATM.GetState() + " | Component State (Display) " + state;
    }
    /* JFrame fields */
    private final int[] frameSize = new int[] {Math.round((Toolkit.getDefaultToolkit().getScreenSize().width / 0.5f)), Math.round((Toolkit.getDefaultToolkit().getScreenSize().height * 0.7f))};


    private int DividerSize() { return 10; }
    private int DividerLocation(){ return frameSize[1]/2;}
    /* JFrame Components */
    private final JSplitPane splitPane1 = BuildSplitPane1(DividerSize(), DividerLocation());
    private final JPanel topPanel = BuildTopPanel();
    private final JPanel bottomPanel = BuildBottomPanel();
    private final JPanel horizontalSplitBottom = BuildBottomSplitPanel();

    /* JPanel fields */


    private final JPanel screenPanel = BuildScreenPanel(frameSize);
    private final JTextPane statusText1;
    private JTextPane statusText2;
    private JTextField inputText;
    private Dimension TopPanelSize() { return new Dimension(screenPanel.getSize().width, screenPanel.getSize().height / 2);}
    /* panels for each UI components are added here. */

    public void AddComponentToBottomPanel(JPanel panel) { horizontalSplitBottom.add(panel); repaint();}



    private void showScreenPanel() {splitPane1.setLeftComponent(screenPanel);}

    private final int idleTimeoutThreshold = 15;

    public Display() {

        this.setVisible(true);
        this.setSize(frameSize[0], frameSize[1]);
        this.setPreferredSize(new Dimension(frameSize[0], frameSize[1]));
        getContentPane().setLayout(new GridLayout());
        
        this.setTitle("ATM");



        statusText1 = BuiltTextPane();
        statusText2 = BuiltTextPane();
        inputText = BuildInputText();

        screenPanel.setVisible(true);
        statusText1.setVisible(true);
        statusText2.setVisible(true);
        screenPanel.add(statusText1);
        screenPanel.add(statusText2);
        topPanel.add(inputText);
        topPanel.setSize(Math.round(topPanel.getWidth() * 0.07f), topPanel.getHeight());





        SetLayouts();
        pack();
        screenPanel.revalidate();
        screenPanel.repaint();
    }

    private JTextField BuildInputText() {

        JTextField field = new JTextField();
        field.setBackground(Color.darkGray);
        field.setDisabledTextColor(Color.white);
        field.setSize(new Dimension(frameSize[0] / 4, frameSize[1] / 4));
        field.setPreferredSize(new Dimension(frameSize[0] / 4, frameSize[1] / 4));

        return field;
    }

    private static JPanel BuildTopPanel() {
        JPanel top = new JPanel();
        top.setVisible(true);


        return top;
    }
    private static JPanel BuildBottomPanel() {
        JPanel bottom = new JPanel();

        bottom.setVisible(true);


        return bottom;
    }
    private static JPanel BuildBottomSplitPanel() {
        JPanel pane = new JPanel();
        pane.setVisible(true);
        pane.setLayout(new GridLayout(2,2));

        return pane;
    }

    private static JSplitPane BuildSplitPane1(int dividerSize, int dividerLocation) {
        JSplitPane pane = new JSplitPane();
        pane.setDividerSize(dividerSize);
        pane.setDividerLocation(dividerLocation);
        pane.setVisible(true);
        pane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        return pane;
    }
    private static JSplitPane BuildSmallSplitPane()
    {

        JSplitPane pane = new JSplitPane();
        pane.setVisible(true);
        pane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

        return pane;
    }

    private static JPanel BuildScreenPanel(int[] frame) {
        JPanel screen = new JPanel();
        screen.setBackground(Color.black);
        screen.setPreferredSize(new Dimension(Math.round(frame[0] / 2f), Math.round(frame[1] /2f)));
        screen.setSize(new Dimension(Math.round(frame[0] / 2f), Math.round(frame[1] /2f)));

        return screen;
    }

    private JTextPane BuiltTextPane() {
        JTextPane pane = new JTextPane();
        pane.setVisible(true);
        pane.setBackground(Color.gray);
        pane.setPreferredSize(new Dimension(Math.round(screenPanel.getWidth() / 2f), Math.round(screenPanel.getHeight() /2.2f)));
        return pane;
    }

    private void SetLayouts() {

        screenPanel.add(statusText1);
        screenPanel.add(statusText2);
        splitPane1.setTopComponent(topPanel);
        splitPane1.setBottomComponent(bottomPanel);
        splitPane1.setVisible(true);
        splitPane1.setBackground(new Color(255));
        getContentPane().add(splitPane1);
        topPanel.setLayout(new GridLayout(2,2));
        topPanel.setSize(new Dimension(TopPanelSize()));
        topPanel.add(screenPanel);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(horizontalSplitBottom);
        screenPanel.setLayout(new GridLayout(2,2));



    }





    public void TimeOut()
    {
        int timer = ATM.GetTimeOut();
        while (timer < idleTimeoutThreshold)
        {
            updateText(statusText1,"System timeout in: " + (idleTimeoutThreshold - timer));
        }

    }



    public void OnBootEndPrint() {

        int i = 0;
        while (i < 100)
        {

            updateText(statusText1,"Loading: " + i);
            i+=2;

            try {

                TimeUnit.MILLISECONDS.sleep(20);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }



    }




    @Override
    public void OnBoot()
    {

    }
    @Override
    public void OnShut() {

    }

    @Override
    public void OnIdle() {

    }

    @Override
    public void OnConnected() {

        inputText.setVisible(true);

    }

    @Override
    public void OnWaiting() {


        Scanner scanner = new Scanner(System.in);

        while(ATM.GetState() == ATM.State.WAITING)
        {

            updateText(statusText1, "ATM Status : Online \n Please Insert Your Card To Begin Transaction");



            int blinkTimer = 2000;
            try {

                TimeUnit.MILLISECONDS.sleep(blinkTimer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            statusText1.setText("_____________________________________");
            try {
                TimeUnit.MILLISECONDS.sleep(blinkTimer/4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void EndCurrentState() {

        statusText1.setText("");
        statusText2.setText("");
    }

    @Override
    public void ConfigureUI() {

    }

    @Override
    public Display GetDisplay() {
        return this;
    }


    @Override
    public JPanel GetPanel() {
        return null;
    }
    @Override public void UpdateState(ATM.State state)
    {

        updateText(statusText2, getStatusText2(state));
        switch (state)
        {

            case CONNECTING, CONNECTED -> {
                updateText(statusText1, getStatusText1(state));
                updateText(inputText, getInputText(state));
            }

            default -> { break; }
        }
    }

    private String getInputText(ATM.State state) {

        switch (state)
        {

            case CONNECTED -> {  return "Enter your PIN: " + PinPad.getPinInput(); }
            default -> { return "";}
        }
    }

    private void updateText(JTextField inputText, String input) {

        if (!Objects.equals(inputText.getText(), input))
        inputText.setText(input);

    }


    private void updateText(JTextPane jTextPane, String s) {

        if (!Objects.equals(jTextPane.getText(), s))
        {
            jTextPane.setText(s);
        }

    }

    public void PrintLn(String s) {
        updateText(statusText1, s);
    }
    public void WelcomeClient(BankCard card)
    {
        statusText2.setText("Welcome " + card.getName());
    }


}

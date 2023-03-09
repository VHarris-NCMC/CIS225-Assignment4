import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class Application {



    public static void main(String[] args){

        JFrame helper = LaunchHelper();
        while(helper.isActive()){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        new ATM();



        while(true)
        {
            ATM._ATM_.UpdateState();
        }

    };
    private static JFrame LaunchHelper()
    {
        return UI_Prefabs.NewCustomerForm();
    }



}

import java.util.concurrent.TimeUnit;

public class NIC implements Adapter {

    public enum ConnectionStatus {
        CONNECTED, DISCONNECTED, TRYINGTOCONNECT
    }

    private static ConnectionStatus connectionStatus;
    private static Bank.Connection _connection_; public static Bank.Connection getCurrentConnection(){ return _connection_;} private static void setConnection(Bank.Connection connection){ _connection_ = connection;}
    public static ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }
    public NIC() {
        connectionStatus = ConnectionStatus.DISCONNECTED;

    }




    public static void EstablishConnection(Bank bank) throws InterruptedException {



        connectionStatus = ConnectionStatus.TRYINGTOCONNECT;
        ATM.RequestStateChange(ATM.State.CONNECTING);
        Thread t1 = new Thread(() ->
        {
            int connectionTimer = 3;

            while (NIC.getConnectionStatus() != ConnectionStatus.CONNECTED) {
                if (connectionTimer > 0) {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        connectionTimer--;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else {

                    connectionStatus = ConnectionStatus.CONNECTED;
                    ATM.RequestStateChange(ATM.State.CONNECTED);
                }


            }
        });
        t1.start();
        setConnection(new Bank.Connection(ATM._ATM_, bank));
    }
}



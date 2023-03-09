public interface Adapter {




    default void SetState(ATM.State state) throws InterruptedException {

        switch (state)
        {

            case BOOT -> OnBoot();
            case WAITING -> OnWaiting();
            case CONNECTING -> OnConnecting();
            case CONNECTED -> OnConnected();
            case DISCONNECTING -> OnDisconnecting();
            case IDLE -> OnIdle();
            case SHUT -> OnShut();
            case ERROR -> OnError();
        }

    }


    default void OnBoot(){};
    default void OnShut() {};
    default void OnConnecting() throws InterruptedException {};
    default void OnDisconnecting() {};
    default void OnIdle() {};
    default void OnConnected() {};
    default void OnWaiting() { };
    default void OnError(){};
    default void EndCurrentState() {};
    default String Name()
    {
        // TODO: Remove use of toString()
        String str = this.getClass().toString();
        return str.replace("class ", " ");
    };

    


    static void PreBootCheck(ATM atm, Adapter adapter)
    {
       atm.OutputToLine(adapter.Name() + " Status: Online");


    };

    default void UpdateState(ATM.State state) { };


}

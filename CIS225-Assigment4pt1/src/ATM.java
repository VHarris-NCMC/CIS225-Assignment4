
import java.util.Arrays;
import java.util.concurrent.TimeUnit;



public class ATM {

    private static final int _DEFAULT_PIN_ = 3852;

    private final int bootTime = 10;
    private final ComponentSlot<CardReader> reader = new ComponentSlot<>();
    private final ComponentSlot<PinPad> pinPad = new ComponentSlot<>();
    private final ComponentSlot<Display> display = new ComponentSlot<>();
    private final ComponentSlot<NIC> networkInterface = new ComponentSlot<>();
    private final ComponentSlot<Dispenser> dispenser = new ComponentSlot<>();
    private final ComponentSlot<DepositSlot> depositSlot = new ComponentSlot<>();
    private final ComponentSlot[] components = new ComponentSlot[] {reader, display, pinPad, depositSlot, networkInterface, dispenser};
    private static int timeoutCounter;
    //CURRENT USER
    private static User currentUser; public static void setCurrentUser(User user) { currentUser = user; }

    public static User getCurrentUser() {
        return currentUser;
    }


    public static Transaction DoTransaction() {

        User user = getCurrentUser();
        Bank bank = Bank.Connection.getBank();







        return new Transaction(user, bank, PinPad.getAccount(), PinPad.getTransactionType(), PinPad.getAmount() );
    }

    public CardReader getCardReader() {
        return reader.Component();
    }



    public static ATM _ATM_;





    public static void RequestStateChange(State state) throws IllegalStateException {

        if (state == GetState()){
            throw new IllegalStateException("Already in State");
        }
        switch (state)
        {
            default -> SetState(state);
        }


    }



    public enum State {BOOT, WAITING, CONNECTING, CONNECTED, DISCONNECTING, IDLE, SHUT, ERROR};
    private static State _STATE_ = State.SHUT;
    public static State GetState() { return _STATE_;}

    private static void SetState(State state)
    {
        _ATM_.StateEnd();
        try{
            _STATE_ = state;
            StateBegin(_STATE_);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    public void UpdateState()
    {


            switch (_STATE_)
            {
                case WAITING -> UPDATE_WAITING();
                default -> { break;}
            }

        for (ComponentSlot<Adapter> componentSlot : components)
        {
            componentSlot.Component().UpdateState(_STATE_);
        }
    }

    private void UPDATE_WAITING() {
    }

    public static int GetTimeOut() { return timeoutCounter;}


    public ATM ()
    {

        if(_ATM_ == null)
        {
            _ATM_ = this;
        }

        InsertComponents();
        SetState(State.SHUT);
        TurnOn();
    }

    private void InsertComponents() {
        //Display MUST be initialized BEFORE other UI Elements
        this.display.insertComponent(new Display());
        this.reader.insertComponent(new CardReader(display.Component()));
        this.pinPad.insertComponent(new PinPad(display.Component()));
        this.dispenser.insertComponent(new Dispenser(display.Component()));
        this.depositSlot.insertComponent(new DepositSlot(display.Component()));
        this.networkInterface.insertComponent(new NIC());

    }
    private void TurnOn()
    {
        SetState(State.BOOT);
    }
    public void OnBoot()
    {
        InitializeComponents();
        SetState(State.WAITING);
    }
    private void InitializeComponents(){

        Arrays.stream(components).forEach(component -> {
            if (component.IsMissing()) {
                SetState(State.ERROR);
                throw new RuntimeException("Unable to boot, component missing");

            }
            try {
                Adapter.PreBootCheck(this, (Adapter) component.Component());
                int betweenLines_milliseconds = 400;
                TimeUnit.MILLISECONDS.sleep(betweenLines_milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }


    public void OutputToLine(String s) {
        display.Component().PrintLn(s);
    }
    public int IdleTime() {
        return 0;
    }


    private static void StateBegin(State state) throws InterruptedException {

        switch (state)
        {

            case BOOT -> _ATM_.OnBoot();
            case WAITING -> _ATM_.OnWaiting();
            case IDLE -> _ATM_.OnIdle();
            case CONNECTING -> _ATM_.OnConnect();
            case DISCONNECTING -> _ATM_.OnDisconnect();
            case CONNECTED -> _ATM_.OnConnected();
            case SHUT -> _ATM_.OnShut();

        }
        for (ComponentSlot component : _ATM_.components)
        {
            Adapter adapter = (Adapter)component.Component();
            adapter.SetState(state);
        }

    }

    private void OnDisconnect() {

    }

    private  void OnConnect() {

    }

    private void OnWaiting() {

    }

    private void OnIdle() {
    }

    private void OnConnected() {
    }

    private void OnShut() {
    }

    private void StateEnd() {

        switch (GetState())
        {
            case BOOT -> OnBootEnd();
            case WAITING -> OnWaitingEnd();
            case CONNECTING -> OnConnectEnd();
            case DISCONNECTING -> OnDisconnectEnd();
            case IDLE -> OnIdleEnd();
            case CONNECTED -> OnActiveEnd();
            case SHUT -> OnShutEnd();
            case ERROR -> OnErrorEnd();

        }

        for (ComponentSlot<Adapter> component : components)
        {
            Adapter adapter = component.Component();
            adapter.EndCurrentState();
        }

    }

    private void OnErrorEnd() {
    }

    private void OnConnectEnd() {
    }

    private void OnDisconnectEnd() {
    }

    private void OnBootEnd() {
        display.Component().OnBootEndPrint();
    }

    private void OnWaitingEnd() {
    }

    private void OnIdleEnd() {
    }

    private void OnActiveEnd() {
    }

    private void OnShutEnd() {
    }


}

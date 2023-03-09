public class ComponentSlot<Adapter> {

    private Adapter component;
    public enum Status { OK, NOT_INSTALLED, MALFUNCTIONING}
    private Status status;

    public ComponentSlot()
    {
        SetStatus(Status.NOT_INSTALLED);
    }

    private void SetStatus(Status status) {
        this.status = status;
    }

    public void insertComponent(Adapter component) {
        try
        {
        this.component = component;
        SetStatus(Status.OK);
        }
        catch(Exception exception)
        {
            throw new RuntimeException("Unable to Insert Component, Component not found");
        }



    }
    public Adapter Component()
    {
        if (component == null)
        {
            throw new RuntimeException("Component not found");
        }
        return component;
    }

    public boolean IsMissing() {
        return status == Status.NOT_INSTALLED;
    }



}

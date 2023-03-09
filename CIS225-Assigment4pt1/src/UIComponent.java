import javax.swing.*;

public interface UIComponent {
    default void ConfigureUI()
    {
        GetDisplay().AddComponentToBottomPanel(this.GetPanel());
    };
    Display GetDisplay();
    JPanel GetPanel();
}

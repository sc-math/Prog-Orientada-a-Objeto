import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BancoEspiao {

    static ImageIcon icon = new ImageIcon("src/Image/spyicon.png");

    static List<ContaCorrente> ListaContasCorrente = new ArrayList<ContaCorrente>();

    public static void main(String[] args) {

        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }


        Login frame = new Login();
        frame.setVisible(true);
    }
}

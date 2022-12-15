import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class eMentorplus {

    static ImageIcon icon = new ImageIcon("src/Image/icon.png");

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
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

        MySQLOperations ops = new MySQLOperations();

//        try {
//            String chave = ops.novaPessoa("78987445685","14/56/1578","Math","17984574785",2);
//
//            ops.novoUsuario("Math","123",3, chave);
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }

//        MySQLOperations conec = new MySQLOperations();
//        conec.formatarBanco();

        Login frame = new Login();
        frame.setVisible(true);

    }
}

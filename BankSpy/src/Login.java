import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame{


    private JPanel LoginPanel;
    private JTextField UsuarioField;
    private JButton LoginButton;
    private JPasswordField SenhaField;


    public Login(){

        super("Login");


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(LoginPanel);
        this.pack();
        this.setIconImage(BancoEspiao.icon.getImage());



        setSize(new Dimension(500,300));

        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = UsuarioField.getText();
                int senha = (int)(Double.parseDouble(SenhaField.getText()));

                if(usuario.equals("Math") && senha == 123){
                    MenuGeral Menu = new MenuGeral();
                    Menu.setVisible(true);
                    setVisible(false);
                    dispose();
                }
            }
        });
    }
}

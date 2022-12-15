import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class Login extends JFrame{

    private JTextField usuarioField;
    private JPanel loginPanel;
    private JPasswordField senhaField;
    private JButton loginButton;
    private JButton signUpButton;

    public Login(){
        super("Login");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(loginPanel);
        this.pack();
        this.setIconImage(eMentorplus.icon.getImage());
        setSize(new Dimension(500,300));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String usuario = usuarioField.getText();
                String senha = senhaField.getText();
//                System.out.println(usuario);
//                System.out.println(senha);

                MySQLOperations conec = new MySQLOperations();

                try {
                    Usuario teste = conec.getUsuario(usuario);

//                    System.out.println(teste.getNomeDoUsuario());
//                    System.out.println(teste.getSenha());
                    if(usuario.equals(teste.getNomeDoUsuario()) && senha.equals(teste.getSenha())){

                        String mensagem = "Login realizado com sucesso.";
                        JOptionPane.showMessageDialog(null,mensagem,"Login",JOptionPane.INFORMATION_MESSAGE);
                        Menu frame = new Menu();
                        frame.setVisible(true);
                        dispose();
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Senha inválida", "Login", JOptionPane.INFORMATION_MESSAGE);

                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }




            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SignUp frame = new SignUp();
                frame.setVisible(true);
                dispose();
            }
        });
    }
}

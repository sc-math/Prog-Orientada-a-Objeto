import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SignUp extends JFrame {

    private JPanel SignUpPanel;
    private JTextField usuarioField;
    private JButton cadastrarButton;
    private JButton voltarButton;
    private JPasswordField senhaField;
    private JTextField telefoneField;
    private JTextField cpfField;
    private JTextField dataField;
    private JTextField nomeField;

    public SignUp (){
        super("Cadastro de Usuário");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(SignUpPanel);
        this.pack();
        this.setIconImage(eMentorplus.icon.getImage());
        setSize(new Dimension(650,400));
        cadastrarButton.setPreferredSize(new Dimension(150,20)); //Tamanho do Botão Cadastrar


        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Login frame = new Login();
                frame.setVisible(true);
                dispose();
            }
        });

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nome = nomeField.getText();
                String data = dataField.getText();
                String cpf = cpfField.getText();
                String telefone = telefoneField.getText();
                String nomeUsuario = usuarioField.getText();
                String senha = senhaField.getText();

                MySQLOperations conec = new MySQLOperations();

                try {
                    String chave = conec.novaPessoa(cpf,data,nome,telefone,2);

                    conec.novoUsuario(nomeUsuario,senha,1,chave);


                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (MySQLOperations.idInvalido idInvalido) {
                    idInvalido.printStackTrace();
                }

                nomeField.setText("");
                dataField.setText("");
                cpfField.setText("");
                telefoneField.setText("");
                usuarioField.setText("");
                senhaField.setText("");

            }
        });
    }
}

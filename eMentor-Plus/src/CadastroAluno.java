import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CadastroAluno extends JFrame{
    private JPanel cadastroAlunoPanel;
    private JTextField nomeField;
    private JTextField dataField;
    private JTextField cpfField;
    private JTextField telefoneField;
    private JTextField matriculaField;
    private JTextField periodoField;
    private JButton voltarButton;
    private JButton cadastarButton;

    public CadastroAluno(){
        super("Cadastro Aluno");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(cadastroAlunoPanel);
        this.pack();
        this.setIconImage(eMentorplus.icon.getImage());
        setSize(new Dimension(650,400)); //Tamanho da Janela
        cadastarButton.setPreferredSize(new Dimension(150,20)); //Tamanho do Botão Cadastrar
        nomeField.setPreferredSize(new Dimension(150,20));
        dataField.setPreferredSize(new Dimension(150,20));
        cpfField.setPreferredSize(new Dimension(150,20));
        telefoneField.setPreferredSize(new Dimension(150,20));
        matriculaField.setPreferredSize(new Dimension(150,20));
        periodoField.setPreferredSize(new Dimension(150,20));


        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu frame = new Menu();
                frame.setVisible(true);
                dispose();
            }
        });


        cadastarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nome = nomeField.getText();
                String data = dataField.getText();
                String cpf = cpfField.getText();
                String telefone = telefoneField.getText();
                String matricula = matriculaField.getText();
                int periodo = Integer.parseInt(periodoField.getText());

                MySQLOperations conec = new MySQLOperations();

                try {
                    String chave = conec.novaPessoa(cpf, data,nome,telefone,0);
                    conec.novoAluno(matricula,periodo, chave);

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
                matriculaField.setText("");
                periodoField.setText("");
            }
        });
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CadastroProfessor extends JFrame{
    private JPanel cadastroProfessorPanel;
    private JTextField nomeField;
    private JTextField dataField;
    private JTextField cpfField;
    private JTextField telefoneField;
    private JTextField admissaoField;
    private JTextField salarioField;
    private JButton voltarButton;
    private JButton cadastrarButton;

    public CadastroProfessor(){
        super("Cadastro Aluno");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(cadastroProfessorPanel);
        this.pack();
        this.setIconImage(eMentorplus.icon.getImage());
        setSize(new Dimension(650,400)); //Tamanho da janela
        cadastrarButton.setPreferredSize(new Dimension(150,20)); // Tamanho do botão Cadastrar


        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu frame = new Menu();
                frame.setVisible(true);
                dispose();
            }
        });

        cadastrarButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String data = dataField.getText();
                String cpf = cpfField.getText();
                String telefone = telefoneField.getText();
                double salario = Double.parseDouble(salarioField.getText());
                String admissao = admissaoField.getText();

                MySQLOperations conec = new MySQLOperations();

                try {
                    String chave = conec.novaPessoa(cpf, data,nome,telefone,1);
                    conec.novoProfessor(admissao,salario, chave);
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
                admissaoField.setText("");
                salarioField.setText("");

            }
        }));
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AlterarDadosProfessores extends JFrame {

    private JPanel alterarDadosProfessorePanel;
    private JTextField telefoneField;
    private JTextField salarioField;
    private JTextField cpfField;
    private JButton voltarButton;
    private JButton alterarButton;
    private JButton buscarButton;
    private JLabel telefoneText;
    private JLabel salarioText;

    public AlterarDadosProfessores(){
        super("Alterar dados dos Professores");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(alterarDadosProfessorePanel);
        this.pack();
        this.setIconImage(eMentorplus.icon.getImage());
        setSize(new Dimension(650,400)); //Tamanho da Janela
        alterarButton.setPreferredSize(new Dimension(150,20)); //Tamanho do Botão Alterar
        buscarButton.setPreferredSize(new Dimension(150,20)); //Tamanho do Botão Buscar

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MySQLOperations conec = new MySQLOperations();

                String cpf = cpfField.getText();

                boolean flag = false;

                try {
                    Pessoa pessoa = conec.getPessoa(cpf);
                    if(pessoa != null && pessoa.getClass() == Professor.class){

                        telefoneText.setEnabled(true);
                        telefoneField.setEnabled(true);
                        salarioText.setEnabled(true);
                        salarioField.setEnabled(true);
                        alterarButton.setEnabled(true);
                    }
                    else
                        JOptionPane.showMessageDialog(null,"CPF informado não é de um professor.", "Aviso", JOptionPane.ERROR_MESSAGE);

                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (SQLException throwables) {

                    throwables.printStackTrace();
                }




            }
        });

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String telefone = telefoneField.getText();
                double salario = Double.parseDouble(salarioField.getText());
                String cpf = cpfField.getText();

                MySQLOperations conec = new MySQLOperations();

                try {
                    Professor pessoa = (Professor) conec.getPessoa(cpf);

                    conec.atualizaTelefonePessoa(pessoa.getCPF(),telefone);
                    conec.atualizaSalarioProfessor(pessoa.getCPF(), salario);

                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                JOptionPane.showMessageDialog(null,"Dados alterados com sucesso.", "Mensagem", JOptionPane.INFORMATION_MESSAGE);

                telefoneText.setEnabled(false);
                telefoneField.setEnabled(false);
                salarioText.setEnabled(false);
                salarioField.setEnabled(false);
                alterarButton.setEnabled(false);

                cpfField.setText("");
                salarioField.setText("");
                telefoneField.setText("");
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Menu frame = new Menu();
                frame.setVisible(true);
                dispose();
            }
        });
    }
}

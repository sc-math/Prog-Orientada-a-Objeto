import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AlterarDadosAluno extends JFrame {

    private JPanel alterarDadosAlunoPanel;
    private JButton buscarButton;
    private JTextField telefoneField;
    private JTextField periodoField;
    private JButton voltarButton;
    private JButton alterarButton;
    private JTextField matriculaField;
    private JLabel telefoneText;
    private JLabel periodoText;

    public AlterarDadosAluno(){
        super("Alterar dados do Aluno");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(alterarDadosAlunoPanel);
        this.pack();
        this.setIconImage(eMentorplus.icon.getImage());
        setSize(new Dimension(650,400)); //Tamanho da Janela
        alterarButton.setPreferredSize(new Dimension(150,20)); //Tamanho do Botão Alterar
        buscarButton.setPreferredSize(new Dimension(150,20)); //Tamanho do Botão Buscar

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MySQLOperations conec = new MySQLOperations();

                String matricula = matriculaField.getText();

                boolean flag = false;

                try {
                    Aluno aluno = conec.getAluno(matricula);
                    if(aluno != null){
                        flag = true;
                    }

                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (SQLException throwables) {
                    System.out.println("Caiu no catch");
                    throwables.printStackTrace();
                }
                if(flag){
                    telefoneText.setEnabled(true);
                    telefoneField.setEnabled(true);
                    periodoText.setEnabled(true);
                    periodoField.setEnabled(true);
                    alterarButton.setEnabled(true);
                }

            }
        });

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String telefone = telefoneField.getText();
                int periodo = Integer.parseInt(periodoField.getText());
                String matricula = matriculaField.getText();

                MySQLOperations conec = new MySQLOperations();

                try {
                    Aluno aluno = conec.getAluno(matricula);
                    conec.atualizaTelefonePessoa(aluno.getCPF(),telefone);
                    conec.atualizaPeriodoAluno(matricula,periodo);
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                JOptionPane.showMessageDialog(null,"Dados alterados com sucesso.", "Mensagem", JOptionPane.INFORMATION_MESSAGE);

                telefoneText.setEnabled(false);
                telefoneField.setEnabled(false);
                periodoText.setEnabled(false);
                periodoField.setEnabled(false);
                alterarButton.setEnabled(false);

                matriculaField.setText("");
                telefoneField.setText("");
                periodoField.setText("");
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

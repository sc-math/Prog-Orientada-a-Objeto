import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.IllegalFormatCodePointException;

public class DepositoCC extends JFrame {

    private JPanel depositoCCPanel;
    private JTextField agenciaField;
    private JTextField contaField;
    private JButton buscarButton;
    private JTextField valorField;
    private JButton depositarButton;
    private JButton voltarButton;
    private JLabel valorLabel;

    private ContaCorrente contaAtual = new ContaCorrente();

    public DepositoCC(){

        super("Deposito Conta Corrente");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(depositoCCPanel);
        this.pack();
        this.setIconImage(BancoEspiao.icon.getImage());
        setSize(new Dimension(500,300));
        valorLabel.setEnabled(false);
        valorField.setEnabled(false);
        depositarButton.setEnabled(false);


        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelecionaContaCorrente Menu = new SelecionaContaCorrente();
                Menu.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConexaoMySQL conec = new ConexaoMySQL();
                contaAtual = conec.buscaContaCorrente(Integer.parseInt(agenciaField.getText()), Integer.parseInt(contaField.getText()));

                if(contaAtual != null){
                valorField.setEnabled(true);
                valorLabel.setEnabled(true);
                depositarButton.setEnabled(true);
                }

                else{
                    JOptionPane.showMessageDialog(null, "Conta não encontrada", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        depositarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(valorField.getText() != ""){
                    java.util.Date dataAtual = new Date();
                    String dataFormatada = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(dataAtual);

                    contaAtual.Deposito(Integer.parseInt(agenciaField.getText()),Integer.parseInt(contaField.getText()), 1 ,"Deposito", dataFormatada, Double.parseDouble(valorField.getText()));

                    JOptionPane.showMessageDialog(null, "Depósito realizado com sucesso!", "Aviso", JOptionPane.INFORMATION_MESSAGE);

                    SelecionaContaCorrente Menu = new SelecionaContaCorrente();
                    Menu.setVisible(true);
                    setVisible(false);
                    dispose();
                }
                else
                    JOptionPane.showMessageDialog(null, "Informe um valor de depósito!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}


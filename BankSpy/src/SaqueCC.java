import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;

public class SaqueCC extends JFrame {

    private JPanel saqueCCPanel;
    private JTextField agenciaField;
    private JTextField contaField;
    private JButton buscarButton;
    private JTextField valorField;
    private JButton voltarButton;
    private JButton sacarButton;
    private JLabel valorLabel;


    private ContaCorrente contaAtual = new ContaCorrente();


    public SaqueCC(){

        super("Saque Conta Corrente");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(saqueCCPanel);
        this.pack();
        this.setIconImage(BancoEspiao.icon.getImage());

        setSize(new Dimension(500,300));
        valorLabel.setEnabled(false);
        valorField.setEnabled(false);
        sacarButton.setEnabled(false);

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
                    sacarButton.setEnabled(true);
                }

                else{
                    JOptionPane.showMessageDialog(null, "Conta não encontrada", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        sacarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(valorField.getText() != "" && Integer.parseInt(valorField.getText()) <= contaAtual.getSaldo()+contaAtual.getLimite()){
                    java.util.Date dataAtual = new Date();
                    String dataFormatada = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(dataAtual);

                    contaAtual.SaqueCC(Integer.parseInt(agenciaField.getText()), Integer.parseInt(contaField.getText()),3,"Saque", dataFormatada, Double.parseDouble(valorField.getText()));

                    JOptionPane.showMessageDialog(null, "Saque realizado com sucesso!", "Aviso", JOptionPane.INFORMATION_MESSAGE);

                    SelecionaContaCorrente Menu = new SelecionaContaCorrente();
                    Menu.setVisible(true);
                    setVisible(false);
                    dispose();
                }
                else if(Integer.parseInt(valorField.getText()) > contaAtual.getSaldo()+contaAtual.getLimite()){
                    JOptionPane.showMessageDialog(null, "Saldo insuficiente!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(null, "Informe um valor de Saque!", "Aviso", JOptionPane.INFORMATION_MESSAGE);

            }
        });
    }
}

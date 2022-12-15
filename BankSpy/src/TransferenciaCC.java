import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;

public class TransferenciaCC extends JFrame{

    private JPanel transferenciaCCPanel;
    private JTextField agenciaField1;
    private JTextField contaField1;
    private JButton buscarButton1;
    private JTextField agenciaField2;
    private JTextField contaField2;
    private JButton buscarButton2;
    private JTextField valorField;
    private JButton voltarButton;
    private JButton transferirButton;
    private JLabel agenciaLabel;
    private JLabel contaLabel;
    private JLabel valorLabel;


    private ContaCorrente contaAtual1 = new ContaCorrente();
    private ContaCorrente contaAtual2 = new ContaCorrente();

    public TransferenciaCC(){

        super("Transferência");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(transferenciaCCPanel);
        this.pack();
        this.setIconImage(BancoEspiao.icon.getImage());

        setSize(new Dimension(500,300));
        valorLabel.setEnabled(false);
        valorField.setEnabled(false);
        transferirButton.setEnabled(false);
        buscarButton2.setEnabled(false);
        agenciaField2.setEnabled(false);
        contaField2.setEnabled(false);


        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelecionaContaCorrente Menu = new SelecionaContaCorrente();
                Menu.setVisible(true);
                setVisible(false);
                dispose();
            }
        });


        buscarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean achou = false;
                int size = BancoEspiao.ListaContasCorrente.size();

                for(int i = 0; i < size; i++){
                    contaAtual1 = BancoEspiao.ListaContasCorrente.get(i);

                    if(contaAtual1.getAgencia() == Integer.parseInt(agenciaField1.getText()) && contaAtual1.getConta() == Integer.parseInt(contaField1.getText())){

                        agenciaField2.setEnabled(true);
                        agenciaLabel.setEnabled(true);
                        contaField2.setEnabled(true);
                        contaLabel.setEnabled(true);
                        buscarButton2.setEnabled(true);
                        achou = true;
                        break;
                    }
                }
                if(!achou){
                    JOptionPane.showMessageDialog(null, "Conta não encontrada", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        buscarButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean achou = false;
                int size = BancoEspiao.ListaContasCorrente.size();

                for(int i = 0; i < size; i++){
                    contaAtual2 = BancoEspiao.ListaContasCorrente.get(i);

                    if(contaAtual2.getAgencia() == Integer.parseInt(agenciaField2.getText()) && contaAtual2.getConta() == Integer.parseInt(contaField2.getText())){

                        valorField.setEnabled(true);
                        valorLabel.setEnabled(true);
                        transferirButton.setEnabled(true);
                        achou = true;
                        break;
                    }
                }
                if(!achou){
                    JOptionPane.showMessageDialog(null, "Conta não encontrada", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        transferirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(valorField.getText() != "" && Integer.parseInt(valorField.getText()) <= contaAtual1.getSaldo()+contaAtual1.getLimite()){
                    java.util.Date dataAtual = new Date();
                    String dataFormatada = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(dataAtual);

                    contaAtual1.SaqueCC(Integer.parseInt(agenciaField2.getText()),Integer.parseInt(contaField2.getText()),2,"Transferência efetuada", dataFormatada, Double.parseDouble(valorField.getText()));
                    contaAtual2.Deposito(Integer.parseInt(agenciaField2.getText()), Integer.parseInt(contaField2.getText()), 2,"Transferência recebida", dataFormatada, Double.parseDouble(valorField.getText()));

                    JOptionPane.showMessageDialog(null, "Transferência realizada com sucesso!", "Aviso", JOptionPane.INFORMATION_MESSAGE);

                    SelecionaContaCorrente Menu = new SelecionaContaCorrente();
                    Menu.setVisible(true);
                    setVisible(false);
                    dispose();
                }
                else if(Integer.parseInt(valorField.getText()) > contaAtual1.getSaldo()+contaAtual1.getLimite()){
                    JOptionPane.showMessageDialog(null, "Saldo insuficiente!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(null, "Informe um valor de Saque!", "Aviso", JOptionPane.INFORMATION_MESSAGE);

            }
        });
    }
}

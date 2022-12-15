import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelecionaContaCorrente extends JFrame {

    private JPanel SelecionaCCPanel;
    private JButton cadastroButton;
    private JButton saqueButton;
    private JButton transferenciaButton;
    private JButton compChequesButton;
    private JButton depositoButton;
    private JButton extratoButton;
    private JButton voltarButton;

    public SelecionaContaCorrente(){
        super("Opções Conta Corrente");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(SelecionaCCPanel);
        this.pack();
        this.setIconImage(BancoEspiao.icon.getImage());
        setSize(new Dimension(500,300));


        cadastroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CadastroContaCorrente Menu = new CadastroContaCorrente();
                Menu.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelecionaContas Menu = new SelecionaContas();
                Menu.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
        depositoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DepositoCC Menu = new DepositoCC();
                Menu.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
        extratoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExtratoCC Menu = new ExtratoCC();
                Menu.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
        saqueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaqueCC Menu = new SaqueCC();
                Menu.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
        transferenciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransferenciaCC Menu = new TransferenciaCC();
                Menu.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
    }
}

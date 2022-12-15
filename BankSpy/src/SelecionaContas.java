import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelecionaContas extends JFrame {


    private JPanel SelecionaContasPanel;
    private JButton contaCorrenteButton;
    private JButton contaPoupancaButton;
    private JButton contaSalarioButton;
    private JButton contaEmpresarialButton;
    private JButton voltarButton;

    public SelecionaContas(){
        super("Seleciona Conta");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(SelecionaContasPanel);
        this.pack();
        this.setIconImage(BancoEspiao.icon.getImage());
        setSize(new Dimension(500,300));


        contaCorrenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelecionaContaCorrente Menu = new SelecionaContaCorrente();
                Menu.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuGeral Menu = new MenuGeral();
                Menu.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
    }
}

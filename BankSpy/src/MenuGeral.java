import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuGeral extends JFrame {

    private JPanel MenuGeralPanel;
    private JButton clientesButton;
    private JButton contasButton;
    private JButton financiamentosButton;
    private JButton usuariosButton;
    private JButton corretagemButton;
    private JButton cotacoesButton;
    private JButton sairButton;

    public MenuGeral(){
        super("Menu Geral");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MenuGeralPanel);
        this.pack();
        this.setIconImage(BancoEspiao.icon.getImage());
        setSize(new Dimension(500,300));


        contasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelecionaContas Menu = new SelecionaContas();
                Menu.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
    }
}

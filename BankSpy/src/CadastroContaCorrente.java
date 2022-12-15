import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroContaCorrente extends JFrame {


    private JPanel cadastroCCPanel;
    private JTextField agenciaField;
    private JTextField numeroContaField;
    private JTextField dataAberturaField;
    private JTextField saldoInicialField;
    private JTextField limiteField;
    private JTextField mensalidadeField;
    private JButton salvarButton;
    private JButton voltarButton;

    public CadastroContaCorrente(){
        super("Cadastro Conta Corrente");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(cadastroCCPanel);
        this.pack();
        this.setIconImage(BancoEspiao.icon.getImage());
        setSize(new Dimension(600,360));


        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ConexaoMySQL conec = new ConexaoMySQL();

                //Insere no MySQL
                conec.insereContaCorrenteNoMySQL(Integer.parseInt(agenciaField.getText()),Integer.parseInt(numeroContaField.getText()) ,dataAberturaField.getText() ,Double.parseDouble(saldoInicialField.getText()) ,Double.parseDouble(limiteField.getText()) ,Double.parseDouble(mensalidadeField.getText()) );

//                ContaCorrente conta = new ContaCorrente(Integer.parseInt(agenciaField.getText()), Integer.parseInt(numeroContaField.getText()), dataAberturaField.getText(), Double.parseDouble(saldoInicialField.getText()), Double.parseDouble(limiteField.getText()), Double.parseDouble(mensalidadeField.getText()));
//                conta.Dados();
//                conta.Extrato();
//                BancoEspiao.ListaContasCorrente.add(conta);
                JOptionPane.showMessageDialog(null,"Cadastro realizado com Sucesso", "Informação",JOptionPane.INFORMATION_MESSAGE);
                SelecionaContaCorrente Menu = new SelecionaContaCorrente();
                Menu.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelecionaContaCorrente Menu = new SelecionaContaCorrente();
                Menu.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
    }
}

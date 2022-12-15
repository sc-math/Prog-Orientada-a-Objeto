import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExtratoCC extends JFrame{

    private JPanel extratoCCPanel;
    private JButton buscarButton;
    private JTextField agenciaField;
    private JTextField contaField;
    private JTable tabelaExtrato;
    private JButton voltarButton;
    private JLabel saldoLabel;
    private JLabel saldoLimiteLabel;
    private javax.swing.JScrollPane JScrollPane;


    public ExtratoCC(){

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(extratoCCPanel);
        this.pack();
        this.setIconImage(BancoEspiao.icon.getImage());

        setSize(new Dimension(500,300));
        tabelaExtrato.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String []{
                        "Data Operação", "Descrição", "Valor"
                }
            ){
            boolean[] canEdit = new boolean[] {
                    false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex){
                return canEdit [columnIndex];
            }
        });
        JScrollPane.setViewportView(tabelaExtrato);



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
                DefaultTableModel Tabela = (DefaultTableModel)  tabelaExtrato.getModel();
                Tabela.getDataVector().removeAllElements();

                ContaCorrente contaAtual = new ContaCorrente();

                ConexaoMySQL conec = new ConexaoMySQL();
                contaAtual = conec.buscaContaCorrente(Integer.parseInt(agenciaField.getText()), Integer.parseInt(contaField.getText()));

                if (contaAtual != null){
                    saldoLabel.setText("Saldo Atual: "+contaAtual.Formata(contaAtual.getSaldo()));
                    saldoLimiteLabel.setText("Saldo + Limite: "+contaAtual.Formata((contaAtual.getSaldo()+contaAtual.getLimite())));

                    buscaExtrato(Integer.parseInt(agenciaField.getText()), Integer.parseInt(contaField.getText()));
                }
                else{
                    JOptionPane.showMessageDialog(null, "Conta não encontrada!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void buscaExtrato(int agencia, int conta) {
        ConexaoMySQL conec = new ConexaoMySQL();
        Connection conexao = conec.realizaConexaoMySQL();

        DefaultTableModel Tabela = (DefaultTableModel) tabelaExtrato.getModel();
        while(Tabela.getRowCount() > 0){
            Tabela.removeRow(0);
        }
        try{
            String sql_selecao_extrato = "SELECT *FROM bancoespiao.operacoes, bancoespiao.contabancaria where " +
                    "operacoes.NumContExtrato = contabancaria.NumeroConta AND contabancaria.Agencia = "+agencia+" AND operacoes.NumContExtrato = "+conta+";";

            PreparedStatement atuador_selecao_extrato = conexao.prepareStatement(sql_selecao_extrato);
            ResultSet ResultadoSelecao = atuador_selecao_extrato.executeQuery();

            while (ResultadoSelecao.next()){
                Tabela.addRow(new Object []{
                        ResultadoSelecao.getString("DataOperacao"),
                        ResultadoSelecao.getString("DescricaoOperacao"),
                        ResultadoSelecao.getDouble("Valor")
                });
            }
            ResultadoSelecao.close();
            atuador_selecao_extrato.close();
            conec.desconectaMySQL(conexao);

        }catch(SQLException l){
            JOptionPane.showMessageDialog(null, "Algum imprevisto ocorreu" + l +"erro", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

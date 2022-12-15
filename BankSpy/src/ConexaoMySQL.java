import javax.swing.*;
import java.sql.*;

public class ConexaoMySQL {

    /* Seção de atributos / variaveis iniciais*/

    private String caminho = "localhost";  // Indica que usaremos o serve na máquina local
    private String porta = "3306";  // Porta padrão de conexão do MySQL Server
    private String nome = "bancoespiao"; //Nome da nossa base de dados
    private String usuario = "Math";  // Usuario padrão MySQL
    private String senha = "arrozdoce";   // Semja definida no momento da Instalaçao
    private String FusoHorario = "?useTimezone=true&serverTimezone=UTC";
    private String url = "jdbc:mysql://"+caminho+":"+porta+"/"+nome+FusoHorario;

    public Connection realizaConexaoMySQL(){
        try{
            return DriverManager.getConnection(url,usuario,senha);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Algum imprevisto ocorreu" + e + "erro", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
    }

    public void desconectaMySQL(Connection conexao){
        try{
            if(conexao != null){
                conexao.close();
            }
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Algum imprevisto ocorreu" + e +"erro", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void insereContaCorrenteNoMySQL(int agencia, int conta, String dataAbertura, double valorSaldo, double valorLimite, double valorMensalidade){

        Connection conexao = realizaConexaoMySQL();
        String sql_bancaria = "insert into contabancaria (NumeroConta, Agencia, Saldo, DataAbertura) values (?,?,?,?)";
        String sql_corrente = "insert into contacorrente (NumeroConta, Limite, Mensalidade) values (?,?,?)";

        try{ // Serão aplicados os recursos para a inserção no MySQL
            PreparedStatement Atuador_bancaria = conexao.prepareStatement(sql_bancaria);
            PreparedStatement Atuador_corrente = conexao.prepareStatement(sql_corrente);

            Atuador_bancaria.setInt(1, conta); //substitui a primeira ? do sql bancaria
            Atuador_bancaria.setInt(2, agencia);  //susbtitui a segunda ? do sql bancaria
            Atuador_bancaria.setDouble(3, valorSaldo);  //substitui a terceira ? do sql bancaria
            Atuador_bancaria.setString(4, dataAbertura);  //substitui a quarta ? do sql bancaria
            Atuador_bancaria.execute();

            Atuador_corrente.setInt(1, conta);    // substitui a primeira ? do sql
            Atuador_corrente.setDouble(2, valorLimite);
            Atuador_corrente.setDouble(3, valorMensalidade);
            Atuador_corrente.execute();

            desconectaMySQL(conexao);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Algum imprevisto ocorreu" + e +"erro", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public ContaCorrente buscaContaCorrente (int agencia, int conta){
        Connection conexao = realizaConexaoMySQL();
        ContaCorrente conta_atual = new ContaCorrente();
        conta_atual=null;

        try{
            String sql_selecao_conta = "SELECT * from bancoespiao.contabancaria, bancoespiao.contacorrente where " +
                    "contabancaria.NumeroConta = contacorrente.NumeroConta AND contabancaria.Agencia = "+agencia+" AND contacorrente.NumeroConta = "+conta+";";

            PreparedStatement atuador_selecao_conta = conexao.prepareStatement(sql_selecao_conta);
            ResultSet ResultadoSelecao = atuador_selecao_conta.executeQuery();

            while(ResultadoSelecao.next()){
                ContaCorrente ObjetoConta = new ContaCorrente();    // Objeto para copiar dados do MySQL

                ObjetoConta.setSaldo(ResultadoSelecao.getDouble("Saldo"));
                ObjetoConta.setAgencia(ResultadoSelecao.getInt("Agencia"));
                ObjetoConta.setDataAbertura(ResultadoSelecao.getString("DataAbertura"));
                ObjetoConta.setLimite(ResultadoSelecao.getDouble("Limite"));
                ObjetoConta.setMensalidadeManutencao(ResultadoSelecao.getDouble("Mensalidade"));

                conta_atual = ObjetoConta; //Adiciona o objeto de conta corrente o objeto atual
            }
            ResultadoSelecao.close();
            atuador_selecao_conta.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Algum imprevisto ocorreu:"+e+"","Erro",JOptionPane.ERROR_MESSAGE);
        }

        desconectaMySQL(conexao);

        return conta_atual;
    }

    public void insereExtratoNoMySQL(int conta, String dataOperacao, int tipoOper, double valorOperacao, String descOper){

        Connection conexao = realizaConexaoMySQL();
        String sql_extrato ="insert into operacoes (NumContExtrato ,TipoOperacao , Valor , DataOperacao, DescricaoOperacao) values (?,?,?,?,?)";

        try{
            PreparedStatement atuador_extrato = conexao.prepareStatement(sql_extrato);

            atuador_extrato.setInt(1, conta);
            atuador_extrato.setInt(2, tipoOper);
            atuador_extrato.setDouble(3, valorOperacao);
            atuador_extrato.setString(4, dataOperacao);
            atuador_extrato.setString(5, descOper);
            atuador_extrato.execute();

        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Algum imprevisto ocorreu:"+e+"","Erro", JOptionPane.ERROR_MESSAGE);
        }

        desconectaMySQL(conexao);
    }


    public void atualizaSaldoNoMySQL(int agencia, int conta, double valor){
        Connection conexao = realizaConexaoMySQL();
        String sql_atualiza_saldo = "update bancoespiao.contabancaria set Saldo='"+valor+"' where Agencia="+agencia+" AND NumeroConta="+conta+";";
        try{
            PreparedStatement atuador = conexao.prepareStatement(sql_atualiza_saldo);
            atuador.executeUpdate();
            atuador.close();

        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Algum imprevisto ocorreu:"+e+"","Erro", JOptionPane.ERROR_MESSAGE);
        }

        desconectaMySQL(conexao);
    }
}

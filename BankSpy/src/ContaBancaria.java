import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ContaBancaria {
    private int Agencia;
    private int Conta;
    private String DataAbertura;
    private double Saldo;
    private List<Operacoes> Extrato = new ArrayList<>();

    public ContaBancaria(){
    }

    public ContaBancaria(int agencia, int conta, String dataAbertura, double saldo) {
        Agencia = agencia;
        Conta = conta;
        DataAbertura = dataAbertura;
        Saldo = saldo;
    }

    public String Formata (double valor){
        DecimalFormat doubleFormata = new DecimalFormat();
        doubleFormata.applyPattern("R$ #,##0.00");
        return  doubleFormata.format(valor);
    }

    public void Saque(int agencia, int conta, int TOper, String DescOperacao, String Data, double Valor){
        ConexaoMySQL conect = new ConexaoMySQL();
        this.Saldo -= Valor;

        conect.insereExtratoNoMySQL(conta, Data, TOper, Valor, DescOperacao);
        conect.atualizaSaldoNoMySQL(agencia, conta, this.Saldo);
        Operacoes lancamentoAtual = new Operacoes(TOper, DescOperacao, Data, Valor);
        Extrato.add(lancamentoAtual);
    }

    public void Deposito(int agencia, int conta, int TOper, String DescOperacao, String Data, double Valor){
        //Elementos do MySQL tanto para extrato quanto atualização de saldo

        ConexaoMySQL conec = new ConexaoMySQL();
        this.Saldo += Valor;

        conec.insereExtratoNoMySQL(conta, Data, TOper, Valor, DescOperacao);
        conec.atualizaSaldoNoMySQL(agencia, conta, this.Saldo);
        Operacoes lancamentoAtual = new Operacoes(TOper, DescOperacao, Data, Valor);
        Extrato.add(lancamentoAtual);
    }

    public void Extrato(){
        Operacoes lancamento = new Operacoes();
        int n = Extrato.size();
        for(int i = 0; i < n; i++){
            lancamento = Extrato.get(i);
            System.out.println(lancamento.getDataOperacao()+" - "+lancamento.getDescOperacao()+" - "+Formata(lancamento.getValor()));
        }
        System.out.println("Saldo atual "+Formata(this.Saldo));
    }

    public void Dados(){
        System.out.println("Agencia: "+this.Agencia);
        System.out.println("Conta: "+this.Conta);
        System.out.println("Data Abertura: "+this.DataAbertura);
    }

    public int getAgencia() {
        return Agencia;
    }

    public int getConta() {
        return Conta;
    }

    public String getDataAbertura() {
        return DataAbertura;
    }

    public double getSaldo() {
        return Saldo;
    }

    public List<Operacoes> getExtrato() {
        return Extrato;
    }

    public void setSaldo(double saldo) {
        Saldo = saldo;
    }

    public void setAgencia(int agencia) {
        Agencia = agencia;
    }

    public void setConta(int conta) {
        Conta = conta;
    }

    public void setDataAbertura(String dataAbertura) {
        DataAbertura = dataAbertura;
    }

    public void setExtrato(List<Operacoes> extrato) {
        Extrato = extrato;
    }
}

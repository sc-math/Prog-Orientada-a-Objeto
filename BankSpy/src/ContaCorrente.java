import java.text.Normalizer;

public class ContaCorrente extends ContaBancaria{
    private double Limite;
    private double MensalidadeManutencao;

    public ContaCorrente(){
    }

    public ContaCorrente(int agencia, int conta, String dataAbertura, double saldo, double limite, double mensalidadeManutencao) {
        super(agencia, conta, dataAbertura, saldo);
        Limite = limite;
        MensalidadeManutencao = mensalidadeManutencao;
    }

    public void SaqueCC(int agencia, int conta, int TOper, String DescOperacao, String Data, double Valor){
        if((getSaldo()+this.Limite)>= Valor){
            ConexaoMySQL conect = new ConexaoMySQL();
            setSaldo(getSaldo()-Valor);


            conect.insereExtratoNoMySQL(conta, Data, TOper, Valor, DescOperacao);
            conect.atualizaSaldoNoMySQL(agencia, conta, getSaldo());
            Operacoes lancamentoAtual = new Operacoes(TOper, DescOperacao, Data, Valor);
            getExtrato().add(lancamentoAtual);
        }
        else
            System.out.println("Saldo de "+Formata(getSaldo())+"é insuficiente para a retirada de "+Formata(Valor));
    }

    public void Extrato(){
        super.Extrato();
        System.out.println("Saldo + Limite: "+Formata(getSaldo()+this.Limite));
    }

    public void Dados(){
        super.Dados();
        System.out.println("Limite: "+Formata(this.Limite));
        System.out.println("Mensalidade: "+Formata(this.MensalidadeManutencao));
    }

    public double getLimite() {
        return Limite;
    }

    public double getMensalidadeManutencao() {
        return MensalidadeManutencao;
    }

    public void setLimite(double limite) {
        Limite = limite;
    }

    public void setMensalidadeManutencao(double mensalidadeManutencao) {
        MensalidadeManutencao = mensalidadeManutencao;
    }
}

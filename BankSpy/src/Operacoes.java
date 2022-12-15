public class Operacoes {
    private int TipoOperacao;  // 1- Deposito   2- Transferencia   3- Saque
    private String DescOperacao;
    private String DataOperacao;
    private double valor;

    public Operacoes(){

    }

    public Operacoes(int TipoOperacao, String DescOperacao, String DataOperacao, double valor){
        this.DataOperacao = DataOperacao;
        this.DescOperacao = DescOperacao;
        this.TipoOperacao = TipoOperacao;
        this.valor = valor;
    }

    public int getTipoOperacao() {
        return TipoOperacao;
    }

    public String getDescOperacao() {
        return DescOperacao;
    }

    public String getDataOperacao() {
        return DataOperacao;
    }

    public double getValor() {
        return valor;
    }
}

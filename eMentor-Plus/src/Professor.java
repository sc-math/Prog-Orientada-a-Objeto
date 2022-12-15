import javax.swing.*;

public class Professor extends Pessoa {
    private String dataDeAdmissao;
    private double salarioBruto;

    public Professor() {
        this.dataDeAdmissao = "";
        this.salarioBruto = 0.0f;
    }

    public Professor(String dataDeAdmissao, double salarioBruto) {
        this.dataDeAdmissao = dataDeAdmissao;
        this.salarioBruto = salarioBruto;
    }

    public Professor(String Nome, String DataDeNascimento, String CPF, String Telefone, String dataDeAdmissao, double salarioBruto) {
        super(Nome, DataDeNascimento, CPF, Telefone);
        this.dataDeAdmissao = dataDeAdmissao;
        this.salarioBruto = salarioBruto;
    }

    public void setDados(String dataDeAdmissao, double salarioBruto) {
        this.dataDeAdmissao = dataDeAdmissao;
        this.salarioBruto = salarioBruto;
    }

    public String getDataDeAdmissao() {
        return dataDeAdmissao;
    }

    public double getSalarioBruto() {
        return salarioBruto;
    }

    public double calcularSalarioLiquido(){
        //desconto de 14% de INSS para todas as faixas de salário e 22,5% de IRPF para salários
        //maiores ou iguais que R$ 5.000,00,
        double quatorzePorcento = (this.salarioBruto * 14) / 100;
        double vinteDoisPorcento = 0.0f;
        if (salarioBruto >= 5000.0f){
            vinteDoisPorcento = (this.salarioBruto * 22.5) / 100;
        }
        double salarioComInss = this.salarioBruto - quatorzePorcento - vinteDoisPorcento;
        return salarioComInss;
    }

    public void imprimirDados(){
        String resposta = "Nome: " + this.Nome + "\nData de Nacimento: " + this.DataDeNascimento + "\nCPF: " + this.CPF +
                "\nN°Telefone: " + this.Telefone + "\nData de Admissao: " + this.dataDeAdmissao + "\nSalario Bruto: " +
                this.salarioBruto + "\nSalario Liquido: " + calcularSalarioLiquido();
        JOptionPane.showMessageDialog(null, resposta, "Dados do Professor", JOptionPane.INFORMATION_MESSAGE);
    }

}

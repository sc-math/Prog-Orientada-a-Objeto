import javax.swing.*;

public class Aluno extends Pessoa{
    private String matricula;
    private int periodo;

    public Aluno() {
        this.matricula = "";
        this.periodo = 0;
    }

    public Aluno(String matricula, int periodo) {
        this.matricula = matricula;
        this.periodo = periodo;
    }

    public Aluno(String Nome, String DataDeNascimento, String CPF, String Telefone, String matricula, int periodo) {
        super(Nome, DataDeNascimento, CPF, Telefone);
        this.matricula = matricula;
        this.periodo = periodo;
    }

    public void setDados(String matricula, int periodo) {
        this.matricula = matricula;
        this.periodo = periodo;
    }

    public String getMatricula() {
        return matricula;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void imprimirDados(){
        String resposta = "Nome: " + this.Nome + "\nData de Nacimento: " + this.DataDeNascimento + "\nCPF: " + this.CPF +
                "\nN°Telefone: " + this.Telefone + "\nMatricula: " + this.matricula + "\nPeriodo: " + this.periodo;
        JOptionPane.showMessageDialog(null, resposta, "Dados do Aluno", JOptionPane.INFORMATION_MESSAGE);
    }
}

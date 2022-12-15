public class Pessoa {
    protected String Nome,DataDeNascimento,CPF,Telefone;

    public Pessoa(){
        this.Nome = "";
        this.CPF = "";
        this.DataDeNascimento = "";
        this.Telefone = "";
    }

    public Pessoa(String Nome, String DataDeNascimento, String CPF, String Telefone) {
        this.Nome = Nome;
        this.DataDeNascimento = DataDeNascimento;
        this.CPF = CPF;
        this.Telefone = Telefone;
    }

    public String getNome(){
        return Nome;
    }

    public String getDataNascimento(){
        return DataDeNascimento;
    }

    public String getCPF(){
        return CPF;
    }

    public String getTelefone(){
        return Telefone;
    }

}

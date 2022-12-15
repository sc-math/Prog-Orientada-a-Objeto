public class Usuario extends Pessoa{
    private String NomeDoUsuario, senha;
    private int NivelDeAcesso;


    public Usuario(String Nome, String NomeDoUsuario, String senha, int NivelDeAcesso,  String DataDeNascimento, String CPF, String Telefone) {
        super(Nome, DataDeNascimento, CPF, Telefone);
        this.NomeDoUsuario = NomeDoUsuario;
        this.senha = senha;
        this.NivelDeAcesso = NivelDeAcesso;
    }

    public void setNomeDoUsuario(String NomeDoUsuario) {
        this.NomeDoUsuario = NomeDoUsuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNivelDeAcesso(int NivelDeAcesso) {
        this.NivelDeAcesso = NivelDeAcesso;
    }

    public String getNomeDoUsuario() {
        return NomeDoUsuario;
    }

    public String getSenha() {
        return senha;
    }

}

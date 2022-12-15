import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.spi.DirStateFactory.Result;
import javax.swing.*;

import java.sql.Statement;

public class MySQLOperations {

    private int chaveIdentificadoraAlunO;
    private int chaveIdentificadoraProfessor;

    private String caminho = "localhost";
    private String nome = "ementorPlus";
    private String user = "Math";
    private String password = "arrozdoce";
    private String porta = "3306";
    private String FusoHorario = "?useTimezone=true&serverTimezone=UTC";
    private String URL = "jdbc:mysql://" + caminho + ":" + porta + "/" + nome + FusoHorario;

    public class idInvalido extends Exception{
        @Override
        public String getMessage(){
            return "ID invalido. O cadastro nao pode ser efetuado";
        }
    }

    public Connection ConexaoMySQL() throws SQLException, ClassNotFoundException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, user, password);
        }catch(SQLDataException e){
            System.out.println("Falha ao conectar ao banco de dados");
            return null;
        }
    }

    public void desconectaMySQL(Connection conexao){
        try{
            if(conexao != null)
                conexao.close();
        }catch(SQLException e){
            System.out.println("Não foi possível encerrar a conexão");
            System.out.println(e.getErrorCode());
        }
    }

    public String novaPessoa(String cpf, String dataNascimento, String nome, String telefone, int id) throws ClassNotFoundException, SQLException{

        Connection conexao = ConexaoMySQL();

        String comando = "insert into pessoa (CPF, DATA_NASCIMENTO, NOME, TELEFONE,"+
                "IDENTIFICADOR) values (?,?,?,?,?)";

        try{

            PreparedStatement atuador = conexao.prepareStatement(comando);

            atuador.setString(1, cpf);
            atuador.setString(2, dataNascimento);
            atuador.setString(3, nome);
            atuador.setString(4, telefone);

            String chavePrimaria;

            if(id == 0)
                chavePrimaria = Integer.toString(gerarIdentificadorAluno());
            else if(id == 1)
                chavePrimaria = Integer.toString(gerarIdentificadorProfessor());
            else
                chavePrimaria = Integer.toString(gerarIdentificadorUsuario());

            String chave = Integer.toString(id) + chavePrimaria;

            atuador.setString(5, chave);
            atuador.execute();


            desconectaMySQL(conexao);

            return chave;

        }catch(SQLException e){

            System.out.println("Falha ao inserir os dados da tabela pessoa");
            System.out.println(e.getErrorCode());
            System.out.println(e.getMessage());

            String comando2;
            if(id == 0)
                comando2 = "SELECT *FROM ementorPlus.chaves where chaves.Aluno_Professor_Usuario=0";

            else if(id == 1)
                comando2 = "SELECT *FROM ementorPlus.chaves where chaves.Aluno_Professor_Usuario=1";

            else
                comando2 = "SELECT *FROM ementorPlus.chaves where chaves.Aluno_Professor_Usuario=2";


            Statement state = conexao.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = state.executeQuery(comando2);
            resultSet.next();

            String chave = resultSet.getString("CHAVE");
            int chaveInt = Integer.parseInt(chave);
            chaveInt--;
            resultSet.updateString("CHAVE", Integer.toString(chaveInt));
            resultSet.updateRow();

        }
        desconectaMySQL(conexao);
        return "-1";
    }

    public void novoUsuario(String nomeUsuario, String senha, int nivelAcesso, String chave) throws ClassNotFoundException, SQLException, MySQLOperations.idInvalido{

        Connection conexao = ConexaoMySQL();

        String comando = "insert into usuario (NOME_USUARIO, SENHA, NIVEL_ACESSO, IDENTIFICADOR) values (?,?,?,?)";

        try{

            PreparedStatement atuador = conexao.prepareStatement(comando);

            atuador.setString(1, nomeUsuario);
            atuador.setString(2, senha);
            atuador.setInt(3, nivelAcesso);

            if(chave == "-1")
                throw new idInvalido();


            atuador.setString(4, chave);

            atuador.execute();

            desconectaMySQL(conexao);

            JOptionPane.showMessageDialog(null, "Usuário cadastrado com Sucesso", "Aviso", JOptionPane.INFORMATION_MESSAGE);


        }catch(SQLException e){

            JOptionPane.showMessageDialog(null,"Nome de Usuário já cadastrado.", "Aviso", JOptionPane.ERROR_MESSAGE);
            System.out.println("Falha ao inserir os dados da tabela usuario");
            System.out.println(e.getErrorCode());
            String comando2 = "SELECT *FROM ementorPlus.pessoa where pessoa.IDENTIFICADOR="+chave;

            Statement state = conexao.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet2 = state.executeQuery(comando2);
            resultSet2.next();

            if(resultSet2 != null)
                resultSet2.deleteRow();

        }catch(idInvalido e){

            JOptionPane.showMessageDialog(null,"CPF já cadastrado.", "Aviso", JOptionPane.ERROR_MESSAGE);

            String comando2 = "SELECT *FROM ementorPlus.pessoa where pessoa.IDENTIFICADOR="+chave;

            Statement state = conexao.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet2 = state.executeQuery(comando2);
            resultSet2.next();


            if(resultSet2 != null)
                resultSet2.deleteRow();

            e.getMessage();

        }

    }

    public void novoAluno(String matricula, int periodo, String chave) throws ClassNotFoundException, SQLException, MySQLOperations.idInvalido{

        Connection conexao = ConexaoMySQL();

        String comando = "insert into aluno (MATRICULA, PERIODO, IDENTIFICADOR) values (?,?,?)";

        try{

            PreparedStatement atuador = conexao.prepareStatement(comando);

            atuador.setString(1, matricula);
            atuador.setInt(2, periodo);

            if(chave == "-1")
                throw new idInvalido();


            atuador.setString(3, chave);

            atuador.execute();

            desconectaMySQL(conexao);

            JOptionPane.showMessageDialog(null, "Cadastro Realizado com Sucesso", "Aviso", JOptionPane.INFORMATION_MESSAGE);

        }catch(SQLException e){

            JOptionPane.showMessageDialog(null,"Matricula já cadastrada.", "Aviso", JOptionPane.ERROR_MESSAGE);

            System.out.println("Falha ao inserir os dados da tabela aluno");
            System.out.println(e.getErrorCode());
            String comando2 = "SELECT *FROM ementorPlus.pessoa where pessoa.IDENTIFICADOR="+chave;

            Statement state = conexao.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet2 = state.executeQuery(comando2);
            resultSet2.next();

            if(resultSet2 != null)
                resultSet2.deleteRow();

        }catch(idInvalido e){

            JOptionPane.showMessageDialog(null,"CPF já cadastrado.", "Aviso", JOptionPane.ERROR_MESSAGE);

            String comando2 = "SELECT *FROM ementorPlus.pessoa where pessoa.IDENTIFICADOR="+chave;

            Statement state = conexao.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet2 = state.executeQuery(comando2);
            resultSet2.next();

            if(resultSet2 != null)
                resultSet2.deleteRow();

            e.getMessage();

        }
    }

    public void novoProfessor(String dataAdimissao, double salarioBruto, String chave) throws ClassNotFoundException, SQLException, MySQLOperations.idInvalido{

        Connection conexao = ConexaoMySQL();

        String comando = "insert into professor (DATA_ADMISSAO, SALARIO_BRUTO,"+
                "IDENTIFICADOR) values (?,?,?)";

        try{

            PreparedStatement atuador = conexao.prepareStatement(comando);

            atuador.setString(1, dataAdimissao);
            atuador.setDouble(2, salarioBruto);
            if(chave == "-1"){
                throw new idInvalido();
            }
            atuador.setString(3, chave);

            atuador.execute();

            desconectaMySQL(conexao);

            JOptionPane.showMessageDialog(null, "Professor cadastrado com Sucesso", "Aviso", JOptionPane.INFORMATION_MESSAGE);


        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Ocorreu um erro.", "Aviso",JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
            System.out.println("Falha ao inserir os dados da tabela professor");
            System.out.println(e.getErrorCode());

        }
        catch(idInvalido e){
            JOptionPane.showMessageDialog(null, "CPF já cadastrado.", "Aviso", JOptionPane.ERROR_MESSAGE);
        }
    }



    public Pessoa getPessoa(String cpf) throws ClassNotFoundException, SQLException{


        Connection conexao = ConexaoMySQL();

        String comando = "SELECT *FROM ementorPlus.pessoa where pessoa.CPF="+cpf;

        try{

            PreparedStatement atuador = conexao.prepareStatement(comando);

            ResultSet resultSet = atuador.executeQuery();
            resultSet.next();

            /* Os parâmetros abaixo devem ser usados nas instâncias nas condicionais */
            String CPF = resultSet.getString("CPF");
            String dataNascimento = resultSet.getString("DATA_NASCIMENTO");
            String nome = resultSet.getString("NOME");
            String telefone = resultSet.getString("TELEFONE");

            String chave = resultSet.getString("IDENTIFICADOR");
            int flag = Integer.parseInt(chave.substring(0, 1));

            if(flag == 0){  // Aluno

                String comando2 = "SELECT *FROM ementorPlus.aluno where aluno.IDENTIFICADOR="+chave;
                PreparedStatement atuador2 = conexao.prepareStatement(comando2);
                ResultSet resultSet2 = atuador2.executeQuery();
                resultSet2.next();

                String matricula = resultSet2.getString("MATRICULA");
                int periodo = resultSet2.getInt("PERIODO");


                Aluno aluno = new Aluno(nome, dataNascimento, CPF, telefone, matricula, periodo);

                desconectaMySQL(conexao);
                return aluno;

            }
            else if(flag == 1){ // Professor

                String comando2 = "SELECT *FROM ementorPlus.professor where professor.IDENTIFICADOR = "+chave;
                PreparedStatement atuador2 = conexao.prepareStatement(comando2);
                ResultSet resultSet2 = atuador2.executeQuery();
                resultSet2.next();

                double salarioBruto = resultSet2.getDouble("SALARIO_BRUTO");
                String dataAdmissao = resultSet2.getString("DATA_ADMISSAO");

                Professor professor = new Professor(nome, dataNascimento, CPF, telefone, dataAdmissao, salarioBruto);

                desconectaMySQL(conexao);
                return professor;

            }
            else{   // Usuario

                String comando2 = "SELECT *FROM ementorPlus.usuario where usuario.IDENTIFICADOR = "+chave;
                PreparedStatement atuador2 = conexao.prepareStatement(comando2);
                ResultSet resultSet2 = atuador2.executeQuery();
                resultSet2.next();

                String nomeUsuario = resultSet2.getString("NOME_USUARIO");
                String senha = resultSet2.getString("SENHA");
                int nivelAcesso = resultSet2.getInt("NIVEL_ACESSO");

                Usuario usuario = new Usuario(nome, nomeUsuario, senha, nivelAcesso, dataNascimento,
                        CPF, telefone);

                desconectaMySQL(conexao);
                return usuario;
            }

        }catch(SQLException e){
            System.out.println("O CPF informado nao esta cadastrado");
            System.out.println(e.getErrorCode());
        }

        return null;
    }

    public Aluno getAluno(String matricula) throws ClassNotFoundException, SQLException{

        Connection conexao = ConexaoMySQL();

        String comando = "SELECT *FROM ementorPlus.aluno, ementorPlus.pessoa where aluno.MATRICULA ="+matricula+
                " AND pessoa.IDENTIFICADOR = aluno.IDENTIFICADOR";

        try{

            PreparedStatement atuador = conexao.prepareStatement(comando);

            ResultSet resultSet = atuador.executeQuery();
            resultSet.next();

            String CPF = resultSet.getString("CPF");
            String dataNascimento = resultSet.getString("DATA_NASCIMENTO");
            String nome = resultSet.getString("NOME");
            String telefone = resultSet.getString("TELEFONE");

            String mat = resultSet.getString("MATRICULA");
            int periodo = resultSet.getInt("PERIODO");

            Aluno aluno = new Aluno(nome, dataNascimento, CPF, telefone, matricula, periodo);



            return aluno;


        }catch(SQLException e){
            System.out.println("A matricula informada nao esta cadastrada");
            System.out.println(e.getErrorCode());
            JOptionPane.showMessageDialog(null,"Matricula inválida.", "Aviso", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public Usuario getUsuario(String nomeUsuario) throws ClassNotFoundException, SQLException{

        Connection conexao = ConexaoMySQL();

        String comando = "SELECT *FROM ementorPlus.usuario, ementorPlus.pessoa where usuario.NOME_USUARIO ='"+nomeUsuario+
                "' AND pessoa.IDENTIFICADOR=usuario.IDENTIFICADOR";

        try{

            PreparedStatement atuador = conexao.prepareStatement(comando);

            ResultSet resultSet = atuador.executeQuery();
            resultSet.next();

            String CPF = resultSet.getString("CPF");
            String dataNascimento = resultSet.getString("DATA_NASCIMENTO");
            String nome = resultSet.getString("NOME");
            String telefone = resultSet.getString("TELEFONE");

            String usu = resultSet.getString("NOME_USUARIO");
            String senha = resultSet.getString("SENHA");

            Usuario usuario = new Usuario(nome, usu,senha, 2, dataNascimento, CPF, telefone);



            return usuario;


        }catch(SQLException e){
            System.out.println("Usuario informado nao esta cadastrado");
            System.out.println(e.getErrorCode());
            JOptionPane.showMessageDialog(null,"Usuario inválido.", "Aviso", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }


    public List<Aluno> getAllAlunos() throws ClassNotFoundException, SQLException{

        Connection conexao = ConexaoMySQL();

        String comando = "SELECT *FROM ementorPlus.aluno, ementorPlus.pessoa where aluno.IDENTIFICADOR=pessoa.IDENTIFICADOR";

        List<Aluno> alunos = new ArrayList<Aluno>();

        try {

            PreparedStatement atuador = conexao.prepareStatement(comando);

            ResultSet resultSet = atuador.executeQuery();

            while(resultSet.next()){

                String CPF = resultSet.getString("CPF");
                String dataNascimento = resultSet.getString("DATA_NASCIMENTO");
                String nome = resultSet.getString("NOME");
                String telefone = resultSet.getString("TELEFONE");

                String mat = resultSet.getString("MATRICULA");
                int periodo = resultSet.getInt("PERIODO");

                Aluno aluno = new Aluno(nome, dataNascimento, CPF, telefone, mat, periodo);
                alunos.add(aluno);

            }

            return alunos;

        } catch (SQLException e) {
            System.out.println("Erro ao buscar alunos");
            System.out.println(e.getErrorCode());
        }

        return null;
    }

    public List<Professor> getAllProfessores() throws ClassNotFoundException, SQLException{

        Connection conexao = ConexaoMySQL();

        String comando = "SELECT *FROM ementorPlus.professor, ementorPlus.pessoa where professor.IDENTIFICADOR=pessoa.IDENTIFICADOR";

        List<Professor> professores = new ArrayList<Professor>();

        try {

            PreparedStatement atuador = conexao.prepareStatement(comando);

            ResultSet resultSet = atuador.executeQuery();

            while(resultSet.next()){

                String CPF = resultSet.getString("CPF");
                String dataNascimento = resultSet.getString("DATA_NASCIMENTO");
                String nome = resultSet.getString("NOME");
                String telefone = resultSet.getString("TELEFONE");

                double salarioBruto = resultSet.getDouble("SALARIO_BRUTO");
                String dataAdmissao = resultSet.getString("DATA_ADMISSAO");

                Professor professor = new Professor(nome, dataNascimento, CPF, telefone, dataAdmissao, salarioBruto);
                professores.add(professor);

            }

            return professores;


        } catch (SQLException e) {
            System.out.println("Erro ao buscar professores");
            System.out.println(e.getErrorCode());
        }

        return null;
    }

    public void atualizaNomePessoa(String cpf, String novoNome) throws ClassNotFoundException, SQLException{

        Connection conexao = ConexaoMySQL();

        String comando = "update ementorPlus.pessoa set NOME='"+novoNome+"' where CPF="+cpf+";";

        try{

            PreparedStatement atuador = conexao.prepareStatement(comando);
            atuador.executeUpdate();

            desconectaMySQL(conexao);

        }catch(SQLException e){
            System.out.println("Erro ao atualizar nome de pessoa");
            System.out.println(e.getErrorCode());
        }

    }

    public void atualizaTelefonePessoa(String cpf, String novoTelefone) throws ClassNotFoundException, SQLException{

        Connection conexao = ConexaoMySQL();

        String comando = "update ementorPlus.pessoa set TELEFONE='"+novoTelefone+"' where CPF="+cpf+";";

        try{

            PreparedStatement atuador = conexao.prepareStatement(comando);
            atuador.executeUpdate();

            desconectaMySQL(conexao);

        }catch(SQLException e){
            System.out.println("Erro ao atualizar nome de pessoa");
            System.out.println(e.getErrorCode());
        }

    }

    public void atualizaPeriodoAluno(String matricula, int novoPeriodo) throws ClassNotFoundException, SQLException{

        Connection conexao = ConexaoMySQL();

        String comando = "update ementorPlus.aluno set PERIODO='"+novoPeriodo+"' where MATRICULA="+matricula+";";

        try{

            PreparedStatement atuador = conexao.prepareStatement(comando);
            atuador.executeUpdate();

            desconectaMySQL(conexao);

        }catch(SQLException e){
            System.out.println("Erro ao atualizar periodo de aluno");
            System.out.println(e.getErrorCode());
        }

    }

    public void atualizaSalarioProfessor(String cpf, double novoSalario) throws ClassNotFoundException, SQLException{

        Connection conexao = ConexaoMySQL();


        /* String comando = "update ementorPlus.pessoa, ementorPlus.professor set professor.SALARIO='"+novoSalario+"' where pessoa.IDENTIFICADOR=professor.IDENTIFICADOR"
        +" AND pessoa.NOME="+nomeProfessor+";"; */

        String comando = "SELECT *FROM ementorPlus.professor, ementorPlus.pessoa where professor.IDENTIFICADOR=pessoa.IDENTIFICADOR"
                +" AND pessoa.CPF="+cpf;


        /* String comando = "update ementorPlus.professor set SALARIO_BRUTO='"+novoSalario+"' where ementorPlus.pessoa.IDENTIFICADOR=professor.INDENTIFICADOR"
        +" AND ementorPlus.pessoa.CPF="+cpf+";";
         */


        try{

            PreparedStatement atuador = conexao.prepareStatement(comando);

            ResultSet resultSet = atuador.executeQuery(comando);
            resultSet.next();

            String identificador = resultSet.getString("IDENTIFICADOR");

            String comando2 = "SELECT *FROM ementorPlus.professor where professor.IDENTIFICADOR="+identificador;

            Statement state = conexao.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet2 = state.executeQuery(comando2);
            resultSet2.next();
            resultSet2.updateDouble("SALARIO_BRUTO", novoSalario);
            resultSet2.updateRow();

            desconectaMySQL(conexao);

        }catch(SQLException e){
            System.out.println("O CPF fornecido nao eh de um professor");
            System.out.println(e.getErrorCode());
            System.out.println(e.getMessage());
        }

    }

    public void deletaPessoa(String cpf) throws ClassNotFoundException, SQLException{

        Connection conexao = ConexaoMySQL();

        String comando = "SELECT *FROM ementorPlus.pessoa where pessoa.CPF="+cpf;

        try{

            Statement state = conexao.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE
            );
            ResultSet resultSet = state.executeQuery(comando);
            resultSet.next();

            Pessoa pessoa = getPessoa(cpf);
            String identificador = resultSet.getString("IDENTIFICADOR");
            resultSet.deleteRow();
            if(pessoa.getClass() == Aluno.class){

                String comando2 = "SELECT *FROM ementorPlus.aluno where aluno.IDENTIFICADOR="+identificador;

                ResultSet resultSet2 = state.executeQuery(comando2);
                resultSet2.next();

                resultSet2.deleteRow();

            }
            else if(pessoa.getClass() == Professor.class){

                String comando2 = "SELECT *FROM ementorPlus.professor where professor.IDENTIFICADOR="+identificador;

                ResultSet resultSet2 = state.executeQuery(comando2);
                resultSet2.next();

                resultSet2.deleteRow();

            }
            else if(pessoa.getClass() == Usuario.class){

                String comando2 = "SELECT *FROM ementorPlus.usuario where usuario.IDENTIFICADOR="+identificador;

                ResultSet resultSet2 = state.executeQuery(comando2);
                resultSet2.next();

                resultSet2.deleteRow();

            }

        }catch(SQLException e){
            System.out.println("Falha ao deletar");
            System.out.println(e.getErrorCode());
            System.out.println(e.getMessage());
        }

    }

    public void formatarBanco() throws ClassNotFoundException, SQLException{

        Connection conexao = ConexaoMySQL();

        String comando = "SELECT *FROM ementorPlus.pessoa";
        try{
            PreparedStatement atuador = conexao.prepareStatement(comando);

            ResultSet resultSet = atuador.executeQuery();

            int count = 0;
            resultSet.next();
            while(true){
                count++;
                if(!resultSet.next()) break;
            }

            atuador.close();
            resultSet.close();

            desconectaMySQL(conexao);

            conexao = ConexaoMySQL();

            Statement state = conexao.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            for(int i = 0; i < count; i++){

                ResultSet resultSet2 = state.executeQuery(comando);
                resultSet2.next();
                String cpf = resultSet2.getString("CPF");
                deletaPessoa(cpf);

            }

            String comando2 = "SELECT *FROM ementorPlus.chaves";

            ResultSet resultSet3 = state.executeQuery(comando2);

            while(resultSet3.next()){   // Para zerar as chaves

                resultSet3.updateString("CHAVE", "0");
                resultSet3.updateRow();
            }

        }catch(SQLException e){
            System.out.println("Erro ao formatar");
            System.out.println(e.getErrorCode());
            System.out.println(e.getMessage());
        }

    }

    private int gerarIdentificadorAluno() throws ClassNotFoundException, SQLException{

        Connection conexao = ConexaoMySQL();

        String comando = "SELECT *FROM ementorPlus.chaves where chaves.Aluno_Professor_Usuario=0";

        try {

            PreparedStatement atuador = conexao.prepareStatement(comando);
            ResultSet resultSet = atuador.executeQuery();
            resultSet.next();

            String chave = resultSet.getString("CHAVE");

            int chavePrimaria = Integer.parseInt(chave);
            int novaChave = chavePrimaria + 1;

            atuador.close();
            resultSet.close();

            String comando2 = "update ementorPlus.chaves set CHAVE='"+Integer.toString(novaChave)+"' where Aluno_Professor_Usuario=0";
            PreparedStatement atuador2 = conexao.prepareStatement(comando2);
            atuador2.executeUpdate();

            desconectaMySQL(conexao);
            return chavePrimaria;

        } catch (SQLException e) {

            System.out.println("Falha ao resgatar a ultima chave de aluno");
            System.out.println(e.getErrorCode());
            System.out.println(e.getLocalizedMessage());
        }

        return -1;
    }

    private int gerarIdentificadorProfessor() throws ClassNotFoundException, SQLException{

        Connection conexao = ConexaoMySQL();

        String comando = "SELECT *FROM ementorPlus.chaves where chaves.Aluno_Professor_Usuario=1";

        try {

            PreparedStatement atuador = conexao.prepareStatement(comando);
            ResultSet resultSet = atuador.executeQuery();
            resultSet.next();

            String chave = resultSet.getString("CHAVE");
            int chavePrimaria = Integer.parseInt(chave);
            int novaChave = chavePrimaria + 1;

            String comando2 = "update ementorPlus.chaves set CHAVE='"+Integer.toString(novaChave)+"' where Aluno_Professor_Usuario=1";
            PreparedStatement atuador2 = conexao.prepareStatement(comando2);
            atuador2.executeUpdate();

            desconectaMySQL(conexao);
            return chavePrimaria;

        } catch (SQLException e) {

            System.out.println("Falha ao resgatar a ultima chave de professor");
            System.out.println(e.getErrorCode());

        }

        return -1;
    }

    private int gerarIdentificadorUsuario() throws ClassNotFoundException, SQLException{

        Connection conexao = ConexaoMySQL();

        String comando = "SELECT *FROM ementorPlus.chaves where chaves.Aluno_Professor_Usuario=2";

        try {

            PreparedStatement atuador = conexao.prepareStatement(comando);
            ResultSet resultSet = atuador.executeQuery();
            resultSet.next();

            String chave = resultSet.getString("CHAVE");
            int chavePrimaria = Integer.parseInt(chave);
            int novaChave = chavePrimaria + 1;

            String comando2 = "update ementorPlus.chaves set CHAVE='"+Integer.toString(novaChave)+"' where Aluno_Professor_Usuario=2";
            PreparedStatement atuador2 = conexao.prepareStatement(comando2);
            atuador2.executeUpdate();

            desconectaMySQL(conexao);
            return chavePrimaria;

        } catch (SQLException e) {

            System.out.println("Falha ao resgatar a ultima chave de usuario");
            System.out.println(e.getErrorCode());

        }

        return -1;
    }

}

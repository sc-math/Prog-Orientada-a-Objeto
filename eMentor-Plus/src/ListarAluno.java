import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ListarAluno extends JFrame{
    private JPanel listarAlunoPanel;
    private JTable tabelaAlunos;
    private JButton voltarButton;
    private javax.swing.JScrollPane JScrollPane;

    public ListarAluno() throws SQLException, ClassNotFoundException {
        super("Lista de Alunos");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(listarAlunoPanel);
        this.pack();
        this.setIconImage(eMentorplus.icon.getImage());
        setSize(new Dimension(650,400)); //Tamanho da janela

        tabelaAlunos.setModel(new DefaultTableModel(
                new Object[][]{

                },
                new String []{
                        "Nome", "Data de Nascimento", "CPF", "Telefone", "Matrícula", "Período"
                }
        ){
            boolean[]canEdit = new boolean[]{
                    false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex){return canEdit [columnIndex];}
        });
        JScrollPane.setViewportView(tabelaAlunos);

        MySQLOperations conec = new MySQLOperations();

        List <Aluno> alunos = conec.getAllAlunos();

        DefaultTableModel tabela = (DefaultTableModel) tabelaAlunos.getModel();
        while(tabela.getRowCount()>0){
            tabela.removeRow(0);
        }

        for(Aluno A : alunos){
            tabela.addRow(new Object [] {
                    A.getNome(), A.getDataNascimento(), A.getCPF(), A.getTelefone(), A.getMatricula(), A.getPeriodo()
            });
        }


        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu frame = new Menu();
                frame.setVisible(true);
                dispose();
            }
        });
    }
}

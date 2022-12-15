import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ListarProfessor extends JFrame{
    private JTable tabelaProfessor;
    private javax.swing.JScrollPane JScrollPane;
    private JButton voltarButton;
    private JPanel listarProfessorPanel;

    public ListarProfessor() throws SQLException, ClassNotFoundException {
        super("Listar Professor");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(listarProfessorPanel);
        this.pack();
        this.setIconImage(eMentorplus.icon.getImage());
        setSize(new Dimension(650,400)); //Tamanho da janela

        tabelaProfessor.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String []{
                        "Nome", "Data de Nascimento", "CPF", "Telefone", "Data Admissão", "Salário"
                }
        ){
            boolean[]canEdit = new boolean[]{
                    false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex){return canEdit [columnIndex];}
        });
        JScrollPane.setViewportView(tabelaProfessor);

        MySQLOperations conec = new MySQLOperations();

        List<Professor> alunos = conec.getAllProfessores();

        DefaultTableModel tabela = (DefaultTableModel) tabelaProfessor.getModel();
        while(tabela.getRowCount()>0){
            tabela.removeRow(0);
        }

        for(Professor A : alunos){
            tabela.addRow(new Object [] {
                    A.getNome(), A.getDataNascimento(), A.getCPF(), A.getTelefone(), A.getDataDeAdmissao(), A.getSalarioBruto()
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

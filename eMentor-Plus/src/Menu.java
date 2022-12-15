import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Menu extends JFrame{

    private JPanel menuPanel;
    private JButton cadastrarAlunoButton;
    private JButton cadastrarProfessorButton;
    private JButton listarAlunoButton;
    private JButton listarProfessorButton;
    private JButton alterarDadosDoAlunoButton;
    private JButton alterarDadosDoProfessorButton;
    private JButton sairButton;

    public Menu(){
        //super("Menu Principal");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(menuPanel);
        this.pack();
        this.setIconImage(eMentorplus.icon.getImage());
        setSize(new Dimension(650,400)); //Tamanho da Janela

        cadastrarAlunoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                CadastroAluno frame = new CadastroAluno();
                frame.setVisible(true);
                dispose();
            }
        });

        cadastrarProfessorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                CadastroProfessor frame = new CadastroProfessor();
                frame.setVisible(true);
                dispose();
            }
        });


        listarAlunoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ListarAluno frame = null;
                try {
                    frame = new ListarAluno();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                frame.setVisible(true);
                dispose();
            }
        });


        listarProfessorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ListarProfessor frame = null;
                try {
                    frame = new ListarProfessor();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                frame.setVisible(true);
                dispose();
            }
        });


        alterarDadosDoAlunoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AlterarDadosAluno frame = new AlterarDadosAluno();
                frame.setVisible(true);
                dispose();
            }
        });


        alterarDadosDoProfessorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AlterarDadosProfessores frame = new AlterarDadosProfessores();
                frame.setVisible(true);
                dispose();
            }
        });
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sair frame = new Sair();
                frame.setVisible(true);
                dispose();
            }
        });
    }
}

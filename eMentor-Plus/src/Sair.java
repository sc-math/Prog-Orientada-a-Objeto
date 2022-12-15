import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sair extends JFrame{
    private JPanel sairPanel;
    private JButton logoffButton;
    private JButton fecharButton;

    public Sair(){
        super("Sair");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(sairPanel);
        this.pack();
        this.setIconImage(eMentorplus.icon.getImage());
        setSize(new Dimension(500,300));


        logoffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login frame = new Login();
                frame.setVisible(true);
                dispose();
            }
        });

        fecharButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}

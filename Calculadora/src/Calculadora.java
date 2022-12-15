import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame{

    public static class DivisaoErro extends RuntimeException{
        public DivisaoErro(){super("Erro: divisão por zero");}
    }

    public static class RaizNegErro extends RuntimeException{
        public RaizNegErro(){super("Erro: raiz quadrada negativa");}
    }

    public static class LogNegErro extends RuntimeException{
        public LogNegErro(){super("Erro: log de número negativo");}
    }


    private JPanel calculadoraPanel;
    private JTextField visor;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a7Button;
    private JButton a8Button;
    private JButton a9Button;
    private JButton a0Button;
    private JButton igualButton;
    private JButton somaButton;
    private JButton multButton;
    private JButton divButton;
    private JButton subtracaoButton;
    private JButton ceButton;
    private JButton expButton;
    private JButton raizButton;
    private JButton logButton;


    public Calculadora(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(calculadoraPanel);
        this.pack();
        this.setIconImage(Main.icon.getImage());

        setSize(new Dimension(300,400));


        ceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visor.setText("");
            }
        });
        a0Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visor.setText(visor.getText()+"0");
            }
        });
        a1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visor.setText(visor.getText()+"1");
            }
        });
        a2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visor.setText(visor.getText()+"2");
            }
        });
        a3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visor.setText(visor.getText()+"3");
            }
        });
        a4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visor.setText(visor.getText()+"4");
            }
        });
        a5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visor.setText(visor.getText()+"5");
            }
        });
        a6Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visor.setText(visor.getText()+"6");
            }
        });
        a7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visor.setText(visor.getText()+"7");
            }
        });
        a8Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visor.setText(visor.getText()+"8");
            }
        });
        a9Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visor.setText(visor.getText()+"9");
            }
        });
        somaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visor.setText(visor.getText()+"+");
            }
        });
        subtracaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visor.setText(visor.getText()+"-");
            }
        });
        multButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visor.setText(visor.getText()+"*");
            }
        });
        divButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visor.setText(visor.getText()+"/");
            }
        });
        expButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = Integer.parseInt(visor.getText());

                try {
                    int b = (int) Math.pow(a,2);

                    visor.setText(Integer.toString(b));
                }
                catch(LogNegErro err){
                    visor.setText(err.getMessage());
                }
            }
        });
        raizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = Integer.parseInt(visor.getText());

                try {
                    if(a < 0){
                        throw new RaizNegErro();
                    }
                    Double b = Math.sqrt(a);

                    visor.setText(Double.toString(b));
                }
                catch(RaizNegErro err){
                    visor.setText(err.getMessage());
                }
            }
        });
        logButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = Integer.parseInt(visor.getText());

                try {
                    if(a < 0){
                        throw new LogNegErro();
                    }
                    Double b = Math.log10(a);

                    visor.setText(Double.toString(b));
                }
                catch(LogNegErro err){
                    visor.setText(err.getMessage());
                }
            }
        });
        igualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String conteudo = visor.getText();
                int tam = conteudo.length();
                int a = 0, b = 0, result = 0;

                String x = "";
                String y = "";
                char operando = 0;

                for(int i = 0; i < tam; i++){
                    if((conteudo.charAt(i) == '+' || conteudo.charAt(i) == '-' || conteudo.charAt(i) == '*' || conteudo.charAt(i) == '/')){
                        operando = conteudo.charAt(i);
                        x = conteudo.substring(0, i);
                        y = conteudo.substring(i+1, tam);
                        a = Integer.parseInt(x);
                        b = Integer.parseInt(y);
                        System.out.println(x);
                        System.out.println(y);
                        System.out.println(operando);;

                    }
                }
                try{
                    switch (operando){
                        case '+':
                            result = a+b;
                            break;
                        case '-':
                            result = a-b;
                            break;
                        case '*':
                            result = a*b;
                            break;
                        case '/':
                            if (b==0)
                                throw new DivisaoErro();
                            else{
                                result = a/b;
                                break;
                            }
                    }
                    visor.setText(Integer.toString(result));
                }
                catch(DivisaoErro err){
                    visor.setText(err.getMessage());
                }
            }
        });
    }
}

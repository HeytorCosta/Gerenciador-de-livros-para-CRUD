import javax.swing.*;

public class TelaPrincipal extends JFrame {

    private JButton buttonCadastro;
    public void Inicio() {
        setVisible(true);
        setTitle("Tela Principal");
        setSize(300, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel labelUsuario = new JLabel("Teste:");
        labelUsuario.setBounds(50, 30, 100, 20);
        add(labelUsuario);

        buttonCadastro = new JButton("Cadastro");
        buttonCadastro.setBounds(100, 150, 100, 20);
        add(buttonCadastro);

        buttonCadastro.addActionListener(e -> {
            TelaDeCadastro();
        });
        getContentPane().add(buttonCadastro);

        setVisible(true);
    }

    public void TelaDeCadastro() {
        setVisible(false);
        Cadastro telaCadastro = new Cadastro();
        telaCadastro.cadastro();
    }
    }

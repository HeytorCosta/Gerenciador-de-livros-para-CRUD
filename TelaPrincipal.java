import javax.swing.*;

public class TelaPrincipal extends JFrame {


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
    }
}

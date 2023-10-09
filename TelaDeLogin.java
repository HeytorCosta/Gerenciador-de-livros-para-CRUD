//Heytor Costa Santos RA:823126990
//Daniel Henrique dos Santos: 823150320

//Curso: Eng. Computação


import javax.swing.*;


public class TelaDeLogin extends JFrame {

    private JTextField textFieldUsuario;
    private JPasswordField passwordFieldSenha;
    private JButton buttonLogin;

    public TelaDeLogin() {
        // Configurações do JFrame
        setTitle("Tela de Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Cria os componentes
        JLabel labelUsuario = new JLabel("Usuário:");
        labelUsuario.setBounds(50, 30, 100, 20);
        add(labelUsuario);

        textFieldUsuario = new JTextField();
        textFieldUsuario.setBounds(150, 30, 100, 20);
        add(textFieldUsuario);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(50, 60, 100, 20);
        add(labelSenha);

        passwordFieldSenha = new JPasswordField();
        passwordFieldSenha.setBounds(150, 60, 100, 20);
        add(passwordFieldSenha);

        buttonLogin = new JButton("Login");
        buttonLogin.setBounds(100, 100, 100, 30);
        add(buttonLogin);

        // Adiciona a ação ao botão de login
        buttonLogin.addActionListener(e -> {
            String usuario = textFieldUsuario.getText();
            String senha = new String(passwordFieldSenha.getPassword());

            if (validarCredenciais(usuario, senha)) {
                JOptionPane.showMessageDialog(null, "Login bem sucedido!");
            } else {
                JOptionPane.showMessageDialog(null, "Nome de usuário ou senha inválidos!");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaDeLogin();
    }

    public static boolean validarCredenciais(String usuario, String senha) {
        if ("admin".equals(usuario) && "123456".equals(senha)) {
            return true;
        } else {
            return false;
        }
    }
}

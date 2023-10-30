//Heytor Costa Santos RA:823126990
//Daniel Henrique dos Santos: 823150320

//Curso: Eng. Computação

import java.sql.*;
import javax.swing.*;

public class TelaDeLogin extends JFrame {

    private JTextField textFieldUsuario;
    private JPasswordField passwordFieldSenha;
    private JButton buttonLogin;

    public TelaDeLogin() {
        // Configurações do JFrame
        setTitle("Tela de Login");
        setSize(300, 220);
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
                
                TelaPrincipal();

            } else {
                
            }
        });
        setVisible(true);
    }

        

    public void TelaPrincipal() {
        setVisible(false);
        TelaPrincipal telaPrincipal = new TelaPrincipal();
        telaPrincipal.Inicio();
    }

    public static void main(String[] args) {
        new TelaDeLogin();

    }

    public static boolean validarCredenciais(String usuario, String senha) {
        
        ConexaoBd conexaoBd = new ConexaoBd();
        Connection conexao = conexaoBd.obterConexao();
        try {
            String sql = "SELECT senha FROM tabela_cadastro WHERE nome = ?";
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setString(1, usuario);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String senhaDB = resultSet.getString("senha");

                if (senha.equals(senhaDB)) {
                    JOptionPane.showMessageDialog(null, "Login Bem Sucedido");
                    return true;
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Senha Incorreta");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "usuario não encontrado");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.*;

public class Cadastro extends JFrame {

    private JComboBox<String> comboBox;
    private JTextField textFieldUsuario;
    private JPasswordField passwordFieldSenha;
    private JButton buttoncadastro;

    public void cadastro() {
        setTitle("Tela de Cadastro");
        setSize(300, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

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

        JLabel labelAutoridade = new JLabel("Autoridade");
        labelAutoridade.setBounds(50, 90, 100, 20);
        add(labelAutoridade);

        String[] Autoridade = {"Administrador", "Usuario",};
        JComboBox<String> comboBox = new JComboBox<>(Autoridade);
        comboBox.setBounds(150, 90, 100, 20);
        
        getContentPane().add(comboBox);

        buttoncadastro = new JButton("Cadastro");
        buttoncadastro.setBounds(100, 140, 100, 30);
        add(buttoncadastro);

        // Adiciona a ação ao botão de cadastro
        buttoncadastro.addActionListener(e -> {
            String usuario = textFieldUsuario.getText();
            String senha = new String(passwordFieldSenha.getPassword());
            String autoridade = (String)comboBox.getSelectedItem();
            cadastroBD(usuario,senha,autoridade);
    });

    setVisible(true);
    }

    private  void cadastroBD(String usuario,String senha,String autoridade) {
        try {
            ConexaoBd conexaoBd = new ConexaoBd();
            Connection conexao = conexaoBd.obterConexao();

            if (conexao != null) {
                System.out.println("Conexão estabelecidacom sucesso!");
                

                String sql = "INSERT INTO tb_cadastro (nome, senha, autoridade) VALUES (?, ?, ?)";

                PreparedStatement ps = conexao.prepareStatement(sql);

                ps.setString(1, usuario);
                ps.setString(2, senha);
                ps.setString(3, autoridade);

                ps.execute();

                conexao.close();
                JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso!");
                TelaDeLogin telaDeLogin = new TelaDeLogin();
                voltarAoLogin(telaDeLogin);

            } else {
                System.out.println("Conexão não estabelecida!");
            }
        } catch (Exception e) {
            System.out.println("Exceção: " +

                    e.getMessage());

        }
    }
    public void voltarAoLogin(JFrame TelaDeLogin) {
        setVisible(false);
        TelaDeLogin.setVisible(true);
        dispose();
    }

}

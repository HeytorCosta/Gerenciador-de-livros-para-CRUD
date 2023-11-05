import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.*;

public class Cadastro extends JFrame {

    private JComboBox<String> comboBox, livroFavorito1 ,livroFavorito2;
    private JTextField textFieldNome, textFieldIdade, textFieldTelefone, textFieldEmail;
    private JPasswordField passwordFieldSenha;
    private JButton buttoncadastro;

    public void cadastro() {
        setTitle("Cadastro");
        setSize(300, 390);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(50, 30, 100, 20);
        add(labelNome);

        textFieldNome = new JTextField();
        textFieldNome.setBounds(150, 30, 100, 20);
        add(textFieldNome);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(50, 60, 100, 20);
        add(labelSenha);

        passwordFieldSenha = new JPasswordField();
        passwordFieldSenha.setBounds(150, 60, 100, 20);
        add(passwordFieldSenha);

        JLabel labelIdade = new JLabel("Idade:");
        labelIdade.setBounds(50, 90, 100, 20);
        add(labelIdade);

        textFieldIdade = new JTextField();
        textFieldIdade.setBounds(150, 90, 100, 20);
        add(textFieldIdade);

        JLabel labelSexo = new JLabel("Sexo");
        labelSexo.setBounds(50, 120, 100, 20);
        add(labelSexo);

        String[] Sexo = {"Masculino", "Feminino",};
        JComboBox<String> comboBox = new JComboBox<>(Sexo);
        comboBox.setBounds(150, 120, 100, 20);
        
        getContentPane().add(comboBox);

        JLabel labelTelefone = new JLabel("Telefone:");
        labelTelefone.setBounds(50, 150, 100, 20);
        add(labelTelefone);

        textFieldTelefone = new JTextField();
        textFieldTelefone.setBounds(150, 150, 100, 20);
        add(textFieldTelefone);

        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setBounds(50, 180, 100, 20);
        add(labelEmail);

        textFieldEmail = new JTextField();
        textFieldEmail.setBounds(150, 180, 100, 20);
        add(textFieldEmail);

        JLabel labelLivroFavorito1 = new JLabel("Genero : ");
        labelLivroFavorito1.setBounds(50, 210, 100, 20);
        add(labelLivroFavorito1);

        String[] LivroFavorito1 = {"Romance", "Ficção","Tecnico"};
        JComboBox<String> livrofavorito1 = new JComboBox<>(LivroFavorito1);
        livrofavorito1.setBounds(150, 210, 100, 20);
        
        getContentPane().add(livrofavorito1);

        JLabel labelLivroFavorito2 = new JLabel("Genero : ");
        labelLivroFavorito2.setBounds(50, 250, 100, 20);
        add(labelLivroFavorito2);

        String[] LivroFavorito2 = {"Romance", "Ficção","Tecnico"};
        JComboBox<String> livroFavorito2 = new JComboBox<>(LivroFavorito2);
        livroFavorito2.setBounds(150, 250, 100, 20);
        
        getContentPane().add(livroFavorito2);

        buttoncadastro = new JButton("Cadastro");
        buttoncadastro.setBounds(100, 290, 100, 30);
        add(buttoncadastro);

        // Adiciona a ação ao botão de cadastro
        buttoncadastro.addActionListener(e -> {
            String Nome = textFieldNome.getText();
            String senha = new String(passwordFieldSenha.getPassword());
            int idade = Integer.valueOf(textFieldIdade.getText());;
            String sexo = (String)comboBox.getSelectedItem();
            int telefone = Integer.valueOf(textFieldTelefone.getText());
            String email = textFieldEmail.getText();
            String favorito1 = (String)livrofavorito1.getSelectedItem();
            String favorito2 = (String)livroFavorito2.getSelectedItem();;
            String autoridade = "Usuario";

            cadastroBD(Nome,senha,idade,sexo,telefone,email,favorito1,favorito2,autoridade);
    });

    setVisible(true);
    }

    private  void cadastroBD(String Nome,String senha,int idade, String sexo,int telefone,String email, String favorito1, String favorito2, String autoridade) {
        try {
            ConexaoBd conexaoBd = new ConexaoBd();
            Connection conexao = conexaoBd.obterConexao();

            if (conexao != null) {
                System.out.println("Conexão estabelecidacom sucesso!");
                

                String sql = "INSERT INTO tabela_cadastro (nome, senha, idade, sexo, telefone, email, livrosFavoritos1, livrosFavoritos2, autoridade) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement ps = conexao.prepareStatement(sql);

                ps.setString(1, Nome);
                ps.setString(2, senha);
                ps.setInt(3, idade);
                ps.setString(4, sexo);
                ps.setInt(5, telefone);
                ps.setString(6, email);
                ps.setString(7, favorito1);
                ps.setString(8, favorito2);
                ps.setString(9, autoridade);

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

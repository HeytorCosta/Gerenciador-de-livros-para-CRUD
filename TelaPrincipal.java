import java.sql.*;
import javax.swing.*;

public class TelaPrincipal extends JFrame {

    private JButton buttonCadastro,buttonCadastroLivro;

    public void Inicio(String usuario) {
        setVisible(true);
        setTitle("Tela Principal");
        setSize(300, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel labelSenha = new JLabel(usuario);
        labelSenha.setBounds(50, 60, 100, 20);
        add(labelSenha);

        buttonCadastroLivro = new JButton("Cadastro de livros");
            buttonCadastroLivro.setBounds(80, 150, 140, 20);
            add(buttonCadastroLivro);

            buttonCadastroLivro.addActionListener(e -> {
                TelaDeCadastroLivros(usuario);
            });
            getContentPane().add(buttonCadastroLivro);
        

        if (VerificarAdministrador(usuario)) {
            buttonCadastro = new JButton("Cadastro");
            buttonCadastro.setBounds(100, 120, 100, 20);
            add(buttonCadastro);

            buttonCadastro.addActionListener(e -> {
                TelaDeCadastro();
            });
            getContentPane().add(buttonCadastro);
        }

        setVisible(true);
    }

    //Chamar tela de cadastro de usuario

    public void TelaDeCadastro() {
        setVisible(false);
        Cadastro telaCadastro = new Cadastro();
        telaCadastro.cadastro();
    }

    //Chamar tela de cadastro de livros

    public void TelaDeCadastroLivros(String usuario){
        setVisible(false);
        Cadastroolivros teladeCadastroolivros = new Cadastroolivros();
        teladeCadastroolivros.cadastrolivros(usuario);
    }

    public static boolean VerificarAdministrador(String usuario){

        ConexaoBd conexaoBd = new ConexaoBd();
        Connection conexao = conexaoBd.obterConexao();
        try {
            String sql = "SELECT autoridade FROM tabela_cadastro WHERE nome = ?";
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setString(1, usuario);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String autoridadeDb = resultSet.getString("autoridade");
                String autoridade = "Administrador";
                return autoridade.equals(autoridadeDb);
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

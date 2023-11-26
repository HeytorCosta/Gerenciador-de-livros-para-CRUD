
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.Console;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TelaPrincipal {

    public void Inicio(String usuario) {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setTitle("Tela Principal");
        frame.setSize(600, 440);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(new BorderLayout());

        barradeFerramentas(usuario, frame);
        exibirlivros(frame);

        frame.setVisible(true);

    }

    // Chamar tela de cadastro de usuario

    public void TelaDeCadastro(JFrame frame) {
        frame.setVisible(false);
        Cadastro telaCadastro = new Cadastro();
        telaCadastro.cadastro();
    }

    // Chamar tela de cadastro de livros

    public void TelaDeCadastroLivros(String usuario, JFrame frame) {
        frame.setVisible(false);
        Cadastroolivros teladeCadastroolivros = new Cadastroolivros();
        teladeCadastroolivros.cadastrolivros(usuario);
    }

    public static boolean VerificarAdministrador(String usuario) {

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

    private void barradeFerramentas(String usuario, JFrame frame) {
        if (VerificarAdministrador(usuario)) {

            JPanel toolbarPanel = new JPanel(new BorderLayout());

            JToolBar toolbar = new JToolBar();
            toolbar.setFloatable(true);

            JButton CadastrarLivro = new JButton("Cadastro Livro");

            CadastrarLivro.addActionListener(e -> {
                TelaDeCadastroLivros(usuario, frame);
            });

            frame.getContentPane().add(CadastrarLivro);

            toolbar.add(CadastrarLivro);

            toolbar.addSeparator();

            JButton CadastroUsuario = new JButton("Cadastrar usuario");
            toolbar.add(CadastroUsuario);

            CadastroUsuario.addActionListener(e -> {
                TelaDeCadastro(frame);
            });
            frame.getContentPane().add(CadastroUsuario);

            toolbar.add(CadastroUsuario);

            toolbarPanel.add(toolbar);

            toolbarPanel.add(toolbar, BorderLayout.PAGE_START);

            // Adiciona o painel ao JFrame
            frame.getContentPane().add(toolbarPanel, BorderLayout.PAGE_START);
            frame.pack();
            frame.setVisible(true);

        } else {
            JPanel toolbarPanel = new JPanel(new BorderLayout());

            JToolBar toolbar = new JToolBar();
            toolbar.setFloatable(false);

            JButton CadastrarLivro = new JButton("Cadastro Livro");

            CadastrarLivro.addActionListener(e -> {
                TelaDeCadastroLivros(usuario, frame);
            });
            frame.getContentPane().add(CadastrarLivro);

            toolbar.add(CadastrarLivro);

            toolbar.addSeparator();

            toolbarPanel.add(toolbar, BorderLayout.PAGE_START);

            // Adiciona o painel ao JFrame
            frame.getContentPane().add(toolbarPanel, BorderLayout.PAGE_START);
            frame.pack();
            frame.setVisible(true);

        }
    }

    public void exibirlivros(JFrame frame) {

        JPanel panel = new JPanel(new FlowLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        int margem = 10; // Ajuste conforme necessário
        panel.setBorder(new EmptyBorder(margem, margem, margem, margem));

        ConexaoBd conexaoBd = new ConexaoBd();
        Connection conexao = conexaoBd.obterConexao();

        try {

            String sql = "SELECT Titulo FROM Nota_livros ORDER BY SomaNotas DESC";
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            JLabel Livros = new JLabel("Livros");
            Livros.setBounds(50, 30, 100, 50);
            panel.add(Livros);

            while (resultSet.next()) {
                String titulo = resultSet.getString("Titulo");
                //int nota = resultSet.getInt("nota");

                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

                JButton button = new JButton(titulo);
                Dimension buttonSize = new Dimension(320, 30);
                button.setPreferredSize(buttonSize);

                button.addActionListener(e -> {
                        try {
                            ExibirLivroDetalhado(titulo);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                });

                //JLabel labelNota = new JLabel("Nota: " + nota);

                buttonPanel.add(button);
                //buttonPanel.add(labelNota);

                panel.add(buttonPanel);
                panel.add(Box.createVerticalStrut(5));

            }
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        JScrollPane scrollPane = new JScrollPane(panel); 
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 

        frame.add(scrollPane);

        frame.setSize(600, 440);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public void ExibirLivroDetalhado(String titulo) throws SQLException {
        DetalhamentoLivros detalhamentoLivros = new DetalhamentoLivros();
        detalhamentoLivros.TelaDoLivroDetalhado(titulo);
    }
}
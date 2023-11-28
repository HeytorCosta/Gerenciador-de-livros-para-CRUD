
import java.awt.*;
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
        exibirlivros(frame, usuario);

        frame.setVisible(true);

    }

    // Chamar tela de cadastro de usuario

    public void TelaDeCadastro(JFrame frame, String usuario) {
        frame.setVisible(false);
        Cadastro telaCadastro = new Cadastro();
        telaCadastro.cadastro(usuario);
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

            toolbar.add(CadastrarLivro);

            toolbar.addSeparator();

            JButton CadastroUsuario = new JButton("Cadastrar usuario");
            toolbar.add(CadastroUsuario);

            CadastroUsuario.addActionListener(e -> {
                TelaDeCadastro(frame, usuario);
            });

            toolbar.add(CadastroUsuario);

            JSeparator separator = new JSeparator(JSeparator.VERTICAL);
            separator.setPreferredSize(new Dimension(0, 20)); 
    
            toolbar.add(separator);


            JButton Sair = new JButton("Log off");
            toolbar.add(Sair);

            Sair.addActionListener(e -> {
                frame.setVisible(false);
                new TelaDeLogin();
            });

            toolbar.add(Sair, BorderLayout.PAGE_END);

            toolbarPanel.add(toolbar);

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

            JSeparator separator = new JSeparator(JSeparator.VERTICAL);
            separator.setPreferredSize(new Dimension(0, 20)); 
    
            toolbar.add(separator);

            JButton Sair = new JButton("Log off");
            toolbar.add(Sair);

            Sair.addActionListener(e -> {
                frame.setVisible(false);
                new TelaDeLogin();
            });

            toolbar.add(Sair, BorderLayout.PAGE_END);

            toolbarPanel.add(toolbar);

            frame.getContentPane().add(toolbarPanel, BorderLayout.PAGE_START);
            frame.pack();
            frame.setVisible(true);

        }
    }

    public void exibirlivros(JFrame frame, String usuario) {

        JPanel panel = new JPanel(new FlowLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        int margem = 10; // Ajuste conforme necessário
        panel.setBorder(new EmptyBorder(margem, margem, margem, margem));

        ConexaoBd conexaoBd = new ConexaoBd();
        Connection conexao = conexaoBd.obterConexao();

        try {

            String sql = "SELECT Titulo, SomaNotas, Qtd_leitores FROM Nota_livros ORDER BY (SomaNotas/Qtd_leitores) DESC";
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            JLabel Livros = new JLabel("Livros");
            Livros.setBounds(50, 30, 100, 50);
            panel.add(Livros);

            while (resultSet.next()) {
                String titulo = resultSet.getString("Titulo");
                int nota = resultSet.getInt("SomaNotas");
                int qtd_leitores = resultSet.getInt("Qtd_leitores");

                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

                JButton button = new JButton(titulo);
                Dimension buttonSize = new Dimension(450, 30);
                button.setPreferredSize(buttonSize);

                button.addActionListener(e -> {
                    try {
                        ExibirLivroDetalhado(titulo, usuario, frame);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                });
                if (nota == 0) {
                    JLabel labelNota = new JLabel("Nota: " + 0.0);

                    buttonPanel.add(button);
                    buttonPanel.add(labelNota);

                } else {
                    float notaFloat = (float) nota;
                    JLabel labelNota = new JLabel("Nota: " + notaFloat / qtd_leitores);

                    buttonPanel.add(button);
                    buttonPanel.add(labelNota);
                }

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

    public void ExibirLivroDetalhado(String titulo, String usuario, JFrame frame) throws SQLException {
        DetalhamentoLivros detalhamentoLivros = new DetalhamentoLivros();
        detalhamentoLivros.TelaDoLivroDetalhado(titulo, usuario, frame);
    }
}
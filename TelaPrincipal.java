import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

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

        // Criar modelo de tabela
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("Título");
        model.addColumn("Autor");
        model.addColumn("Editora");
        model.addColumn("Tipo");
        model.addColumn("Nota");

        ConexaoBd conexaoBd = new ConexaoBd();
        Connection conexao = conexaoBd.obterConexao();

        try {

            String sql = "SELECT * FROM tabela_livros";
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("cod_livro");
                String titulo = resultSet.getString("Titulo");
                String autor = resultSet.getString("Autor");
                String editora = resultSet.getString("Editora");
                String tipo = resultSet.getString("Tipo");
                int nota = resultSet.getInt("nota");

                model.addRow(new Object[] { id, titulo, autor, editora, tipo, nota });
            }
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        // Criação do painel para agrupar barra de ferramentas e a tabela
        JPanel painel = new JPanel(new BorderLayout());
        painel.add(scrollPane, BorderLayout.CENTER);

        // Adiciona o painel ao JFrame
        frame.getContentPane().add(painel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

    }
}

import java.awt.*;
import java.sql.*;
import javax.swing.*;
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

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(
                new Object[][] {
                },
                new String[] { "Título", "Autor", "Editora", "Tipo", "Nota" });
        table.setModel(model);

        ConexaoBd conexaoBd = new ConexaoBd();
        Connection conexao = conexaoBd.obterConexao();

        try {

            String sql = "SELECT * FROM tabela_livros ORDER BY nota DESC";
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String titulo = resultSet.getString("Titulo");
                String autor = resultSet.getString("Autor");
                String editora = resultSet.getString("Editora");
                String tipo = resultSet.getString("Tipo");
                int nota = resultSet.getInt("nota");

                model.addRow(new Object[] { titulo, autor, editora, tipo, nota });
            }
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        // table.setRowSorter(sorter);

        // // Especificando a coluna a ser ordenada (supondo que a coluna das notas seja
        // a sexta, índice 5)
        // sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(5,
        // SortOrder.DESCENDING))); // Ordena por nota em ordem decrescente

        // Adicionando a tabela a um JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel painel = new JPanel(new BorderLayout());
        painel.add(scrollPane, BorderLayout.CENTER);

        // Adiciona o painel ao JFrame
        frame.getContentPane().add(painel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

    }
}
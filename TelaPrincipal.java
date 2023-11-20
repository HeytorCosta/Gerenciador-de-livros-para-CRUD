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
        frame.setLayout(null);


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

            JToolBar toolbar = new JToolBar();
            toolbar.setFloatable(true);
            toolbar.setBounds(0, 0, 600, 20);

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

            frame.add(toolbar);

        } else {

            JToolBar toolbar = new JToolBar();
            toolbar.setFloatable(true);
            toolbar.setBounds(0, 0, 600, 20);

            JButton CadastrarLivro = new JButton("Cadastro Livro");

            CadastrarLivro.addActionListener(e -> {
                TelaDeCadastroLivros(usuario, frame);
            });
            frame.getContentPane().add(CadastrarLivro);

            toolbar.add(CadastrarLivro);

            toolbar.addSeparator();

            frame.add(toolbar);
        }
    }

    public void exibirlivros(JFrame frame) {

        // Criar modelo de tabela
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Título");
        model.addColumn("Autor");
        model.addColumn("Editora");
        model.addColumn("Tipo");
        model.addColumn("Nota");

        // Criar tabela com modelo
        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

         // Configurar layout do painel principal
         JPanel panel = new JPanel();
         panel.setBounds(20, 60, 400, 200);
 
         // Adicionar painel de rolagem ao painel principal
         panel.add(scrollPane);
 
       

        ConexaoBd conexaoBd = new ConexaoBd();
        Connection conexao = conexaoBd.obterConexao();

        try {

            String sql = "SELECT cod_livro, Tituto, Autor, Editora, Tipo, nota FROM tabela_livros";
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("cod_livro");
                String titulo = rs.getString("Titulo");
                String autor = rs.getString("Autor");
                String editora = rs.getString("Editora");
                String tipo = rs.getString("Tipo");
                int nota = rs.getInt("nota");

                model.addRow(new Object[] { id, titulo, autor, editora, tipo, nota });
            }
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        frame.revalidate();

    }
}


import javax.swing.*;
import java.sql.*;

public class DetalhamentoLivros extends JFrame {

    public void TelaDoLivroDetalhado(String titulo, String usuario, JFrame frame) {

        setTitle(titulo);
        setSize(600, 440);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel TextoTitulo = new JLabel("Tilulo:");
        TextoTitulo.setBounds(15, 5, 300, 20);
        add(TextoTitulo);

        JLabel Titulo = new JLabel(titulo);
        Titulo.setBounds(55, 5, 300, 20);
        add(Titulo);

        ConexaoBd conexaoBd = new ConexaoBd();
        Connection conexao = conexaoBd.obterConexao();
        try {
            String sql = "SELECT Descrição, Autor, Editora, Tipo FROM tabela_livros WHERE Titulo = ?";
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setString(1, titulo);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String descricao = resultSet.getString("Descrição");
                String autor = resultSet.getString("Autor");
                String editora = resultSet.getString("Editora");
                String tipo = resultSet.getString("Tipo");

                JLabel TextoDescrição = new JLabel("Descrição: ");
                TextoDescrição.setBounds(15, 35, 300, 20);
                add(TextoDescrição);

                JLabel DescriçãoLivro = new JLabel(descricao);
                DescriçãoLivro.setBounds(85, 35, 300, 20);
                add(DescriçãoLivro);

                JLabel TextoAutor = new JLabel("Autor: ");
                TextoAutor.setBounds(15, 65, 300, 20);
                add(TextoAutor);

                JLabel AutorLivro = new JLabel(autor);
                AutorLivro.setBounds(55, 65, 300, 20);
                add(AutorLivro);

                JLabel TextoEditora = new JLabel("Editora: ");
                TextoEditora.setBounds(15, 95, 300, 20);
                add(TextoEditora);

                JLabel EditoraLivro = new JLabel(editora);
                EditoraLivro.setBounds(65, 95, 300, 20);
                add(EditoraLivro);

                JLabel TextoTipo = new JLabel("Tipo: ");
                TextoTipo.setBounds(15, 125, 300, 20);
                add(TextoTipo);

                JLabel TipoLivro = new JLabel(tipo);
                TipoLivro.setBounds(50, 125, 300, 20);
                add(TipoLivro);

            } else {
                System.out.println("volta nada");
            }
            resultSet.close();
            statement.close();
            conexao.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        JButton Avalie = new JButton("Avalie o livro");
        Avalie.setBounds(440, 365, 120, 30);
        getContentPane().add(Avalie);

        Avalie.addActionListener(e -> {
            AvaliarLivro(titulo, usuario, frame);
        });

        JButton Voltar = new JButton("Voltar");
        Voltar.setBounds(20, 365, 120, 30);
        getContentPane().add(Voltar);

        Voltar.addActionListener(e -> {
            setVisible(false);
        });

        setVisible(true);
    }

    public void AvaliarLivro(String titulo, String usuario, JFrame frame) {
        TelaAvaliarLivro telaAvaliarLivro = new TelaAvaliarLivro();
        telaAvaliarLivro.telaAvaliarLivro(titulo, usuario, frame);
    }

}

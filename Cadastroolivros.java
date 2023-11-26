import javax.swing.*;
import java.sql.*;

public class Cadastroolivros extends JFrame {

    private JButton buttoncadastro;
    private JTextField textFieldNomeLivro, textFieldAutorLivro, textFieldEditoraLivro,textFieldTipoLivro;

    public void cadastrolivros(String usuario) {
        setTitle("Cadastro Livro");
        setSize(300, 390);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel labelNome = new JLabel("Nome do Livro:");
        labelNome.setBounds(50, 30, 100, 20);
        add(labelNome);

        textFieldNomeLivro = new JTextField();
        textFieldNomeLivro.setBounds(150, 30, 100, 20);
        add(textFieldNomeLivro);

        JLabel labelDescrição = new JLabel("Descrição:");
        labelDescrição.setBounds(50, 60, 100, 20);
        add(labelDescrição);

        JTextField textFieldDescrição = new JTextField();
        textFieldDescrição.setBounds(150,60,100,20);
        add(textFieldDescrição);

        JLabel labelAutor = new JLabel("Autor do Livro:");
        labelAutor.setBounds(50, 90, 100, 20);
        add(labelAutor);

        textFieldAutorLivro = new JTextField();
        textFieldAutorLivro.setBounds(150, 90, 100, 20);
        add(textFieldAutorLivro);

        JLabel labelEditora = new JLabel("Editora do Livro:");
        labelEditora.setBounds(50, 120, 100, 20);
        add(labelEditora);

        textFieldEditoraLivro = new JTextField();
        textFieldEditoraLivro.setBounds(150, 120, 100, 20);
        add(textFieldEditoraLivro);

        JLabel labelTipo = new JLabel("Tipo do Livro:");
        labelTipo.setBounds(50, 150, 100, 20);
        add(labelTipo);

        textFieldTipoLivro = new JTextField();
        textFieldTipoLivro.setBounds(150, 150, 100, 20);
        add(textFieldTipoLivro);


        buttoncadastro = new JButton("Cadastrar");
        buttoncadastro.setBounds(100, 190, 100, 30);
        add(buttoncadastro);

        // Adiciona a ação ao botão de cadastro de livros
        
        buttoncadastro.addActionListener(e -> {
            String NomeLivro = textFieldNomeLivro.getText();
            String DescriçãoLivros=textFieldDescrição.getText();
            String AutorLivro = textFieldAutorLivro.getText();
            String EditoraLivro = textFieldEditoraLivro.getText();
            String TipoLivro = textFieldTipoLivro.getText();
            cadastrolivrosBD(NomeLivro,DescriçãoLivros,AutorLivro,EditoraLivro,TipoLivro,usuario);
        
            
    });


        setVisible(true);
    }
public void cadastrolivrosBD(String NomeLivro, String DescriçãoLivro, String AutorLivro, String EditoraLivro, String TipoLivro,String usuario){
    try {
        ConexaoBd conexaoBd = new ConexaoBd();
        Connection conexao = conexaoBd.obterConexao();

        if (conexao != null) {
            System.out.println("Conexão estabelecidacom sucesso!");
            

            String sql = "INSERT INTO tabela_livros (Titulo, Descrição, Autor, Editora, Tipo) VALUES (?,?,?,?,?)";

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, NomeLivro);
            ps.setString(2, DescriçãoLivro);
            ps.setString(3, AutorLivro);
            ps.setString(4, EditoraLivro);
            ps.setString(5, TipoLivro);
            
           
            ps.execute();

            conexao.close();
            JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso!");
            
            RetornoTelaPrincipal(usuario);
            
            
}else {
    System.out.println("Conexão não estabelecida!");
}
} catch (Exception e) {
System.out.println("Exceção: " +

        e.getMessage());

}

}
public void RetornoTelaPrincipal(String usuario){
    TelaPrincipal telaPrincipal = new TelaPrincipal();
            telaPrincipal.Inicio(usuario);

            setVisible(false);
}
}

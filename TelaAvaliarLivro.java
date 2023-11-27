import java.sql.*;
import javax.swing.*;

import javax.swing.JFrame;

public class TelaAvaliarLivro extends JFrame {
    public void telaAvaliarLivro(String titulo) {
        setTitle("Avalie:" + titulo);
        setSize(300, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        ConexaoBd conexaoBd = new ConexaoBd();
        Connection conexao = conexaoBd.obterConexao();

        try {
            String sql = "SELECT SomaNotas,Qtd_leitores FROM Nota_livros WHERE Titulo = ?";
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setString(1, titulo);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int somanotas = resultSet.getInt("SomaNotas");
                int qtd_leitores = resultSet.getInt("Qtd_leitores");
                if (somanotas == 0) {
                    JLabel textonotamedia = new JLabel("Ninguem avaliou!");
                    textonotamedia.setBounds(100, 10, 100, 20);
                    add(textonotamedia);

                    JLabel QualNota = new JLabel("Qual é sua avaliação");
                    QualNota.setBounds(90, 40, 120, 20);
                    add(QualNota);

                    String[] Nota = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
                    JComboBox<String> notalivro = new JComboBox<>(Nota);
                    notalivro.setBounds(100, 70, 100, 30);
                    getContentPane().add(notalivro);

                    JButton avaliação = new JButton("Avalie");
                    avaliação.setBounds(100,120,100,30);
                    avaliação.addActionListener(e ->{
                        String stringNotaAtribuida = (String)notalivro.getSelectedItem();
                        int NotaAtribuida = Integer.parseInt(stringNotaAtribuida);
                        inserirnota(titulo, NotaAtribuida);
                        JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso!");
                        setVisible(false);

                    });
                    getContentPane().add(avaliação);
                    
                } else {
                    float notamedia = somanotas / qtd_leitores;

                    JLabel textonotamedia = new JLabel("Nota Do livro : " + notamedia);
                    textonotamedia.setBounds(100, 10, 100, 20);
                    add(textonotamedia);

                    JLabel QualNota = new JLabel("Qual é sua avaliação");
                    QualNota.setBounds(90, 40, 120, 20);
                    add(QualNota);

                    String[] Nota = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
                    JComboBox<String> notalivro = new JComboBox<>(Nota);
                    notalivro.setBounds(100, 70, 100, 30);
                    getContentPane().add(notalivro);

                    JButton avaliação = new JButton("Avalie");
                    avaliação.setBounds(100,120,100,30);
                    avaliação.addActionListener(e ->{
                        String stringNotaAtribuida = (String)notalivro.getSelectedItem();
                        int NotaAtribuida = Integer.parseInt(stringNotaAtribuida);
                        inserirnota(titulo, NotaAtribuida);
                        JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso!");
                        setVisible(false);

                    });
                    getContentPane().add(avaliação);
                    
                }

            } else {
                System.out.println("volta nada");
            }
            resultSet.close();
            statement.close();
            conexao.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        setVisible(true);
    }

    public void inserirnota(String titulo, int NotaAtribuida){

        ConexaoBd conexaoBd = new ConexaoBd();
        Connection conexao = conexaoBd.obterConexao();

        try {
            String sql = "UPDATE Nota_livros SET SomaNotas = SomaNotas + ?, Qtd_leitores = Qtd_leitores + 1 WHERE Titulo = ?";
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt(1, NotaAtribuida);
            statement.setString(2, titulo);

            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Dados atualizados com sucesso para o livro: " + titulo);
            } else {
                System.out.println("Nenhum livro encontrado com o título: " + titulo);
            }

            statement.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }

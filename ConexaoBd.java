import java.sql.*;

public class ConexaoBd {

    Connection conexao = null;

    public Connection obterConexao() {
        if (conexao == null) {
            try {
                Connection conexao = DriverManager.getConnection(
                        // essa é a conhecida string de conexão
                        "jdbc:mysql://localhost:3306/teste",
                        "root",
                        "Gerv1nh@");
                this.conexao = conexao;
            } catch (Exception e) {
                System.out.println("Exceção: " + e.getMessage());
            }
        }
        return conexao;
    }
}

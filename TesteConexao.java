import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.*;
public class TesteConexao {
public static void main(String[] args) {
try{
Connection conexao =
DriverManager.getConnection(

//essa é a conhecida string de conexão
"jdbc:mysql://localhost:3306/teste",
"root",
"Gerv1nh@"
);
if (conexao != null){
System.out.println("Conexão estabelecidacom sucesso!");
String nome = JOptionPane.showInputDialog("Qual o nome?");
String senha =JOptionPane.showInputDialog("Qual a senha?");

//o comando a ser executado é uma simples string
//os símbolos ? reservam o lugar de valores a serem especificados
String sql = "INSERT INTO tb_usuario (nome, senha) VALUES (?, ?)";
//uma estrutura que representa o comando é "preparada". Assim, ele pode ser executado diversas vezes com dados diferentes

PreparedStatement ps = conexao.prepareStatement(sql);
//configuramos os valores a serem inseridos
ps.setString(1, nome);
ps.setString(2, senha);
//executamos o comando
ps.execute();
//fechamos a conexão para que o MySQL Server possa liberar recursos
conexao.close();
JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso!");
}
else{
System.out.println("Conexão não estabelecida!");
}
}
catch (Exception e){
    System.out.println("Exceção: " +

e.getMessage());

}
}

}

 
package cadastrousermysql;

import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

 
public class CadastroUserMysql {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean rodando = true;

        while (rodando) {
            System.out.println("========");
            System.out.println("[1]Inserir Usuários");
            System.out.println("[2]Buscar Usuários");
            System.out.println("[3]Excluir Usuários");
            System.out.println("[4]Alter Usuário");
            System.out.println("[5]Sair");
            System.out.print("Digite a opção:");
            String opcao = sc.nextLine();

            switch (opcao) {
                case "1":
                    rodarInserirUsuario();

                    break;

                case "2":
                    rodarBuscarUsuarios();
                    break;

                case "3":
                    rodarExcluirUsuario();

                case "4":
                    rodarAlterarUsuario();

                    break;

                case "5":
                    rodando = false;
                    break;
            }

        }

    }

    public static void rodarInserirUsuario() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o email: ");
        String email = scanner.nextLine();
        System.out.println("Digite a senha:");
        String senha = scanner.nextLine();

        Usuario u = new Usuario();
        u.setEmail(email);
        u.setSenha(senha);

        inserirUsuario(u);

    }

    private static void inserirUsuario(Usuario u) {
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?useTimezone=true&serverTimezone=UTC", "root", "");

            PreparedStatement stmt = connection.prepareStatement("INSERT INTO cadastro_usuarios.usuario(email,senha)VALUES (?,?)");

            stmt.setString(1, u.getEmail());
            stmt.setString(2, u.getSenha());
            int linhasAfetadadas = stmt.executeUpdate();

            if (linhasAfetadadas > 0) {
                System.out.println("Usuario Cadastrado com sucesso");
            } else {
                System.out.println("Algo deu errado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rodarBuscarUsuarios() {
        buscarUsuarios();
    }

    public static void buscarUsuarios() {
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?useTimezone=true&serverTimezone=UTC", "root", "");

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM cadastro_usuarios.usuario");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String senha = rs.getString("senha");
                System.out.println("");
                System.out.println("Id: " + id);
                System.out.println("Email: " + email);
                System.out.println("Senha: " + senha);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rodarExcluirUsuario() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o id que deseja excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Usuario u = new Usuario();
        u.setId(id);

        excluirUsuario(u);
    }

    private static void excluirUsuario(Usuario u) {
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?useTimezone=true&serverTimezone=UTC", "root", "");

            PreparedStatement stmt = connection.prepareStatement("DELETE  FROM cadastro_usuarios.usuario  WHERE id= ?");

            stmt.setInt(1, u.getId());

            int linhasExcluidas = stmt.executeUpdate();

            if (linhasExcluidas > 0) {
                System.out.println("Usuario Excluido com sucesso");
            } else {
                System.out.println("Algo deu errado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void rodarAlterarUsuario() {
        
        rodarBuscarUsuarios();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o id");
        int id = scanner.nextInt();
        String nextLine = scanner.nextLine();
        System.out.println("Digite o email: ");
        String email = scanner.nextLine();
        System.out.println("Digite a senha:");
        String senha = scanner.nextLine();

        Usuario u = new Usuario();
        u.setId(id);
        u.setEmail(email);
        u.setSenha(senha);

        alterarUsuario(u);
 
          
        }

    

    

    private static void alterarUsuario(Usuario u) {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?useTimezone=true&serverTimezone=UTC", "root", "");
            PreparedStatement stmt = connection.prepareStatement("UPDATE cadastro_usuarios.usuario SET email = ?,senha = ? WHERE id = ?");

            stmt.setString(1, u.getEmail());
            stmt.setString(2, u.getSenha());
            stmt.setInt(3, u.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Usuário Alterado com sucesso");

            } else {
                System.out.println("Erro tente novamente!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

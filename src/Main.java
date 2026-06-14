import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream("db.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        String sql =
                "SELECT birth_date FROM customers WHERE customer_id = ? AND first_name = ?";

        try (
                Connection con =
                        DriverManager.getConnection(url, user, password);
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {
            ps.setInt(1,7);
            ps.setString(2,"Ilene");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getDate("birth_date")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3310/hospital_db";
        String user = "root";
        String pass = "1234";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT first_name, last_name FROM patients")) {

                while (rs.next()) {
                    System.out.println(rs.getString("first_name") + " " + rs.getString("last_name"));
                }

                String insertSql = "INSERT INTO patients (first_name, last_name, gender, birth_date) VALUES (?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                    ps.setString(1, "Lara");
                    ps.setString(2, "Cruz");
                    ps.setString(3, "F");
                    ps.setDate(4, Date.valueOf("1999-05-10"));
                    ps.executeUpdate();
                }

                String updateSql = "UPDATE patients SET contact_number = ? WHERE patient_id = ?";
                try (PreparedStatement ps1 = conn.prepareStatement(updateSql)) {
                    ps1.setString(1, "09170000000");
                    ps1.setInt(2, 5);
                    ps1.executeUpdate();
                }

                String deleteSql = "DELETE FROM patients WHERE patient_id = ?";
                try (PreparedStatement ps2 = conn.prepareStatement(deleteSql)) {
                    ps2.setInt(1, 10);
                    ps2.executeUpdate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver not found!");
        }
    }
}

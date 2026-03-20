package databases;

import java.sql.*;

public class basics {
    public static void main(String[] args) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "pvs", "infis");

        Statement statement = con.createStatement();
        ResultSet resultset = statement.executeQuery("SELECT * FROM city");
        System.out.println("asi ok");

        while (resultset.next()) {
            System.out.println(resultset.getString("Name")
            + ", " + resultset.getString("CountryCode")
            + ", " + resultset.getString("Population"));
        }
    }
}

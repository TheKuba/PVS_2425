package databases;

import java.sql.*;

public class Basics {
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "pvs", "infis");

        Statement statement = conn.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM city");
        System.out.println("asi ok");

        while (resultSet.next()){
            System.out.println(resultSet.getString("Name")
            +", " + resultSet.getString("CountryCode")
            +", " + resultSet.getInt("Population"));
        }
    }
}

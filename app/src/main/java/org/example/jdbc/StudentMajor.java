package org.example.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.derby.jdbc.ClientDriver;

public class StudentMajor {
    public static void main(String[] args) {
        String url = "jdbc:derby://localhost/studentdb";
        String qry = "select SName, DName from DEPTH, STUDENT"
            + "where MajorId = DId";

        Driver d = new ClientDriver();
        try (Connection conn = d.connect(url, null);
            Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(qry);
            System.out.println("Name\tMajor");
            while (rs.next()) {
                String sname = rs.getString("SName");
                String dname = rs.getString("DName");
                System.out.println(sname + "\t" + dname);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

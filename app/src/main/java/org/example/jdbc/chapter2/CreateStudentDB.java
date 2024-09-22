package org.example.jdbc.chapter2;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.derby.jdbc.ClientDriver;

public class CreateStudentDB {
    public static void main(String[] args) {
        String url = "jdbc:derby://localhost/studentdb;create=true";

        Driver d = new ClientDriver();

        try (Connection conn = d.connect(url, null);
                Statement stmt = conn.createStatement()) {
            String s = "create table STUDENT(SId int, SName varchar(10), MajorId int, GradYear int)";
            stmt.executeUpdate(s);
            System.out.println("Table STUDENT created.");

            s = "insert into STUDENT(SId, SName, MajorId, GradYear) values ";
            String[] studvals = {
                "(1, 'joe', 10, 2021)",
                "(2, 'amy', 20, 2020)",
                "(3, 'max', 10, 2022)",
                "(5, 'bob', 30, 2020)",
                "(6, 'kim', 20, 2020)",
                "(7, 'art', 30, 2021)",
                "(8, 'pat', 20, 2019)",
                "(9, 'lee', 10, 2021)",
            };

            for (int i=0; i<studvals.length; i++) {
                stmt.executeUpdate(s + studvals[i]);
            }
            System.out.println("STUDENT records inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

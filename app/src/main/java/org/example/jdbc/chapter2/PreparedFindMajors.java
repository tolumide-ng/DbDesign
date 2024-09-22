package org.example.jdbc.chapter2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import org.apache.derby.jdbc.ClientDataSource;

public class PreparedFindMajors {
    public static void main(String[] args) {
        System.out.print("Enter a department name: ");
        String major;
        try (Scanner sc = new Scanner(System.in)) {
            major = sc.next();
        }
        String qry = "select sname, gradyear from student, dept "
            + "where did = majorid and dname = ?";

        ClientDataSource ds = new ClientDataSource();
        ds.setServerName("localhost");
        ds.setDatabaseName("studentdb");

        try (Connection conn = ds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(qry)) {
            pstmt.setString(1, major);
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("Here are the " + major + " majors");
                System.out.println("name\tGradYear");
                while (rs.next()) {
                    String sname = rs.getString("sname");
                    int gradyear = rs.getInt("gradyear");
                    System.out.println(sname + "\t" + gradyear);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}

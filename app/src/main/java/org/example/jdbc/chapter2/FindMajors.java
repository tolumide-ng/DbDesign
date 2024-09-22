package org.example.jdbc.chapter2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import org.apache.derby.jdbc.ClientDataSource;

public class FindMajors {
    public static void main(String[] args) {
        System.out.print("Enter a department name: ");
        String major;
        try (Scanner sc = new Scanner(System.in)) {
            major = sc.next();
        }
        String qry = "select sname, gradyear from student, depth "
            + "where did = majorid and dname = '" + major + "'";

        ClientDataSource ds = new ClientDataSource();
        ds.setServerName("localhost");
        ds.setDatabaseName("studentdb");
        
        try ( Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(qry)) {
            
                System.out.println("Here are the " + major + " majors");
                System.out.println("Name \tGradYear");;

            while (rs.next()) {
                String sname = rs.getString("sname");
                int gradyear = rs.getInt("gradyear");
                System.out.println(sname + "\t" + gradyear);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

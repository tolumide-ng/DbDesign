package org.example.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.Statement;

import org.apache.derby.jdbc.ClientDriver;

public class ChangeMajor {
    public static void main(String[] args) {
        String url = "jdbc:derby://localhost/studentdb";
        String cmd = "update STUDENT set MajorId=30 where sName='amy'";

        Driver d = new ClientDriver();
        try ( Connection conn = d.connect(url, null); 
                Statement stmt = conn.createStatement();) {
            int howmany = stmt.executeUpdate(cmd);
            System.out.println(howmany + " records changed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

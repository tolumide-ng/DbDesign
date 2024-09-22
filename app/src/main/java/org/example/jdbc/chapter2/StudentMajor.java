package org.example.jdbc.chapter2;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

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

            ResultSetMetaData md = rs.getMetaData();
            for (int i=1; i< md.getColumnCount(); i++) {
                String name = md.getColumnName(i);
                int size = md.getColumnDisplaySize(i);
                int typecode = md.getColumnType(i);

                String type;

                switch (typecode) {
                    case Types.INTEGER:
                        type = "int";
                        break;
                    case Types.VARCHAR:
                        type = "string";
                        break;
                    default:
                        type = "other";
                        System.out.println("name " + "\t" + type + "\t" + size);
                        break;
                }
            }

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
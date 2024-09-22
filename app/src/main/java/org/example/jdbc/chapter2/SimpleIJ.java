package org.example.jdbc.chapter2;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

import org.apache.derby.jdbc.ClientDriver;
import org.apache.derby.jdbc.EmbeddedDriver;

public class SimpleIJ {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Connect> ");
        String s = sc.nextLine();

        Driver d = (s.contains("//")) ? new ClientDriver() : new EmbeddedDriver();

        try (Connection conn = d.connect(s, null);
            Statement stmt = conn.createStatement()) {
                System.out.println("\nSQL>");
                while (sc.hasNextLine()) {
                    String cmd = sc.nextLine().trim();
                    if (cmd.startsWith("exit")) {
                        break;
                    } else if (cmd.startsWith("select")) {
                        doQuery(stmt, cmd);
                    } else {
                        doUpdate(stmt, cmd);
                    }
                    System.out.println("\nSQL> ");
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sc.close();
    }

    private static void doQuery(Statement stmt, String cmd) {
        try (ResultSet rs = stmt.executeQuery(cmd)
        ) {
            ResultSetMetaData md = rs.getMetaData();
            int numCols = md.getColumnCount();
            int totalWidth = 0;

            // print header
            for (int i =1; i< numCols; i++) {
                String fldname = md.getColumnName(i);
                int width = md.getColumnDisplaySize(i);
                totalWidth += width;
                String fmt = "%" + width + "s";
                System.out.format(fmt, fldname);
            }
            System.out.println();
            for (int i=0; i < totalWidth; i++) {
                System.out.print("-");
            }
            System.out.println();

            // print records
            while (rs.next()) {
                for (int i=1; i<numCols; i++) {
                    String fldname = md.getColumnName(i);
                    int fldtype = md.getColumnType(i);

                    String fmt = "%" + md.getColumnDisplaySize(i);
                    if (fldtype == Types.INTEGER) {
                        int ival = rs.getInt(fldname);
                        System.out.format(fmt + "d", ival);
                    } else {
                        String sval = rs.getString(fldname);
                        System.out.format(fmt + "s", sval);
                    }
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    private static void doUpdate(Statement stmt, String cmd) {
        try {
            int howMany = stmt.executeUpdate(cmd);
            System.out.println(howMany + " records processed");
        } catch (Exception e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }
}

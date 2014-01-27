/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.querytool.dao;

/**
 *
 * @author 367940
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author 367940
 */
public class DBConnect {

    //private static String schema = "MyJavaSchema";
    private static Connection conn = null;
    private static DatabaseMetaData dbmd = null;
    private static String driver = "org.postgresql.Driver";
    private static String url = "jdbc:postgresql://localhost:5432/postgressSQLMyDB";
    private String userName = "";
    private String password = "";

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public DBConnect(String uname, String pass) throws ClassNotFoundException,
            SQLException {
        this.userName = uname;
        this.password = pass;
    }

    public boolean connect() throws ClassNotFoundException, SQLException {
        boolean status = false;
        if (DBConnect.getDBConnection(userName, password) != null) {
            status = true;
        }
        return status;
    }

    private static Connection getDBConnection(String uname, String pass) throws ClassNotFoundException, SQLException {

        if (conn == null) {

            Class.forName(driver);
            conn = DriverManager.getConnection(url, uname, pass);
            dbmd = conn.getMetaData();
            //conn.setSchema(schema);
        }

        return conn;
    }

    public String process(String query) throws SQLException, IOException {
        String oppath = null;

        ResultSet rset = ExeQuery(query);
        String data = htmlFormat(rset);
        oppath = fileOutput(data);

        return oppath;
    }

    private ResultSet ExeQuery(String query) throws SQLException {
        Statement stmt = null;
        ResultSet rset = null;
        stmt = conn.createStatement();
        rset = stmt.executeQuery(query);
        return rset;
    }

    private String htmlFormat(ResultSet rset) throws SQLException {

        ResultSetMetaData rsmd = rset.getMetaData();
        StringBuilder sb = new StringBuilder();
        int colcnt = rsmd.getColumnCount();
        String cols[] = new String[colcnt];
        sb.append("<table border=\"1\">");
        sb.append("\n<tr>");
        for (int i = 0; i < colcnt - 1; i++) {
            cols[i] = rsmd.getColumnName(i + 2);
            System.out.println(cols[i]);
            sb.append("\n<td>" + cols[i] + "</td>");
        }
        sb.append("\n</tr>");
        while (rset.next()) {
            sb.append("\n<tr>");
            for (int i = 2; i <= colcnt; i++) {
                sb.append("\n<td>" + rset.getString(i) + "</td>");

            }
            sb.append("\n</tr>");
        }
        sb.append("</table>");
        String str = sb.toString();
        System.out.println(str);
        return str;
    }

    private String fileOutput(String data) throws IOException {

        File opfile = new File("c:\\selected.html");
        FileWriter fos = null;
        BufferedWriter outputFile = null;

        fos = new FileWriter(opfile);

        outputFile = new BufferedWriter(fos);

        outputFile.write(data);
        outputFile.close();

        return opfile.toPath().toAbsolutePath().toString();

    }
}

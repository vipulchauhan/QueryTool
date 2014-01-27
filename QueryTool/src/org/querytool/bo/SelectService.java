/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.querytool.bo;

import org.querytool.dao.DBConnect;
import java.io.IOException;
import java.sql.SQLException;




/**
 *
 * @author 367940
 */
public class SelectService implements Runnable {

    private String outputpath = null;
    private String username = null;
    private String password = null;
    private String query = null;
    private StringBuffer logs = new StringBuffer();
    private boolean status = false;

    public void run() {
        DBConnect dbc = null;
        String oppath = null;
        if (varify()) {
            try {
                dbc = new DBConnect(this.username, this.password);
            } catch (ClassNotFoundException ex) {
                logs.append(ex.getMessage());
            } catch (SQLException ex) {
                logs.append(ex.getMessage());
            }
           
            

            try {
                if (dbc.connect()) {
                    oppath = dbc.process(this.query);
                    status = true;
                    logs.append("\n===success===\n");
                }
            } catch (ClassNotFoundException ex) {
                logs.append(ex.getMessage());
            } catch (SQLException ex) {
                logs.append(ex.getMessage());
            } catch (IOException ex) {
                logs.append(ex.getMessage());
            }


            outputpath = new String(oppath);

        } else {
            logs.append("Missing input parameter");
        }
    }

    private boolean varify() {
        if (username == null || password == null || query == null) {
            return false;
        } else {
            return true;
        }
    }

    public void setParams(String uname, String passwd, String query) {
        this.username = uname;
        this.password = passwd;
        this.query = query;

    }

    public String getLog() {
        return logs.toString();
    }

    public String getFile() {
        return this.outputpath;
    }

    public boolean getStatus() {
        return this.status;
    }
}

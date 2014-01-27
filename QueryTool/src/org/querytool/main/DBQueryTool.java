/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.querytool.main;

import org.querytool.gui.DbSqlGui;



/**
 *
 * @author 367940
 */
public class DBQueryTool {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DbSqlGui().setVisible(true);
            }
        });
       
             
        
    }
}

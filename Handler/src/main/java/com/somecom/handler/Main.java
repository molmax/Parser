package com.somecom.handler;

import com.somecom.parser.*;
import com.somecom.dbc.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Cannon on 23.04.2016.
 * <p>Модуль отвечающий за работу приложения</p>
 */
public class Main {

    private static final String XLSX_FILE_PATH = "D:/project2.xlsx";
    private static final String INSERT = "INSERT INTO \"Requirements\" (\"Requirement\", \"Description\", \"Comments\", \"Requirement_Implemented\", \"Project\") VALUES(?,?,?,?,?);";

    public static void main(String[] args){
        List <Entry> entries = new ArrayList<Entry>();
        ParserImp parser = new ParserImp();

        try {
            parser.parseXLS(XLSX_FILE_PATH,entries);
        } catch (IOException e) {
            System.out.println("Faield to parse file!");
            e.printStackTrace();
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, e);
        }

        DBControllerImp controller = new DBControllerImp();
        Connection con = controller.getConnection();



         String requirement;
         String description;
         String comment;
         String result;
         String projectName;

        for (Entry entry : entries) {

            requirement = entry.getRequirement();
            description = entry.getDescription();
            comment = entry.getComment();
            result = entry.getResult();
            projectName = entry.getProjectName();

            try {
                PreparedStatement ps;
                ps = con.prepareStatement(INSERT);
                ps.setString(1, requirement);
                ps.setString(2, description);
                ps.setString(3, comment);
                ps.setString(4, result);
                ps.setString(5, projectName);
                ps.executeUpdate();
                System.out.println("Success!");

            } catch (SQLException e) {
                System.out.println("Unable to insert");
                e.printStackTrace();
            }
        }

        controller.executeQuery("SELECT * FROM \"Requirements\" ORDER BY \"Project\";");

        try {
            con.close();
            System.out.println("Connection closed");

        } catch (SQLException e) {
            System.out.println("Unable to close connection");
            e.printStackTrace();
        }

        controller.closeConnection();
    }
}




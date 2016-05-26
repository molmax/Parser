package com.somecom.dbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import com.somecom.parser.*;

/**
 * Created by Cannon on 16.04.2016.
 */
public class DBControllerImp implements DBController {

    public Connection connection = null;
    public Statement statement = null;
    public ResultSet rs = null;
    private static String URL;
    private static String NAME;
    private static String PASSWORD;

    /**
     * <p>Устанавливает соединение с базой данных</p>
     * *@return объект connection
     * * */
    public Connection getConnection() {

        Properties props = new Properties();

        try{
            props.load(new FileInputStream(new File("D:/testConfig.properties")));
        } catch (FileNotFoundException e){
            System.out.println("File not found!");
            e.printStackTrace();
        } catch (IOException e){
            System.out.println("Can not read file");
            e.printStackTrace();
        }

        URL = props.getProperty("url");
        NAME = props.getProperty("name");
        PASSWORD = props.getProperty("password");

        try {
            connection = DriverManager.getConnection(URL,NAME,PASSWORD);
            System.out.println("Connection established");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * <p>Закрывает statement если он не null</p>
     * */
    public void closeStatement() {
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Unable to close statement!");
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>Закрывает соединение с базой данных</p>
     * */
    public void closeConnection(){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Unable to close connection!");
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>Метод для создания таблицы в базе данных</p>
     * @param tableName Имя создаваемой таблицы
     * @param createStatement Передаваемая команда CREATE TABLE
     * */
    public void createTable(String tableName,String createStatement) {

        try{
            connection = getConnection();
            statement = connection.createStatement();
            statement.execute(createStatement);
            System.out.println("Table "+tableName+" created!");
        } catch (SQLException ex){
            System.out.println("Unable to create table"+tableName+"!");
            ex.printStackTrace();
        } finally {
            closeStatement();
            closeConnection();
        }
    }

    /**
     * <p>Метод для вставки записей в таблицу</p>
     * @param tableName Имя таблицы с которой работаем
     * @param insertStatement Передаваемая команда INSERT
     * */
    public void insertIntoTable(String tableName, String insertStatement) {

        connection = getConnection();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(insertStatement);
            System.out.println("Statement inserted into table "+tableName+"!");
        } catch (SQLException e) {
            System.out.println("Insert failed!");
            e.printStackTrace();
        }
        finally {
            closeStatement();
            closeConnection();
        }
    }

    /**
     * <p>Метод для обновления записей в таблице</p>
     * @param tableName Имя таблицы с которой работаем
     * @param updateStatement Передаваемая команда UPDATE
     * */
    public void updateTable(String tableName, String updateStatement) {

        connection = getConnection();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(updateStatement);
            System.out.println("Table "+tableName+" updated!");
        } catch (SQLException e) {
            System.out.println("Update failed!");
            e.printStackTrace();
        }
        finally {
            closeStatement();
            closeConnection();
        }
    }

    /**
     * <p>Метод для удаления записей из таблицы</p>
     * @param tableName Имя таблицы с которой работаем
     * @param deleteStatement Передаваемая команда DELETE
     * */
    public void deleteFromTable(String tableName, String deleteStatement) {

        connection = getConnection();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(deleteStatement);
            System.out.println("Delete from "+tableName+" executed!");
        } catch (SQLException e) {
            System.out.println("Delete failed!");
            e.printStackTrace();
        }
        finally {
            closeStatement();
            closeConnection();
        }
    }

    /**
     * <p>Метод для работы с командой SELECT</p>
     * <p>Производится выборка из таблицы в соответствии с переданным в качестве аргумента запросом</p>
     * @param query Запрос к базе данных на операцию выборки
     * @return Возвращает ResultSet, содержащий информацию по результатам запроса SELECT. Так же результат выборки выводится в консоль
     * */
    public ResultSet executeQuery(String query) {

        try {
            connection = getConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            rs = statement.executeQuery(query);
            while (rs.next()){
                String entryID = rs.getString("ID");
                String requirement = rs.getString("Requirement");
                String description = rs.getString("Description");
                String comments = rs.getString("Comments");
                String result = rs.getString("Requirement_Implemented");
                String project = rs.getString("Project");

                System.out.println(entryID +" | "+ requirement +" | "+ description +" | "+ comments +" | "+ result +" | "+ project);
            }
        } catch (SQLException e) {
            System.out.println("Unable to execute query!");
            e.printStackTrace();
        }
        finally {
            closeStatement();
            closeConnection();
        }
        return rs;
    }

    /**
     * <p>Метод, возвращаюшщий все записи из таблицы</p>
     * @return Возвращает ResultSet, содержащий все записи в текущей таблице
     * */
    public ResultSet getObjs(){
        String query = "SELECT * FROM  \"Requirements\"";
        try {

        try {
            connection = getConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
            connection.setAutoCommit(false);
        try {

            rs = statement.executeQuery(query);
            while (rs.next()){

                String entryID = rs.getString("ID");
                String requirement = rs.getString("Requirement");
                String description = rs.getString("Description");
                String comments = rs.getString("Comments");
                String result = rs.getString("Requirement_Implemented");
                String project = rs.getString("Project");

                System.out.println(entryID +" | "+ requirement +" | "+ description +" | "+ comments +" | "+ result +" | "+ project);
            }
        } catch (SQLException e) {
            connection.rollback();
            System.out.println("Unable to execute query!");
            e.printStackTrace();
        }
        finally {
            connection.commit();
            closeStatement();
            closeConnection();
        }
        } catch (SQLException e){
            System.out.println("Method failed!");
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * <p>Метод читает все записи из БД (для web)</p>
     * @param entries Пустая коллекция, которую метод заполняет инициализированными объектами
     * @return Возвращает коллекцию объектов, соответствующих записям в БД
     * */
    @Override
    public List<Entry> readAllFromDataBase(List <Entry> entries){

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = getConnection();
        try{
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM \"Requirements\"");

            dataBaseEntryInit(entries, rs);

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }

        finally {
            connection.commit();
            closeStatement();
            closeConnection();
        }
        } catch (SQLException e){
            System.out.println("Method failed!");
            e.printStackTrace();
        }
        return entries;
    }

    /**
     * <p>Метод читает записи из выбранных проектов в БД(для web)</p>
     * @param entries Пустая коллекция, которую метод заполняет инициализированными объектами
     * @param projectName Имя интересующего проекта
     * @return Возвращает коллекцию объектов, соответствующих записям в БД
     * */
    @Override
    public List<Entry> readFromDataBaseByProjectName (List <Entry> entries, String projectName) {

        final String QUERY = "SELECT * FROM \"Requirements\" WHERE \"Project\" = ?";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = getConnection();
        try{
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setString(1,projectName);
            rs = ps.executeQuery();

            dataBaseEntryInit(entries, rs);

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        finally {
            connection.commit();
            closeStatement();
            closeConnection();
        }
        } catch (SQLException e){
            System.out.println("Method failed!");
            e.printStackTrace();
        }
        return entries;
    }

    /**
     * <p>Метод возвращает записи из БД в соответствии с выбранными ключевыми словами(для web)</p>
     * @param entries Пустая коллекция, которую метод заполняет инициализированными объектами
     * @param requirement Интересующий параметр(требование)
     * @return Возвращает коллекцию объектов, соответствующих записям в БД
     * */
    @Override
    public List<Entry> readFromDataBaseByRequirement (List <Entry> entries, String requirement) {

        final String QUERY = "SELECT * FROM \"Requirements\" WHERE \"Requirement\" = ?";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = getConnection();
        try{
            try {
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(QUERY);
                ps.setString(1,requirement);
                rs = ps.executeQuery();

                dataBaseEntryInit(entries, rs);

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
            finally {
                connection.commit();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException e){
            System.out.println("Method failed!");
            e.printStackTrace();
        }
        return entries;
    }

    /**
     * <p>Метод возвращает записи из БД в соответствии с выбранными ключевыми словами и именем проекта(для web)</p>
     * @param entries Пустая коллекция, которую метод заполняет инициализированными объектами
     * @param requirement Интересующий параметр(требование)
     * @param projectName Имя интересующего проекта
     * @return Возвращает коллекцию объектов, соответствующих записям в БД
     * */
    @Override
    public List<Entry> readFromDataBaseByProjectNameAndRequirement (List <Entry> entries, String projectName, String requirement) {

        final String QUERY = "SELECT * FROM \"Requirements\" WHERE \"Project\" = ? AND\"Requirement\" = ?";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = getConnection();
        try{
            try {
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(QUERY);
                ps.setString(1,projectName);
                ps.setString(2,requirement);
                rs = ps.executeQuery();

                dataBaseEntryInit(entries, rs);

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
            finally {
                connection.commit();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException e){
            System.out.println("Method failed!");
            e.printStackTrace();
        }
        return entries;
    }

    /**
     * <p>В методе инициализируются поля объекта данными из БД. Для использования в других методах чтения из БД</p>
     * @param entries Пустая коллекция, которую метод заполняет инициализированными объектами
     * @param rs Result Set для работы с объектами БД
     * @return Возвращает коллекцию объектов, соответствующих записям в БД
     * */
    protected static List<Entry> dataBaseEntryInit(List <Entry> entries, ResultSet rs) throws SQLException {

        while (rs.next()){
            Entry entry = new Entry();
            entry.setRequirement(rs.getString("Requirement"));
            entry.setDescription(rs.getString("Description"));
            entry.setComment(rs.getString("Comments"));
            entry.setResult(rs.getString("Requirement_Implemented"));
            entry.setProjectName(rs.getString("Project"));
            entries.add(entry);
        }
        return entries;
    }
}



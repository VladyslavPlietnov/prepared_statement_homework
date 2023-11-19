package org.example.database;

import org.example.database.Database;

import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseInitService {

    public static final String INIT_WORKER="CREATE TABLE worker (\n" +
            "    id INT PRIMARY KEY,\n" +
            "    NAME VARCHAR(?) NOT NULL,\n" +
            "    CHECK (NAME LIKE '__%'),\n" +
            "    BIRTHDAY DATE, \n" +
            "    CHECK (BIRTHDAY >= ?),\n" +
            "    LEVEL VARCHAR(?),\n" +
            "    CHECK(LEVEL = ? \n" +
            "    OR LEVEL = ? \n" +
            "    OR LEVEL = ? \n" +
            "    OR LEVEL = ?),\n" +
            "    SALARY INT,\n" +
            "    CHECK (SALARY >=? AND SALARY <=?)\n" +
            ")";
    public static final String INIT_CLIENT ="CREATE TABLE client (\n" +
            "    id INT PRIMARY KEY,\n" +
            "    NAME VARCHAR(?) NOT NULL,\n" +
            "    CHECK (NAME LIKE '__%')\n" +
            ")";
    public static final String INIT_PROJECT = "CREATE TABLE project (\n" +
            "    ID INT PRIMARY KEY,\n" +
            "    CLIENT_ID INT,\n" +
            "    FOREIGN KEY(CLIENT_ID) REFERENCES client1(id) ON DELETE CASCADE,\n" +
            "    START_DATE DATE,\n" +
            "    FINISH_DATE DATE\n" +
            ")";
    public static final String INIT_PROJECT_WORKER = "CREATE TABLE project_worker (\n" +
            "    PROJECT_ID INT,\n" +
            "    WORKER_ID INT,\n" +
            "    PRIMARY KEY(PROJECT_ID,WORKER_ID),\n" +
            "    FOREIGN KEY(PROJECT_ID) REFERENCES project1(ID), \n" +
            "    FOREIGN KEY(WORKER_ID) REFERENCES worker1(id)\n" +
            ")";
    public static void main(String[] args) {


        System.out.println(initWorker());
        System.out.println(initClient());
        System.out.println(initProject());
        System.out.println(initProjectWorker());


        Database.closeConnection();

        }
    public static int initWorker(){
        try{
            PreparedStatement statement = Database.getConnection().prepareStatement(INIT_WORKER);
            statement.setInt(1,1000);
            statement.setString(2,"1901.01.01");
            statement.setInt(3,10);
            statement.setString(4,"Trainee");
            statement.setString(5,"Junior");
            statement.setString(6,"Middle");
            statement.setString(7,"Senior");
            statement.setInt(8,100);
            statement.setInt(9,100000);
            return Database.getInstance().executeUpdate(statement);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static int initClient(){
        try{
            PreparedStatement statement = Database.getConnection().prepareStatement(INIT_CLIENT);
            statement.setInt(1,1000);
            return Database.getInstance().executeUpdate(statement);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return -1;
    }
    public static int initProject(){
        try{
            PreparedStatement statement = Database.getConnection().prepareStatement(INIT_PROJECT);

            return Database.getInstance().executeUpdate(statement);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return -1;
    }
    public static int initProjectWorker(){
        try{
            PreparedStatement statement = Database.getConnection().prepareStatement(INIT_PROJECT_WORKER);

            return Database.getInstance().executeUpdate(statement);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return -1;
    }
    public static void dropTables(){
        try {
            PreparedStatement statement1 = Database.getConnection().prepareStatement("DROP TABLE project_worker");
            PreparedStatement statement2 = Database.getConnection().prepareStatement("DROP TABLE project");
            PreparedStatement statement3 = Database.getConnection().prepareStatement("DROP TABLE worker");

            PreparedStatement statement4 = Database.getConnection().prepareStatement("DROP TABLE client");

            Database.getInstance().executeUpdate(statement1);
            Database.getInstance().executeUpdate(statement2);
            Database.getInstance().executeUpdate(statement3);

            Database.getInstance().executeUpdate(statement4);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    }


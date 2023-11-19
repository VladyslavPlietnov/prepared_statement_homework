package org.example.database;
import org.example.objects.PropertiesReader;

import java.sql.*;

public class Database {
    private static final Database INSTANCE = new Database();
    private static Connection connection;

    private Database(){
        String url = PropertiesReader.getConnectionURLForMySql();
        String name = PropertiesReader.getDatabaseUser();
        String password = PropertiesReader.getDatabasePassword();

        try{
            connection = DriverManager.getConnection(url, name, password);
        }catch(SQLException e){
            System.out.println("Exception. Reason:" + e.getMessage());
            throw new RuntimeException("Cannot create connection");
        }
    }

    public static Database getInstance(){
        return INSTANCE;
    }
    public static Connection getConnection(){
        return connection;
    }

    public ResultSet executeResult(PreparedStatement statement){
        try{
            return statement.executeQuery();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int executeUpdate(PreparedStatement statement){
        try{
            return statement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static void closeConnection(){
        try{
            connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}

package org.example.database;
import org.example.objects.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseQueryService {
    public static ArrayList<LongestProject> findLongestProject(){
        ArrayList<LongestProject> outcome = new ArrayList<>();


            String query = "SELECT ID, DATEDIFF(FINISH_DATE, START_DATE)/30 AS MONTHS\n" +
                    "FROM project\n" +
                    "WHERE DATEDIFF(FINISH_DATE, START_DATE) = (\n" +
                    "    SELECT MAX(DIFF)\n" +
                    "    FROM(\n" +
                    "        SELECT DATEDIFF(FINISH_DATE, START_DATE) AS DIFF, ID\n" +
                    "        FROM project\n" +
                    "        GROUP BY ID\n" +
                    "    ) AS T\n" +
                    ")";
        try{
            PreparedStatement statement = Database.getConnection().prepareStatement(query);

            ResultSet result = Database.getInstance().executeResult(statement);
            while (result.next()){
                outcome.add(new LongestProject(result.getInt("ID"),result.getDouble("MONTHS")));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return outcome;
    }

    public static ArrayList<MaxProjectsClient> findMaxProjectsClient(){
        ArrayList<MaxProjectsClient> outcome = new ArrayList<>();


            String query = "SELECT *\n" +
                    "FROM client\n" +
                    "WHERE (\n" +
                    "    SELECT COUNT(CLIENT_ID)\n" +
                    "    FROM project\n" +
                    "    WHERE client.id = project.CLIENT_ID\n" +
                    "    ) = (\n" +
                    "       SELECT MAX(COUNTING)\n" +
                    "FROM( \n" +
                    "    SELECT COUNT(CLIENT_ID) AS COUNTING, CLIENT_ID\n" +
                    "    FROM project\n" +
                    "    GROUP BY CLIENT_ID\n" +
                    ") AS T \n" +
                    "    )";
            try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);

            ResultSet result = Database.getInstance().executeResult(statement);

                while (result.next()){
                    outcome.add(new MaxProjectsClient(result.getInt("id"),result.getString("NAME")));
                }
            }catch (SQLException e){
                System.out.println("reason: " + e.getMessage());
            }


        return outcome;
    }
    public static ArrayList<MaxSalaryWorker> findMaxSalaryWorker(){
        ArrayList<MaxSalaryWorker> outcome = new ArrayList<>();

            String query = "SELECT * FROM worker \n" +
                    "WHERE SALARY = (SELECT MAX(SALARY)\n" +
                    "FROM worker)";
            try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);

            ResultSet result = Database.getInstance().executeResult(statement);

                while (result.next()){
                    outcome.add(new MaxSalaryWorker(result.getString("NAME"),result.getInt("SALARY")));
                }
            }catch (SQLException e){
                System.out.println("reason: " + e.getMessage());
            }

        return outcome;
    }
    public static ArrayList<YoungestEldestWorker> findYoungestEldestWorkers(String parameter1, String parameter2){
        ArrayList<YoungestEldestWorker> outcome = new ArrayList<>();

            String query = "SELECT NAME, BIRTHDAY, (CASE WHEN (BIRTHDAY = (\n" +
                    "      SELECT MAX(BIRTHDAY)\n" +
                    "      FROM worker\n" +
                    "      )) THEN ?\n" +
                    "        WHEN (BIRTHDAY = (\n" +
                    "      SELECT MIN(BIRTHDAY)\n" +
                    "      FROM worker\n" +
                    "      )) THEN ?\n" +
                    "   END) AS TYPE\n" +
                    "FROM worker\n" +
                    "WHERE BIRTHDAY = (\n" +
                    "    SELECT MIN(BIRTHDAY)\n" +
                    "    FROM worker\n" +
                    ") OR BIRTHDAY = (SELECT MAX(BIRTHDAY)\n" +
                    "    FROM worker);";

            try {
                PreparedStatement statement = Database.getConnection().prepareStatement(query);
                statement.setString(1, parameter1);
                statement.setString(2, parameter2);

                ResultSet result = Database.getInstance().executeResult(statement);

                while (result.next()){
                    outcome.add(new YoungestEldestWorker(result.getString("NAME"),result.getString("BIRTHDAY"),result.getString("TYPE")));
                }
            }catch (SQLException e){
                System.out.println("reason: " + e.getMessage());
            }


        return outcome;
    }
    public static ArrayList<ProjectPrice> printProjectPrices(){
        ArrayList<ProjectPrice> outcome = new ArrayList<>();

            String query = "SELECT ID AS NAME, ( \n" +
                    "    SELECT SUM(SALARY) \n" +
                    "    FROM worker\n" +
                    "    WHERE id = (\n" +
                    "        SELECT WORKER_ID\n" +
                    "        FROM project_worker\n" +
                    "        WHERE project_worker.PROJECT_ID = project.ID AND WORKER_ID = id\n" +
                    "    )\n" +
                    "    )* DATEDIFF(FINISH_DATE,START_DATE)/30 AS PRICE \n" +
                    "FROM project;";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);

            ResultSet result = Database.getInstance().executeResult(statement);

                while (result.next()){
                    outcome.add(new ProjectPrice(result.getInt("NAME"),result.getDouble("PRICE")));
                }
            }catch (SQLException e){
                System.out.println("reason: " + e.getMessage());
            }

        return outcome;
    }


    public static void main(String[] args) {
        findLongestProject().forEach(a -> System.out.println(a.toString()));
        System.out.println("\n");
        findMaxProjectsClient().forEach(a -> System.out.println(a.toString()));
        System.out.println("\n");
        findMaxSalaryWorker().forEach(a -> System.out.println(a.toString()));
        System.out.println("\n");
        findYoungestEldestWorkers("Youngest", "Eldest").forEach(a -> System.out.println(a.toString()));
        System.out.println("\n");
        printProjectPrices().forEach(a -> System.out.println(a.toString()));
        Database.closeConnection();
    }
}

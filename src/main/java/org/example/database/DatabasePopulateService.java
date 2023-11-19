package org.example.database;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.example.database.Database;
import org.example.objects.Client;
import org.example.objects.Project;
import org.example.objects.ProjectWorker;
import org.example.objects.Worker;

import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabasePopulateService {
    public static final String INSERT_STATEMENT_WORKER = "INSERT INTO worker VALUES(?,?,?,?,?)";
    public static final String INSERT_STATEMENT_CLIENT = "INSERT INTO client VALUES(?,?)";
    public static final String INSERT_STATEMENT_PROJECT = "INSERT INTO project VALUES(?,?,?,?)";
    public static final String INSERT_STATEMENT_PROJECT_WORKER = "INSERT INTO project_worker VALUES(?,?)";
    public static void main(String[] args) {
        populateWorker();

        populateClient();

        populateProject();

        populateProjectWorker();

        Database.closeConnection();
    }

    public static void populateWorker(){
          try(FileReader reader = new FileReader("worker1.txt")){

              char[] buff = new char[5000];
              reader.read(buff);
              String json = String.valueOf(buff);
              json = json.trim();
              json = json.substring(0,json.length()-1);

              Gson gson = new Gson();
              String[] workersJson = json.split(";");


              try{
                  PreparedStatement statement = Database.getConnection().prepareStatement(INSERT_STATEMENT_WORKER);
              for(String workerJson:workersJson){

                  Worker worker = gson.fromJson(workerJson, Worker.class);

                  statement.setString(1, worker.id);

                  statement.setString(2, worker.name);

                  statement.setString(3, worker.birthday);
                      statement.setString(4, worker.level);
                      statement.setInt(5, worker.salary);
                      statement.addBatch();

              }

                  statement.executeBatch();
              }catch(SQLException e){
                  System.out.println("Something wrong with statement, worker. " + e.getMessage());
              }

          }catch(IOException e ){
              System.out.println(" Problem with file reader" + e.getMessage());
          }
    }

    public static void populateClient(){
        try(FileReader reader = new FileReader("client1.txt")){

            char[] buff = new char[5000];
            reader.read(buff);
            String json = String.valueOf(buff);
            json = json.trim();
            json = json.substring(0,json.length()-1);

            Gson gson = new Gson();
            String[] clientsJson = json.split(";");
            try{
                PreparedStatement statement = Database.getConnection().prepareStatement(INSERT_STATEMENT_CLIENT);
                for(String clientJson:clientsJson){
                    Client client = gson.fromJson(clientJson, Client.class);


                    statement.setInt(1, client.id);
                    statement.setString(2, client.name);
                    statement.addBatch();

                }

                statement.executeBatch();
            }catch(SQLException e){
                System.out.println("Something wrong with statement, client." + e.getMessage());
            }

        }catch(IOException e ){
            System.out.println(" Problem with file reader" + e.getMessage());
        }
    }

    public static void populateProject(){
        try(FileReader reader = new FileReader("project1.txt")){

            char[] buff = new char[5000];
            reader.read(buff);
            String json = String.valueOf(buff);
            json = json.trim();
            json = json.substring(0,json.length()-1);

            Gson gson = new Gson();
            String[] projectsJson = json.split(";");
            try{
                PreparedStatement statement = Database.getConnection().prepareStatement(INSERT_STATEMENT_PROJECT);
                for(String projectJson:projectsJson){
                    Project project = gson.fromJson(projectJson, Project.class);


                    statement.setInt(1, project.id);
                    statement.setInt(2, project.clientId);
                    statement.setString(3, project.startDate);
                    statement.setString(4, project.finishDate);
                    statement.addBatch();

                }

                statement.executeBatch();
            }catch(SQLException e){
                System.out.println("Something wrong with statement, project. " + e.getMessage());
            }

        }catch(IOException e ){
            System.out.println(" Problem with file reader" + e.getMessage());
        }
    }

    public static void populateProjectWorker(){
        try(FileReader reader = new FileReader("project_worker1.txt")){

            char[] buff = new char[5000];
            reader.read(buff);
            String json = String.valueOf(buff);
            json = json.trim();
            json = json.substring(0,json.length()-1);

            Gson gson = new Gson();
            String[] projectWorkersJson = json.split(";");
            try{
                PreparedStatement statement = Database.getConnection().prepareStatement(INSERT_STATEMENT_PROJECT_WORKER);
                for(String projectWorkerJson:projectWorkersJson){
                    ProjectWorker projectWorker = gson.fromJson(projectWorkerJson, ProjectWorker.class);


                    statement.setInt(1, projectWorker.projectId);
                    statement.setInt(2, projectWorker.workerId);
                    statement.addBatch();

                }

                statement.executeBatch();
            }catch(SQLException e){
                System.out.println("Something wrong with statement, project_worker. " + e.getMessage());
            }

        }catch(IOException e ){
            System.out.println(" Problem with file reader" + e.getMessage());
        }
    }
}

package org.example.objects;

public class MaxProjectsClient {
    public int id;
    public String name;
    public MaxProjectsClient(int id, String name){
        this.id = id;
        this.name = name;
    }
    @Override
    public String toString(){
        return "id: " +id + ";" + "name: " + name + ";";
    }
}

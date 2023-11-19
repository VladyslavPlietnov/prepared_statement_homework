package org.example.objects;

public class LongestProject {
    public int id;
    public double months;
    public LongestProject(int id, double months){
        this.id = id;
        this.months = months;
    }
    @Override
    public String toString(){
        return "id: "+id +";" + "months: " + months+ ";";
    }
}

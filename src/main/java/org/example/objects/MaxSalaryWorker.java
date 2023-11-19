package org.example.objects;

public class MaxSalaryWorker {
    public String name;
    public int salary;
    public MaxSalaryWorker(String name, int salary){
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString(){
        return "name: " +name + ";" + "salary: " + salary + ";";
    }
}

package org.example.objects;

public class Project {
    public int id, clientId;
    public String startDate, finishDate;

    public Project(int id, int clientId, String startDate, String finishDate) {
        this.id = id;
        this.clientId = clientId;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }
}

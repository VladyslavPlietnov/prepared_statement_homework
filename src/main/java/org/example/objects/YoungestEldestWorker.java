package org.example.objects;

public class YoungestEldestWorker {
    public String name;
    public String birthday;
    public String type;

    public YoungestEldestWorker(String name, String birthday, String type) {
        this.name = name;
        this.birthday = birthday;
        this.type = type;
    }

    @Override
    public String toString() {
        return "YoungestEldestWorker{" +
                "name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

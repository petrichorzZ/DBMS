package myDatabase;

import java.io.Serializable;

public class Attribute implements Serializable{
    String name;

    public Attribute() {
    }

    public Attribute(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

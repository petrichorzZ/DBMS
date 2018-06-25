package myDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{
    private String name;
    private String passWord;
    private List<Database> databases;

    public User() {
        name = null;
        passWord = null;
        databases = new ArrayList<>();
    }

    public User(String name,String passWord) {
        this();
        this.name = name;
        this.passWord = passWord;
    }

    public User(String name, String passWord, List<Database> databases) {
        this(name,passWord);
        this.databases = databases;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public List<Database> getDatabases() {
        return databases;
    }

    public void setDatabases(List<Database> databases) {
        this.databases = databases;
    }

    public Database getDatabase(String name){
        for(Database database:databases){
            if(database.getName().equals(name)){
                return database;
            }
        }
        return null;
    }

    public void addDatabase(Database database){
        databases.add(database);
    }

    public boolean isDatabaseExist(String name){
        for(Database database:databases){
            if(database.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public void deleteDatabase(String name){
        for(Database database:databases){
            if(database.getName().equals(name)){
                databases.remove(database);
                break;
            }
        }
    }
}

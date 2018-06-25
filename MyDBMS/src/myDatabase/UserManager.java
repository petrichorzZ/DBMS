package myDatabase;

import SQL.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import static test.FrameTest.userManager;

public class UserManager implements Serializable{
    private  String path;
    private  List<User> users;
    private  String currentDatabase;
    private  String currentTable;
    private  String currentUser;

    public UserManager() {
        User user = new User("admin","admin");
        users = new ArrayList<>();
        users.add(user);
        currentUser = "admin";
    }

    public  String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public  List<User> getUsers() {
        return users;
    }

    public  void setUsers(List<User> users) {
        this.users = users;
    }

    public String getCurrentDatabase() {
        return currentDatabase;
    }

    public void setCurrentDatabase(String currentDatabase) {
        this.currentDatabase = currentDatabase;
    }

    public String getCurrentTable() {
        return currentTable;
    }

    public void setCurrentTable(String currentTable) {
        this.currentTable = currentTable;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public  boolean isUserExist(String name, String pwd){
        for(User user:users){
            if(user.getName().equals(name)&&user.getPassWord().equals(pwd)){
                return true;
            }
        }
        return false;
    }

    public  void addUser(User user){
        users.add(user);
    }

    public User getUser(String name){
        for(User user:users){
            if(user.getName().equals(name)){
                return user;
            }
        }

        return null;
    }

    public boolean isDatabaseExist(String name){
        return getUser(currentUser).isDatabaseExist(name);
    }

    public void addDatabase(Database database){
        getUser(currentUser).addDatabase(database);
    }

    public Database getDatabase(String name){
        return getUser(currentUser).getDatabase(name);
    }

    public boolean isTableExist(String name){
        return getUser(currentUser).getDatabase(currentDatabase).isTableExist(name);
    }

    public void addTable(Table table){
       getDatabase(currentDatabase).addTable(table);
    }

    public Table getTable(String name){
        return getDatabase(currentDatabase).getTable(name);
    }

    public void deleteDatabase(String name){
        getUser(currentUser).deleteDatabase(name);
    }

    public void deleteTable(String name){
        getDatabase(currentDatabase).deleteTable(name);
    }

    public boolean isColumnExist(String name){
         return getTable(currentTable).isColumnExist(name);
    }

    public void addColumn(Column column){
        getTable(currentTable).addColumn(column);
    }

    public void deleteColumn(String name){
        getTable(currentTable).deleteColumn(name);
    }

    public Column getColumn(String name) {
        return getTable(currentTable).getColumn(name);
    }

    public int getTableSize(){
        return getTable(currentTable).getLines().size();
    }

    public boolean LoginCheck(String name,String password){
        if(isUserExist(name,password)){
            return true;
        }
        return false;
    }
}

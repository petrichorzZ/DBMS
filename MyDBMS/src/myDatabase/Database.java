package myDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable{
    private String name;
    private List<Table> tables;
    private transient List<View> views;
    public Database(){
        this.name = null;
        this.tables  = new ArrayList<>();
        this.views = new ArrayList<>();
    }

    public Database(String name) {
        this();
        this.name = name;
    }

    public Database(String name, List<Table> tables, List<View> views) {
        this();
        this.tables = tables;
        this.views = views;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<View> getViews() {
        return views;
    }

    public void setViews(List<View> views) {
        this.views = views;
    }

    public void addTable(Table table){
        tables.add(table);
    }

    public Table getTable(String name){
        for(Table table:tables){
            if(table.getName().equals(name)){
                return table;
            }
        }
        return null;
    }

    public boolean isTableExist(String name){
        for(Table table:tables){
            if(table.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public void deleteTable(String name){
        for(Table table:tables){
            if(table.getName().equals(name)){
                tables.remove(table);
                break;
            }
        }
    }
}

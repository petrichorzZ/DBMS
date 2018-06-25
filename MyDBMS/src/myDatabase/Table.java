package myDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Table implements Serializable{
    private String name;
    private List<Column> columns;//字段
    private List<Line> lines;
    public Table() {
        this.name = null;
        this.columns = new ArrayList<>();
        this.lines = new ArrayList<>();
    }

    public Table(String name) {
        this();
        this.name = name;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public Table(String name, List<Column> columns) {
        this.name = name;

        this.columns = columns;
    }

    public Table(String name, List<Column> columns, List<Line> lines) {
        this.name = name;
        this.columns = columns;
        this.lines = lines;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public void addColumn(Column column){
        columns.add(column);
    }

    public void addLine(Line line){
        lines.add(line);
    }

    public boolean isColumnExist(String name){
        for(Column column:columns){
            if(column.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public void deleteColumn(String name){
        for(Column column:columns){
            if(column.getName().equals(name)){
                columns.remove(column);
                break;
            }
        }
    }

    public Column getColumn(String name){
        for(Column column:columns){
            if(column.getName().equals(name)){
                return column;
            }
        }
        return null;
    }

    public void setColumn(String column,String value,int row){
        int col;
        for(int i =0;i<columns.size();i++){
            if(columns.get(i).getName().equals(column)){
                col = i;
                lines.get(row).getDatas().set(col,value);
                break;
            }
        }

    }
//    public String toString(){
//        for(Column column:columns){
//            System.out.print(column.getName()+"     ");
//        }
//        System.out.println();
// //       showDatas();
//        return null;
////        StringBuffer stringBuffer = new StringBuffer();
////        for(Column column:columns){
////            stringBuffer.append(column.getName()+"\t");
////        }
////        return stringBuffer.toString();
//    }

//    public void showDatas(){
//        for(Line line:lines){
//            System.out.println(line.toString());
//        }
//    }

}

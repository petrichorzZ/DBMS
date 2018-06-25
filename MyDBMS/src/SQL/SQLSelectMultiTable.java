package SQL;

import myDatabase.Column;
import myDatabase.Line;
import myDatabase.Table;

import java.util.*;

import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class SQLSelectMultiTable extends SQL {
    private String[] columns ;
    private String[][] tableColumns;
    private String[] tables;
    private String[][] columnValues;
    private String[] conditions;
    private String[][]conditonTableColumn;
    private Map<String,String> map = new HashMap<>();
    private Map<String,Integer>count = new HashMap<>();
    public SQLSelectMultiTable(int SQLType, String string) {
        super(SQLType);
        if(isSQLLegal(string)){
            if(userManager.getCurrentDatabase()!=null){
                boolean allTable = true;
                for(int i=0;i<tables.length;i++){
                    if(!userManager.isTableExist(tables[i].trim())){
                        allTable  = false;
                        break;
                    }
                }
                boolean allColumn =true;
                for(int i=0;i<tableColumns.length;i++){
                    tableColumns[i][0] =tableColumns[i][0].trim();
                    tableColumns[i][1] =tableColumns[i][1].trim();
                    map.put(tableColumns[i][1],tableColumns[i][0]);
                    userManager.setCurrentTable(tableColumns[i][0]);
                    if(!userManager.isColumnExist(tableColumns[i][1])){
                        allColumn = false;
                        break;
                    }
                }
                boolean allConditions = true;

                if(allTable&&allColumn){
                    List<Integer> colNum = new ArrayList<>();
                    for(int i=0;i<tableColumns.length;i++){
                        colNum.add(Util.getColNum(tableColumns[i][1],tableColumns[i][0]));
                    }
                    List<Integer> conditonColNum = new ArrayList<>();
//                  String[]conditonColNum = new String[conditonTableColumn.length];
                    for(int i=0;i<conditonTableColumn.length;i++){
                        conditonTableColumn[i][0] = conditonTableColumn[i][0].trim();
                        conditonTableColumn[i][1] = conditonTableColumn[i][1].trim();
                        conditonColNum.add(Util.getColNum(conditonTableColumn[i][1],conditonTableColumn[i][0]));
                    }

                    for(String key:map.keySet()){
                        String value  = map.get(key);
                        if(count.containsKey(value)){
                            int t = (count.get(value)) + 1;
                            count.put(value,t);
                        }else{
                            count.put(value,1);
                        }
                    }
                    Table table =userManager.getTable(tableColumns[0][0]);//新表
                    List<Line> lines = new ArrayList<>();//所有数据
                    int len = count.get(tableColumns[0][0]);
                    int size = userManager.getTable(tableColumns[0][0]).getLines().size();
                    for(int i=0;i<size;i++){
                        Line line = new Line();
                        for(int j=0;j<len;j++){
                            line.addData(userManager.getTable(tableColumns[0][0]).getLines().get(i).getDatas().get(colNum.get(j)));
                        }
                        lines.add(line);
                    }


                    for(int i=1;i<tableColumns.length-len;i++){
                        Table t =  userManager.getTable(tableColumns[i][0]);
                        Map<String,String> conditon = new HashMap<>();
                        for(int j=0;j<t.getLines().size();j++){
                            String key = t.getLines().get(j).getDatas().get(conditonColNum.get(i*2-1));
                            String value = t.getLines().get(j).getDatas().get(colNum.get(i));
                            conditon.put(key,value);
                        }

                        for(int j=0;j<size;j++){
                            String key=table.getLines().get(j).getDatas().get(conditonColNum.get((i-1)*2));
//                            String key=table.getLines().get(j).getDatas().get(Util.getColNum(conditonTableColumn[(i-1)*2][1],conditonTableColumn[(i-1)*2][0]));
                            lines.get(j).addData(conditon.get(key));
                        }
                    }

                    for(int i = 0;i<size;i++){
                        System.out.println(lines.get(i).toString());
                    }


                }else{
                    cacheOutput = "所查寻的表或字段不全存在";
                    System.out.println("所查寻的表或字段不全存在");
                }
            }else{

            }
        }else{

        }
    }

    @Override
    public boolean isSQLLegal(String string) {

        int count = Util.CharacterCount(string,' ');
        if(count>=5){
            int i,j,k;
            i = string.indexOf("select");
            j = string.indexOf("from");
            k = string.indexOf("where");
            String column = string.substring(i+6,j).trim();
            String table = string.substring(j+4,k).trim();
            String condition = string.substring(k+5,string.length()).trim();
            columns = column.split(",");
            tableColumns = new String[columns.length][2];
            for(i=0;i<columns.length;i++){
                tableColumns[i] = columns[i].split("\\.");
            }
            tables = table.split(",");
            conditions = condition.split("and");
            columnValues = new String[conditions.length][];
            for(i=0;i<conditions.length;i++){
                columnValues[i] = conditions[i].split("=");
            }
            conditonTableColumn = new String[columnValues.length*2][];
            for(i=0,j=0;i<columnValues.length;i++,j+=2){
                conditonTableColumn[j] = columnValues[i][0].split("\\.");
                conditonTableColumn[j+1] = columnValues[i][1].split("\\.");
            }
            return true;
        }
        return false;
    }
}

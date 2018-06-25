package SQL;

import myDatabase.Line;
import java.util.ArrayList;
import java.util.List;
import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class SQLSelectColumn extends SQL {
    private String []columns;
    private int []indexs;
    private String conditionValue;
    private String conditionColumn;
    private String operator;
    private String tableName =null;
    private List<Integer> lineNum = new ArrayList<>();
    public SQLSelectColumn(int SQLType,String string){
        super(SQLType);
        if(isSQLLegal(string)){
            if(userManager.getCurrentDatabase()!=null){
                if(this.tableName==null){
                    this.tableName = Util.getLastFirstWord(string);
                }else {
                    if(userManager.isColumnExist(conditionColumn)){
                        lineNum = Util.conditonHandle(tableName,conditionColumn,conditionValue,operator);
                    }else {
                        cacheOutput = "条件字段不存在";
                        System.out.println(cacheOutput);
                    }
                }
                userManager.setCurrentTable(tableName);
                if(userManager.isTableExist(tableName)){
                    boolean allColumn  =true;
                    int index =0;
                    for(String columnName:columns){
                        columnName = columnName.trim();
                        if(userManager.isColumnExist(columnName)){
                            for(int i = 0;i<userManager.getTable(tableName).getColumns().size();i++){
                                if(userManager.getTable(tableName).getColumns().get(i).getName().equals(columnName)){
                                    indexs[index] = i;
                                }
                            }
                        }else{
                            allColumn = false;
                        }
                        index++;
                    }
                    if(allColumn){
                       if(lineNum.size()==0){
                           for(int i=0;i<userManager.getTable(tableName).getLines().size();i++){
                               lineNum.add(i);
                           }
                       }
                        for(int l:lineNum){
                            Line line = userManager.getTable(tableName).getLines().get(l);
                            for( int i=0;i<indexs.length;i++){
                                cacheOutput +=line.getDatas().get(indexs[i])+"   ";
                                System.out.print(line.getDatas().get(indexs[i])+"   ");
                            }
                            cacheOutput+="\r\n";
                            System.out.println();
                        }
                    }else {
                        cacheOutput = "你所查询的表不全部包含你所查找的列";
                        System.out.println("你所查询的表不全部包含你所查找的列");
                    }
                }else {
                    cacheOutput = "你所要查看的表不存在";
                    System.out.println("你所要查看的表不存在");
                }
            }else {
                cacheOutput = "你还没有使用数据库，无法查看表的内容";
                System.out.println("你还没有使用数据库，无法查看表的内容");
            }
        }else {
            cacheOutput = "你所输入的SQL语句不合法";
            System.out.println("你所输入的SQL语句不合法");
        }
    }
    @Override
    public boolean isSQLLegal(String string) {
        int count = Util.CharacterCount(string,' ');
        if(count>=3){
            int i = string.indexOf(" ");
            int j = string.toLowerCase().indexOf("from");
            String sub = string.substring(i+1,j).trim();
            columns = sub.split(",");
            indexs = new int[columns.length];
            if(string.toLowerCase().contains("where")){
                i = string.toLowerCase().indexOf("where");
                this.tableName = string.substring(j+4,i).trim();
                String condition = string.substring(i+5);
                for(i=0;i<condition.length();i++){
                    if(Util.isDigit(condition.charAt(i))||Util.isLetter(condition.charAt(i))||condition.charAt(i)=='_'||condition.charAt(i)==' '){
                        continue;
                    }else{
                        break;
                    }
                }
                conditionColumn = condition.substring(0,i).trim();

                for(j=i;j<condition.length();j++){
                    if(condition.charAt(j)=='>'||condition.charAt(j)=='<'||condition.charAt(j)=='='||condition.charAt(j)=='!'){
                        continue;
                    }else {
                        break;
                    }
                }
                this.operator = condition.substring(i,j).trim();
                this.conditionValue = condition.substring(j,condition.length()).trim();

            }
            return true;
        }

        return false;
    }
}

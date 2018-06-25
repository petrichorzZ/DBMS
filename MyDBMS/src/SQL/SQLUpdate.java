package SQL;

import java.util.ArrayList;
import java.util.List;

import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class SQLUpdate extends SQL {
    private String tableName;
    private String []updateColumns;
    private String condition;
    private String []conditionColumns;
    private String []columnNames;
    private String [] columnValues;
    private List<Integer> lineNum = new ArrayList<>();
    private List<Integer> colNum = new ArrayList<>();
    private String operator;
    public SQLUpdate(int SQLType, String string) {
        super(SQLType);
        if(isSQLLegal(string)){
            if(userManager.getCurrentDatabase()!=null){
                if(userManager.isTableExist(tableName)){
                   userManager.setCurrentTable(tableName);
                   if(userManager.isColumnExist(conditionColumns[0])){
                       boolean allColumn = true;
                       for(int i=0;i<updateColumns.length;i++){
                           if(!userManager.isColumnExist(columnNames[i])){
                               allColumn =false;
                               break;
                           }
                       }
                       if(allColumn){
                           lineNum = Util.conditonHandle(tableName,conditionColumns[0],conditionColumns[1],operator);
                           colNum = Util.getRowNum(columnNames,tableName);
                           for(int index:lineNum){
                               for(int i=0;i<columnNames.length;i++){
//                                   userManager.getTable(tableName).setColumn(columnNames[i], columnValues[i],index);
                                   Util.setColumn(tableName,columnValues[i],index,colNum.get(i));
                               }
                           }
                           cacheOutput = "修改数据成功   "+lineNum.size()+"条数据改动";
                           System.out.println("修改数据成功   "+lineNum.size()+"条数据改动");
                       }else{
                           cacheOutput = "你所要修改的字段不全存在";
                           System.out.println("你所要修改的字段不全存在");
                       }
                   }else{
                       cacheOutput = "条件字段不存在";
                       System.out.println("条件字段不存在");
                   }
                }else{
                    cacheOutput = "你所要修改的表不存在";
                    System.out.println("你所要修改的表不存在");
                }
            }else{
                cacheOutput = "你还没有使用数据库，无法修改表的内容";
                System.out.println("你还没有使用数据库，无法修改表的内容");
            }
        }else{
            cacheOutput = "你所输入的SQL语句不合法";
            System.out.println("你所输入的SQL语句不合法");
        }
    }

    @Override
    public boolean isSQLLegal(String string) {
        int count  = Util.CharacterCount(string,' ');
        if(count>=5){
            this.tableName = Util.getSecondWord(string);
            int j = string.toLowerCase().indexOf("where");
            int i = string.toLowerCase().indexOf("set");
            this.updateColumns = string.substring(i+3,j).trim().split(",");
            this.condition = string.substring(j+5,string.length()).trim();
            int k;
            conditionColumns =new String[2];
            for(k=0;k<condition.length();k++){
                if(Util.isDigit(condition.charAt(k))||Util.isLetter(condition.charAt(k))||condition.charAt(k)=='_'||condition.charAt(k)==' '){
                    continue;
                }else{
                    break;
                }
            }
            conditionColumns[0] = condition.substring(0,k).trim();
            int t;
            for(t=k;t<condition.length();t++){
                if(condition.charAt(t)=='>'||condition.charAt(t)=='<'||condition.charAt(t)=='='||condition.charAt(t)=='!'){
                    continue;
                }else {
                    break;
                }
            }
            this.operator = condition.substring(k,t).trim();
            conditionColumns[1] = condition.substring(t,condition.length()).trim();
            columnNames = new String[updateColumns.length];
            columnValues = new String[updateColumns.length];

            for(k=0;k<updateColumns.length;k++){
                int index = updateColumns[k].indexOf("=");
                columnNames[k] = updateColumns[k].substring(0,index).trim();
                columnValues[k] = updateColumns[k].substring(index+1,updateColumns[k].length()).trim();
            }
            return true;
        }
        return false;
    }
}

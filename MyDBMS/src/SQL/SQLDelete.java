package SQL;


import myDatabase.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class SQLDelete extends SQL {
    private String tableName;
    private String conditionColumn;
    private String conditionValue;
    private String operator;
    private int  colNum;
    private List<Integer> lineNum = new ArrayList<>();
    public SQLDelete(int SQLType,String string){
        super(SQLType);
        if(isSQLLegal(string)){
            if(userManager.getCurrentDatabase()!=null){
                if(userManager.isTableExist(tableName)){
                     userManager.setCurrentTable(tableName);
                     if(userManager.isColumnExist(conditionColumn)){
                         colNum = Util.getColNum(conditionColumn,tableName);
                         lineNum = Util.conditonHandle(tableName,conditionColumn,conditionValue,operator);
                         Table table  =userManager.getTable(tableName);
                         Collections.reverse(lineNum);
                         for(int i:lineNum){
                             table.getLines().remove(i);
                         }
                         cacheOutput = "删除数据成功";
                         System.out.println(cacheOutput);
                     }else{
                         cacheOutput = "条件字段不存在";
                         System.out.println(cacheOutput);
                     }
                }else{
                    cacheOutput ="你所要删除内容的表不存在";
                    System.out.println(cacheOutput);
                }
            }else{
                cacheOutput = "你还没有使用数据库，无法删除表的内容";
                System.out.println(cacheOutput);
            }
        }else{
            cacheOutput = "你所输入的SQL语句不合法";
            System.out.println(cacheOutput);
        }
    }
    @Override
    public boolean isSQLLegal(String string) {
        int count = Util.CharacterCount(string,' ');
        if(count>=4){
            this.tableName = Util.getThirdWord(string);
            int i = string.indexOf("where");
            String condition = string.substring(i+5).trim();
            for(i=0;i<condition.length();i++){
                if(Util.isDigit(condition.charAt(i))||Util.isLetter(condition.charAt(i))||condition.charAt(i)=='_'){
                    continue;
                }else{
                    break;
                }
            }
            this.conditionColumn = condition.substring(0,i);
            int j;
            for(j=i;j<condition.length();j++){
                if(Util.isDigit(condition.charAt(j))||Util.isLetter(condition.charAt(j))||condition.charAt(i)=='_'){
                    break;
                }
            }
            this.operator = condition.substring(i,j).trim();
            this.conditionValue = condition.substring(j,condition.length());
            return true;
        }
        return false;
    }
}

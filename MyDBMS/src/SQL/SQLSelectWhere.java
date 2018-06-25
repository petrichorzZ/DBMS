package SQL;

import myDatabase.Line;
import myDatabase.Table;

import java.util.ArrayList;
import java.util.List;

import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class SQLSelectWhere extends SQL {
    private String columnName;
    private String columnValue;
    private String operator;
    private String tableName;
    private List<Integer> lineNum = new ArrayList<>();
    public SQLSelectWhere(int SQLType,String string){
        super(SQLType);
        if(isSQLLegal(string)){
            if(userManager.getCurrentDatabase()!=null){
                if(userManager.isTableExist(tableName)){
                    userManager.setCurrentTable(tableName);
                    if(userManager.isColumnExist(columnName)){
                        lineNum = Util.conditonHandle(tableName,columnName,columnValue,operator);
                        for(int i=0;i<lineNum.size();i++){
                            Line line = userManager.getTable(tableName).getLines().get(lineNum.get(i));
                            cacheOutput +=line.toString()+"\r\n";
                            System.out.println(line.toString());
                        }

                    }else{
                        cacheOutput = "你所要查找的条件字段不存在";
                        System.out.println("你所要查找的条件字段不存在");
                    }
                }else{
                    cacheOutput = "你所要查看的表不存在";
                    System.out.println("你所要查看的表不存在");
                }
            }else{
                cacheOutput = "你还没有使用数据库，无法查看表的内容";
                System.out.println("你还没有使用数据库，无法查看表的内容");
            }
        }else{
            cacheOutput = "你所输入的SQL语句不合法";
            System.out.println("你所输入的SQL语句不合法");
        }
    }

    @Override
    public boolean isSQLLegal(String string) {
        int count = Util.CharacterCount(string ,' ');
        if(count>=5){
            this.tableName = Util.getForthWord(string);
            int index = string.indexOf("where");
            String key =  string.substring(index+5,string.length()).trim();
            int i;
            for(i=0;i<key.length();i++){
                if(Util.isDigit(key.charAt(i))||Util.isLetter(key.charAt(i))||key.charAt(i)=='_'){
                    continue;
                }else{
                    break;
                }
            }
            this.columnName = key.substring(0,i);
            int j;
            for(j=i;j<key.length();j++){
                if(Util.isDigit(key.charAt(j))||Util.isLetter(key.charAt(j))||key.charAt(i)=='_'){
                    break;
                }
            }
            this.operator = key.substring(i,j).trim();
            this.columnValue = key.substring(j,key.length());
            return true;
        }
        return false;
    }
}

package SQL;

import myDatabase.Column;

import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class SQLDescTable extends SQL {
    public SQLDescTable(int SQLType,String string){
        if(isSQLLegal(string)){
            if(userManager.getCurrentUser()!=null){
                String tableName = Util.getLastFirstWord(string);
                if(userManager.isTableExist(tableName)){
                    cacheOutput +="字段名称"+"\t"+"字段类型"+"\t"+"字段属性"+"\r\n";
                    System.out.println("字段名称"+"\t"+"字段类型"+"\t"+"字段属性");
                    for(Column column:userManager.getTable(tableName).getColumns()){
                        cacheOutput+=column.getName()+"\t"+column.getDataType()+"\t"+column.toString()+"\r\n";
                        System.out.printf("%-12s%-12s%s",column.getName(),column.getDataType(),column.toString());
                    }
                }else{
                    cacheOutput = "表 "+tableName+" 不存在，不能查看表的信息";
                    System.out.println("表 "+tableName+" 不存在，不能查看表的信息");
                }
            }else{
                cacheOutput = "你还没有使用任何数据库，无法查看表的信息";
                System.out.println("你还没有使用任何数据库，无法查看表的信息");
            }
        }else {
            cacheOutput = "你所输入的SQL语句不合法";
            System.out.println("你所输入的SQL语句不合法");
        }
    }
    @Override
    public boolean isSQLLegal(String string) {
        int count = Util.CharacterCount(string,' ');
        if(count==2){
            return true;
        }
        return false;
    }
}

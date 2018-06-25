package SQL;

import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class SQLUseTable extends SQL {

    public SQLUseTable(int SQLType,String string){
        super(SQLType);
        if(isSQLLegal(string)){
            String tableName = Util.getThirdWord(string);
           if(userManager.getCurrentDatabase()!=null){
               if(userManager.isTableExist(tableName)){
                   userManager.setCurrentTable(tableName);
                   cacheOutput = "正在使用表 "+tableName;
                   System.out.println("正在使用表 "+tableName);
               }else {
                   cacheOutput = "表 "+tableName+" 不存在，无法使用";
                   System.out.println("表 "+tableName+" 不存在，无法使用");
               }
           }else{
               cacheOutput = "你还没有使用数据库，无法使用表";
               System.out.println("你还没有使用数据库，无法使用表");
           }
        }else {
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

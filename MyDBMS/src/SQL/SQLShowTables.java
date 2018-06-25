package SQL;

import myDatabase.Table;

import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class SQLShowTables extends SQL {
    public SQLShowTables(int SQLType,String string){
        super(SQLType);
        if(isSQLLegal(string)){
            if(userManager.getCurrentDatabase()!=null){
                if(userManager.getDatabase(userManager.getCurrentDatabase()).getTables().size()==0){
                    cacheOutput = "数据库 "+userManager.getCurrentDatabase()+" 当前还没有表";
                    System.out.println("数据库 "+userManager.getCurrentDatabase()+" 当前还没有表");
                }else {
                    cacheOutput = "数据库 "+userManager.getCurrentDatabase()+" 当前的所有表如下：";
                    System.out.println("数据库 "+userManager.getCurrentDatabase()+" 当前的所有表如下：");
                    for(Table table:userManager.getDatabase(userManager.getCurrentDatabase()).getTables()){
                        cacheOutput +="\r\n"+table.getName();
                        System.out.println(table.getName());
                    }
                }
            }else {
                cacheOutput = "你还没有使用数据库，无法展示所有的表";
                System.out.println("你还没有使用数据库，无法展示所有的表");
            }
        }else {
            cacheOutput = "你所输入的SQL语句不合法";
            System.out.println("你所输入的SQL语句不合法");
        }
    }
    @Override
    public boolean isSQLLegal(String string) {
        int count = Util.CharacterCount(string,' ');
        if(count==1){
            return true;
        }
        return false;
    }
}

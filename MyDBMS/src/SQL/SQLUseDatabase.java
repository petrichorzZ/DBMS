package SQL;

import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class SQLUseDatabase extends SQL {

    public SQLUseDatabase(int SQLType,String string){
        super(SQLType);
        if(isSQLLegal(string)){
            String dbName = Util.getThirdWord(string);
            if(userManager.isDatabaseExist(dbName)){
                userManager.setCurrentDatabase(dbName);
                cacheOutput = "正在使用数据库 "+dbName;
                System.out.println("正在使用数据库 "+dbName);
            }else {
                cacheOutput = "数据库 "+dbName+" 不存在，无法使用";
                System.out.println("数据库 "+dbName+" 不存在，无法使用");
            }
        }else{
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

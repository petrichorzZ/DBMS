package SQL;

import myDatabase.Database;

import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class SQLCreateDatabase extends SQL {
    public String name;
    public SQLCreateDatabase(int SQLType,String string){
        super(SQLType);
        if(isSQLLegal(string)){
            String name = Util.getThirdWord(string);
            CreateDatabase(name);
        }else {
            cacheOutput =  "你所输入的SQL语句不合法";
            System.out.println("你所输入的SQL语句不合法");
        }
    }

    private void CreateDatabase(String name){
        if(userManager.isDatabaseExist(name)){
            cacheOutput = "数据库 "+name+" 已存在，不能创建";
            System.out.println("数据库 "+name+" 已存在，不能创建");
        }else{
            Database database = new Database(name);
            userManager.addDatabase(database);
            cacheOutput = "创建数据库成功";
            System.out.println("创建数据库成功");
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

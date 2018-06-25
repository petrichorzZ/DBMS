package SQL;

import myDatabase.Database;

import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class SQLShowDatabases extends SQL{
    public SQLShowDatabases(int SQLType,String string){
        super(SQLType);
        if(isSQLLegal(string)){
           if(userManager.getCurrentUser()!=null){
               if(userManager.getUser(userManager.getCurrentUser()).getDatabases().size()==0){
                   cacheOutput = "系统当前没有数据库";
                   System.out.println("系统当前没有数据库");
               }else {
                   cacheOutput = "系统当前的数据库如下：";
                   System.out.println("系统当前的数据库如下：");
                   for(Database database:userManager.getUser(userManager.getCurrentUser()).getDatabases()){
                       cacheOutput+="\r\n"+database.getName();
                       System.out.println(database.getName());
                   }
               }
           }else {
               cacheOutput = "你还没有登陆";
               System.out.println("你还没有登陆");
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

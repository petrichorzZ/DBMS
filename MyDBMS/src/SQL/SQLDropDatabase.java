package SQL;

import java.util.Scanner;

import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class SQLDropDatabase extends SQL {
    public SQLDropDatabase(int SQLType,String string){
        super(SQLType);
        if(isSQLLegal(string)){
            String dbName = Util.getThirdWord(string);
            if(userManager.isDatabaseExist(dbName)){
//                cacheOutput = "删除数据库后，其中的数据将会被彻底删除，确认是否是删除?（Yes/No）";
//                System.out.println("删除数据库后，其中的数据将会被彻底删除，确认是否是删除?（Yes/No）");
//                Scanner scanner = new Scanner(System.in);
//                String input = scanner.nextLine();
//                if(input.equalsIgnoreCase("yse")){
                    userManager.deleteDatabase(dbName);
                    if(userManager.getCurrentDatabase()==dbName){
                        userManager.setCurrentDatabase(null);
                    }
                    cacheOutput = "删除数据库成功";
                    System.out.println("删除数据库成功");
//                }
            }else {
                cacheOutput = "你所要删除的数据库不存在";
                System.out.println("你所要删除的数据库不存在");
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

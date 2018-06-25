package SQL;

import java.util.Scanner;

import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class SQLDropTable extends SQL {
    public SQLDropTable(int SQLType,String string){
        if(isSQLLegal(string)){
            if(userManager.getCurrentDatabase()!=null){
                String tableName = Util.getThirdWord(string);
                if(userManager.isTableExist(tableName)){
//                    cacheOutput  = "删除表后，其中的数据将会被彻底删除，确认是否删除?（Yes/No）";
//                    System.out.println("删除表后，其中的数据将会被彻底删除，确认是否删除?（Yes/No）");
//                    Scanner scanner = new Scanner(System.in);
//                    String input = scanner.nextLine();
//                    if(input.equalsIgnoreCase("yse")) {
                        userManager.deleteTable(tableName);
                        if (userManager.getCurrentTable() == tableName) {
                            userManager.setCurrentTable(null);
                        }
                        cacheOutput = "删除表成功";
                        System.out.println("删除表成功");
//                    }
                }else {
                    cacheOutput = "你所要删除的表不存在，无法删除";
                    System.out.println("你所要删除的表不存在，无法删除");
                }
            }else {
                cacheOutput = "你还没有使用数据库，无法删除表";
                System.out.println("你还没有使用数据库，无法删除表");
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

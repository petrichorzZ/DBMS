package SQL;

import myDatabase.Line;

import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class SQLSelect extends SQL {
    public SQLSelect(int SQLType,String string){
        super(SQLType);
        if(isSQLLegal(string)){
            if(userManager.getCurrentDatabase()!=null){
                String tableName = Util.getForthWord(string);
                userManager.setCurrentTable(tableName);
                if(userManager.isTableExist(tableName)){
                    for(Line line:userManager.getTable(tableName).getLines()){
                        cacheOutput +=line.toString()+"\r\n";
                        System.out.println(line.toString());
                    }
                }else {
                    cacheOutput = "你所要查看的表不存在";
                    System.out.println("你所要查看的表不存在");
                }
            }else {
                cacheOutput = "还没有使用数据库，不能查看表的内容";
                System.out.println("还没有使用数据库，不能查看表的内容");
            }
        }else {
            cacheOutput = "你所输入的SQL语句不合法";
            System.out.println("你所输入的SQL语句不合法");
        }
    }
    @Override
    public boolean isSQLLegal(String string) {
        int count = Util.CharacterCount(string,' ');
        if(count == 3){
            return true;
        }
        return false;
    }
}

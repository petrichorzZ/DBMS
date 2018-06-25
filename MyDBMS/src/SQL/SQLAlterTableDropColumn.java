package SQL;

import myDatabase.Line;
import myDatabase.Table;

import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class SQLAlterTableDropColumn extends SQL {
    String columnName;
    public SQLAlterTableDropColumn(int SQLType,String string){
        if(isSQLLegal(string)){
            if(userManager.getCurrentDatabase()!=null){
                String tableName = Util.getThirdWord(string);
                if(userManager.isTableExist(tableName)){
                    if(userManager.getCurrentTable()!=null&&userManager.getCurrentTable().equals(tableName)){

                        if(userManager.isColumnExist(columnName)){

                            int colNum = Util.getColNum(columnName,tableName);
                            System.out.println(colNum);
                            Table table = userManager.getTable(tableName);
                            Line line;
                            Line l;
                            for(int i=0;i<table.getLines().size();i++){
                                line = new Line();
                                l= table.getLines().get(i);
                                for(int j=0;j<l.getDatas().size();j++){
                                    if(j!=colNum){
                                        line.addData(l.getDatas().get(j));
                                    }
                                }
                                table.getLines().set(i,line);
                            }
                            userManager.deleteColumn(columnName);
                            cacheOutput = "删除字段成功";
                            System.out.println("删除字段成功");
                        }else {
                            cacheOutput = "字段 "+columnName+" 不存在，无法删除";
                            System.out.println("字段 "+columnName+" 不存在，无法删除");
                        }
                    }else {
                        cacheOutput = "你没有使用表 "+tableName+" ,无法修改";
                        System.out.println("你没有使用表 "+tableName+" ,无法修改");
                    }
                }else {
                    cacheOutput = "表 "+tableName+" 不存在，无法修改";
                    System.out.println("表 "+tableName+" 不存在，无法修改");
                }
            }else{
                cacheOutput = "你还没有使用数据库，不能修改表";
                System.out.println("你还没有使用数据库，不能修改表");
            }
        }else {
            cacheOutput = "你所输入的SQL语句不合法";
            System.out.println("你所输入的SQL语句不合法");
        }
    }
    @Override
    public boolean isSQLLegal(String string) {
        int i = string.lastIndexOf(" ");
        this.columnName = string.substring(i+1,string.length());
        int count = Util.CharacterCount(string,' ');
        if(count==5){
            return true;
        }
        return false;
    }
}

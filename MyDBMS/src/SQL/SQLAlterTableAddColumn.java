package SQL;

import myDatabase.Column;
import myDatabase.EnumDataType;
import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class SQLAlterTableAddColumn extends SQL {
    private String dataType;
    private String columnName;
    public SQLAlterTableAddColumn(int SQLType,String string){
        super(SQLType);
        if(isSQLLegal(string)){
            if(userManager.getCurrentDatabase()!=null){
                String tableName = Util.getThirdWord(string);
                if(userManager.isTableExist(tableName)){
                    if(userManager.getCurrentTable()!=null&&userManager.getCurrentTable().equals(tableName)){
                        if(!userManager.isColumnExist(columnName)){
                            Column column = new Column(columnName);
                            column.setDataType(dataType);
                            userManager.addColumn(column);
                            cacheOutput = "添加字段成功";
                            System.out.println("添加字段成功");

                        }else {
                            cacheOutput = "字段 "+columnName+" 已存在，不能重复添加";
                            System.out.println("字段 "+columnName+" 已存在，不能重复添加");
                        }
                    }else {
                        cacheOutput = "你没有使用表 "+tableName+" ,无法修改";
                        System.out.println("你没有使用表 "+tableName+" ,无法修改");
                    }
                }else {
                    cacheOutput = "表 "+tableName+" 不存在，无法修改";
                    System.out.println("表 "+tableName+" 不存在，无法修改");
                }
            }else {
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
        int j = string.lastIndexOf(" ",i-1);
        String colName = string.substring(j+1,i);
        this.columnName = colName;
        String dataType = string.substring(i+1,string.length());
        boolean isDataType = false;
        for(EnumDataType enumDataType:EnumDataType.values()){
            if(enumDataType.toString().equalsIgnoreCase(dataType)){
                this.dataType = dataType;
                isDataType = true;
                break;
            }
        }
        int count = Util.CharacterCount(string,' ');
        if(count == 6 && isDataType){
            return true;
        }
        return false;
    }
}

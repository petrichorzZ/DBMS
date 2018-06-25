package SQL;

import myDatabase.*;

import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class SQLCreateTable extends SQL {
    private String tableName;
    private String []columns;
    private String [][]attributes;
    private String []dataTypes;
    public SQLCreateTable(int SQLType,String string){
        super(SQLType);
        if(isSQLLegal(string)){
            if(userManager.getCurrentDatabase()!=null){
                if(!userManager.isTableExist(this.tableName)){
                    Table table = new Table(tableName);
                    for(int i=0;i<columns.length;i++){
                        Column column = new Column(columns[i]);
                        column.setDataType(this.dataTypes[i]);
                        for(int j=0;j<attributes[i].length;j++){
                            Attribute attribute = new Attribute(attributes[i][j]);
                            column.addAttribute(attribute);
                        }
                        table.addColumn(column);
                    }
                    userManager.addTable(table);
                    cacheOutput = "创建表成功";
                    System.out.println("创建表成功");
                }else {
                    cacheOutput = "表 "+tableName+" 已存在，不能再次创建";
                    System.out.println("表 "+tableName+" 已存在，不能再次创建");
                }
            }else{
                cacheOutput = "你还没有使用任何数据库，无法创建表";
                System.out.println("你还没有使用任何数据库，无法创建表");
            }
        }else {
            cacheOutput = "你所输入的SQL语句不合法";
            System.out.println("你所输入的SQL语句不合法");
        }

    }

    @Override
    public boolean isSQLLegal(String string) {
        int i = string.indexOf("(");
        if(i==-1){
            return false;
        }
        int j = string.lastIndexOf(")");
        if(j==-1){
            return false;
        }

        String sub = string.substring(0,i);
        sub = sub.trim();
        int count = Util.CharacterCount(sub,' ');
        if(count!=2){
            return false;
        }
        this.tableName = Util.getThirdWord(sub);
        String str = string.substring(i+1,j);
        str = str.trim();
        String []values = str.split(",");
        String []keys;
        this.attributes = new String[values.length][];
        this.columns = new String[values.length];
        this.dataTypes = new String[values.length];
        for(int t=0;t<values.length;t++){
            values[t] = values[t].trim();
            keys  = values[t].split(" ");
            if(keys.length<=1) {
                return false;
            }else {
                boolean  isDataType = false;
                for(EnumDataType dataType:EnumDataType.values()){
                   if(dataType.toString().equals(keys[1].toUpperCase())){
                       isDataType = true;
                       dataTypes[t] = keys[1];
                       break;
                   }
               }
               if(isDataType){
                   columns[t] = keys[0];
                   attributes[t] = new String[keys.length-2];
                    for(int k=2;k<keys.length;k++){
                        boolean isDataAttribute = false;
                        for(EnumDataAttribute dataAttribute:EnumDataAttribute.values()){
                            if(dataAttribute.toString().equals(keys[k].toUpperCase())){
                                isDataAttribute = true;
                                break;
                            }
                        }
                        if(isDataAttribute){
                            attributes[t][k-2]=keys[k];
                            continue;
                        }else {
                            return false;
                        }
                    }
               }else {
                   return false;
               }
            }
        }
        return true;
    }
}


